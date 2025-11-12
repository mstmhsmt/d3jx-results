package com.google.android.exoplayer2.mediacodec;
import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.CodecException;
import android.media.MediaCodec.CryptoException;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSession.DrmSessionException;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract renderer that uses {@link MediaCodec} to decode samples for rendering.
 */
@TargetApi(value = 16) public abstract class MediaCodecRenderer extends BaseRenderer {
  public static class DecoderInitializationException extends Exception {
    private static final int CUSTOM_ERROR_CODE_BASE = -50000;

    private static final int NO_SUITABLE_DECODER_ERROR = CUSTOM_ERROR_CODE_BASE + 1;

    private static final int DECODER_QUERY_ERROR = CUSTOM_ERROR_CODE_BASE + 2;

    /**
     * The mime type for which a decoder was being initialized.
     */
    public final String mimeType;

    /**
     * Whether it was required that the decoder support a secure output path.
     */
    public final boolean secureDecoderRequired;

    /**
     * The name of the decoder that failed to initialize. Null if no suitable decoder was found.
     */
    public final String decoderName;

    /**
     * An optional developer-readable diagnostic information string. May be null.
     */
    public final String diagnosticInfo;

    public DecoderInitializationException(Format format, Throwable cause, boolean secureDecoderRequired, int errorCode) {
      super("Decoder init failed: [" + errorCode + "], " + format, cause);
      this.mimeType = format.sampleMimeType;
      this.secureDecoderRequired = secureDecoderRequired;
      this.decoderName = null;
      this.diagnosticInfo = buildCustomDiagnosticInfo(errorCode);
    }

    public DecoderInitializationException(Format format, Throwable cause, boolean secureDecoderRequired, String decoderName) {
      super("Decoder init failed: " + decoderName + ", " + format, cause);
      this.mimeType = format.sampleMimeType;
      this.secureDecoderRequired = secureDecoderRequired;
      this.decoderName = decoderName;
      this.diagnosticInfo = Util.SDK_INT >= 21 ? getDiagnosticInfoV21(cause) : null;
    }

    @TargetApi(value = 21) private static String getDiagnosticInfoV21(Throwable cause) {
      if (cause instanceof CodecException) {
        return ((CodecException) cause).getDiagnosticInfo();
      }
      return null;
    }

    private static String buildCustomDiagnosticInfo(int errorCode) {
      String sign = errorCode < 0 ? "neg_" : "";
      return "com.google.android.exoplayer.MediaCodecTrackRenderer_" + sign + Math.abs(errorCode);
    }
  }

  private static final String TAG = "MediaCodecRenderer";

  /**
   * If the {@link MediaCodec} is hotswapped (i.e. replaced during playback), this is the period of
   * time during which {@link #isReady()} will report true regardless of whether the new codec has
   * output frames that are ready to be rendered.
   * <p>
   * This allows codec hotswapping to be performed seamlessly, without interrupting the playback of
   * other renderers, provided the new codec is able to decode some frames within this time period.
   */
  private static final long MAX_CODEC_HOTSWAP_TIME_MS = 1000;

  /**
   * There is no pending adaptive reconfiguration work.
   */
  private static final int RECONFIGURATION_STATE_NONE = 0;

  /**
   * Codec configuration data needs to be written into the next buffer.
   */
  private static final int RECONFIGURATION_STATE_WRITE_PENDING = 1;

  /**
   * Codec configuration data has been written into the next buffer, but that buffer still needs to
   * be returned to the codec.
   */
  private static final int RECONFIGURATION_STATE_QUEUE_PENDING = 2;

  /**
   * The codec does not need to be re-initialized.
   */
  private static final int REINITIALIZATION_STATE_NONE = 0;

  /**
   * The input format has changed in a way that requires the codec to be re-initialized, but we
   * haven't yet signaled an end of stream to the existing codec. We need to do so in order to
   * ensure that it outputs any remaining buffers before we release it.
   */
  private static final int REINITIALIZATION_STATE_SIGNAL_END_OF_STREAM = 1;

  /**
   * The input format has changed in a way that requires the codec to be re-initialized, and we've
   * signaled an end of stream to the existing codec. We're waiting for the codec to output an end
   * of stream signal to indicate that it has output any remaining buffers before we release it.
   */
  private static final int REINITIALIZATION_STATE_WAIT_END_OF_STREAM = 2;

  /**
   * H.264/AVC buffer to queue when using the adaptation workaround (see
   * {@link #codecNeedsAdaptationWorkaround(String)}. Consists of three NAL units with start codes:
   * Baseline sequence/picture parameter sets and a 32 * 32 pixel IDR slice. This stream can be
   * queued to force a resolution change when adapting to a new format.
   */
  private static final byte[] ADAPTATION_WORKAROUND_BUFFER = Util.getBytesFromHexString("0000016742C00BDA259000000168CE0F13200000016588840DCE7118A0002FBF1C31C3275D78");

  private static final int ADAPTATION_WORKAROUND_SLICE_WIDTH_HEIGHT = 32;

  private final MediaCodecSelector mediaCodecSelector;

  private final DrmSessionManager<FrameworkMediaCrypto> drmSessionManager;

  private final boolean playClearSamplesWithoutKeys;

  private final DecoderInputBuffer buffer;

  private final DecoderInputBuffer flagsOnlyBuffer;

  private final FormatHolder formatHolder;

  private final List<Long> decodeOnlyPresentationTimestamps;

  private final MediaCodec.BufferInfo outputBufferInfo;

  private Format format;

  private DrmSession<FrameworkMediaCrypto> drmSession;

  private MediaCodec codec;

  private DrmSession<FrameworkMediaCrypto> pendingDrmSession;

  private MediaCodecInfo codecInfo;

  private boolean codecNeedsDiscardToSpsWorkaround;

  private boolean codecNeedsFlushWorkaround;

  private boolean codecNeedsAdaptationWorkaround;

  private boolean codecNeedsEosPropagationWorkaround;

  private boolean codecNeedsEosFlushWorkaround;

  private boolean codecNeedsEosOutputExceptionWorkaround;

  private boolean codecNeedsMonoChannelCountWorkaround;

  private boolean codecNeedsAdaptationWorkaroundBuffer;

  private boolean shouldSkipAdaptationWorkaroundOutputBuffer;

  private ByteBuffer[] inputBuffers;

  private ByteBuffer[] outputBuffers;

  private long codecHotswapDeadlineMs;

  private int inputIndex;

  private int outputIndex;

  private boolean shouldSkipOutputBuffer;

  private boolean codecReconfigured;

  private int codecReconfigurationState;

  private int codecReinitializationState;

  private boolean codecReceivedBuffers;

  private boolean codecReceivedEos;

  private boolean inputStreamEnded;

  private boolean outputStreamEnded;

  private boolean waitingForKeys;

  private boolean waitingForFirstSyncFrame;

  protected DecoderCounters decoderCounters;

  /**
   * @param trackType The track type that the renderer handles. One of the {@code C.TRACK_TYPE_*}
   *     constants defined in {@link C}.
   * @param mediaCodecSelector A decoder selector.
   * @param drmSessionManager For use with encrypted media. May be null if support for encrypted
   *     media is not required.
   * @param playClearSamplesWithoutKeys Encrypted media may contain clear (un-encrypted) regions.
   *     For example a media file may start with a short clear region so as to allow playback to
   *     begin in parallel with key acquisition. This parameter specifies whether the renderer is
   *     permitted to play clear regions of encrypted media files before {@code drmSessionManager}
   *     has obtained the keys necessary to decrypt encrypted regions of the media.
   */
  public MediaCodecRenderer(int trackType, MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys) {
    super(trackType);
    Assertions.checkState(Util.SDK_INT >= 16);
    this.mediaCodecSelector = Assertions.checkNotNull(mediaCodecSelector);
    this.drmSessionManager = drmSessionManager;
    this.playClearSamplesWithoutKeys = playClearSamplesWithoutKeys;
    buffer = new DecoderInputBuffer(DecoderInputBuffer.BUFFER_REPLACEMENT_MODE_DISABLED);
    flagsOnlyBuffer = DecoderInputBuffer.newFlagsOnlyInstance();
    formatHolder = new FormatHolder();
    decodeOnlyPresentationTimestamps = new ArrayList<>();
    outputBufferInfo = new MediaCodec.BufferInfo();
    codecReconfigurationState = RECONFIGURATION_STATE_NONE;
    codecReinitializationState = REINITIALIZATION_STATE_NONE;
  }

  @Override public final int supportsMixedMimeTypeAdaptation() {
    return ADAPTIVE_NOT_SEAMLESS;
  }

  @Override public final int supportsFormat(Format format) throws ExoPlaybackException {
    try {
      int formatSupport = supportsFormat(mediaCodecSelector, format);
      if ((formatSupport & FORMAT_SUPPORT_MASK) > FORMAT_UNSUPPORTED_DRM && !isDrmSchemeSupported(drmSessionManager, format.drmInitData)) {
        formatSupport = (formatSupport & ~FORMAT_SUPPORT_MASK) | FORMAT_UNSUPPORTED_DRM;
      }
      return formatSupport;
    } catch (DecoderQueryException e) {
      throw ExoPlaybackException.createForRenderer(e, getIndex());
    }
  }

  /**
   * Returns the extent to which the renderer is capable of supporting a given format.
   *
   * @param mediaCodecSelector The decoder selector.
   * @param format The format.
   * @return The extent to which the renderer is capable of supporting the given format. See
   *     {@link #supportsFormat(Format)} for more detail.
   * @throws DecoderQueryException If there was an error querying decoders.
   */
  protected abstract int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) throws DecoderQueryException;

  /**
   * Returns a {@link MediaCodecInfo} for a given format.
   *
   * @param mediaCodecSelector The decoder selector.
   * @param format The format for which a decoder is required.
   * @param requiresSecureDecoder Whether a secure decoder is required.
   * @return A {@link MediaCodecInfo} describing the decoder to instantiate, or null if no
   *     suitable decoder exists.
   * @throws DecoderQueryException Thrown if there was an error querying decoders.
   */
  protected MediaCodecInfo getDecoderInfo(MediaCodecSelector mediaCodecSelector, Format format, boolean requiresSecureDecoder) throws DecoderQueryException {
    return mediaCodecSelector.getDecoderInfo(format.sampleMimeType, requiresSecureDecoder);
  }

  /**
   * Configures a newly created {@link MediaCodec}.
   *
   * @param codecInfo Information about the {@link MediaCodec} being configured.
   * @param codec The {@link MediaCodec} to configure.
   * @param format The format for which the codec is being configured.
   * @param crypto For drm protected playbacks, a {@link MediaCrypto} to use for decryption.
   * @throws DecoderQueryException If an error occurs querying {@code codecInfo}.
   */
  protected abstract void configureCodec(MediaCodecInfo codecInfo, MediaCodec codec, Format format, MediaCrypto crypto) throws DecoderQueryException;

  @SuppressWarnings(value = { "deprecation" }) protected final void maybeInitCodec() throws ExoPlaybackException {
    if (codec != null || format == null) {
      return;
    }
    drmSession = pendingDrmSession;
    String mimeType = format.sampleMimeType;
    MediaCrypto wrappedMediaCrypto = null;
    boolean drmSessionRequiresSecureDecoder = false;
    if (drmSession != null) {
      FrameworkMediaCrypto mediaCrypto = drmSession.getMediaCrypto();
      if (mediaCrypto == null) {
        DrmSessionException drmError = drmSession.getError();
        if (drmError != null) {
          throw ExoPlaybackException.createForRenderer(drmError, getIndex());
        }
        return;
      }
      wrappedMediaCrypto = mediaCrypto.getWrappedMediaCrypto();
      drmSessionRequiresSecureDecoder = mediaCrypto.requiresSecureDecoderComponent(mimeType);
    }
    if (codecInfo == null) {
      try {
        codecInfo = getDecoderInfo(mediaCodecSelector, format, drmSessionRequiresSecureDecoder);
        if (codecInfo == null && drmSessionRequiresSecureDecoder) {
          codecInfo = getDecoderInfo(mediaCodecSelector, format, false);
          if (codecInfo != null) {
            Log.w(TAG, "Drm session requires secure decoder for " + mimeType + ", but " + "no secure decoder available. Trying to proceed with " + codecInfo.name + ".");
          }
        }
      } catch (DecoderQueryException e) {
        throwDecoderInitError(new DecoderInitializationException(format, e, drmSessionRequiresSecureDecoder, DecoderInitializationException.DECODER_QUERY_ERROR));
      }
      if (codecInfo == null) {
        throwDecoderInitError(new DecoderInitializationException(format, null, drmSessionRequiresSecureDecoder, DecoderInitializationException.NO_SUITABLE_DECODER_ERROR));
      }
    }
    if (!shouldInitCodec(codecInfo)) {
      return;
    }
    String codecName = codecInfo.name;


    codecNeedsDiscardToSpsWorkaround = codecNeedsDiscardToSpsWorkaround(codecName, format);
    codecNeedsFlushWorkaround = codecNeedsFlushWorkaround(codecName);
    codecNeedsAdaptationWorkaround = codecNeedsAdaptationWorkaround(codecName);
    codecNeedsEosPropagationWorkaround = codecNeedsEosPropagationWorkaround(codecName);
    codecNeedsEosFlushWorkaround = codecNeedsEosFlushWorkaround(codecName);
    codecNeedsEosOutputExceptionWorkaround = codecNeedsEosOutputExceptionWorkaround(codecName);
    codecNeedsMonoChannelCountWorkaround = codecNeedsMonoChannelCountWorkaround(codecName, format);
    try {
      long codecInitializingTimestamp = SystemClock.elapsedRealtime();
      TraceUtil.beginSection("createCodec:" + codecName);
      codec = MediaCodec.createByCodecName(codecName);
      TraceUtil.endSection();
      TraceUtil.beginSection("configureCodec");
      configureCodec(codecInfo, codec, format, wrappedMediaCrypto);
      TraceUtil.endSection();
      TraceUtil.beginSection("startCodec");
      codec.start();
      TraceUtil.endSection();
      long codecInitializedTimestamp = SystemClock.elapsedRealtime();
      onCodecInitialized(codecName, codecInitializedTimestamp, codecInitializedTimestamp - codecInitializingTimestamp);
      inputBuffers = codec.getInputBuffers();
      outputBuffers = codec.getOutputBuffers();
    } catch (Exception e) {
      throwDecoderInitError(new DecoderInitializationException(format, e, drmSessionRequiresSecureDecoder, codecName));
    }
    codecHotswapDeadlineMs = getState() == STATE_STARTED ? (SystemClock.elapsedRealtime() + MAX_CODEC_HOTSWAP_TIME_MS) : C.TIME_UNSET;
    inputIndex = C.INDEX_UNSET;
    outputIndex = C.INDEX_UNSET;
    waitingForFirstSyncFrame = true;
    decoderCounters.decoderInitCount++;
  }

  private void throwDecoderInitError(DecoderInitializationException e) throws ExoPlaybackException {
    throw ExoPlaybackException.createForRenderer(e, getIndex());
  }

  protected boolean shouldInitCodec(MediaCodecInfo codecInfo) {
    return true;
  }

  protected final MediaCodec getCodec() {
    return codec;
  }

  protected final MediaCodecInfo getCodecInfo() {
    return codecInfo;
  }

  @Override protected void onEnabled(boolean joining) throws ExoPlaybackException {
    decoderCounters = new DecoderCounters();
  }

  @Override protected void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
    inputStreamEnded = false;
    outputStreamEnded = false;
    if (codec != null) {
      flushCodec();
    }
  }

  @Override protected void onDisabled() {
    format = null;
    try {
      releaseCodec();
    }  finally {
      try {
        if (drmSession != null) {
          drmSessionManager.releaseSession(drmSession);
        }
      }  finally {
        try {
          if (pendingDrmSession != null && pendingDrmSession != drmSession) {
            drmSessionManager.releaseSession(pendingDrmSession);
          }
        }  finally {
          drmSession = null;
          pendingDrmSession = null;
        }
      }
    }
  }

  protected void releaseCodec() {
    codecHotswapDeadlineMs = C.TIME_UNSET;
    inputIndex = C.INDEX_UNSET;
    outputIndex = C.INDEX_UNSET;
    waitingForKeys = false;
    shouldSkipOutputBuffer = false;
    decodeOnlyPresentationTimestamps.clear();
    inputBuffers = null;
    outputBuffers = null;
    codecInfo = null;
    codecReconfigured = false;
    codecReceivedBuffers = false;
    codecNeedsDiscardToSpsWorkaround = false;
    codecNeedsFlushWorkaround = false;
    codecNeedsAdaptationWorkaround = false;
    codecNeedsEosPropagationWorkaround = false;
    codecNeedsEosFlushWorkaround = false;
    codecNeedsMonoChannelCountWorkaround = false;
    codecNeedsAdaptationWorkaroundBuffer = false;
    shouldSkipAdaptationWorkaroundOutputBuffer = false;
    codecReceivedEos = false;
    codecReconfigurationState = RECONFIGURATION_STATE_NONE;
    codecReinitializationState = REINITIALIZATION_STATE_NONE;
    buffer.data = null;
    if (codec != null) {
      decoderCounters.decoderReleaseCount++;
      try {
        codec.stop();
      }  finally {
        try {
          codec.release();
        }  finally {
          codec = null;
          if (drmSession != null && pendingDrmSession != drmSession) {
            try {
              drmSessionManager.releaseSession(drmSession);
            }  finally {
              drmSession = null;
            }
          }
        }
      }
    }
  }

  @Override protected void onStarted() {
  }

  @Override protected void onStopped() {
  }

  @Override public void render(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException {
    if (outputStreamEnded) {
      renderToEndOfStream();
      return;
    }
    if (format == null) {
      flagsOnlyBuffer.clear();
      int result = readSource(formatHolder, flagsOnlyBuffer, true);
      if (result == C.RESULT_FORMAT_READ) {
        onInputFormatChanged(formatHolder.format);
      } else {
        if (result == C.RESULT_BUFFER_READ) {
          Assertions.checkState(flagsOnlyBuffer.isEndOfStream());
          inputStreamEnded = true;
          processEndOfStream();
          return;
        } else {
          return;
        }
      }
    }
    maybeInitCodec();
    if (codec != null) {
      TraceUtil.beginSection("drainAndFeed");
      while (drainOutputBuffer(positionUs, elapsedRealtimeUs)) {
      }
      while (feedInputBuffer()) {
      }
      TraceUtil.endSection();
    } else {
      skipSource(positionUs);
      flagsOnlyBuffer.clear();
      int result = readSource(formatHolder, flagsOnlyBuffer, false);
      if (result == C.RESULT_FORMAT_READ) {
        onInputFormatChanged(formatHolder.format);
      } else {
        if (result == C.RESULT_BUFFER_READ) {
          Assertions.checkState(flagsOnlyBuffer.isEndOfStream());
          inputStreamEnded = true;
          processEndOfStream();
        }
      }
    }
    decoderCounters.ensureUpdated();
  }

  protected void flushCodec() throws ExoPlaybackException {
    codecHotswapDeadlineMs = C.TIME_UNSET;
    inputIndex = C.INDEX_UNSET;
    outputIndex = C.INDEX_UNSET;
    waitingForFirstSyncFrame = true;
    waitingForKeys = false;
    shouldSkipOutputBuffer = false;
    decodeOnlyPresentationTimestamps.clear();
    codecNeedsAdaptationWorkaroundBuffer = false;
    shouldSkipAdaptationWorkaroundOutputBuffer = false;
    if (codecNeedsFlushWorkaround || (codecNeedsEosFlushWorkaround && codecReceivedEos)) {
      releaseCodec();
      maybeInitCodec();
    } else {
      if (codecReinitializationState != REINITIALIZATION_STATE_NONE) {
        releaseCodec();
        maybeInitCodec();
      } else {
        codec.flush();
        codecReceivedBuffers = false;
      }
    }
    if (codecReconfigured && format != null) {
      codecReconfigurationState = RECONFIGURATION_STATE_WRITE_PENDING;
    }
  }

  /**
   * @return Whether it may be possible to feed more input data.
   * @throws ExoPlaybackException If an error occurs feeding the input buffer.
   */
  private boolean feedInputBuffer() throws ExoPlaybackException {
    if (codec == null || codecReinitializationState == REINITIALIZATION_STATE_WAIT_END_OF_STREAM || inputStreamEnded) {
      return false;
    }
    if (inputIndex < 0) {
      inputIndex = codec.dequeueInputBuffer(0);
      if (inputIndex < 0) {
        return false;
      }
      buffer.data = inputBuffers[inputIndex];
      buffer.clear();
    }
    if (codecReinitializationState == REINITIALIZATION_STATE_SIGNAL_END_OF_STREAM) {
      if (codecNeedsEosPropagationWorkaround) {
      } else {
        codecReceivedEos = true;
        codec.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
        inputIndex = C.INDEX_UNSET;
      }
      codecReinitializationState = REINITIALIZATION_STATE_WAIT_END_OF_STREAM;
      return false;
    }
    if (codecNeedsAdaptationWorkaroundBuffer) {
      codecNeedsAdaptationWorkaroundBuffer = false;
      buffer.data.put(ADAPTATION_WORKAROUND_BUFFER);
      codec.queueInputBuffer(inputIndex, 0, ADAPTATION_WORKAROUND_BUFFER.length, 0, 0);
      inputIndex = C.INDEX_UNSET;
      codecReceivedBuffers = true;
      return true;
    }
    int result;
    int adaptiveReconfigurationBytes = 0;
    if (waitingForKeys) {
      result = C.RESULT_BUFFER_READ;
    } else {
      if (codecReconfigurationState == RECONFIGURATION_STATE_WRITE_PENDING) {
        for (int i = 0; i < format.initializationData.size(); i++) {
          byte[] data = format.initializationData.get(i);
          buffer.data.put(data);
        }
        codecReconfigurationState = RECONFIGURATION_STATE_QUEUE_PENDING;
      }
      adaptiveReconfigurationBytes = buffer.data.position();
      result = readSource(formatHolder, buffer, false);
    }
    if (result == C.RESULT_NOTHING_READ) {
      return false;
    }
    if (result == C.RESULT_FORMAT_READ) {
      if (codecReconfigurationState == RECONFIGURATION_STATE_QUEUE_PENDING) {
        buffer.clear();
        codecReconfigurationState = RECONFIGURATION_STATE_WRITE_PENDING;
      }
      onInputFormatChanged(formatHolder.format);
      return true;
    }
    if (buffer.isEndOfStream()) {
      if (codecReconfigurationState == RECONFIGURATION_STATE_QUEUE_PENDING) {
        buffer.clear();
        codecReconfigurationState = RECONFIGURATION_STATE_WRITE_PENDING;
      }
      inputStreamEnded = true;
      if (!codecReceivedBuffers) {
        processEndOfStream();
        return false;
      }
      try {
        if (codecNeedsEosPropagationWorkaround) {
        } else {
          codecReceivedEos = true;
          codec.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
          inputIndex = C.INDEX_UNSET;
        }
      } catch (CryptoException e) {
        throw ExoPlaybackException.createForRenderer(e, getIndex());
      }
      return false;
    }
    if (waitingForFirstSyncFrame && !buffer.isKeyFrame()) {
      buffer.clear();
      if (codecReconfigurationState == RECONFIGURATION_STATE_QUEUE_PENDING) {
        codecReconfigurationState = RECONFIGURATION_STATE_WRITE_PENDING;
      }
      return true;
    }
    waitingForFirstSyncFrame = false;
    boolean bufferEncrypted = buffer.isEncrypted();
    waitingForKeys = shouldWaitForKeys(bufferEncrypted);
    if (waitingForKeys) {
      return false;
    }
    if (codecNeedsDiscardToSpsWorkaround && !bufferEncrypted) {
      NalUnitUtil.discardToSps(buffer.data);
      if (buffer.data.position() == 0) {
        return true;
      }
      codecNeedsDiscardToSpsWorkaround = false;
    }
    try {
      long presentationTimeUs = buffer.timeUs;
      if (buffer.isDecodeOnly()) {
        decodeOnlyPresentationTimestamps.add(presentationTimeUs);
      }
      buffer.flip();
      onQueueInputBuffer(buffer);
      if (bufferEncrypted) {
        MediaCodec.CryptoInfo cryptoInfo = getFrameworkCryptoInfo(buffer, adaptiveReconfigurationBytes);
        codec.queueSecureInputBuffer(inputIndex, 0, cryptoInfo, presentationTimeUs, 0);
      } else {
        codec.queueInputBuffer(inputIndex, 0, buffer.data.limit(), presentationTimeUs, 0);
      }
      inputIndex = C.INDEX_UNSET;
      codecReceivedBuffers = true;
      codecReconfigurationState = RECONFIGURATION_STATE_NONE;
      decoderCounters.inputBufferCount++;
    } catch (CryptoException e) {
      throw ExoPlaybackException.createForRenderer(e, getIndex());
    }
    return true;
  }

  private static MediaCodec.CryptoInfo getFrameworkCryptoInfo(DecoderInputBuffer buffer, int adaptiveReconfigurationBytes) {
    MediaCodec.CryptoInfo cryptoInfo = buffer.cryptoInfo.getFrameworkCryptoInfoV16();
    if (adaptiveReconfigurationBytes == 0) {
      return cryptoInfo;
    }
    if (cryptoInfo.numBytesOfClearData == null) {
      cryptoInfo.numBytesOfClearData = new int[1];
    }
    cryptoInfo.numBytesOfClearData[0] += adaptiveReconfigurationBytes;
    return cryptoInfo;
  }

  private boolean shouldWaitForKeys(boolean bufferEncrypted) throws ExoPlaybackException {
    if (drmSession == null || (!bufferEncrypted && playClearSamplesWithoutKeys)) {
      return false;
    }
    @DrmSession.State int drmSessionState = drmSession.getState();
    if (drmSessionState == DrmSession.STATE_ERROR) {
      throw ExoPlaybackException.createForRenderer(drmSession.getError(), getIndex());
    }
    return drmSessionState != DrmSession.STATE_OPENED_WITH_KEYS;
  }

  /**
   * Called when a {@link MediaCodec} has been created and configured.
   * <p>
   * The default implementation is a no-op.
   *
   * @param name The name of the codec that was initialized.
   * @param initializedTimestampMs {@link SystemClock#elapsedRealtime()} when initialization
   *     finished.
   * @param initializationDurationMs The time taken to initialize the codec in milliseconds.
   */
  protected void onCodecInitialized(String name, long initializedTimestampMs, long initializationDurationMs) {
  }

  /**
   * Called when a new format is read from the upstream {@link MediaPeriod}.
   *
   * @param newFormat The new format.
   * @throws ExoPlaybackException If an error occurs reinitializing the {@link MediaCodec}.
   */
  protected void onInputFormatChanged(Format newFormat) throws ExoPlaybackException {
    Format oldFormat = format;
    format = newFormat;
    boolean drmInitDataChanged = !Util.areEqual(format.drmInitData, oldFormat == null ? null : oldFormat.drmInitData);
    if (drmInitDataChanged) {
      if (format.drmInitData != null) {
        if (drmSessionManager == null) {
          throw ExoPlaybackException.createForRenderer(new IllegalStateException("Media requires a DrmSessionManager"), getIndex());
        }
        pendingDrmSession = drmSessionManager.acquireSession(Looper.myLooper(), format.drmInitData);
        if (pendingDrmSession == drmSession) {
          drmSessionManager.releaseSession(pendingDrmSession);
        }
      } else {
        pendingDrmSession = null;
      }
    }
    if (pendingDrmSession == drmSession && codec != null && canReconfigureCodec(codec, codecInfo.adaptive, oldFormat, format)) {
      codecReconfigured = true;
      codecReconfigurationState = RECONFIGURATION_STATE_WRITE_PENDING;
      codecNeedsAdaptationWorkaroundBuffer = codecNeedsAdaptationWorkaround && format.width == oldFormat.width && format.height == oldFormat.height;
    } else {
      if (codecReceivedBuffers) {
        codecReinitializationState = REINITIALIZATION_STATE_SIGNAL_END_OF_STREAM;
      } else {
        releaseCodec();
        maybeInitCodec();
      }
    }
  }

  /**
   * Called when the output format of the {@link MediaCodec} changes.
   * <p>
   * The default implementation is a no-op.
   *
   * @param codec The {@link MediaCodec} instance.
   * @param outputFormat The new output format.
   * @throws ExoPlaybackException Thrown if an error occurs handling the new output format.
   */
  protected void onOutputFormatChanged(MediaCodec codec, MediaFormat outputFormat) throws ExoPlaybackException {
  }

  /**
   * Called immediately before an input buffer is queued into the codec.
   * <p>
   * The default implementation is a no-op.
   *
   * @param buffer The buffer to be queued.
   */
  protected void onQueueInputBuffer(DecoderInputBuffer buffer) {
  }

  /**
   * Called when an output buffer is successfully processed.
   * <p>
   * The default implementation is a no-op.
   *
   * @param presentationTimeUs The timestamp associated with the output buffer.
   */
  protected void onProcessedOutputBuffer(long presentationTimeUs) {
  }

  /**
   * Determines whether the existing {@link MediaCodec} should be reconfigured for a new format by
   * sending codec specific initialization data at the start of the next input buffer. If true is
   * returned then the {@link MediaCodec} instance will be reconfigured in this way. If false is
   * returned then the instance will be released, and a new instance will be created for the new
   * format.
   * <p>
   * The default implementation returns false.
   *
   * @param codec The existing {@link MediaCodec} instance.
   * @param codecIsAdaptive Whether the codec is adaptive.
   * @param oldFormat The format for which the existing instance is configured.
   * @param newFormat The new format.
   * @return Whether the existing instance can be reconfigured.
   */
  protected boolean canReconfigureCodec(MediaCodec codec, boolean codecIsAdaptive, Format oldFormat, Format newFormat) {
    return false;
  }

  @Override public boolean isEnded() {
    return outputStreamEnded;
  }

  @Override public boolean isReady() {
    return format != null && !waitingForKeys && (isSourceReady() || outputIndex >= 0 || (codecHotswapDeadlineMs != C.TIME_UNSET && SystemClock.elapsedRealtime() < codecHotswapDeadlineMs));
  }

  /**
   * Returns the maximum time to block whilst waiting for a decoded output buffer.
   *
   * @return The maximum time to block, in microseconds.
   */
  protected long getDequeueOutputBufferTimeoutUs() {
    return 0;
  }

  /**
   * @return Whether it may be possible to drain more output data.
   * @throws ExoPlaybackException If an error occurs draining the output buffer.
   */
  @SuppressWarnings(value = { "deprecation" }) private boolean drainOutputBuffer(long positionUs, long elapsedRealtimeUs) throws ExoPlaybackException {
    if (outputIndex < 0) {
      if (codecNeedsEosOutputExceptionWorkaround && codecReceivedEos) {
        try {
          outputIndex = codec.dequeueOutputBuffer(outputBufferInfo, getDequeueOutputBufferTimeoutUs());
        } catch (IllegalStateException e) {
          processEndOfStream();
          if (outputStreamEnded) {
            releaseCodec();
          }
          return false;
        }
      } else {
        outputIndex = codec.dequeueOutputBuffer(outputBufferInfo, getDequeueOutputBufferTimeoutUs());
      }
      if (outputIndex >= 0) {
        if (shouldSkipAdaptationWorkaroundOutputBuffer) {
          shouldSkipAdaptationWorkaroundOutputBuffer = false;
          codec.releaseOutputBuffer(outputIndex, false);
          outputIndex = C.INDEX_UNSET;
          return true;
        }
        if ((outputBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
          processEndOfStream();
          outputIndex = C.INDEX_UNSET;
          return false;
        } else {
          ByteBuffer outputBuffer = outputBuffers[outputIndex];
          if (outputBuffer != null) {
            outputBuffer.position(outputBufferInfo.offset);
            outputBuffer.limit(outputBufferInfo.offset + outputBufferInfo.size);
          }
          shouldSkipOutputBuffer = shouldSkipOutputBuffer(outputBufferInfo.presentationTimeUs);
        }
      } else {
        if (outputIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
          processOutputFormat();
          return true;
        } else {
          if (outputIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
            processOutputBuffersChanged();
            return true;
          } else {
            if (codecNeedsEosPropagationWorkaround && (inputStreamEnded || codecReinitializationState == REINITIALIZATION_STATE_WAIT_END_OF_STREAM)) {
              processEndOfStream();
            }
            return false;
          }
        }
      }
    }
    boolean processedOutputBuffer;
    if (codecNeedsEosOutputExceptionWorkaround && codecReceivedEos) {
      try {
        processedOutputBuffer = processOutputBuffer(positionUs, elapsedRealtimeUs, codec, outputBuffers[outputIndex], outputIndex, outputBufferInfo.flags, outputBufferInfo.presentationTimeUs, shouldSkipOutputBuffer);
      } catch (IllegalStateException e) {
        processEndOfStream();
        if (outputStreamEnded) {
          releaseCodec();
        }
        return false;
      }
    } else {
      processedOutputBuffer = processOutputBuffer(positionUs, elapsedRealtimeUs, codec, outputBuffers[outputIndex], outputIndex, outputBufferInfo.flags, outputBufferInfo.presentationTimeUs, shouldSkipOutputBuffer);
    }
    if (processedOutputBuffer) {
      onProcessedOutputBuffer(outputBufferInfo.presentationTimeUs);
      outputIndex = C.INDEX_UNSET;
      return true;
    }
    return false;
  }

  /**
   * Processes a new output format.
   */
  private void processOutputFormat() throws ExoPlaybackException {
    MediaFormat format = codec.getOutputFormat();
    if (codecNeedsAdaptationWorkaround && format.getInteger(MediaFormat.KEY_WIDTH) == ADAPTATION_WORKAROUND_SLICE_WIDTH_HEIGHT && format.getInteger(MediaFormat.KEY_HEIGHT) == ADAPTATION_WORKAROUND_SLICE_WIDTH_HEIGHT) {
      shouldSkipAdaptationWorkaroundOutputBuffer = true;
      return;
    }
    if (codecNeedsMonoChannelCountWorkaround) {
      format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
    }
    onOutputFormatChanged(codec, format);
  }

  /**
   * Processes a change in the output buffers.
   */
  @SuppressWarnings(value = { "deprecation" }) private void processOutputBuffersChanged() {
    outputBuffers = codec.getOutputBuffers();
  }

  /**
   * Processes an output media buffer.
   * <p>
   * When a new {@link ByteBuffer} is passed to this method its position and limit delineate the
   * data to be processed. The return value indicates whether the buffer was processed in full. If
   * true is returned then the next call to this method will receive a new buffer to be processed.
   * If false is returned then the same buffer will be passed to the next call. An implementation of
   * this method is free to modify the buffer and can assume that the buffer will not be externally
   * modified between successive calls. Hence an implementation can, for example, modify the
   * buffer's position to keep track of how much of the data it has processed.
   * <p>
   * Note that the first call to this method following a call to
   * {@link #onPositionReset(long, boolean)} will always receive a new {@link ByteBuffer} to be
   * processed.
   *
   * @param positionUs The current media time in microseconds, measured at the start of the
   *     current iteration of the rendering loop.
   * @param elapsedRealtimeUs {@link android.os.SystemClock#elapsedRealtime()} in microseconds,
   *     measured at the start of the current iteration of the rendering loop.
   * @param codec The {@link MediaCodec} instance.
   * @param buffer The output buffer to process.
   * @param bufferIndex The index of the output buffer.
   * @param bufferFlags The flags attached to the output buffer.
   * @param bufferPresentationTimeUs The presentation time of the output buffer in microseconds.
   * @param shouldSkip Whether the buffer should be skipped (i.e. not rendered).
   *
   * @return Whether the output buffer was fully processed (e.g. rendered or skipped).
   * @throws ExoPlaybackException If an error occurs processing the output buffer.
   */
  protected abstract boolean processOutputBuffer(long positionUs, long elapsedRealtimeUs, MediaCodec codec, ByteBuffer buffer, int bufferIndex, int bufferFlags, long bufferPresentationTimeUs, boolean shouldSkip) throws ExoPlaybackException;

  /**
   * Incrementally renders any remaining output.
   * <p>
   * The default implementation is a no-op.
   *
   * @throws ExoPlaybackException Thrown if an error occurs rendering remaining output.
   */
  protected void renderToEndOfStream() throws ExoPlaybackException {
  }

  /**
   * Processes an end of stream signal.
   *
   * @throws ExoPlaybackException If an error occurs processing the signal.
   */
  private void processEndOfStream() throws ExoPlaybackException {
    if (codecReinitializationState == REINITIALIZATION_STATE_WAIT_END_OF_STREAM) {
      releaseCodec();
      maybeInitCodec();
    } else {
      outputStreamEnded = true;
      renderToEndOfStream();
    }
  }

  private boolean shouldSkipOutputBuffer(long presentationTimeUs) {
    int size = decodeOnlyPresentationTimestamps.size();
    for (int i = 0; i < size; i++) {
      if (decodeOnlyPresentationTimestamps.get(i) == presentationTimeUs) {
        decodeOnlyPresentationTimestamps.remove(i);
        return true;
      }
    }
    return false;
  }

  /**
   * Returns whether the encryption scheme is supported, or true if {@code drmInitData} is null.
   *
   * @param drmSessionManager The drm session manager associated with the renderer.
   * @param drmInitData {@link DrmInitData} of the format to check for support.
   * @return Whether the encryption scheme is supported, or true if {@code drmInitData} is null.
   */
  private static boolean isDrmSchemeSupported(DrmSessionManager drmSessionManager, @Nullable DrmInitData drmInitData) {
    if (drmInitData == null) {
      return true;
    } else {
      if (drmSessionManager == null) {
        return false;
      }
    }
    return drmSessionManager.canAcquireSession(drmInitData);
  }

  /**
   * Returns whether the decoder is known to fail when flushed.
   * <p>
   * If true is returned, the renderer will work around the issue by releasing the decoder and
   * instantiating a new one rather than flushing the current instance.
   * <p>
   * See [Internal: b/8347958, b/8543366].
   *
   * @param name The name of the decoder.
   * @return True if the decoder is known to fail when flushed.
   */
  private static boolean codecNeedsFlushWorkaround(String name) {
    return Util.SDK_INT < 18 || (Util.SDK_INT == 18 && ("OMX.SEC.avc.dec".equals(name) || "OMX.SEC.avc.dec.secure".equals(name))) || (Util.SDK_INT == 19 && Util.MODEL.startsWith("SM-G800") && ("OMX.Exynos.avc.dec".equals(name) || "OMX.Exynos.avc.dec.secure".equals(name)));
  }

  /**
   * Returns whether the decoder is known to get stuck during some adaptations where the resolution
   * does not change.
   * <p>
   * If true is returned, the renderer will work around the issue by queueing and discarding a blank
   * frame at a different resolution, which resets the codec's internal state.
   * <p>
   * See [Internal: b/27807182].
   *
   * @param name The name of the decoder.
   * @return True if the decoder is known to get stuck during some adaptations.
   */
  private static boolean codecNeedsAdaptationWorkaround(String name) {
    return Util.SDK_INT < 24 && ("OMX.Nvidia.h264.decode".equals(name) || "OMX.Nvidia.h264.decode.secure".equals(name)) && ("flounder".equals(Util.DEVICE) || "flounder_lte".equals(Util.DEVICE) || "grouper".equals(Util.DEVICE) || "tilapia".equals(Util.DEVICE));
  }

  /**
   * Returns whether the decoder is an H.264/AVC decoder known to fail if NAL units are queued
   * before the codec specific data.
   * <p>
   * If true is returned, the renderer will work around the issue by discarding data up to the SPS.
   *
   * @param name The name of the decoder.
   * @param format The format used to configure the decoder.
   * @return True if the decoder is known to fail if NAL units are queued before CSD.
   */
  private static boolean codecNeedsDiscardToSpsWorkaround(String name, Format format) {
    return Util.SDK_INT < 21 && format.initializationData.isEmpty() && "OMX.MTK.VIDEO.DECODER.AVC".equals(name);
  }

  /**
   * Returns whether the decoder is known to handle the propagation of the
   * {@link MediaCodec#BUFFER_FLAG_END_OF_STREAM} flag incorrectly on the host device.
   * <p>
   * If true is returned, the renderer will work around the issue by approximating end of stream
   * behavior without relying on the flag being propagated through to an output buffer by the
   * underlying decoder.
   *
   * @param name The name of the decoder.
   * @return True if the decoder is known to handle {@link MediaCodec#BUFFER_FLAG_END_OF_STREAM}
   *     propagation incorrectly on the host device. False otherwise.
   */
  private static boolean codecNeedsEosPropagationWorkaround(String name) {
    return Util.SDK_INT <= 17 && ("OMX.rk.video_decoder.avc".equals(name) || "OMX.allwinner.video.decoder.avc".equals(name));
  }

  /**
   * Returns whether the decoder is known to behave incorrectly if flushed after receiving an input
   * buffer with {@link MediaCodec#BUFFER_FLAG_END_OF_STREAM} set.
   * <p>
   * If true is returned, the renderer will work around the issue by instantiating a new decoder
   * when this case occurs.
   * <p>
   * See [Internal: b/8578467, b/23361053].
   *
   * @param name The name of the decoder.
   * @return True if the decoder is known to behave incorrectly if flushed after receiving an input
   *     buffer with {@link MediaCodec#BUFFER_FLAG_END_OF_STREAM} set. False otherwise.
   */
  private static boolean codecNeedsEosFlushWorkaround(String name) {
    return (Util.SDK_INT <= 23 && "OMX.google.vorbis.decoder".equals(name)) || (Util.SDK_INT <= 19 && "hb2000".equals(Util.DEVICE) && ("OMX.amlogic.avc.decoder.awesome".equals(name) || "OMX.amlogic.avc.decoder.awesome.secure".equals(name)));
  }

  /**
   * Returns whether the decoder may throw an {@link IllegalStateException} from
   * {@link MediaCodec#dequeueOutputBuffer(MediaCodec.BufferInfo, long)} or
   * {@link MediaCodec#releaseOutputBuffer(int, boolean)} after receiving an input
   * buffer with {@link MediaCodec#BUFFER_FLAG_END_OF_STREAM} set.
   * <p>
   * See [Internal: b/17933838].
   *
   * @param name The name of the decoder.
   * @return True if the decoder may throw an exception after receiving an end-of-stream buffer.
   */
  private static boolean codecNeedsEosOutputExceptionWorkaround(String name) {
    return Util.SDK_INT == 21 && "OMX.google.aac.decoder".equals(name);
  }

  /**
   * Returns whether the decoder is known to set the number of audio channels in the output format
   * to 2 for the given input format, whilst only actually outputting a single channel.
   * <p>
   * If true is returned then we explicitly override the number of channels in the output format,
   * setting it to 1.
   *
   * @param name The decoder name.
   * @param format The input format.
   * @return True if the decoder is known to set the number of audio channels in the output format
   *     to 2 for the given input format, whilst only actually outputting a single channel. False
   *     otherwise.
   */
  private static boolean codecNeedsMonoChannelCountWorkaround(String name, Format format) {
    return Util.SDK_INT <= 18 && format.channelCount == 1 && "OMX.MTK.AUDIO.DECODER.MP3".equals(name);
  }
}

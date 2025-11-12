package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.EOFException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.google.android.exoplayer2.extractor.ts.Ac4Extractor;

public final class DefaultHlsExtractorFactory implements HlsExtractorFactory {

    public static final String AAC_FILE_EXTENSION = ".aac";

    public static final String AC3_FILE_EXTENSION = ".ac3";

    public static final String EC3_FILE_EXTENSION = ".ec3";

    public static final String MP3_FILE_EXTENSION = ".mp3";

    public static final String MP4_FILE_EXTENSION = ".mp4";

    public static final String M4_FILE_EXTENSION_PREFIX = ".m4";

    public static final String MP4_FILE_EXTENSION_PREFIX = ".mp4";

    public static final String CMF_FILE_EXTENSION_PREFIX = ".cmf";

    public static final String VTT_FILE_EXTENSION = ".vtt";

    public static final String WEBVTT_FILE_EXTENSION = ".webvtt";

    @DefaultTsPayloadReaderFactory.Flags
    final private int payloadReaderFactoryFlags;

    public DefaultHlsExtractorFactory() {
        this(0);
    }

    public DefaultHlsExtractorFactory(int payloadReaderFactoryFlags) {
        this.payloadReaderFactoryFlags = payloadReaderFactoryFlags;
    }

    @Override
    public Result createExtractor(Extractor previousExtractor, Uri uri, Format format, List<Format> muxedCaptionFormats, DrmInitData drmInitData, TimestampAdjuster timestampAdjuster, Map<String, List<String>> responseHeaders, ExtractorInput extractorInput) throws InterruptedException, IOException {
        
if (previousExtractor != null) {
            if (isReusable(previousExtractor)) {
                return buildResult(previousExtractor);
            } else {
                Result result = buildResultForSameExtractorType(previousExtractor, format, timestampAdjuster);
                if (result == null) {
                    throw new IllegalArgumentException("Unexpected previousExtractor type: " + previousExtractor.getClass().getSimpleName());
                }
            }
        }

        Extractor extractorByFileExtension = createExtractorByFileExtension(uri, format, muxedCaptionFormats, drmInitData, timestampAdjuster);
        extractorInput.resetPeekPosition();
        if (sniffQuietly(extractorByFileExtension, extractorInput)) {
            return buildResult(extractorByFileExtension);
        }
        if (!(extractorByFileExtension instanceof WebvttExtractor)) {
            WebvttExtractor webvttExtractor = new WebvttExtractor(format.language, timestampAdjuster);
            if (sniffQuietly(webvttExtractor, extractorInput)) {
                return buildResult(webvttExtractor);
            }
        }
        if (!(extractorByFileExtension instanceof AdtsExtractor)) {
            AdtsExtractor adtsExtractor = new AdtsExtractor();
            if (sniffQuietly(adtsExtractor, extractorInput)) {
                return buildResult(adtsExtractor);
            }
        }
        if (!(extractorByFileExtension instanceof Ac3Extractor)) {
            Ac3Extractor ac3Extractor = new Ac3Extractor();
            if (sniffQuietly(ac3Extractor, extractorInput)) {
                return buildResult(ac3Extractor);
            }
        }
        if (!(extractorByFileExtension instanceof Ac4Extractor)) {
            Ac4Extractor ac4Extractor = new Ac4Extractor();
            if (sniffQuietly(ac4Extractor, extractorInput)) {
                return buildResult(ac4Extractor);
            }
        }
        if (!(extractorByFileExtension instanceof Mp3Extractor)) {
            Mp3Extractor mp3Extractor = new Mp3Extractor(0, 0);
            if (sniffQuietly(mp3Extractor, extractorInput)) {
                return buildResult(mp3Extractor);
            }
        }
        if (!(extractorByFileExtension instanceof FragmentedMp4Extractor)) {
            FragmentedMp4Extractor fragmentedMp4Extractor = new FragmentedMp4Extractor(0, timestampAdjuster, null, drmInitData, muxedCaptionFormats != null ? muxedCaptionFormats : Collections.emptyList());
            if (sniffQuietly(fragmentedMp4Extractor, extractorInput)) {
                return buildResult(fragmentedMp4Extractor);
            }
        }
        if (!(extractorByFileExtension instanceof TsExtractor)) {
            TsExtractor tsExtractor = createTsExtractor(payloadReaderFactoryFlags, format, muxedCaptionFormats, timestampAdjuster);
            if (sniffQuietly(tsExtractor, extractorInput)) {
                return buildResult(tsExtractor);
            }
        }
        return buildResult(extractorByFileExtension);
    }

    private Extractor createExtractorByFileExtension(Uri uri, Format format, List<Format> muxedCaptionFormats, DrmInitData drmInitData, TimestampAdjuster timestampAdjuster) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null) {
            lastPathSegment = "";
        }
        if (MimeTypes.TEXT_VTT.equals(format.sampleMimeType) || lastPathSegment.endsWith(WEBVTT_FILE_EXTENSION) || lastPathSegment.endsWith(VTT_FILE_EXTENSION)) {
            return new WebvttExtractor(format.language, timestampAdjuster);
        } else if (lastPathSegment.endsWith(AAC_FILE_EXTENSION)) {
            return new AdtsExtractor();
        } else if (lastPathSegment.endsWith(AC3_FILE_EXTENSION) || lastPathSegment.endsWith(EC3_FILE_EXTENSION)) {
            return new Ac3Extractor();
        } else if (lastPathSegment.endsWith(AC4_FILE_EXTENSION)) {
            return new Ac4Extractor();
        } else if (lastPathSegment.endsWith(MP3_FILE_EXTENSION)) {
            return new Mp3Extractor(0, 0);
        } else if (lastPathSegment.endsWith(MP4_FILE_EXTENSION) || lastPathSegment.startsWith(M4_FILE_EXTENSION_PREFIX, lastPathSegment.length() - 4) || lastPathSegment.startsWith(MP4_FILE_EXTENSION_PREFIX, lastPathSegment.length() - 5) || lastPathSegment.startsWith(CMF_FILE_EXTENSION_PREFIX, lastPathSegment.length() - 5)) {
            return new FragmentedMp4Extractor(0, timestampAdjuster, null, drmInitData, muxedCaptionFormats != null ? muxedCaptionFormats : Collections.emptyList());
        } else {
            return createTsExtractor(payloadReaderFactoryFlags, format, muxedCaptionFormats, timestampAdjuster);
        }
    }

    static private TsExtractor createTsExtractor(@DefaultTsPayloadReaderFactory.Flags int userProvidedPayloadReaderFactoryFlags, Format format, List<Format> muxedCaptionFormats, TimestampAdjuster timestampAdjuster) {
        @DefaultTsPayloadReaderFactory.Flags
        int payloadReaderFactoryFlags = DefaultTsPayloadReaderFactory.FLAG_IGNORE_SPLICE_INFO_STREAM | userProvidedPayloadReaderFactoryFlags;
        if (muxedCaptionFormats != null) {
            payloadReaderFactoryFlags |= DefaultTsPayloadReaderFactory.FLAG_OVERRIDE_CAPTION_DESCRIPTORS;
        } else {
            muxedCaptionFormats = Collections.singletonList(Format.createTextSampleFormat(null, MimeTypes.APPLICATION_CEA608, 0, null));
        }
        String codecs = format.codecs;
        if (!TextUtils.isEmpty(codecs)) {
            if (!MimeTypes.AUDIO_AAC.equals(MimeTypes.getAudioMediaMimeType(codecs))) {
                payloadReaderFactoryFlags |= DefaultTsPayloadReaderFactory.FLAG_IGNORE_AAC_STREAM;
            }
            if (!MimeTypes.VIDEO_H264.equals(MimeTypes.getVideoMediaMimeType(codecs))) {
                payloadReaderFactoryFlags |= DefaultTsPayloadReaderFactory.FLAG_IGNORE_H264_STREAM;
            }
        }
        return new TsExtractor(TsExtractor.MODE_HLS, timestampAdjuster, new DefaultTsPayloadReaderFactory(payloadReaderFactoryFlags, muxedCaptionFormats));
    }

    static private Result buildResult(Extractor extractor) {
        return new Result(extractor, extractor instanceof AdtsExtractor || extractor instanceof Ac3Extractor || extractor instanceof Ac4Extractor || extractor instanceof Mp3Extractor, isReusable(extractor));
    }

    static private boolean sniffQuietly(Extractor extractor, ExtractorInput input) throws InterruptedException, IOException {
        boolean result = false;
        try {
            result = extractor.sniff(input);
        } catch (EOFException e) {
        } finally {
            input.resetPeekPosition();
        }
        return result;
    }

    static private boolean isReusable(Extractor previousExtractor) {
        return previousExtractor instanceof TsExtractor || previousExtractor instanceof FragmentedMp4Extractor;
    }

    public static final String AC4_FILE_EXTENSION = ".ac4";

    static private Result buildResultForSameExtractorType(Extractor previousExtractor, Format format, TimestampAdjuster timestampAdjuster) {
        if (previousExtractor instanceof WebvttExtractor) {
            return buildResult(new WebvttExtractor(format.language, timestampAdjuster));
        } else if (previousExtractor instanceof AdtsExtractor) {
            return buildResult(new AdtsExtractor());
        } else if (previousExtractor instanceof Ac3Extractor) {
            return buildResult(new Ac3Extractor());
        } else if (previousExtractor instanceof Mp3Extractor) {
            return buildResult(new Mp3Extractor());
        } else {
            return null;
        }
    }
}

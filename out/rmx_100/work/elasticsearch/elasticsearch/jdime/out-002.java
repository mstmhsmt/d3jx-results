package org.elasticsearch.repositories.s3;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.metrics.MetricCollection;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.AbortMultipartUploadResponse;
import com.amazonaws.services.s3.model.CopyObjectResult;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CopyPartRequest;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import com.amazonaws.services.s3.model.CopyPartResult;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.SdkPartType;
import software.amazon.awssdk.services.s3.model.StorageClass;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;
import org.elasticsearch.common.blobstore.BlobPath;
import org.elasticsearch.common.blobstore.BlobStoreException;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.core.Tuple;
import org.elasticsearch.test.ESTestCase;
import org.mockito.ArgumentCaptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.elasticsearch.repositories.blobstore.BlobStoreTestUtil.randomPurpose;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class S3BlobStoreContainerTests extends ESTestCase {
  public void testExecuteSingleUploadBlobSizeTooLarge() {
    final long blobSize = ByteSizeUnit.GB.toBytes(randomIntBetween(6, 10));
    final S3BlobStore blobStore = mock(S3BlobStore.class);
    final S3BlobContainer blobContainer = new S3BlobContainer(mock(BlobPath.class), blobStore);
    final IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> blobContainer.executeSingleUpload(randomPurpose(), blobStore, randomAlphaOfLengthBetween(1, 10), null, blobSize));
    assertEquals("Upload request size [" + blobSize + "] can\'t be larger than 5gb", e.getMessage());
  }

  public void testExecuteSingleUploadBlobSizeLargerThanBufferSize() {
    final S3BlobStore blobStore = mock(S3BlobStore.class);
    when(blobStore.bufferSizeInBytes()).thenReturn(ByteSizeUnit.MB.toBytes(1));
    final S3BlobContainer blobContainer = new S3BlobContainer(mock(BlobPath.class), blobStore);
    final String blobName = randomAlphaOfLengthBetween(1, 10);
    final IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> blobContainer.executeSingleUpload(randomPurpose(), blobStore, blobName, new ByteArrayInputStream(new byte[0]), ByteSizeUnit.MB.toBytes(2)));
    assertEquals("Upload request size [2097152] can\'t be larger than buffer size", e.getMessage());
  }

  public void testExecuteSingleUpload() throws IOException {
    final String bucketName = randomAlphaOfLengthBetween(1, 10);
    final String blobName = randomAlphaOfLengthBetween(1, 10);
    final BlobPath blobPath = BlobPath.EMPTY;
    if (randomBoolean()) {
      IntStream.of(randomIntBetween(1, 5)).forEach((value) -> blobPath.add("path_" + value));
    }
    final int bufferSize = randomIntBetween(1024, 2048);
    final int blobSize = randomIntBetween(0, bufferSize);
    final S3BlobStore blobStore = mock(S3BlobStore.class);
    when(blobStore.bucket()).thenReturn(bucketName);
    when(blobStore.bufferSizeInBytes()).thenReturn((long) bufferSize);
    final S3BlobContainer blobContainer = new S3BlobContainer(blobPath, blobStore);
    final boolean serverSideEncryption = randomBoolean();
    when(blobStore.serverSideEncryption()).thenReturn(serverSideEncryption);
    final StorageClass storageClass = randomFrom(StorageClass.values());
    when(blobStore.getStorageClass()).thenReturn(storageClass);
    final ObjectCannedACL cannedAccessControlList = randomBoolean() ? randomFrom(ObjectCannedACL.values()) : null;
    if (cannedAccessControlList != null) {
      when(blobStore.getCannedACL()).thenReturn(cannedAccessControlList);
    }
    final S3Client client = configureMockClient(blobStore);
    final ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
    final ArgumentCaptor<RequestBody> bodyCaptor = ArgumentCaptor.forClass(RequestBody.class);
    when(client.putObject(requestCaptor.capture(), bodyCaptor.capture())).thenReturn(PutObjectResponse.builder().build());
    final ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[blobSize]);
    blobContainer.executeSingleUpload(randomPurpose(), blobStore, blobName, inputStream, blobSize);
    final PutObjectRequest request = requestCaptor.getValue();
    assertEquals(bucketName, request.bucket());
    assertEquals(blobPath.buildAsString() + blobName, request.key());
    assertEquals(Long.valueOf(blobSize), request.contentLength());
    assertEquals(storageClass, request.storageClass());
    assertEquals(cannedAccessControlList, request.acl());
    if (serverSideEncryption) {
      assertEquals(PutObjectRequest.builder().serverSideEncryption("AES256").build().sseCustomerAlgorithm(), request.sseCustomerAlgorithm());
    }
    final RequestBody requestBody = bodyCaptor.getValue();
    try (var contentStream = requestBody.contentStreamProvider().newStream()) {
      assertEquals(inputStream.available(), blobSize);
      final int toSkip = between(0, blobSize);
      contentStream.skipNBytes(toSkip);
      assertEquals(inputStream.available(), blobSize - toSkip);
    }
  }

  public void testExecuteMultipartUploadBlobSizeTooLarge() {
    final long blobSize = ByteSizeUnit.TB.toBytes(randomIntBetween(6, 10));
    final S3BlobStore blobStore = mock(S3BlobStore.class);
    final S3BlobContainer blobContainer = new S3BlobContainer(mock(BlobPath.class), blobStore);
    final IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> blobContainer.executeMultipartUpload(randomPurpose(), blobStore, randomAlphaOfLengthBetween(1, 10), null, blobSize));
    assertEquals("Multipart upload request size [" + blobSize + "] can\'t be larger than 5tb", e.getMessage());
  }

  public void testExecuteMultipartUploadBlobSizeTooSmall() {
    final long blobSize = ByteSizeUnit.MB.toBytes(randomIntBetween(1, 4));
    final S3BlobStore blobStore = mock(S3BlobStore.class);
    final S3BlobContainer blobContainer = new S3BlobContainer(mock(BlobPath.class), blobStore);
    final IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> blobContainer.executeMultipartUpload(randomPurpose(), blobStore, randomAlphaOfLengthBetween(1, 10), null, blobSize));
    assertEquals("Multipart upload request size [" + blobSize + "] can\'t be smaller than 5mb", e.getMessage());
  }

  public void testExecuteMultipartUpload() throws IOException {

<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    final ObjectCannedACL cannedAccessControlList = randomBoolean() ? randomFrom(ObjectCannedACL.values()) : null;
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    final S3Client client = configureMockClient(blobStore);
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    final var uploadId = randomIdentifier();
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    final ArgumentCaptor<CreateMultipartUploadRequest> createMultipartUploadRequestCaptor = ArgumentCaptor.forClass(CreateMultipartUploadRequest.class);
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    when(client.createMultipartUpload(createMultipartUploadRequestCaptor.capture())).thenReturn(CreateMultipartUploadResponse.builder().uploadId(uploadId).build());
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    final ArgumentCaptor<UploadPartRequest> uploadPartRequestCaptor = ArgumentCaptor.forClass(UploadPartRequest.class);
=======
>>>>>>> Unknown file: This is a bug in JDime.

    final ArgumentCaptor<RequestBody> uploadPartBodyCaptor = ArgumentCaptor.forClass(RequestBody.class);

<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    when(client.uploadPart(uploadPartRequestCaptor.capture(), uploadPartBodyCaptor.capture())).thenAnswer((invocationOnMock) -> {
      final UploadPartRequest request = (UploadPartRequest) invocationOnMock.getArguments()[0];
      final UploadPartResponse.Builder responseBuilder = UploadPartResponse.builder();
      responseBuilder.eTag(expectedEtags.get(request.partNumber() - 1));
      return responseBuilder.build();
    });
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    final ArgumentCaptor<CompleteMultipartUploadRequest> completeMultipartUploadRequestCaptor = ArgumentCaptor.forClass(CompleteMultipartUploadRequest.class);
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    when(client.completeMultipartUpload(completeMultipartUploadRequestCaptor.capture())).thenReturn(CompleteMultipartUploadResponse.builder().build());
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    final CreateMultipartUploadRequest initRequest = createMultipartUploadRequestCaptor.getValue();
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    assertEquals(bucketName, initRequest.bucket());
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    assertEquals(blobPath.buildAsString() + blobName, initRequest.key());
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    assertEquals(storageClass, initRequest.storageClass());
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    assertEquals(cannedAccessControlList, initRequest.acl());
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    if (serverSideEncryption) {
      assertEquals(PutObjectRequest.builder().serverSideEncryption("AES256").build().sseCustomerAlgorithm(), initRequest.sseCustomerAlgorithm());
    }
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    final List<UploadPartRequest> uploadPartRequests = uploadPartRequestCaptor.getAllValues();
=======
>>>>>>> Unknown file: This is a bug in JDime.

    assertEquals(numberOfParts.v1().intValue(), uploadPartRequests.size());
    final List<RequestBody> uploadPartBodies = uploadPartBodyCaptor.getAllValues();

<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    assertEquals(numberOfParts.v1().intValue(), uploadPartBodies.size());
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    for (int i = 0; i < uploadPartRequests.size(); i++) {
      final UploadPartRequest uploadRequest = uploadPartRequests.get(i);
      assertEquals(bucketName, uploadRequest.bucket());
      assertEquals(blobPath.buildAsString() + blobName, uploadRequest.key());
      assertEquals(uploadId, uploadRequest.uploadId());
      assertEquals(i + 1, uploadRequest.partNumber().intValue());
      assertEquals(uploadRequest.sdkPartType() + " at " + i + " of " + uploadPartRequests.size(), uploadRequest.sdkPartType() == SdkPartType.LAST, i == uploadPartRequests.size() - 1);
      assertEquals("part " + i, uploadRequest.sdkPartType() == SdkPartType.LAST ? Optional.of(numberOfParts.v2()) : Optional.of(bufferSize), uploadPartBodies.get(i).optionalContentLength());
    }
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    final CompleteMultipartUploadRequest compRequest = completeMultipartUploadRequestCaptor.getValue();
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    assertEquals(bucketName, compRequest.bucket());
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    assertEquals(blobPath.buildAsString() + blobName, compRequest.key());
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    assertEquals(uploadId, compRequest.uploadId());
=======
>>>>>>> Unknown file: This is a bug in JDime.


<<<<<<< commits-rmx_100/elasticsearch/elasticsearch/28c4ac0987353d44e56233fe335f5da533ae43b6/S3BlobStoreContainerTests-24165da.java
    final List<String> actualETags = compRequest.multipartUpload().parts().stream().map(CompletedPart::eTag).collect(Collectors.toList());
=======
>>>>>>> Unknown file: This is a bug in JDime.

    testExecuteMultipart(false);
  }

  public void testExecuteMultipartCopy() throws IOException {
    testExecuteMultipart(true);
  }

  void testExecuteMultipart(boolean doCopy) throws IOException {
    final String bucketName = randomAlphaOfLengthBetween(1, 10);
    final String blobName = randomAlphaOfLengthBetween(1, 10);
    final String sourceBucketName = randomAlphaOfLengthBetween(1, 10);
    final String sourceBlobName = randomAlphaOfLengthBetween(1, 10);
    final BlobPath blobPath = BlobPath.EMPTY;
    if (randomBoolean()) {
      IntStream.of(randomIntBetween(1, 5)).forEach((value) -> blobPath.add("path_" + value));
    }
    final var sourceBlobPath = BlobPath.EMPTY;
    if (randomBoolean()) {
      IntStream.of(randomIntBetween(1, 5)).forEach((value) -> sourceBlobPath.add("path_" + value));
    }
    final long blobSize = ByteSizeUnit.GB.toBytes(randomIntBetween(1, 128));
    final long bufferSize = ByteSizeUnit.MB.toBytes(randomIntBetween(5, 1024));
    final S3BlobStore blobStore = mock(S3BlobStore.class);
    when(blobStore.bucket()).thenReturn(bucketName);
    when(blobStore.bufferSizeInBytes()).thenReturn(bufferSize);
    final S3BlobStore sourceBlobStore = mock(S3BlobStore.class);
    when(sourceBlobStore.bucket()).thenReturn(sourceBucketName);
    final boolean serverSideEncryption = randomBoolean();
    when(blobStore.serverSideEncryption()).thenReturn(serverSideEncryption);
    final StorageClass storageClass = randomFrom(StorageClass.values());
    when(blobStore.getStorageClass()).thenReturn(storageClass);
    final CannedAccessControlList cannedAccessControlList = randomBoolean() ? randomFrom(CannedAccessControlList.values()) : null;
    if (cannedAccessControlList != null) {
      when(blobStore.getCannedACL()).thenReturn(cannedAccessControlList);
    }
    final AmazonS3 client = configureMockClient(blobStore);
    final ArgumentCaptor<InitiateMultipartUploadRequest> initArgCaptor = ArgumentCaptor.forClass(InitiateMultipartUploadRequest.class);
    final InitiateMultipartUploadResult initResult = new InitiateMultipartUploadResult();
    initResult.setUploadId(randomAlphaOfLength(10));
    when(client.initiateMultipartUpload(initArgCaptor.capture())).thenReturn(initResult);
    final ArgumentCaptor<UploadPartRequest> uploadArgCaptor = ArgumentCaptor.forClass(UploadPartRequest.class);
    final var copyArgCaptor = ArgumentCaptor.forClass(CopyPartRequest.class);
    final List<String> expectedEtags = new ArrayList<>();
    final long partSize = Math.min(doCopy ? ByteSizeUnit.GB.toBytes(5) : bufferSize, blobSize);
    long totalBytes = 0;
    do {
      expectedEtags.add(randomAlphaOfLength(50));
      totalBytes += partSize;
    } while(totalBytes < blobSize);
    if (doCopy) {
      when(client.copyPart(copyArgCaptor.capture())).thenAnswer((invocationOnMock) -> {
        final CopyPartRequest request = (CopyPartRequest) invocationOnMock.getArguments()[0];
        final CopyPartResult result = new CopyPartResult();
        result.setETag(expectedEtags.get(request.getPartNumber() - 1));
        return result;
      });
    } else {
      when(client.uploadPart(uploadArgCaptor.capture())).thenAnswer((invocationOnMock) -> {
        final UploadPartRequest request = (UploadPartRequest) invocationOnMock.getArguments()[0];
        final UploadPartResult response = new UploadPartResult();
        response.setPartNumber(request.getPartNumber());
        response.setETag(expectedEtags.get(request.getPartNumber() - 1));
        return response;
      });
    }
    final ArgumentCaptor<CompleteMultipartUploadRequest> compArgCaptor = ArgumentCaptor.forClass(CompleteMultipartUploadRequest.class);
    when(client.completeMultipartUpload(compArgCaptor.capture())).thenReturn(new CompleteMultipartUploadResult());
    final ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[0]);
    final S3BlobContainer blobContainer = new S3BlobContainer(blobPath, blobStore);
    final S3BlobContainer sourceContainer = new S3BlobContainer(sourceBlobPath, sourceBlobStore);
    if (doCopy) {
      blobContainer.executeMultipartCopy(randomPurpose(), sourceContainer, sourceBlobName, blobName, blobSize);
    } else {
      blobContainer.executeMultipartUpload(randomPurpose(), blobStore, blobName, inputStream, blobSize);
    }
    final InitiateMultipartUploadRequest initRequest = initArgCaptor.getValue();
    assertEquals(bucketName, initRequest.getBucketName());
    assertEquals(blobPath.buildAsString() + blobName, initRequest.getKey());
    assertEquals(storageClass, initRequest.getStorageClass());
    assertEquals(cannedAccessControlList, initRequest.getCannedACL());
    if (serverSideEncryption) {
      assertEquals(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION, initRequest.getObjectMetadata().getSSEAlgorithm());
    }
    final Tuple<Long, Long> numberOfParts = S3BlobContainer.numberOfMultiparts(blobSize, partSize);
    if (doCopy) {
      final var copyRequests = copyArgCaptor.getAllValues();
      assertEquals(numberOfParts.v1().intValue(), copyRequests.size());
      for (int i = 0; i < copyRequests.size(); i++) {
        final var request = copyRequests.get(i);
        final long startOffset = i * partSize;
        final long endOffset = Math.min(startOffset + partSize - 1, blobSize - 1);
        assertEquals(sourceBucketName, request.getSourceBucketName());
        assertEquals(sourceBlobPath.buildAsString() + sourceBlobName, request.getSourceKey());
        assertEquals(bucketName, request.getDestinationBucketName());
        assertEquals(blobPath.buildAsString() + blobName, request.getDestinationKey());
        assertEquals(initResult.getUploadId(), request.getUploadId());
        assertEquals(i + 1, request.getPartNumber());
        assertEquals(Long.valueOf(startOffset), request.getFirstByte());
        assertEquals(Long.valueOf(endOffset), request.getLastByte());
      }
    } else {
      final List<UploadPartRequest> uploadRequests = uploadArgCaptor.getAllValues();
      assertEquals(numberOfParts.v1().intValue(), uploadRequests.size());
      for (int i = 0; i < uploadRequests.size(); i++) {
        final UploadPartRequest uploadRequest = uploadRequests.get(i);
        assertEquals(bucketName, uploadRequest.getBucketName());
        assertEquals(blobPath.buildAsString() + blobName, uploadRequest.getKey());
        assertEquals(initResult.getUploadId(), uploadRequest.getUploadId());
        assertEquals(i + 1, uploadRequest.getPartNumber());
        assertEquals(inputStream, uploadRequest.getInputStream());
        if (i == (uploadRequests.size() - 1)) {
          assertTrue(uploadRequest.isLastPart());
          assertEquals(numberOfParts.v2().longValue(), uploadRequest.getPartSize());
        } else {
          assertFalse(uploadRequest.isLastPart());
          assertEquals(bufferSize, uploadRequest.getPartSize());
        }
      }
    }
    final CompleteMultipartUploadRequest compRequest = compArgCaptor.getValue();
    assertEquals(bucketName, compRequest.getBucketName());
    assertEquals(blobPath.buildAsString() + blobName, compRequest.getKey());
    assertEquals(initResult.getUploadId(), compRequest.getUploadId());
    final List<String> actualETags = compRequest.getPartETags().stream().map(PartETag::getETag).collect(Collectors.toList());
    assertEquals(expectedEtags, actualETags);
    closeMockClient(blobStore);
  }

  public void testExecuteMultipartUploadAborted() {
    final String bucketName = randomAlphaOfLengthBetween(1, 10);
    final String blobName = randomAlphaOfLengthBetween(1, 10);
    final BlobPath blobPath = BlobPath.EMPTY;
    final long blobSize = ByteSizeUnit.MB.toBytes(765);
    final long bufferSize = ByteSizeUnit.MB.toBytes(150);
    final S3BlobStore blobStore = mock(S3BlobStore.class);
    when(blobStore.bucket()).thenReturn(bucketName);
    when(blobStore.bufferSizeInBytes()).thenReturn(bufferSize);
    when(blobStore.getStorageClass()).thenReturn(randomFrom(StorageClass.values()));
    final S3Client client = mock(S3Client.class);
    final SdkHttpClient httpClient = mock(SdkHttpClient.class);
    final AmazonS3Reference clientReference = new AmazonS3Reference(client, httpClient);
    when(blobStore.clientReference()).then((invocation) -> {
      clientReference.mustIncRef();
      return clientReference;
    });
    when(blobStore.getMetricPublisher(any(), any())).thenReturn(new MetricPublisher() {
      @Override public void publish(MetricCollection metricCollection) {
      }

      @Override public void close() {
      }
    });
    final String uploadId = randomAlphaOfLength(25);
    final int stage = randomInt(2);
    final List<AwsServiceException> exceptions = Arrays.asList(S3Exception.builder().message("Expected initialization request to fail").build(), S3Exception.builder().message("Expected upload part request to fail").build(), S3Exception.builder().message("Expected completion request to fail").build());
    if (stage == 0) {
      when(client.createMultipartUpload(any(CreateMultipartUploadRequest.class))).thenThrow(exceptions.get(stage));
    } else {
      if (stage == 1) {
        final CreateMultipartUploadResponse.Builder initResult = CreateMultipartUploadResponse.builder();
        initResult.uploadId(uploadId);
        when(client.createMultipartUpload(any(CreateMultipartUploadRequest.class))).thenReturn(initResult.build());
        when(client.uploadPart(any(UploadPartRequest.class), any(RequestBody.class))).thenThrow(exceptions.get(stage));
      } else {
        final CreateMultipartUploadResponse.Builder initResult = CreateMultipartUploadResponse.builder();
        initResult.uploadId(uploadId);
        when(client.createMultipartUpload(any(CreateMultipartUploadRequest.class))).thenReturn(initResult.build());
        when(client.uploadPart(any(UploadPartRequest.class), any(RequestBody.class))).thenAnswer((invocationOnMock) -> {
          final UploadPartRequest request = (UploadPartRequest) invocationOnMock.getArguments()[0];
          final UploadPartResponse.Builder response = UploadPartResponse.builder();
          response.eTag(randomAlphaOfLength(20));
          return response.build();
        });
        when(client.completeMultipartUpload(any(CompleteMultipartUploadRequest.class))).thenThrow(exceptions.get(stage));
      }
    }
    final ArgumentCaptor<AbortMultipartUploadRequest> argumentCaptor = ArgumentCaptor.forClass(AbortMultipartUploadRequest.class);
    when(client.abortMultipartUpload(argumentCaptor.capture())).thenReturn(AbortMultipartUploadResponse.builder().build());
    final IOException e = expectThrows(IOException.class, () -> {
      final S3BlobContainer blobContainer = new S3BlobContainer(BlobPath.EMPTY, blobStore);
      blobContainer.executeMultipartUpload(randomPurpose(), blobStore, blobName, new ByteArrayInputStream(new byte[0]), blobSize);
    });
    assertEquals("Unable to upload or copy object [" + blobName + "] using multipart upload", e.getMessage());
    assertThat(e.getCause(), instanceOf(S3Exception.class));
    assertEquals(exceptions.get(stage).getMessage(), e.getCause().getMessage());
    if (stage == 0) {
      verify(client, times(1)).createMultipartUpload(any(CreateMultipartUploadRequest.class));
      verify(client, times(0)).uploadPart(any(UploadPartRequest.class), any(RequestBody.class));
      verify(client, times(0)).completeMultipartUpload(any(CompleteMultipartUploadRequest.class));
      verify(client, times(0)).abortMultipartUpload(any(AbortMultipartUploadRequest.class));
    } else {
      verify(client, times(1)).createMultipartUpload(any(CreateMultipartUploadRequest.class));
      if (stage == 1) {
        verify(client, times(1)).uploadPart(any(UploadPartRequest.class), any(RequestBody.class));
        verify(client, times(0)).completeMultipartUpload(any(CompleteMultipartUploadRequest.class));
      } else {
        verify(client, times(6)).uploadPart(any(UploadPartRequest.class), any(RequestBody.class));
        verify(client, times(1)).completeMultipartUpload(any(CompleteMultipartUploadRequest.class));
      }
      verify(client, times(1)).abortMultipartUpload(any(AbortMultipartUploadRequest.class));
      final AbortMultipartUploadRequest abortRequest = argumentCaptor.getValue();
      assertEquals(bucketName, abortRequest.bucket());
      assertEquals(blobName, abortRequest.key());
      assertEquals(uploadId, abortRequest.uploadId());
    }
    closeMockClient(blobStore);
  }

  public void testCopy() throws Exception {
    final var sourceBucketName = randomAlphaOfLengthBetween(1, 10);
    final var sourceBlobName = randomAlphaOfLengthBetween(1, 10);
    final var blobName = randomAlphaOfLengthBetween(1, 10);
    final StorageClass storageClass = randomFrom(StorageClass.values());
    final CannedAccessControlList cannedAccessControlList = randomBoolean() ? randomFrom(CannedAccessControlList.values()) : null;
    final var blobStore = mock(S3BlobStore.class);
    when(blobStore.bucket()).thenReturn(sourceBucketName);
    when(blobStore.getStorageClass()).thenReturn(storageClass);
    if (cannedAccessControlList != null) {
      when(blobStore.getCannedACL()).thenReturn(cannedAccessControlList);
    }
    when(blobStore.maxCopySizeBeforeMultipart()).thenReturn(S3Repository.MIN_PART_SIZE_USING_MULTIPART.getBytes());
    final var sourceBlobPath = BlobPath.EMPTY.add(randomAlphaOfLengthBetween(1, 10));
    final var sourceBlobContainer = new S3BlobContainer(sourceBlobPath, blobStore);
    final var destinationBlobPath = BlobPath.EMPTY.add(randomAlphaOfLengthBetween(1, 10));
    final var destinationBlobContainer = new S3BlobContainer(destinationBlobPath, blobStore);
    final var client = configureMockClient(blobStore);
    final ArgumentCaptor<CopyObjectRequest> captor = ArgumentCaptor.forClass(CopyObjectRequest.class);
    when(client.copyObject(captor.capture())).thenReturn(new CopyObjectResult());
    destinationBlobContainer.copyBlob(randomPurpose(), sourceBlobContainer, sourceBlobName, blobName, randomLongBetween(1, 10_000));
    final CopyObjectRequest request = captor.getValue();
    assertEquals(sourceBucketName, request.getSourceBucketName());
    assertEquals(sourceBlobPath.buildAsString() + sourceBlobName, request.getSourceKey());
    assertEquals(sourceBucketName, request.getDestinationBucketName());
    assertEquals(destinationBlobPath.buildAsString() + blobName, request.getDestinationKey());
    assertEquals(storageClass.toString(), request.getStorageClass());
    assertEquals(cannedAccessControlList, request.getCannedAccessControlList());
    closeMockClient(blobStore);
  }

  private static S3Client configureMockClient(S3BlobStore blobStore) {
    final S3Client client = mock(S3Client.class);
    final SdkHttpClient httpClient = mock(SdkHttpClient.class);
    try (AmazonS3Reference clientReference = new AmazonS3Reference(client, httpClient)) {
      clientReference.mustIncRef();
      when(blobStore.clientReference()).then((invocation) -> {
        clientReference.mustIncRef();
        return clientReference;
      });
      when(blobStore.getMetricPublisher(any(), any())).thenReturn(new MetricPublisher() {
        @Override public void publish(MetricCollection metricCollection) {
        }

        @Override public void close() {
        }
      });
    }
    return client;
  }

  private static void closeMockClient(S3BlobStore blobStore) {
    final var finalClientReference = blobStore.clientReference();
    assertFalse(finalClientReference.decRef());
    assertTrue(finalClientReference.decRef());
    assertFalse(finalClientReference.hasReferences());
  }

  public void testNumberOfMultipartsWithZeroPartSize() {
    final IllegalArgumentException e = expectThrows(IllegalArgumentException.class, () -> S3BlobContainer.numberOfMultiparts(randomNonNegativeLong(), 0L));
    assertEquals("Part size must be greater than zero", e.getMessage());
  }

  public void testNumberOfMultiparts() {
    final ByteSizeUnit unit = randomFrom(ByteSizeUnit.BYTES, ByteSizeUnit.KB, ByteSizeUnit.MB, ByteSizeUnit.GB);
    final long size = unit.toBytes(randomIntBetween(2, 1000));
    final int factor = randomIntBetween(2, 10);
    assertNumberOfMultiparts(1, 0L, 0L, size);
    assertNumberOfMultiparts(1, size, size, size);
    assertNumberOfMultiparts(1, size, size, size * factor);
    assertNumberOfMultiparts(factor, size, size * factor, size);
    final long remaining = randomIntBetween(1, (size > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int) size - 1);
    assertNumberOfMultiparts(factor + 1, remaining, (size * factor) + remaining, size);
  }

  public void testInitCannedACL() {
    String[] aclList = new String[] { "private", "public-read", "public-read-write", "authenticated-read", "bucket-owner-read", "bucket-owner-full-control" };
    assertThat(S3BlobStore.initCannedACL(null), equalTo(ObjectCannedACL.PRIVATE));
    assertThat(S3BlobStore.initCannedACL(""), equalTo(ObjectCannedACL.PRIVATE));
    for (String aclString : aclList) {
      ObjectCannedACL acl = S3BlobStore.initCannedACL(aclString);
      assertThat(acl.toString(), equalTo(aclString));
    }
    for (ObjectCannedACL awsList : ObjectCannedACL.values()) {
      ObjectCannedACL acl = S3BlobStore.initCannedACL(awsList.toString());
      assertThat(acl, equalTo(awsList));
    }
  }

  public void testInvalidCannedACL() {
    BlobStoreException ex = expectThrows(BlobStoreException.class, () -> S3BlobStore.initCannedACL("test_invalid"));
    assertThat(ex.getMessage(), equalTo("cannedACL is not valid: [test_invalid]"));
  }

  public void testInitStorageClass() {
    assertThat(S3BlobStore.initStorageClass(null), equalTo(StorageClass.STANDARD));
    assertThat(S3BlobStore.initStorageClass(""), equalTo(StorageClass.STANDARD));
    assertThat(S3BlobStore.initStorageClass("standard"), equalTo(StorageClass.STANDARD));
    assertThat(S3BlobStore.initStorageClass("standard_ia"), equalTo(StorageClass.STANDARD_IA));
    assertThat(S3BlobStore.initStorageClass("onezone_ia"), equalTo(StorageClass.ONEZONE_IA));
    assertThat(S3BlobStore.initStorageClass("reduced_redundancy"), equalTo(StorageClass.REDUCED_REDUNDANCY));
    assertThat(S3BlobStore.initStorageClass("intelligent_tiering"), equalTo(StorageClass.INTELLIGENT_TIERING));
  }

  public void testCaseInsensitiveStorageClass() {
    assertThat(S3BlobStore.initStorageClass("sTandaRd"), equalTo(StorageClass.STANDARD));
    assertThat(S3BlobStore.initStorageClass("sTandaRd_Ia"), equalTo(StorageClass.STANDARD_IA));
    assertThat(S3BlobStore.initStorageClass("oNeZoNe_iA"), equalTo(StorageClass.ONEZONE_IA));
    assertThat(S3BlobStore.initStorageClass("reduCED_redundancy"), equalTo(StorageClass.REDUCED_REDUNDANCY));
    assertThat(S3BlobStore.initStorageClass("intelLigeNt_tieriNG"), equalTo(StorageClass.INTELLIGENT_TIERING));
  }

  public void testInvalidStorageClass() {
    BlobStoreException ex = expectThrows(BlobStoreException.class, () -> S3BlobStore.initStorageClass("whatever"));
    assertThat(ex.getMessage(), equalTo("`whatever` is not a known S3 Storage Class."));
  }

  public void testRejectGlacierStorageClass() {
    BlobStoreException ex = expectThrows(BlobStoreException.class, () -> S3BlobStore.initStorageClass("glacier"));
    assertThat(ex.getMessage(), equalTo("Glacier storage class is not supported"));
  }

  private static void assertNumberOfMultiparts(final int expectedParts, final long expectedRemaining, long totalSize, long partSize) {
    final Tuple<Long, Long> result = S3BlobContainer.numberOfMultiparts(totalSize, partSize);
    assertEquals("Expected number of parts [" + expectedParts + "] but got [" + result.v1() + "]", expectedParts, (long) result.v1());
    assertEquals("Expected remaining [" + expectedRemaining + "] but got [" + result.v2() + "]", expectedRemaining, (long) result.v2());
  }
}
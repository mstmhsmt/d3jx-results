package voldemort.client.protocol.pb;

public final class VAdminProto {
  private VAdminProto() {
  }

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
  }

  public enum AdminRequestType implements com.google.protobuf.ProtocolMessageEnum {
    GET_METADATA(0, 0),
    UPDATE_METADATA(1, 1),
    UPDATE_PARTITION_ENTRIES(2, 2),
    FETCH_PARTITION_ENTRIES(3, 3),
    DELETE_PARTITION_ENTRIES(4, 4),
    INITIATE_FETCH_AND_UPDATE(5, 5),
    ASYNC_OPERATION_STATUS(6, 6),
    INITIATE_REBALANCE_NODE(7, 7),
    ASYNC_OPERATION_STOP(8, 8),
    ASYNC_OPERATION_LIST(9, 9),
    TRUNCATE_ENTRIES(10, 10),
    ADD_STORE(11, 11),
    DELETE_STORE(12, 12),
    FETCH_STORE(13, 13),
    SWAP_STORE(14, 14),
    ROLLBACK_STORE(15, 15),
    GET_RO_MAX_VERSION_DIR(16, 16),
    GET_RO_CURRENT_VERSION_DIR(17, 17),
    FETCH_PARTITION_FILES(18, 18),
    UPDATE_SLOP_ENTRIES(19, 20),
    FAILED_FETCH_STORE(20, 22),
    GET_RO_STORAGE_FORMAT(21, 23),
    REBALANCE_STATE_CHANGE(22, 24),
    REPAIR_JOB(23, 25),
    INITIATE_REBALANCE_NODE_ON_DONOR(24, 26),
    NATIVE_BACKUP(24, 26),
    DELETE_STORE_REBALANCE_STATE(25, 27)
    ;

    public final int getNumber() {
      return value;
    }

    public static AdminRequestType valueOf(int value) {
      switch (value) {
        case 0:
        return GET_METADATA;
        case 1:
        return UPDATE_METADATA;
        case 2:
        return UPDATE_PARTITION_ENTRIES;
        case 3:
        return FETCH_PARTITION_ENTRIES;
        case 4:
        return DELETE_PARTITION_ENTRIES;
        case 5:
        return INITIATE_FETCH_AND_UPDATE;
        case 6:
        return ASYNC_OPERATION_STATUS;
        case 7:
        return INITIATE_REBALANCE_NODE;
        case 8:
        return ASYNC_OPERATION_STOP;
        case 9:
        return ASYNC_OPERATION_LIST;
        case 10:
        return TRUNCATE_ENTRIES;
        case 11:
        return ADD_STORE;
        case 12:
        return DELETE_STORE;
        case 13:
        return FETCH_STORE;
        case 14:
        return SWAP_STORE;
        case 15:
        return ROLLBACK_STORE;
        case 16:
        return GET_RO_MAX_VERSION_DIR;
        case 17:
        return GET_RO_CURRENT_VERSION_DIR;
        case 18:
        return FETCH_PARTITION_FILES;
        case 20:
        return UPDATE_SLOP_ENTRIES;
        case 22:
        return FAILED_FETCH_STORE;
        case 23:
        return GET_RO_STORAGE_FORMAT;
        case 24:
        return REBALANCE_STATE_CHANGE;
        case 25:
        return REPAIR_JOB;
        case 26:
        return INITIATE_REBALANCE_NODE_ON_DONOR;
        case 27:
        return 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        DELETE_STORE_REBALANCE_STATE
=======
        NATIVE_BACKUP
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        ;
        default:
        return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<AdminRequestType> internalGetValueMap() {
      return internalValueMap;
    }

    private static com.google.protobuf.Internal.EnumLiteMap<AdminRequestType> internalValueMap = new com.google.protobuf.Internal.EnumLiteMap<AdminRequestType>() {
      public AdminRequestType findValueByNumber(int number) {
        return AdminRequestType.valueOf(number);
      }
    };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }

    public final com.google.protobuf.Descriptors.EnumDescriptor getDescriptorForType() {
      return getDescriptor();
    }

    public static final com.google.protobuf.Descriptors.EnumDescriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.getDescriptor().getEnumTypes().get(0);
    }

    private static final AdminRequestType[] VALUES = { GET_METADATA, UPDATE_METADATA, UPDATE_PARTITION_ENTRIES, FETCH_PARTITION_ENTRIES, DELETE_PARTITION_ENTRIES, INITIATE_FETCH_AND_UPDATE, ASYNC_OPERATION_STATUS, INITIATE_REBALANCE_NODE, ASYNC_OPERATION_STOP, ASYNC_OPERATION_LIST, TRUNCATE_ENTRIES, ADD_STORE, DELETE_STORE, FETCH_STORE, SWAP_STORE, ROLLBACK_STORE, GET_RO_MAX_VERSION_DIR, GET_RO_CURRENT_VERSION_DIR, FETCH_PARTITION_FILES, UPDATE_SLOP_ENTRIES, FAILED_FETCH_STORE, GET_RO_STORAGE_FORMAT, REBALANCE_STATE_CHANGE, REPAIR_JOB, 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    INITIATE_REBALANCE_NODE_ON_DONOR
=======
    NATIVE_BACKUP
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    , DELETE_STORE_REBALANCE_STATE };

    public static AdminRequestType valueOf(com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException("EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;

    private final int value;

    private AdminRequestType(int index, int value) {
      this.index = index;
      this.value = value;
    }

    static {
      voldemort.client.protocol.pb.VAdminProto.getDescriptor();
    }
  }

  public static final class GetMetadataRequest extends com.google.protobuf.GeneratedMessage {
    private GetMetadataRequest() {
      initFields();
    }

    private GetMetadataRequest(boolean noInit) {
    }

    private static final GetMetadataRequest defaultInstance;

    public static GetMetadataRequest getDefaultInstance() {
      return defaultInstance;
    }

    public GetMetadataRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetMetadataRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetMetadataRequest_fieldAccessorTable;
    }

    public static final int KEY_FIELD_NUMBER = 1;

    private boolean hasKey;

    private com.google.protobuf.ByteString key_ = com.google.protobuf.ByteString.EMPTY;

    public boolean hasKey() {
      return hasKey;
    }

    public com.google.protobuf.ByteString getKey() {
      return key_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasKey) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasKey()) {
        output.writeBytes(1, getKey());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasKey()) {
        size += com.google.protobuf.CodedOutputStream.computeBytesSize(1, getKey());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasKey()) {
          setKey(other.getKey());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setKey(input.readBytes());
              break;
            }
          }
        }
      }

      public boolean hasKey() {
        return result.hasKey();
      }

      public com.google.protobuf.ByteString getKey() {
        return result.getKey();
      }

      public Builder setKey(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasKey = true;
        result.key_ = value;
        return this;
      }

      public Builder clearKey() {
        result.hasKey = false;
        result.key_ = getDefaultInstance().getKey();
        return this;
      }
    }

    static {
      defaultInstance = new GetMetadataRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class GetMetadataResponse extends com.google.protobuf.GeneratedMessage {
    private GetMetadataResponse() {
      initFields();
    }

    private GetMetadataResponse(boolean noInit) {
    }

    private static final GetMetadataResponse defaultInstance;

    public static GetMetadataResponse getDefaultInstance() {
      return defaultInstance;
    }

    public GetMetadataResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetMetadataResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetMetadataResponse_fieldAccessorTable;
    }

    public static final int VERSION_FIELD_NUMBER = 1;

    private boolean hasVersion;

    private voldemort.client.protocol.pb.VProto.Versioned version_;

    public boolean hasVersion() {
      return hasVersion;
    }

    public voldemort.client.protocol.pb.VProto.Versioned getVersion() {
      return version_;
    }

    public static final int ERROR_FIELD_NUMBER = 2;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      version_ = voldemort.client.protocol.pb.VProto.Versioned.getDefaultInstance();
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasVersion()) {
        if (!getVersion().isInitialized()) {
          return false;
        }
      }
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasVersion()) {
        output.writeMessage(1, getVersion());
      }
      if (hasError()) {
        output.writeMessage(2, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasVersion()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getVersion());
      }
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasVersion()) {
          mergeVersion(other.getVersion());
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Versioned.Builder subBuilder = voldemort.client.protocol.pb.VProto.Versioned.newBuilder();
              if (hasVersion()) {
                subBuilder.mergeFrom(getVersion());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setVersion(subBuilder.buildPartial());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasVersion() {
        return result.hasVersion();
      }

      public voldemort.client.protocol.pb.VProto.Versioned getVersion() {
        return result.getVersion();
      }

      public Builder setVersion(voldemort.client.protocol.pb.VProto.Versioned value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasVersion = true;
        result.version_ = value;
        return this;
      }

      public Builder setVersion(voldemort.client.protocol.pb.VProto.Versioned.Builder builderForValue) {
        result.hasVersion = true;
        result.version_ = builderForValue.build();
        return this;
      }

      public Builder mergeVersion(voldemort.client.protocol.pb.VProto.Versioned value) {
        if (result.hasVersion() && result.version_ != voldemort.client.protocol.pb.VProto.Versioned.getDefaultInstance()) {
          result.version_ = voldemort.client.protocol.pb.VProto.Versioned.newBuilder(result.version_).mergeFrom(value).buildPartial();
        } else {
          result.version_ = value;
        }
        result.hasVersion = true;
        return this;
      }

      public Builder clearVersion() {
        result.hasVersion = false;
        result.version_ = voldemort.client.protocol.pb.VProto.Versioned.getDefaultInstance();
        return this;
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new GetMetadataResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class UpdateMetadataRequest extends com.google.protobuf.GeneratedMessage {
    private UpdateMetadataRequest() {
      initFields();
    }

    private UpdateMetadataRequest(boolean noInit) {
    }

    private static final UpdateMetadataRequest defaultInstance;

    public static UpdateMetadataRequest getDefaultInstance() {
      return defaultInstance;
    }

    public UpdateMetadataRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdateMetadataRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdateMetadataRequest_fieldAccessorTable;
    }

    public static final int KEY_FIELD_NUMBER = 1;

    private boolean hasKey;

    private com.google.protobuf.ByteString key_ = com.google.protobuf.ByteString.EMPTY;

    public boolean hasKey() {
      return hasKey;
    }

    public com.google.protobuf.ByteString getKey() {
      return key_;
    }

    public static final int VERSIONED_FIELD_NUMBER = 2;

    private boolean hasVersioned;

    private voldemort.client.protocol.pb.VProto.Versioned versioned_;

    public boolean hasVersioned() {
      return hasVersioned;
    }

    public voldemort.client.protocol.pb.VProto.Versioned getVersioned() {
      return versioned_;
    }

    private void initFields() {
      versioned_ = voldemort.client.protocol.pb.VProto.Versioned.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (!hasKey) {
        return false;
      }
      if (!hasVersioned) {
        return false;
      }
      if (!getVersioned().isInitialized()) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasKey()) {
        output.writeBytes(1, getKey());
      }
      if (hasVersioned()) {
        output.writeMessage(2, getVersioned());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasKey()) {
        size += com.google.protobuf.CodedOutputStream.computeBytesSize(1, getKey());
      }
      if (hasVersioned()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getVersioned());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasKey()) {
          setKey(other.getKey());
        }
        if (other.hasVersioned()) {
          mergeVersioned(other.getVersioned());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setKey(input.readBytes());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VProto.Versioned.Builder subBuilder = voldemort.client.protocol.pb.VProto.Versioned.newBuilder();
              if (hasVersioned()) {
                subBuilder.mergeFrom(getVersioned());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setVersioned(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasKey() {
        return result.hasKey();
      }

      public com.google.protobuf.ByteString getKey() {
        return result.getKey();
      }

      public Builder setKey(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasKey = true;
        result.key_ = value;
        return this;
      }

      public Builder clearKey() {
        result.hasKey = false;
        result.key_ = getDefaultInstance().getKey();
        return this;
      }

      public boolean hasVersioned() {
        return result.hasVersioned();
      }

      public voldemort.client.protocol.pb.VProto.Versioned getVersioned() {
        return result.getVersioned();
      }

      public Builder setVersioned(voldemort.client.protocol.pb.VProto.Versioned value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasVersioned = true;
        result.versioned_ = value;
        return this;
      }

      public Builder setVersioned(voldemort.client.protocol.pb.VProto.Versioned.Builder builderForValue) {
        result.hasVersioned = true;
        result.versioned_ = builderForValue.build();
        return this;
      }

      public Builder mergeVersioned(voldemort.client.protocol.pb.VProto.Versioned value) {
        if (result.hasVersioned() && result.versioned_ != voldemort.client.protocol.pb.VProto.Versioned.getDefaultInstance()) {
          result.versioned_ = voldemort.client.protocol.pb.VProto.Versioned.newBuilder(result.versioned_).mergeFrom(value).buildPartial();
        } else {
          result.versioned_ = value;
        }
        result.hasVersioned = true;
        return this;
      }

      public Builder clearVersioned() {
        result.hasVersioned = false;
        result.versioned_ = voldemort.client.protocol.pb.VProto.Versioned.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new UpdateMetadataRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class UpdateMetadataResponse extends com.google.protobuf.GeneratedMessage {
    private UpdateMetadataResponse() {
      initFields();
    }

    private UpdateMetadataResponse(boolean noInit) {
    }

    private static final UpdateMetadataResponse defaultInstance;

    public static UpdateMetadataResponse getDefaultInstance() {
      return defaultInstance;
    }

    public UpdateMetadataResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdateMetadataResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdateMetadataResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new UpdateMetadataResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class FileEntry extends com.google.protobuf.GeneratedMessage {
    private FileEntry() {
      initFields();
    }

    private FileEntry(boolean noInit) {
    }

    private static final FileEntry defaultInstance;

    public static FileEntry getDefaultInstance() {
      return defaultInstance;
    }

    public FileEntry getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FileEntry_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FileEntry_fieldAccessorTable;
    }

    public static final int FILE_NAME_FIELD_NUMBER = 1;

    private boolean hasFileName;

    private java.lang.String fileName_ = "";

    public boolean hasFileName() {
      return hasFileName;
    }

    public java.lang.String getFileName() {
      return fileName_;
    }

    public static final int FILE_SIZE_BYTES_FIELD_NUMBER = 2;

    private boolean hasFileSizeBytes;

    private long fileSizeBytes_ = 0L;

    public boolean hasFileSizeBytes() {
      return hasFileSizeBytes;
    }

    public long getFileSizeBytes() {
      return fileSizeBytes_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasFileName) {
        return false;
      }
      if (!hasFileSizeBytes) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasFileName()) {
        output.writeString(1, getFileName());
      }
      if (hasFileSizeBytes()) {
        output.writeInt64(2, getFileSizeBytes());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasFileName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getFileName());
      }
      if (hasFileSizeBytes()) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, getFileSizeBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.FileEntry parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FileEntry parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FileEntry parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FileEntry parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FileEntry parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FileEntry parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FileEntry parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FileEntry parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FileEntry parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FileEntry parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.FileEntry prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.FileEntry result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.FileEntry();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.FileEntry internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.FileEntry();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.FileEntry.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.FileEntry getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.FileEntry.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.FileEntry build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.FileEntry buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.FileEntry buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.FileEntry returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.FileEntry) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.FileEntry) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.FileEntry other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.FileEntry.getDefaultInstance()) {
          return this;
        }
        if (other.hasFileName()) {
          setFileName(other.getFileName());
        }
        if (other.hasFileSizeBytes()) {
          setFileSizeBytes(other.getFileSizeBytes());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setFileName(input.readString());
              break;
            }
            case 16:
            {
              setFileSizeBytes(input.readInt64());
              break;
            }
          }
        }
      }

      public boolean hasFileName() {
        return result.hasFileName();
      }

      public java.lang.String getFileName() {
        return result.getFileName();
      }

      public Builder setFileName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasFileName = true;
        result.fileName_ = value;
        return this;
      }

      public Builder clearFileName() {
        result.hasFileName = false;
        result.fileName_ = getDefaultInstance().getFileName();
        return this;
      }

      public boolean hasFileSizeBytes() {
        return result.hasFileSizeBytes();
      }

      public long getFileSizeBytes() {
        return result.getFileSizeBytes();
      }

      public Builder setFileSizeBytes(long value) {
        result.hasFileSizeBytes = true;
        result.fileSizeBytes_ = value;
        return this;
      }

      public Builder clearFileSizeBytes() {
        result.hasFileSizeBytes = false;
        result.fileSizeBytes_ = 0L;
        return this;
      }
    }

    static {
      defaultInstance = new FileEntry(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class PartitionEntry extends com.google.protobuf.GeneratedMessage {
    private PartitionEntry() {
      initFields();
    }

    private PartitionEntry(boolean noInit) {
    }

    private static final PartitionEntry defaultInstance;

    public static PartitionEntry getDefaultInstance() {
      return defaultInstance;
    }

    public PartitionEntry getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_PartitionEntry_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_PartitionEntry_fieldAccessorTable;
    }

    public static final int KEY_FIELD_NUMBER = 1;

    private boolean hasKey;

    private com.google.protobuf.ByteString key_ = com.google.protobuf.ByteString.EMPTY;

    public boolean hasKey() {
      return hasKey;
    }

    public com.google.protobuf.ByteString getKey() {
      return key_;
    }

    public static final int VERSIONED_FIELD_NUMBER = 2;

    private boolean hasVersioned;

    private voldemort.client.protocol.pb.VProto.Versioned versioned_;

    public boolean hasVersioned() {
      return hasVersioned;
    }

    public voldemort.client.protocol.pb.VProto.Versioned getVersioned() {
      return versioned_;
    }

    private void initFields() {
      versioned_ = voldemort.client.protocol.pb.VProto.Versioned.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (!hasKey) {
        return false;
      }
      if (!hasVersioned) {
        return false;
      }
      if (!getVersioned().isInitialized()) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasKey()) {
        output.writeBytes(1, getKey());
      }
      if (hasVersioned()) {
        output.writeMessage(2, getVersioned());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasKey()) {
        size += com.google.protobuf.CodedOutputStream.computeBytesSize(1, getKey());
      }
      if (hasVersioned()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getVersioned());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionEntry parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionEntry parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionEntry parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionEntry parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionEntry parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionEntry parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionEntry parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionEntry parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionEntry parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionEntry parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.PartitionEntry prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.PartitionEntry result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.PartitionEntry();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.PartitionEntry internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.PartitionEntry();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.PartitionEntry.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionEntry getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.PartitionEntry.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionEntry build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.PartitionEntry buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionEntry buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.PartitionEntry returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.PartitionEntry) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.PartitionEntry) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.PartitionEntry other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.PartitionEntry.getDefaultInstance()) {
          return this;
        }
        if (other.hasKey()) {
          setKey(other.getKey());
        }
        if (other.hasVersioned()) {
          mergeVersioned(other.getVersioned());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setKey(input.readBytes());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VProto.Versioned.Builder subBuilder = voldemort.client.protocol.pb.VProto.Versioned.newBuilder();
              if (hasVersioned()) {
                subBuilder.mergeFrom(getVersioned());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setVersioned(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasKey() {
        return result.hasKey();
      }

      public com.google.protobuf.ByteString getKey() {
        return result.getKey();
      }

      public Builder setKey(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasKey = true;
        result.key_ = value;
        return this;
      }

      public Builder clearKey() {
        result.hasKey = false;
        result.key_ = getDefaultInstance().getKey();
        return this;
      }

      public boolean hasVersioned() {
        return result.hasVersioned();
      }

      public voldemort.client.protocol.pb.VProto.Versioned getVersioned() {
        return result.getVersioned();
      }

      public Builder setVersioned(voldemort.client.protocol.pb.VProto.Versioned value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasVersioned = true;
        result.versioned_ = value;
        return this;
      }

      public Builder setVersioned(voldemort.client.protocol.pb.VProto.Versioned.Builder builderForValue) {
        result.hasVersioned = true;
        result.versioned_ = builderForValue.build();
        return this;
      }

      public Builder mergeVersioned(voldemort.client.protocol.pb.VProto.Versioned value) {
        if (result.hasVersioned() && result.versioned_ != voldemort.client.protocol.pb.VProto.Versioned.getDefaultInstance()) {
          result.versioned_ = voldemort.client.protocol.pb.VProto.Versioned.newBuilder(result.versioned_).mergeFrom(value).buildPartial();
        } else {
          result.versioned_ = value;
        }
        result.hasVersioned = true;
        return this;
      }

      public Builder clearVersioned() {
        result.hasVersioned = false;
        result.versioned_ = voldemort.client.protocol.pb.VProto.Versioned.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new PartitionEntry(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class UpdatePartitionEntriesRequest extends com.google.protobuf.GeneratedMessage {
    private UpdatePartitionEntriesRequest() {
      initFields();
    }

    private UpdatePartitionEntriesRequest(boolean noInit) {
    }

    private static final UpdatePartitionEntriesRequest defaultInstance;

    public static UpdatePartitionEntriesRequest getDefaultInstance() {
      return defaultInstance;
    }

    public UpdatePartitionEntriesRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdatePartitionEntriesRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdatePartitionEntriesRequest_fieldAccessorTable;
    }

    public static final int STORE_FIELD_NUMBER = 1;

    private boolean hasStore;

    private java.lang.String store_ = "";

    public boolean hasStore() {
      return hasStore;
    }

    public java.lang.String getStore() {
      return store_;
    }

    public static final int PARTITION_ENTRY_FIELD_NUMBER = 2;

    private boolean hasPartitionEntry;

    private voldemort.client.protocol.pb.VAdminProto.PartitionEntry partitionEntry_;

    public boolean hasPartitionEntry() {
      return hasPartitionEntry;
    }

    public voldemort.client.protocol.pb.VAdminProto.PartitionEntry getPartitionEntry() {
      return partitionEntry_;
    }

    public static final int FILTER_FIELD_NUMBER = 3;

    private boolean hasFilter;

    private voldemort.client.protocol.pb.VAdminProto.VoldemortFilter filter_;

    public boolean hasFilter() {
      return hasFilter;
    }

    public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter getFilter() {
      return filter_;
    }

    private void initFields() {
      partitionEntry_ = voldemort.client.protocol.pb.VAdminProto.PartitionEntry.getDefaultInstance();
      filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (!hasStore) {
        return false;
      }
      if (!hasPartitionEntry) {
        return false;
      }
      if (!getPartitionEntry().isInitialized()) {
        return false;
      }
      if (hasFilter()) {
        if (!getFilter().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStore()) {
        output.writeString(1, getStore());
      }
      if (hasPartitionEntry()) {
        output.writeMessage(2, getPartitionEntry());
      }
      if (hasFilter()) {
        output.writeMessage(3, getFilter());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStore()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStore());
      }
      if (hasPartitionEntry()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getPartitionEntry());
      }
      if (hasFilter()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, getFilter());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStore()) {
          setStore(other.getStore());
        }
        if (other.hasPartitionEntry()) {
          mergePartitionEntry(other.getPartitionEntry());
        }
        if (other.hasFilter()) {
          mergeFilter(other.getFilter());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStore(input.readString());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VAdminProto.PartitionEntry.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.PartitionEntry.newBuilder();
              if (hasPartitionEntry()) {
                subBuilder.mergeFrom(getPartitionEntry());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setPartitionEntry(subBuilder.buildPartial());
              break;
            }
            case 26:
            {
              voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.newBuilder();
              if (hasFilter()) {
                subBuilder.mergeFrom(getFilter());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setFilter(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasStore() {
        return result.hasStore();
      }

      public java.lang.String getStore() {
        return result.getStore();
      }

      public Builder setStore(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStore = true;
        result.store_ = value;
        return this;
      }

      public Builder clearStore() {
        result.hasStore = false;
        result.store_ = getDefaultInstance().getStore();
        return this;
      }

      public boolean hasPartitionEntry() {
        return result.hasPartitionEntry();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionEntry getPartitionEntry() {
        return result.getPartitionEntry();
      }

      public Builder setPartitionEntry(voldemort.client.protocol.pb.VAdminProto.PartitionEntry value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasPartitionEntry = true;
        result.partitionEntry_ = value;
        return this;
      }

      public Builder setPartitionEntry(voldemort.client.protocol.pb.VAdminProto.PartitionEntry.Builder builderForValue) {
        result.hasPartitionEntry = true;
        result.partitionEntry_ = builderForValue.build();
        return this;
      }

      public Builder mergePartitionEntry(voldemort.client.protocol.pb.VAdminProto.PartitionEntry value) {
        if (result.hasPartitionEntry() && result.partitionEntry_ != voldemort.client.protocol.pb.VAdminProto.PartitionEntry.getDefaultInstance()) {
          result.partitionEntry_ = voldemort.client.protocol.pb.VAdminProto.PartitionEntry.newBuilder(result.partitionEntry_).mergeFrom(value).buildPartial();
        } else {
          result.partitionEntry_ = value;
        }
        result.hasPartitionEntry = true;
        return this;
      }

      public Builder clearPartitionEntry() {
        result.hasPartitionEntry = false;
        result.partitionEntry_ = voldemort.client.protocol.pb.VAdminProto.PartitionEntry.getDefaultInstance();
        return this;
      }

      public boolean hasFilter() {
        return result.hasFilter();
      }

      public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter getFilter() {
        return result.getFilter();
      }

      public Builder setFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasFilter = true;
        result.filter_ = value;
        return this;
      }

      public Builder setFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.Builder builderForValue) {
        result.hasFilter = true;
        result.filter_ = builderForValue.build();
        return this;
      }

      public Builder mergeFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter value) {
        if (result.hasFilter() && result.filter_ != voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance()) {
          result.filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.newBuilder(result.filter_).mergeFrom(value).buildPartial();
        } else {
          result.filter_ = value;
        }
        result.hasFilter = true;
        return this;
      }

      public Builder clearFilter() {
        result.hasFilter = false;
        result.filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new UpdatePartitionEntriesRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class UpdatePartitionEntriesResponse extends com.google.protobuf.GeneratedMessage {
    private UpdatePartitionEntriesResponse() {
      initFields();
    }

    private UpdatePartitionEntriesResponse(boolean noInit) {
    }

    private static final UpdatePartitionEntriesResponse defaultInstance;

    public static UpdatePartitionEntriesResponse getDefaultInstance() {
      return defaultInstance;
    }

    public UpdatePartitionEntriesResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdatePartitionEntriesResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdatePartitionEntriesResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new UpdatePartitionEntriesResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class VoldemortFilter extends com.google.protobuf.GeneratedMessage {
    private VoldemortFilter() {
      initFields();
    }

    private VoldemortFilter(boolean noInit) {
    }

    private static final VoldemortFilter defaultInstance;

    public static VoldemortFilter getDefaultInstance() {
      return defaultInstance;
    }

    public VoldemortFilter getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_VoldemortFilter_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_VoldemortFilter_fieldAccessorTable;
    }

    public static final int NAME_FIELD_NUMBER = 1;

    private boolean hasName;

    private java.lang.String name_ = "";

    public boolean hasName() {
      return hasName;
    }

    public java.lang.String getName() {
      return name_;
    }

    public static final int DATA_FIELD_NUMBER = 2;

    private boolean hasData;

    private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;

    public boolean hasData() {
      return hasData;
    }

    public com.google.protobuf.ByteString getData() {
      return data_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasName) {
        return false;
      }
      if (!hasData) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasName()) {
        output.writeString(1, getName());
      }
      if (hasData()) {
        output.writeBytes(2, getData());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getName());
      }
      if (hasData()) {
        size += com.google.protobuf.CodedOutputStream.computeBytesSize(2, getData());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortFilter parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortFilter parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortFilter parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortFilter parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortFilter parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortFilter parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortFilter parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortFilter parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortFilter parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortFilter parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.VoldemortFilter result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.VoldemortFilter();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.VoldemortFilter internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.VoldemortFilter();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.VoldemortFilter buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.VoldemortFilter returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.VoldemortFilter) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.VoldemortFilter) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance()) {
          return this;
        }
        if (other.hasName()) {
          setName(other.getName());
        }
        if (other.hasData()) {
          setData(other.getData());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setName(input.readString());
              break;
            }
            case 18:
            {
              setData(input.readBytes());
              break;
            }
          }
        }
      }

      public boolean hasName() {
        return result.hasName();
      }

      public java.lang.String getName() {
        return result.getName();
      }

      public Builder setName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasName = true;
        result.name_ = value;
        return this;
      }

      public Builder clearName() {
        result.hasName = false;
        result.name_ = getDefaultInstance().getName();
        return this;
      }

      public boolean hasData() {
        return result.hasData();
      }

      public com.google.protobuf.ByteString getData() {
        return result.getData();
      }

      public Builder setData(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasData = true;
        result.data_ = value;
        return this;
      }

      public Builder clearData() {
        result.hasData = false;
        result.data_ = getDefaultInstance().getData();
        return this;
      }
    }

    static {
      defaultInstance = new VoldemortFilter(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class UpdateSlopEntriesRequest extends com.google.protobuf.GeneratedMessage {
    private UpdateSlopEntriesRequest() {
      initFields();
    }

    private UpdateSlopEntriesRequest(boolean noInit) {
    }

    private static final UpdateSlopEntriesRequest defaultInstance;

    public static UpdateSlopEntriesRequest getDefaultInstance() {
      return defaultInstance;
    }

    public UpdateSlopEntriesRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdateSlopEntriesRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdateSlopEntriesRequest_fieldAccessorTable;
    }

    public static final int STORE_FIELD_NUMBER = 1;

    private boolean hasStore;

    private java.lang.String store_ = "";

    public boolean hasStore() {
      return hasStore;
    }

    public java.lang.String getStore() {
      return store_;
    }

    public static final int KEY_FIELD_NUMBER = 2;

    private boolean hasKey;

    private com.google.protobuf.ByteString key_ = com.google.protobuf.ByteString.EMPTY;

    public boolean hasKey() {
      return hasKey;
    }

    public com.google.protobuf.ByteString getKey() {
      return key_;
    }

    public static final int VERSION_FIELD_NUMBER = 3;

    private boolean hasVersion;

    private voldemort.client.protocol.pb.VProto.VectorClock version_;

    public boolean hasVersion() {
      return hasVersion;
    }

    public voldemort.client.protocol.pb.VProto.VectorClock getVersion() {
      return version_;
    }

    public static final int REQUEST_TYPE_FIELD_NUMBER = 4;

    private boolean hasRequestType;

    private voldemort.client.protocol.pb.VProto.RequestType requestType_;

    public boolean hasRequestType() {
      return hasRequestType;
    }

    public voldemort.client.protocol.pb.VProto.RequestType getRequestType() {
      return requestType_;
    }

    public static final int VALUE_FIELD_NUMBER = 5;

    private boolean hasValue;

    private com.google.protobuf.ByteString value_ = com.google.protobuf.ByteString.EMPTY;

    public boolean hasValue() {
      return hasValue;
    }

    public com.google.protobuf.ByteString getValue() {
      return value_;
    }

    public static final int TRANSFORM_FIELD_NUMBER = 6;

    private boolean hasTransform;

    private com.google.protobuf.ByteString transform_ = com.google.protobuf.ByteString.EMPTY;

    public boolean hasTransform() {
      return hasTransform;
    }

    public com.google.protobuf.ByteString getTransform() {
      return transform_;
    }

    private void initFields() {
      version_ = voldemort.client.protocol.pb.VProto.VectorClock.getDefaultInstance();
      requestType_ = voldemort.client.protocol.pb.VProto.RequestType.GET;
    }

    public final boolean isInitialized() {
      if (!hasStore) {
        return false;
      }
      if (!hasKey) {
        return false;
      }
      if (!hasVersion) {
        return false;
      }
      if (!hasRequestType) {
        return false;
      }
      if (!getVersion().isInitialized()) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStore()) {
        output.writeString(1, getStore());
      }
      if (hasKey()) {
        output.writeBytes(2, getKey());
      }
      if (hasVersion()) {
        output.writeMessage(3, getVersion());
      }
      if (hasRequestType()) {
        output.writeEnum(4, getRequestType().getNumber());
      }
      if (hasValue()) {
        output.writeBytes(5, getValue());
      }
      if (hasTransform()) {
        output.writeBytes(6, getTransform());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStore()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStore());
      }
      if (hasKey()) {
        size += com.google.protobuf.CodedOutputStream.computeBytesSize(2, getKey());
      }
      if (hasVersion()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, getVersion());
      }
      if (hasRequestType()) {
        size += com.google.protobuf.CodedOutputStream.computeEnumSize(4, getRequestType().getNumber());
      }
      if (hasValue()) {
        size += com.google.protobuf.CodedOutputStream.computeBytesSize(5, getValue());
      }
      if (hasTransform()) {
        size += com.google.protobuf.CodedOutputStream.computeBytesSize(6, getTransform());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStore()) {
          setStore(other.getStore());
        }
        if (other.hasKey()) {
          setKey(other.getKey());
        }
        if (other.hasVersion()) {
          mergeVersion(other.getVersion());
        }
        if (other.hasRequestType()) {
          setRequestType(other.getRequestType());
        }
        if (other.hasValue()) {
          setValue(other.getValue());
        }
        if (other.hasTransform()) {
          setTransform(other.getTransform());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStore(input.readString());
              break;
            }
            case 18:
            {
              setKey(input.readBytes());
              break;
            }
            case 26:
            {
              voldemort.client.protocol.pb.VProto.VectorClock.Builder subBuilder = voldemort.client.protocol.pb.VProto.VectorClock.newBuilder();
              if (hasVersion()) {
                subBuilder.mergeFrom(getVersion());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setVersion(subBuilder.buildPartial());
              break;
            }
            case 32:
            {
              int rawValue = input.readEnum();
              voldemort.client.protocol.pb.VProto.RequestType value = voldemort.client.protocol.pb.VProto.RequestType.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(4, rawValue);
              } else {
                setRequestType(value);
              }
              break;
            }
            case 42:
            {
              setValue(input.readBytes());
              break;
            }
            case 50:
            {
              setTransform(input.readBytes());
              break;
            }
          }
        }
      }

      public boolean hasStore() {
        return result.hasStore();
      }

      public java.lang.String getStore() {
        return result.getStore();
      }

      public Builder setStore(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStore = true;
        result.store_ = value;
        return this;
      }

      public Builder clearStore() {
        result.hasStore = false;
        result.store_ = getDefaultInstance().getStore();
        return this;
      }

      public boolean hasKey() {
        return result.hasKey();
      }

      public com.google.protobuf.ByteString getKey() {
        return result.getKey();
      }

      public Builder setKey(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasKey = true;
        result.key_ = value;
        return this;
      }

      public Builder clearKey() {
        result.hasKey = false;
        result.key_ = getDefaultInstance().getKey();
        return this;
      }

      public boolean hasVersion() {
        return result.hasVersion();
      }

      public voldemort.client.protocol.pb.VProto.VectorClock getVersion() {
        return result.getVersion();
      }

      public Builder setVersion(voldemort.client.protocol.pb.VProto.VectorClock value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasVersion = true;
        result.version_ = value;
        return this;
      }

      public Builder setVersion(voldemort.client.protocol.pb.VProto.VectorClock.Builder builderForValue) {
        result.hasVersion = true;
        result.version_ = builderForValue.build();
        return this;
      }

      public Builder mergeVersion(voldemort.client.protocol.pb.VProto.VectorClock value) {
        if (result.hasVersion() && result.version_ != voldemort.client.protocol.pb.VProto.VectorClock.getDefaultInstance()) {
          result.version_ = voldemort.client.protocol.pb.VProto.VectorClock.newBuilder(result.version_).mergeFrom(value).buildPartial();
        } else {
          result.version_ = value;
        }
        result.hasVersion = true;
        return this;
      }

      public Builder clearVersion() {
        result.hasVersion = false;
        result.version_ = voldemort.client.protocol.pb.VProto.VectorClock.getDefaultInstance();
        return this;
      }

      public boolean hasRequestType() {
        return result.hasRequestType();
      }

      public voldemort.client.protocol.pb.VProto.RequestType getRequestType() {
        return result.getRequestType();
      }

      public Builder setRequestType(voldemort.client.protocol.pb.VProto.RequestType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasRequestType = true;
        result.requestType_ = value;
        return this;
      }

      public Builder clearRequestType() {
        result.hasRequestType = false;
        result.requestType_ = voldemort.client.protocol.pb.VProto.RequestType.GET;
        return this;
      }

      public boolean hasValue() {
        return result.hasValue();
      }

      public com.google.protobuf.ByteString getValue() {
        return result.getValue();
      }

      public Builder setValue(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasValue = true;
        result.value_ = value;
        return this;
      }

      public Builder clearValue() {
        result.hasValue = false;
        result.value_ = getDefaultInstance().getValue();
        return this;
      }

      public boolean hasTransform() {
        return result.hasTransform();
      }

      public com.google.protobuf.ByteString getTransform() {
        return result.getTransform();
      }

      public Builder setTransform(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasTransform = true;
        result.transform_ = value;
        return this;
      }

      public Builder clearTransform() {
        result.hasTransform = false;
        result.transform_ = getDefaultInstance().getTransform();
        return this;
      }
    }

    static {
      defaultInstance = new UpdateSlopEntriesRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class UpdateSlopEntriesResponse extends com.google.protobuf.GeneratedMessage {
    private UpdateSlopEntriesResponse() {
      initFields();
    }

    private UpdateSlopEntriesResponse(boolean noInit) {
    }

    private static final UpdateSlopEntriesResponse defaultInstance;

    public static UpdateSlopEntriesResponse getDefaultInstance() {
      return defaultInstance;
    }

    public UpdateSlopEntriesResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdateSlopEntriesResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_UpdateSlopEntriesResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new UpdateSlopEntriesResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class FetchPartitionFilesRequest extends com.google.protobuf.GeneratedMessage {
    private FetchPartitionFilesRequest() {
      initFields();
    }

    private FetchPartitionFilesRequest(boolean noInit) {
    }

    private static final FetchPartitionFilesRequest defaultInstance;

    public static FetchPartitionFilesRequest getDefaultInstance() {
      return defaultInstance;
    }

    public FetchPartitionFilesRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FetchPartitionFilesRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FetchPartitionFilesRequest_fieldAccessorTable;
    }

    public static final int STORE_FIELD_NUMBER = 1;

    private boolean hasStore;

    private java.lang.String store_ = "";

    public boolean hasStore() {
      return hasStore;
    }

    public java.lang.String getStore() {
      return store_;
    }

    public static final int REPLICA_TO_PARTITION_FIELD_NUMBER = 2;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> replicaToPartition_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> getReplicaToPartitionList() {
      return replicaToPartition_;
    }

    public int getReplicaToPartitionCount() {
      return replicaToPartition_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getReplicaToPartition(int index) {
      return replicaToPartition_.get(index);
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStore) {
        return false;
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStore()) {
        output.writeString(1, getStore());
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        output.writeMessage(2, element);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStore()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStore());
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, element);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.replicaToPartition_ != java.util.Collections.EMPTY_LIST) {
          result.replicaToPartition_ = java.util.Collections.unmodifiableList(result.replicaToPartition_);
        }
        voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStore()) {
          setStore(other.getStore());
        }
        if (!other.replicaToPartition_.isEmpty()) {
          if (result.replicaToPartition_.isEmpty()) {
            result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
          }
          result.replicaToPartition_.addAll(other.replicaToPartition_);
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStore(input.readString());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.PartitionTuple.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addReplicaToPartition(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasStore() {
        return result.hasStore();
      }

      public java.lang.String getStore() {
        return result.getStore();
      }

      public Builder setStore(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStore = true;
        result.store_ = value;
        return this;
      }

      public Builder clearStore() {
        result.hasStore = false;
        result.store_ = getDefaultInstance().getStore();
        return this;
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> getReplicaToPartitionList() {
        return java.util.Collections.unmodifiableList(result.replicaToPartition_);
      }

      public int getReplicaToPartitionCount() {
        return result.getReplicaToPartitionCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getReplicaToPartition(int index) {
        return result.getReplicaToPartition(index);
      }

      public Builder setReplicaToPartition(int index, voldemort.client.protocol.pb.VAdminProto.PartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.replicaToPartition_.set(index, value);
        return this;
      }

      public Builder setReplicaToPartition(int index, voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder builderForValue) {
        result.replicaToPartition_.set(index, builderForValue.build());
        return this;
      }

      public Builder addReplicaToPartition(voldemort.client.protocol.pb.VAdminProto.PartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        result.replicaToPartition_.add(value);
        return this;
      }

      public Builder addReplicaToPartition(voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder builderForValue) {
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        result.replicaToPartition_.add(builderForValue.build());
        return this;
      }

      public Builder addAllReplicaToPartition(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.PartitionTuple> values) {
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        super.addAll(values, result.replicaToPartition_);
        return this;
      }

      public Builder clearReplicaToPartition() {
        result.replicaToPartition_ = java.util.Collections.emptyList();
        return this;
      }
    }

    static {
      defaultInstance = new FetchPartitionFilesRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class FetchPartitionEntriesRequest extends com.google.protobuf.GeneratedMessage {
    private FetchPartitionEntriesRequest() {
      initFields();
    }

    private FetchPartitionEntriesRequest(boolean noInit) {
    }

    private static final FetchPartitionEntriesRequest defaultInstance;

    public static FetchPartitionEntriesRequest getDefaultInstance() {
      return defaultInstance;
    }

    public FetchPartitionEntriesRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FetchPartitionEntriesRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FetchPartitionEntriesRequest_fieldAccessorTable;
    }

    public static final int REPLICA_TO_PARTITION_FIELD_NUMBER = 1;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> replicaToPartition_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> getReplicaToPartitionList() {
      return replicaToPartition_;
    }

    public int getReplicaToPartitionCount() {
      return replicaToPartition_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getReplicaToPartition(int index) {
      return replicaToPartition_.get(index);
    }

    public static final int STORE_FIELD_NUMBER = 2;

    private boolean hasStore;

    private java.lang.String store_ = "";

    public boolean hasStore() {
      return hasStore;
    }

    public java.lang.String getStore() {
      return store_;
    }

    public static final int FILTER_FIELD_NUMBER = 3;

    private boolean hasFilter;

    private voldemort.client.protocol.pb.VAdminProto.VoldemortFilter filter_;

    public boolean hasFilter() {
      return hasFilter;
    }

    public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter getFilter() {
      return filter_;
    }

    public static final int FETCH_VALUES_FIELD_NUMBER = 4;

    private boolean hasFetchValues;

    private boolean fetchValues_ = false;

    public boolean hasFetchValues() {
      return hasFetchValues;
    }

    public boolean getFetchValues() {
      return fetchValues_;
    }

    public static final int SKIP_RECORDS_FIELD_NUMBER = 5;

    private boolean hasSkipRecords;

    private long skipRecords_ = 0L;

    public boolean hasSkipRecords() {
      return hasSkipRecords;
    }

    public long getSkipRecords() {
      return skipRecords_;
    }

    public static final int INITIAL_CLUSTER_FIELD_NUMBER = 6;

    private boolean hasInitialCluster;

    private java.lang.String initialCluster_ = "";

    public boolean hasInitialCluster() {
      return hasInitialCluster;
    }

    public java.lang.String getInitialCluster() {
      return initialCluster_;
    }

    private void initFields() {
      filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (!hasStore) {
        return false;
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      if (hasFilter()) {
        if (!getFilter().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        output.writeMessage(1, element);
      }
      if (hasStore()) {
        output.writeString(2, getStore());
      }
      if (hasFilter()) {
        output.writeMessage(3, getFilter());
      }
      if (hasFetchValues()) {
        output.writeBool(4, getFetchValues());
      }
      if (hasSkipRecords()) {
        output.writeInt64(5, getSkipRecords());
      }
      if (hasInitialCluster()) {
        output.writeString(6, getInitialCluster());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, element);
      }
      if (hasStore()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(2, getStore());
      }
      if (hasFilter()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, getFilter());
      }
      if (hasFetchValues()) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(4, getFetchValues());
      }
      if (hasSkipRecords()) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(5, getSkipRecords());
      }
      if (hasInitialCluster()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(6, getInitialCluster());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.replicaToPartition_ != java.util.Collections.EMPTY_LIST) {
          result.replicaToPartition_ = java.util.Collections.unmodifiableList(result.replicaToPartition_);
        }
        voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.getDefaultInstance()) {
          return this;
        }
        if (!other.replicaToPartition_.isEmpty()) {
          if (result.replicaToPartition_.isEmpty()) {
            result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
          }
          result.replicaToPartition_.addAll(other.replicaToPartition_);
        }
        if (other.hasStore()) {
          setStore(other.getStore());
        }
        if (other.hasFilter()) {
          mergeFilter(other.getFilter());
        }
        if (other.hasFetchValues()) {
          setFetchValues(other.getFetchValues());
        }
        if (other.hasSkipRecords()) {
          setSkipRecords(other.getSkipRecords());
        }
        if (other.hasInitialCluster()) {
          setInitialCluster(other.getInitialCluster());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.PartitionTuple.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addReplicaToPartition(subBuilder.buildPartial());
              break;
            }
            case 18:
            {
              setStore(input.readString());
              break;
            }
            case 26:
            {
              voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.newBuilder();
              if (hasFilter()) {
                subBuilder.mergeFrom(getFilter());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setFilter(subBuilder.buildPartial());
              break;
            }
            case 32:
            {
              setFetchValues(input.readBool());
              break;
            }
            case 40:
            {
              setSkipRecords(input.readInt64());
              break;
            }
            case 50:
            {
              setInitialCluster(input.readString());
              break;
            }
          }
        }
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> getReplicaToPartitionList() {
        return java.util.Collections.unmodifiableList(result.replicaToPartition_);
      }

      public int getReplicaToPartitionCount() {
        return result.getReplicaToPartitionCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getReplicaToPartition(int index) {
        return result.getReplicaToPartition(index);
      }

      public Builder setReplicaToPartition(int index, voldemort.client.protocol.pb.VAdminProto.PartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.replicaToPartition_.set(index, value);
        return this;
      }

      public Builder setReplicaToPartition(int index, voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder builderForValue) {
        result.replicaToPartition_.set(index, builderForValue.build());
        return this;
      }

      public Builder addReplicaToPartition(voldemort.client.protocol.pb.VAdminProto.PartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        result.replicaToPartition_.add(value);
        return this;
      }

      public Builder addReplicaToPartition(voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder builderForValue) {
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        result.replicaToPartition_.add(builderForValue.build());
        return this;
      }

      public Builder addAllReplicaToPartition(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.PartitionTuple> values) {
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        super.addAll(values, result.replicaToPartition_);
        return this;
      }

      public Builder clearReplicaToPartition() {
        result.replicaToPartition_ = java.util.Collections.emptyList();
        return this;
      }

      public boolean hasStore() {
        return result.hasStore();
      }

      public java.lang.String getStore() {
        return result.getStore();
      }

      public Builder setStore(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStore = true;
        result.store_ = value;
        return this;
      }

      public Builder clearStore() {
        result.hasStore = false;
        result.store_ = getDefaultInstance().getStore();
        return this;
      }

      public boolean hasFilter() {
        return result.hasFilter();
      }

      public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter getFilter() {
        return result.getFilter();
      }

      public Builder setFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasFilter = true;
        result.filter_ = value;
        return this;
      }

      public Builder setFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.Builder builderForValue) {
        result.hasFilter = true;
        result.filter_ = builderForValue.build();
        return this;
      }

      public Builder mergeFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter value) {
        if (result.hasFilter() && result.filter_ != voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance()) {
          result.filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.newBuilder(result.filter_).mergeFrom(value).buildPartial();
        } else {
          result.filter_ = value;
        }
        result.hasFilter = true;
        return this;
      }

      public Builder clearFilter() {
        result.hasFilter = false;
        result.filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance();
        return this;
      }

      public boolean hasFetchValues() {
        return result.hasFetchValues();
      }

      public boolean getFetchValues() {
        return result.getFetchValues();
      }

      public Builder setFetchValues(boolean value) {
        result.hasFetchValues = true;
        result.fetchValues_ = value;
        return this;
      }

      public Builder clearFetchValues() {
        result.hasFetchValues = false;
        result.fetchValues_ = false;
        return this;
      }

      public boolean hasSkipRecords() {
        return result.hasSkipRecords();
      }

      public long getSkipRecords() {
        return result.getSkipRecords();
      }

      public Builder setSkipRecords(long value) {
        result.hasSkipRecords = true;
        result.skipRecords_ = value;
        return this;
      }

      public Builder clearSkipRecords() {
        result.hasSkipRecords = false;
        result.skipRecords_ = 0L;
        return this;
      }

      public boolean hasInitialCluster() {
        return result.hasInitialCluster();
      }

      public java.lang.String getInitialCluster() {
        return result.getInitialCluster();
      }

      public Builder setInitialCluster(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasInitialCluster = true;
        result.initialCluster_ = value;
        return this;
      }

      public Builder clearInitialCluster() {
        result.hasInitialCluster = false;
        result.initialCluster_ = getDefaultInstance().getInitialCluster();
        return this;
      }
    }

    static {
      defaultInstance = new FetchPartitionEntriesRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class FetchPartitionEntriesResponse extends com.google.protobuf.GeneratedMessage {
    private FetchPartitionEntriesResponse() {
      initFields();
    }

    private FetchPartitionEntriesResponse(boolean noInit) {
    }

    private static final FetchPartitionEntriesResponse defaultInstance;

    public static FetchPartitionEntriesResponse getDefaultInstance() {
      return defaultInstance;
    }

    public FetchPartitionEntriesResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FetchPartitionEntriesResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FetchPartitionEntriesResponse_fieldAccessorTable;
    }

    public static final int PARTITION_ENTRY_FIELD_NUMBER = 1;

    private boolean hasPartitionEntry;

    private voldemort.client.protocol.pb.VAdminProto.PartitionEntry partitionEntry_;

    public boolean hasPartitionEntry() {
      return hasPartitionEntry;
    }

    public voldemort.client.protocol.pb.VAdminProto.PartitionEntry getPartitionEntry() {
      return partitionEntry_;
    }

    public static final int KEY_FIELD_NUMBER = 2;

    private boolean hasKey;

    private com.google.protobuf.ByteString key_ = com.google.protobuf.ByteString.EMPTY;

    public boolean hasKey() {
      return hasKey;
    }

    public com.google.protobuf.ByteString getKey() {
      return key_;
    }

    public static final int ERROR_FIELD_NUMBER = 3;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      partitionEntry_ = voldemort.client.protocol.pb.VAdminProto.PartitionEntry.getDefaultInstance();
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasPartitionEntry()) {
        if (!getPartitionEntry().isInitialized()) {
          return false;
        }
      }
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasPartitionEntry()) {
        output.writeMessage(1, getPartitionEntry());
      }
      if (hasKey()) {
        output.writeBytes(2, getKey());
      }
      if (hasError()) {
        output.writeMessage(3, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasPartitionEntry()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getPartitionEntry());
      }
      if (hasKey()) {
        size += com.google.protobuf.CodedOutputStream.computeBytesSize(2, getKey());
      }
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasPartitionEntry()) {
          mergePartitionEntry(other.getPartitionEntry());
        }
        if (other.hasKey()) {
          setKey(other.getKey());
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VAdminProto.PartitionEntry.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.PartitionEntry.newBuilder();
              if (hasPartitionEntry()) {
                subBuilder.mergeFrom(getPartitionEntry());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setPartitionEntry(subBuilder.buildPartial());
              break;
            }
            case 18:
            {
              setKey(input.readBytes());
              break;
            }
            case 26:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasPartitionEntry() {
        return result.hasPartitionEntry();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionEntry getPartitionEntry() {
        return result.getPartitionEntry();
      }

      public Builder setPartitionEntry(voldemort.client.protocol.pb.VAdminProto.PartitionEntry value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasPartitionEntry = true;
        result.partitionEntry_ = value;
        return this;
      }

      public Builder setPartitionEntry(voldemort.client.protocol.pb.VAdminProto.PartitionEntry.Builder builderForValue) {
        result.hasPartitionEntry = true;
        result.partitionEntry_ = builderForValue.build();
        return this;
      }

      public Builder mergePartitionEntry(voldemort.client.protocol.pb.VAdminProto.PartitionEntry value) {
        if (result.hasPartitionEntry() && result.partitionEntry_ != voldemort.client.protocol.pb.VAdminProto.PartitionEntry.getDefaultInstance()) {
          result.partitionEntry_ = voldemort.client.protocol.pb.VAdminProto.PartitionEntry.newBuilder(result.partitionEntry_).mergeFrom(value).buildPartial();
        } else {
          result.partitionEntry_ = value;
        }
        result.hasPartitionEntry = true;
        return this;
      }

      public Builder clearPartitionEntry() {
        result.hasPartitionEntry = false;
        result.partitionEntry_ = voldemort.client.protocol.pb.VAdminProto.PartitionEntry.getDefaultInstance();
        return this;
      }

      public boolean hasKey() {
        return result.hasKey();
      }

      public com.google.protobuf.ByteString getKey() {
        return result.getKey();
      }

      public Builder setKey(com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasKey = true;
        result.key_ = value;
        return this;
      }

      public Builder clearKey() {
        result.hasKey = false;
        result.key_ = getDefaultInstance().getKey();
        return this;
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new FetchPartitionEntriesResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class DeletePartitionEntriesRequest extends com.google.protobuf.GeneratedMessage {
    private DeletePartitionEntriesRequest() {
      initFields();
    }

    private DeletePartitionEntriesRequest(boolean noInit) {
    }

    private static final DeletePartitionEntriesRequest defaultInstance;

    public static DeletePartitionEntriesRequest getDefaultInstance() {
      return defaultInstance;
    }

    public DeletePartitionEntriesRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeletePartitionEntriesRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeletePartitionEntriesRequest_fieldAccessorTable;
    }

    public static final int STORE_FIELD_NUMBER = 1;

    private boolean hasStore;

    private java.lang.String store_ = "";

    public boolean hasStore() {
      return hasStore;
    }

    public java.lang.String getStore() {
      return store_;
    }

    public static final int REPLICA_TO_PARTITION_FIELD_NUMBER = 2;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> replicaToPartition_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> getReplicaToPartitionList() {
      return replicaToPartition_;
    }

    public int getReplicaToPartitionCount() {
      return replicaToPartition_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getReplicaToPartition(int index) {
      return replicaToPartition_.get(index);
    }

    public static final int FILTER_FIELD_NUMBER = 3;

    private boolean hasFilter;

    private voldemort.client.protocol.pb.VAdminProto.VoldemortFilter filter_;

    public boolean hasFilter() {
      return hasFilter;
    }

    public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter getFilter() {
      return filter_;
    }

    public static final int INITIAL_CLUSTER_FIELD_NUMBER = 4;

    private boolean hasInitialCluster;

    private java.lang.String initialCluster_ = "";

    public boolean hasInitialCluster() {
      return hasInitialCluster;
    }

    public java.lang.String getInitialCluster() {
      return initialCluster_;
    }

    private void initFields() {
      filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (!hasStore) {
        return false;
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      if (hasFilter()) {
        if (!getFilter().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStore()) {
        output.writeString(1, getStore());
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        output.writeMessage(2, element);
      }
      if (hasFilter()) {
        output.writeMessage(3, getFilter());
      }
      if (hasInitialCluster()) {
        output.writeString(4, getInitialCluster());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStore()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStore());
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, element);
      }
      if (hasFilter()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, getFilter());
      }
      if (hasInitialCluster()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(4, getInitialCluster());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.replicaToPartition_ != java.util.Collections.EMPTY_LIST) {
          result.replicaToPartition_ = java.util.Collections.unmodifiableList(result.replicaToPartition_);
        }
        voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStore()) {
          setStore(other.getStore());
        }
        if (!other.replicaToPartition_.isEmpty()) {
          if (result.replicaToPartition_.isEmpty()) {
            result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
          }
          result.replicaToPartition_.addAll(other.replicaToPartition_);
        }
        if (other.hasFilter()) {
          mergeFilter(other.getFilter());
        }
        if (other.hasInitialCluster()) {
          setInitialCluster(other.getInitialCluster());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStore(input.readString());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.PartitionTuple.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addReplicaToPartition(subBuilder.buildPartial());
              break;
            }
            case 26:
            {
              voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.newBuilder();
              if (hasFilter()) {
                subBuilder.mergeFrom(getFilter());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setFilter(subBuilder.buildPartial());
              break;
            }
            case 34:
            {
              setInitialCluster(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasStore() {
        return result.hasStore();
      }

      public java.lang.String getStore() {
        return result.getStore();
      }

      public Builder setStore(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStore = true;
        result.store_ = value;
        return this;
      }

      public Builder clearStore() {
        result.hasStore = false;
        result.store_ = getDefaultInstance().getStore();
        return this;
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> getReplicaToPartitionList() {
        return java.util.Collections.unmodifiableList(result.replicaToPartition_);
      }

      public int getReplicaToPartitionCount() {
        return result.getReplicaToPartitionCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getReplicaToPartition(int index) {
        return result.getReplicaToPartition(index);
      }

      public Builder setReplicaToPartition(int index, voldemort.client.protocol.pb.VAdminProto.PartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.replicaToPartition_.set(index, value);
        return this;
      }

      public Builder setReplicaToPartition(int index, voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder builderForValue) {
        result.replicaToPartition_.set(index, builderForValue.build());
        return this;
      }

      public Builder addReplicaToPartition(voldemort.client.protocol.pb.VAdminProto.PartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        result.replicaToPartition_.add(value);
        return this;
      }

      public Builder addReplicaToPartition(voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder builderForValue) {
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        result.replicaToPartition_.add(builderForValue.build());
        return this;
      }

      public Builder addAllReplicaToPartition(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.PartitionTuple> values) {
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        super.addAll(values, result.replicaToPartition_);
        return this;
      }

      public Builder clearReplicaToPartition() {
        result.replicaToPartition_ = java.util.Collections.emptyList();
        return this;
      }

      public boolean hasFilter() {
        return result.hasFilter();
      }

      public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter getFilter() {
        return result.getFilter();
      }

      public Builder setFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasFilter = true;
        result.filter_ = value;
        return this;
      }

      public Builder setFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.Builder builderForValue) {
        result.hasFilter = true;
        result.filter_ = builderForValue.build();
        return this;
      }

      public Builder mergeFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter value) {
        if (result.hasFilter() && result.filter_ != voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance()) {
          result.filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.newBuilder(result.filter_).mergeFrom(value).buildPartial();
        } else {
          result.filter_ = value;
        }
        result.hasFilter = true;
        return this;
      }

      public Builder clearFilter() {
        result.hasFilter = false;
        result.filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance();
        return this;
      }

      public boolean hasInitialCluster() {
        return result.hasInitialCluster();
      }

      public java.lang.String getInitialCluster() {
        return result.getInitialCluster();
      }

      public Builder setInitialCluster(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasInitialCluster = true;
        result.initialCluster_ = value;
        return this;
      }

      public Builder clearInitialCluster() {
        result.hasInitialCluster = false;
        result.initialCluster_ = getDefaultInstance().getInitialCluster();
        return this;
      }
    }

    static {
      defaultInstance = new DeletePartitionEntriesRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class DeletePartitionEntriesResponse extends com.google.protobuf.GeneratedMessage {
    private DeletePartitionEntriesResponse() {
      initFields();
    }

    private DeletePartitionEntriesResponse(boolean noInit) {
    }

    private static final DeletePartitionEntriesResponse defaultInstance;

    public static DeletePartitionEntriesResponse getDefaultInstance() {
      return defaultInstance;
    }

    public DeletePartitionEntriesResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeletePartitionEntriesResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeletePartitionEntriesResponse_fieldAccessorTable;
    }

    public static final int COUNT_FIELD_NUMBER = 1;

    private boolean hasCount;

    private long count_ = 0L;

    public boolean hasCount() {
      return hasCount;
    }

    public long getCount() {
      return count_;
    }

    public static final int ERROR_FIELD_NUMBER = 2;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasCount()) {
        output.writeInt64(1, getCount());
      }
      if (hasError()) {
        output.writeMessage(2, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasCount()) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(1, getCount());
      }
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasCount()) {
          setCount(other.getCount());
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8:
            {
              setCount(input.readInt64());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasCount() {
        return result.hasCount();
      }

      public long getCount() {
        return result.getCount();
      }

      public Builder setCount(long value) {
        result.hasCount = true;
        result.count_ = value;
        return this;
      }

      public Builder clearCount() {
        result.hasCount = false;
        result.count_ = 0L;
        return this;
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new DeletePartitionEntriesResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class InitiateFetchAndUpdateRequest extends com.google.protobuf.GeneratedMessage {
    private InitiateFetchAndUpdateRequest() {
      initFields();
    }

    private InitiateFetchAndUpdateRequest(boolean noInit) {
    }

    private static final InitiateFetchAndUpdateRequest defaultInstance;

    public static InitiateFetchAndUpdateRequest getDefaultInstance() {
      return defaultInstance;
    }

    public InitiateFetchAndUpdateRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_InitiateFetchAndUpdateRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_InitiateFetchAndUpdateRequest_fieldAccessorTable;
    }

    public static final int NODE_ID_FIELD_NUMBER = 1;

    private boolean hasNodeId;

    private int nodeId_ = 0;

    public boolean hasNodeId() {
      return hasNodeId;
    }

    public int getNodeId() {
      return nodeId_;
    }

    public static final int STORE_FIELD_NUMBER = 2;

    private boolean hasStore;

    private java.lang.String store_ = "";

    public boolean hasStore() {
      return hasStore;
    }

    public java.lang.String getStore() {
      return store_;
    }

    public static final int FILTER_FIELD_NUMBER = 3;

    private boolean hasFilter;

    private voldemort.client.protocol.pb.VAdminProto.VoldemortFilter filter_;

    public boolean hasFilter() {
      return hasFilter;
    }

    public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter getFilter() {
      return filter_;
    }

    public static final int REPLICA_TO_PARTITION_FIELD_NUMBER = 4;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> replicaToPartition_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> getReplicaToPartitionList() {
      return replicaToPartition_;
    }

    public int getReplicaToPartitionCount() {
      return replicaToPartition_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getReplicaToPartition(int index) {
      return replicaToPartition_.get(index);
    }

    public static final int INITIAL_CLUSTER_FIELD_NUMBER = 5;

    private boolean hasInitialCluster;

    private java.lang.String initialCluster_ = "";

    public boolean hasInitialCluster() {
      return hasInitialCluster;
    }

    public java.lang.String getInitialCluster() {
      return initialCluster_;
    }

    public static final int OPTIMIZE_FIELD_NUMBER = 6;

    private boolean hasOptimize;

    private boolean optimize_ = false;

    public boolean hasOptimize() {
      return hasOptimize;
    }

    public boolean getOptimize() {
      return optimize_;
    }

    private void initFields() {
      filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (!hasNodeId) {
        return false;
      }
      if (!hasStore) {
        return false;
      }
      if (hasFilter()) {
        if (!getFilter().isInitialized()) {
          return false;
        }
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasNodeId()) {
        output.writeInt32(1, getNodeId());
      }
      if (hasStore()) {
        output.writeString(2, getStore());
      }
      if (hasFilter()) {
        output.writeMessage(3, getFilter());
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        output.writeMessage(4, element);
      }
      if (hasInitialCluster()) {
        output.writeString(5, getInitialCluster());
      }
      if (hasOptimize()) {
        output.writeBool(6, getOptimize());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasNodeId()) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(1, getNodeId());
      }
      if (hasStore()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(2, getStore());
      }
      if (hasFilter()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, getFilter());
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(4, element);
      }
      if (hasInitialCluster()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(5, getInitialCluster());
      }
      if (hasOptimize()) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(6, getOptimize());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.replicaToPartition_ != java.util.Collections.EMPTY_LIST) {
          result.replicaToPartition_ = java.util.Collections.unmodifiableList(result.replicaToPartition_);
        }
        voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasNodeId()) {
          setNodeId(other.getNodeId());
        }
        if (other.hasStore()) {
          setStore(other.getStore());
        }
        if (other.hasFilter()) {
          mergeFilter(other.getFilter());
        }
        if (!other.replicaToPartition_.isEmpty()) {
          if (result.replicaToPartition_.isEmpty()) {
            result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
          }
          result.replicaToPartition_.addAll(other.replicaToPartition_);
        }
        if (other.hasInitialCluster()) {
          setInitialCluster(other.getInitialCluster());
        }
        if (other.hasOptimize()) {
          setOptimize(other.getOptimize());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8:
            {
              setNodeId(input.readInt32());
              break;
            }
            case 18:
            {
              setStore(input.readString());
              break;
            }
            case 26:
            {
              voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.newBuilder();
              if (hasFilter()) {
                subBuilder.mergeFrom(getFilter());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setFilter(subBuilder.buildPartial());
              break;
            }
            case 34:
            {
              voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.PartitionTuple.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addReplicaToPartition(subBuilder.buildPartial());
              break;
            }
            case 42:
            {
              setInitialCluster(input.readString());
              break;
            }
            case 48:
            {
              setOptimize(input.readBool());
              break;
            }
          }
        }
      }

      public boolean hasNodeId() {
        return result.hasNodeId();
      }

      public int getNodeId() {
        return result.getNodeId();
      }

      public Builder setNodeId(int value) {
        result.hasNodeId = true;
        result.nodeId_ = value;
        return this;
      }

      public Builder clearNodeId() {
        result.hasNodeId = false;
        result.nodeId_ = 0;
        return this;
      }

      public boolean hasStore() {
        return result.hasStore();
      }

      public java.lang.String getStore() {
        return result.getStore();
      }

      public Builder setStore(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStore = true;
        result.store_ = value;
        return this;
      }

      public Builder clearStore() {
        result.hasStore = false;
        result.store_ = getDefaultInstance().getStore();
        return this;
      }

      public boolean hasFilter() {
        return result.hasFilter();
      }

      public voldemort.client.protocol.pb.VAdminProto.VoldemortFilter getFilter() {
        return result.getFilter();
      }

      public Builder setFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasFilter = true;
        result.filter_ = value;
        return this;
      }

      public Builder setFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.Builder builderForValue) {
        result.hasFilter = true;
        result.filter_ = builderForValue.build();
        return this;
      }

      public Builder mergeFilter(voldemort.client.protocol.pb.VAdminProto.VoldemortFilter value) {
        if (result.hasFilter() && result.filter_ != voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance()) {
          result.filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.newBuilder(result.filter_).mergeFrom(value).buildPartial();
        } else {
          result.filter_ = value;
        }
        result.hasFilter = true;
        return this;
      }

      public Builder clearFilter() {
        result.hasFilter = false;
        result.filter_ = voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.getDefaultInstance();
        return this;
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> getReplicaToPartitionList() {
        return java.util.Collections.unmodifiableList(result.replicaToPartition_);
      }

      public int getReplicaToPartitionCount() {
        return result.getReplicaToPartitionCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getReplicaToPartition(int index) {
        return result.getReplicaToPartition(index);
      }

      public Builder setReplicaToPartition(int index, voldemort.client.protocol.pb.VAdminProto.PartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.replicaToPartition_.set(index, value);
        return this;
      }

      public Builder setReplicaToPartition(int index, voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder builderForValue) {
        result.replicaToPartition_.set(index, builderForValue.build());
        return this;
      }

      public Builder addReplicaToPartition(voldemort.client.protocol.pb.VAdminProto.PartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        result.replicaToPartition_.add(value);
        return this;
      }

      public Builder addReplicaToPartition(voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder builderForValue) {
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        result.replicaToPartition_.add(builderForValue.build());
        return this;
      }

      public Builder addAllReplicaToPartition(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.PartitionTuple> values) {
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        super.addAll(values, result.replicaToPartition_);
        return this;
      }

      public Builder clearReplicaToPartition() {
        result.replicaToPartition_ = java.util.Collections.emptyList();
        return this;
      }

      public boolean hasInitialCluster() {
        return result.hasInitialCluster();
      }

      public java.lang.String getInitialCluster() {
        return result.getInitialCluster();
      }

      public Builder setInitialCluster(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasInitialCluster = true;
        result.initialCluster_ = value;
        return this;
      }

      public Builder clearInitialCluster() {
        result.hasInitialCluster = false;
        result.initialCluster_ = getDefaultInstance().getInitialCluster();
        return this;
      }

      public boolean hasOptimize() {
        return result.hasOptimize();
      }

      public boolean getOptimize() {
        return result.getOptimize();
      }

      public Builder setOptimize(boolean value) {
        result.hasOptimize = true;
        result.optimize_ = value;
        return this;
      }

      public Builder clearOptimize() {
        result.hasOptimize = false;
        result.optimize_ = false;
        return this;
      }
    }

    static {
      defaultInstance = new InitiateFetchAndUpdateRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class AsyncOperationStatusRequest extends com.google.protobuf.GeneratedMessage {
    private AsyncOperationStatusRequest() {
      initFields();
    }

    private AsyncOperationStatusRequest(boolean noInit) {
    }

    private static final AsyncOperationStatusRequest defaultInstance;

    public static AsyncOperationStatusRequest getDefaultInstance() {
      return defaultInstance;
    }

    public AsyncOperationStatusRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationStatusRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationStatusRequest_fieldAccessorTable;
    }

    public static final int REQUEST_ID_FIELD_NUMBER = 1;

    private boolean hasRequestId;

    private int requestId_ = 0;

    public boolean hasRequestId() {
      return hasRequestId;
    }

    public int getRequestId() {
      return requestId_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasRequestId) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasRequestId()) {
        output.writeInt32(1, getRequestId());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasRequestId()) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(1, getRequestId());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasRequestId()) {
          setRequestId(other.getRequestId());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8:
            {
              setRequestId(input.readInt32());
              break;
            }
          }
        }
      }

      public boolean hasRequestId() {
        return result.hasRequestId();
      }

      public int getRequestId() {
        return result.getRequestId();
      }

      public Builder setRequestId(int value) {
        result.hasRequestId = true;
        result.requestId_ = value;
        return this;
      }

      public Builder clearRequestId() {
        result.hasRequestId = false;
        result.requestId_ = 0;
        return this;
      }
    }

    static {
      defaultInstance = new AsyncOperationStatusRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class AsyncOperationStopRequest extends com.google.protobuf.GeneratedMessage {
    private AsyncOperationStopRequest() {
      initFields();
    }

    private AsyncOperationStopRequest(boolean noInit) {
    }

    private static final AsyncOperationStopRequest defaultInstance;

    public static AsyncOperationStopRequest getDefaultInstance() {
      return defaultInstance;
    }

    public AsyncOperationStopRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationStopRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationStopRequest_fieldAccessorTable;
    }

    public static final int REQUEST_ID_FIELD_NUMBER = 1;

    private boolean hasRequestId;

    private int requestId_ = 0;

    public boolean hasRequestId() {
      return hasRequestId;
    }

    public int getRequestId() {
      return requestId_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasRequestId) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasRequestId()) {
        output.writeInt32(1, getRequestId());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasRequestId()) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(1, getRequestId());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasRequestId()) {
          setRequestId(other.getRequestId());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8:
            {
              setRequestId(input.readInt32());
              break;
            }
          }
        }
      }

      public boolean hasRequestId() {
        return result.hasRequestId();
      }

      public int getRequestId() {
        return result.getRequestId();
      }

      public Builder setRequestId(int value) {
        result.hasRequestId = true;
        result.requestId_ = value;
        return this;
      }

      public Builder clearRequestId() {
        result.hasRequestId = false;
        result.requestId_ = 0;
        return this;
      }
    }

    static {
      defaultInstance = new AsyncOperationStopRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class AsyncOperationStopResponse extends com.google.protobuf.GeneratedMessage {
    private AsyncOperationStopResponse() {
      initFields();
    }

    private AsyncOperationStopResponse(boolean noInit) {
    }

    private static final AsyncOperationStopResponse defaultInstance;

    public static AsyncOperationStopResponse getDefaultInstance() {
      return defaultInstance;
    }

    public AsyncOperationStopResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationStopResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationStopResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new AsyncOperationStopResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class AsyncOperationListRequest extends com.google.protobuf.GeneratedMessage {
    private AsyncOperationListRequest() {
      initFields();
    }

    private AsyncOperationListRequest(boolean noInit) {
    }

    private static final AsyncOperationListRequest defaultInstance;

    public static AsyncOperationListRequest getDefaultInstance() {
      return defaultInstance;
    }

    public AsyncOperationListRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationListRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationListRequest_fieldAccessorTable;
    }

    public static final int SHOW_COMPLETE_FIELD_NUMBER = 2;

    private boolean hasShowComplete;

    private boolean showComplete_ = false;

    public boolean hasShowComplete() {
      return hasShowComplete;
    }

    public boolean getShowComplete() {
      return showComplete_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasShowComplete) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasShowComplete()) {
        output.writeBool(2, getShowComplete());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasShowComplete()) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(2, getShowComplete());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasShowComplete()) {
          setShowComplete(other.getShowComplete());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 16:
            {
              setShowComplete(input.readBool());
              break;
            }
          }
        }
      }

      public boolean hasShowComplete() {
        return result.hasShowComplete();
      }

      public boolean getShowComplete() {
        return result.getShowComplete();
      }

      public Builder setShowComplete(boolean value) {
        result.hasShowComplete = true;
        result.showComplete_ = value;
        return this;
      }

      public Builder clearShowComplete() {
        result.hasShowComplete = false;
        result.showComplete_ = false;
        return this;
      }
    }

    static {
      defaultInstance = new AsyncOperationListRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class AsyncOperationListResponse extends com.google.protobuf.GeneratedMessage {
    private AsyncOperationListResponse() {
      initFields();
    }

    private AsyncOperationListResponse(boolean noInit) {
    }

    private static final AsyncOperationListResponse defaultInstance;

    public static AsyncOperationListResponse getDefaultInstance() {
      return defaultInstance;
    }

    public AsyncOperationListResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationListResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationListResponse_fieldAccessorTable;
    }

    public static final int REQUEST_IDS_FIELD_NUMBER = 1;

    private java.util.List<java.lang.Integer> requestIds_ = java.util.Collections.emptyList();

    public java.util.List<java.lang.Integer> getRequestIdsList() {
      return requestIds_;
    }

    public int getRequestIdsCount() {
      return requestIds_.size();
    }

    public int getRequestIds(int index) {
      return requestIds_.get(index);
    }

    public static final int ERROR_FIELD_NUMBER = 2;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      for (int element : getRequestIdsList()) {
        output.writeInt32(1, element);
      }
      if (hasError()) {
        output.writeMessage(2, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      {
        int dataSize = 0;
        for (int element : getRequestIdsList()) {
          dataSize += com.google.protobuf.CodedOutputStream.computeInt32SizeNoTag(element);
        }
        size += dataSize;
        size += 1 * getRequestIdsList().size();
      }
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.requestIds_ != java.util.Collections.EMPTY_LIST) {
          result.requestIds_ = java.util.Collections.unmodifiableList(result.requestIds_);
        }
        voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse.getDefaultInstance()) {
          return this;
        }
        if (!other.requestIds_.isEmpty()) {
          if (result.requestIds_.isEmpty()) {
            result.requestIds_ = new java.util.ArrayList<java.lang.Integer>();
          }
          result.requestIds_.addAll(other.requestIds_);
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8:
            {
              addRequestIds(input.readInt32());
              break;
            }
            case 10:
            {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              while (input.getBytesUntilLimit() > 0) {
                addRequestIds(input.readInt32());
              }
              input.popLimit(limit);
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public java.util.List<java.lang.Integer> getRequestIdsList() {
        return java.util.Collections.unmodifiableList(result.requestIds_);
      }

      public int getRequestIdsCount() {
        return result.getRequestIdsCount();
      }

      public int getRequestIds(int index) {
        return result.getRequestIds(index);
      }

      public Builder setRequestIds(int index, int value) {
        result.requestIds_.set(index, value);
        return this;
      }

      public Builder addRequestIds(int value) {
        if (result.requestIds_.isEmpty()) {
          result.requestIds_ = new java.util.ArrayList<java.lang.Integer>();
        }
        result.requestIds_.add(value);
        return this;
      }

      public Builder addAllRequestIds(java.lang.Iterable<? extends java.lang.Integer> values) {
        if (result.requestIds_.isEmpty()) {
          result.requestIds_ = new java.util.ArrayList<java.lang.Integer>();
        }
        super.addAll(values, result.requestIds_);
        return this;
      }

      public Builder clearRequestIds() {
        result.requestIds_ = java.util.Collections.emptyList();
        return this;
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new AsyncOperationListResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class PartitionTuple extends com.google.protobuf.GeneratedMessage {
    private PartitionTuple() {
      initFields();
    }

    private PartitionTuple(boolean noInit) {
    }

    private static final PartitionTuple defaultInstance;

    public static PartitionTuple getDefaultInstance() {
      return defaultInstance;
    }

    public PartitionTuple getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_PartitionTuple_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_PartitionTuple_fieldAccessorTable;
    }

    public static final int REPLICA_TYPE_FIELD_NUMBER = 1;

    private boolean hasReplicaType;

    private int replicaType_ = 0;

    public boolean hasReplicaType() {
      return hasReplicaType;
    }

    public int getReplicaType() {
      return replicaType_;
    }

    public static final int PARTITIONS_FIELD_NUMBER = 2;

    private java.util.List<java.lang.Integer> partitions_ = java.util.Collections.emptyList();

    public java.util.List<java.lang.Integer> getPartitionsList() {
      return partitions_;
    }

    public int getPartitionsCount() {
      return partitions_.size();
    }

    public int getPartitions(int index) {
      return partitions_.get(index);
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasReplicaType) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasReplicaType()) {
        output.writeInt32(1, getReplicaType());
      }
      for (int element : getPartitionsList()) {
        output.writeInt32(2, element);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasReplicaType()) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(1, getReplicaType());
      }
      {
        int dataSize = 0;
        for (int element : getPartitionsList()) {
          dataSize += com.google.protobuf.CodedOutputStream.computeInt32SizeNoTag(element);
        }
        size += dataSize;
        size += 1 * getPartitionsList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionTuple parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionTuple parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionTuple parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionTuple parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionTuple parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionTuple parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionTuple parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionTuple parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionTuple parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PartitionTuple parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.PartitionTuple prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.PartitionTuple result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.PartitionTuple();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.PartitionTuple internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.PartitionTuple();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.PartitionTuple.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.PartitionTuple.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionTuple build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.PartitionTuple buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionTuple buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.partitions_ != java.util.Collections.EMPTY_LIST) {
          result.partitions_ = java.util.Collections.unmodifiableList(result.partitions_);
        }
        voldemort.client.protocol.pb.VAdminProto.PartitionTuple returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.PartitionTuple) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.PartitionTuple) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.PartitionTuple other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.PartitionTuple.getDefaultInstance()) {
          return this;
        }
        if (other.hasReplicaType()) {
          setReplicaType(other.getReplicaType());
        }
        if (!other.partitions_.isEmpty()) {
          if (result.partitions_.isEmpty()) {
            result.partitions_ = new java.util.ArrayList<java.lang.Integer>();
          }
          result.partitions_.addAll(other.partitions_);
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8:
            {
              setReplicaType(input.readInt32());
              break;
            }
            case 16:
            {
              addPartitions(input.readInt32());
              break;
            }
            case 18:
            {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              while (input.getBytesUntilLimit() > 0) {
                addPartitions(input.readInt32());
              }
              input.popLimit(limit);
              break;
            }
          }
        }
      }

      public boolean hasReplicaType() {
        return result.hasReplicaType();
      }

      public int getReplicaType() {
        return result.getReplicaType();
      }

      public Builder setReplicaType(int value) {
        result.hasReplicaType = true;
        result.replicaType_ = value;
        return this;
      }

      public Builder clearReplicaType() {
        result.hasReplicaType = false;
        result.replicaType_ = 0;
        return this;
      }

      public java.util.List<java.lang.Integer> getPartitionsList() {
        return java.util.Collections.unmodifiableList(result.partitions_);
      }

      public int getPartitionsCount() {
        return result.getPartitionsCount();
      }

      public int getPartitions(int index) {
        return result.getPartitions(index);
      }

      public Builder setPartitions(int index, int value) {
        result.partitions_.set(index, value);
        return this;
      }

      public Builder addPartitions(int value) {
        if (result.partitions_.isEmpty()) {
          result.partitions_ = new java.util.ArrayList<java.lang.Integer>();
        }
        result.partitions_.add(value);
        return this;
      }

      public Builder addAllPartitions(java.lang.Iterable<? extends java.lang.Integer> values) {
        if (result.partitions_.isEmpty()) {
          result.partitions_ = new java.util.ArrayList<java.lang.Integer>();
        }
        super.addAll(values, result.partitions_);
        return this;
      }

      public Builder clearPartitions() {
        result.partitions_ = java.util.Collections.emptyList();
        return this;
      }
    }

    static {
      defaultInstance = new PartitionTuple(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class PerStorePartitionTuple extends com.google.protobuf.GeneratedMessage {
    private PerStorePartitionTuple() {
      initFields();
    }

    private PerStorePartitionTuple(boolean noInit) {
    }

    private static final PerStorePartitionTuple defaultInstance;

    public static PerStorePartitionTuple getDefaultInstance() {
      return defaultInstance;
    }

    public PerStorePartitionTuple getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_PerStorePartitionTuple_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_PerStorePartitionTuple_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private boolean hasStoreName;

    private java.lang.String storeName_ = "";

    public boolean hasStoreName() {
      return hasStoreName;
    }

    public java.lang.String getStoreName() {
      return storeName_;
    }

    public static final int REPLICA_TO_PARTITION_FIELD_NUMBER = 2;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> replicaToPartition_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> getReplicaToPartitionList() {
      return replicaToPartition_;
    }

    public int getReplicaToPartitionCount() {
      return replicaToPartition_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getReplicaToPartition(int index) {
      return replicaToPartition_.get(index);
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStoreName) {
        return false;
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreName()) {
        output.writeString(1, getStoreName());
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        output.writeMessage(2, element);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreName());
      }
      for (voldemort.client.protocol.pb.VAdminProto.PartitionTuple element : getReplicaToPartitionList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, element);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.replicaToPartition_ != java.util.Collections.EMPTY_LIST) {
          result.replicaToPartition_ = java.util.Collections.unmodifiableList(result.replicaToPartition_);
        }
        voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreName()) {
          setStoreName(other.getStoreName());
        }
        if (!other.replicaToPartition_.isEmpty()) {
          if (result.replicaToPartition_.isEmpty()) {
            result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
          }
          result.replicaToPartition_.addAll(other.replicaToPartition_);
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreName(input.readString());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.PartitionTuple.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addReplicaToPartition(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasStoreName() {
        return result.hasStoreName();
      }

      public java.lang.String getStoreName() {
        return result.getStoreName();
      }

      public Builder setStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreName = true;
        result.storeName_ = value;
        return this;
      }

      public Builder clearStoreName() {
        result.hasStoreName = false;
        result.storeName_ = getDefaultInstance().getStoreName();
        return this;
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.PartitionTuple> getReplicaToPartitionList() {
        return java.util.Collections.unmodifiableList(result.replicaToPartition_);
      }

      public int getReplicaToPartitionCount() {
        return result.getReplicaToPartitionCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.PartitionTuple getReplicaToPartition(int index) {
        return result.getReplicaToPartition(index);
      }

      public Builder setReplicaToPartition(int index, voldemort.client.protocol.pb.VAdminProto.PartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.replicaToPartition_.set(index, value);
        return this;
      }

      public Builder setReplicaToPartition(int index, voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder builderForValue) {
        result.replicaToPartition_.set(index, builderForValue.build());
        return this;
      }

      public Builder addReplicaToPartition(voldemort.client.protocol.pb.VAdminProto.PartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        result.replicaToPartition_.add(value);
        return this;
      }

      public Builder addReplicaToPartition(voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder builderForValue) {
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        result.replicaToPartition_.add(builderForValue.build());
        return this;
      }

      public Builder addAllReplicaToPartition(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.PartitionTuple> values) {
        if (result.replicaToPartition_.isEmpty()) {
          result.replicaToPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PartitionTuple>();
        }
        super.addAll(values, result.replicaToPartition_);
        return this;
      }

      public Builder clearReplicaToPartition() {
        result.replicaToPartition_ = java.util.Collections.emptyList();
        return this;
      }
    }

    static {
      defaultInstance = new PerStorePartitionTuple(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class RebalancePartitionInfoMap extends com.google.protobuf.GeneratedMessage {
    private RebalancePartitionInfoMap() {
      initFields();
    }

    private RebalancePartitionInfoMap(boolean noInit) {
    }

    private static final RebalancePartitionInfoMap defaultInstance;

    public static RebalancePartitionInfoMap getDefaultInstance() {
      return defaultInstance;
    }

    public RebalancePartitionInfoMap getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RebalancePartitionInfoMap_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RebalancePartitionInfoMap_fieldAccessorTable;
    }

    public static final int STEALER_ID_FIELD_NUMBER = 1;

    private boolean hasStealerId;

    private int stealerId_ = 0;

    public boolean hasStealerId() {
      return hasStealerId;
    }

    public int getStealerId() {
      return stealerId_;
    }

    public static final int DONOR_ID_FIELD_NUMBER = 2;

    private boolean hasDonorId;

    private int donorId_ = 0;

    public boolean hasDonorId() {
      return hasDonorId;
    }

    public int getDonorId() {
      return donorId_;
    }

    public static final int ATTEMPT_FIELD_NUMBER = 3;

    private boolean hasAttempt;

    private int attempt_ = 0;

    public boolean hasAttempt() {
      return hasAttempt;
    }

    public int getAttempt() {
      return attempt_;
    }

    public static final int REPLICA_TO_ADD_PARTITION_FIELD_NUMBER = 4;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple> replicaToAddPartition_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple> getReplicaToAddPartitionList() {
      return replicaToAddPartition_;
    }

    public int getReplicaToAddPartitionCount() {
      return replicaToAddPartition_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple getReplicaToAddPartition(int index) {
      return replicaToAddPartition_.get(index);
    }

    public static final int REPLICA_TO_DELETE_PARTITION_FIELD_NUMBER = 5;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple> replicaToDeletePartition_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple> getReplicaToDeletePartitionList() {
      return replicaToDeletePartition_;
    }

    public int getReplicaToDeletePartitionCount() {
      return replicaToDeletePartition_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple getReplicaToDeletePartition(int index) {
      return replicaToDeletePartition_.get(index);
    }

    public static final int INITIAL_CLUSTER_FIELD_NUMBER = 6;

    private boolean hasInitialCluster;

    private java.lang.String initialCluster_ = "";

    public boolean hasInitialCluster() {
      return hasInitialCluster;
    }

    public java.lang.String getInitialCluster() {
      return initialCluster_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStealerId) {
        return false;
      }
      if (!hasDonorId) {
        return false;
      }
      if (!hasAttempt) {
        return false;
      }
      if (!hasInitialCluster) {
        return false;
      }
      for (voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple element : getReplicaToAddPartitionList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      for (voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple element : getReplicaToDeletePartitionList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStealerId()) {
        output.writeInt32(1, getStealerId());
      }
      if (hasDonorId()) {
        output.writeInt32(2, getDonorId());
      }
      if (hasAttempt()) {
        output.writeInt32(3, getAttempt());
      }
      for (voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple element : getReplicaToAddPartitionList()) {
        output.writeMessage(4, element);
      }
      for (voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple element : getReplicaToDeletePartitionList()) {
        output.writeMessage(5, element);
      }
      if (hasInitialCluster()) {
        output.writeString(6, getInitialCluster());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStealerId()) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(1, getStealerId());
      }
      if (hasDonorId()) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(2, getDonorId());
      }
      if (hasAttempt()) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(3, getAttempt());
      }
      for (voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple element : getReplicaToAddPartitionList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(4, element);
      }
      for (voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple element : getReplicaToDeletePartitionList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(5, element);
      }
      if (hasInitialCluster()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(6, getInitialCluster());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.replicaToAddPartition_ != java.util.Collections.EMPTY_LIST) {
          result.replicaToAddPartition_ = java.util.Collections.unmodifiableList(result.replicaToAddPartition_);
        }
        if (result.replicaToDeletePartition_ != java.util.Collections.EMPTY_LIST) {
          result.replicaToDeletePartition_ = java.util.Collections.unmodifiableList(result.replicaToDeletePartition_);
        }
        voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.getDefaultInstance()) {
          return this;
        }
        if (other.hasStealerId()) {
          setStealerId(other.getStealerId());
        }
        if (other.hasDonorId()) {
          setDonorId(other.getDonorId());
        }
        if (other.hasAttempt()) {
          setAttempt(other.getAttempt());
        }
        if (!other.replicaToAddPartition_.isEmpty()) {
          if (result.replicaToAddPartition_.isEmpty()) {
            result.replicaToAddPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple>();
          }
          result.replicaToAddPartition_.addAll(other.replicaToAddPartition_);
        }
        if (!other.replicaToDeletePartition_.isEmpty()) {
          if (result.replicaToDeletePartition_.isEmpty()) {
            result.replicaToDeletePartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple>();
          }
          result.replicaToDeletePartition_.addAll(other.replicaToDeletePartition_);
        }
        if (other.hasInitialCluster()) {
          setInitialCluster(other.getInitialCluster());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8:
            {
              setStealerId(input.readInt32());
              break;
            }
            case 16:
            {
              setDonorId(input.readInt32());
              break;
            }
            case 24:
            {
              setAttempt(input.readInt32());
              break;
            }
            case 34:
            {
              voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addReplicaToAddPartition(subBuilder.buildPartial());
              break;
            }
            case 42:
            {
              voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addReplicaToDeletePartition(subBuilder.buildPartial());
              break;
            }
            case 50:
            {
              setInitialCluster(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasStealerId() {
        return result.hasStealerId();
      }

      public int getStealerId() {
        return result.getStealerId();
      }

      public Builder setStealerId(int value) {
        result.hasStealerId = true;
        result.stealerId_ = value;
        return this;
      }

      public Builder clearStealerId() {
        result.hasStealerId = false;
        result.stealerId_ = 0;
        return this;
      }

      public boolean hasDonorId() {
        return result.hasDonorId();
      }

      public int getDonorId() {
        return result.getDonorId();
      }

      public Builder setDonorId(int value) {
        result.hasDonorId = true;
        result.donorId_ = value;
        return this;
      }

      public Builder clearDonorId() {
        result.hasDonorId = false;
        result.donorId_ = 0;
        return this;
      }

      public boolean hasAttempt() {
        return result.hasAttempt();
      }

      public int getAttempt() {
        return result.getAttempt();
      }

      public Builder setAttempt(int value) {
        result.hasAttempt = true;
        result.attempt_ = value;
        return this;
      }

      public Builder clearAttempt() {
        result.hasAttempt = false;
        result.attempt_ = 0;
        return this;
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple> getReplicaToAddPartitionList() {
        return java.util.Collections.unmodifiableList(result.replicaToAddPartition_);
      }

      public int getReplicaToAddPartitionCount() {
        return result.getReplicaToAddPartitionCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple getReplicaToAddPartition(int index) {
        return result.getReplicaToAddPartition(index);
      }

      public Builder setReplicaToAddPartition(int index, voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.replicaToAddPartition_.set(index, value);
        return this;
      }

      public Builder setReplicaToAddPartition(int index, voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.Builder builderForValue) {
        result.replicaToAddPartition_.set(index, builderForValue.build());
        return this;
      }

      public Builder addReplicaToAddPartition(voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.replicaToAddPartition_.isEmpty()) {
          result.replicaToAddPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple>();
        }
        result.replicaToAddPartition_.add(value);
        return this;
      }

      public Builder addReplicaToAddPartition(voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.Builder builderForValue) {
        if (result.replicaToAddPartition_.isEmpty()) {
          result.replicaToAddPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple>();
        }
        result.replicaToAddPartition_.add(builderForValue.build());
        return this;
      }

      public Builder addAllReplicaToAddPartition(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple> values) {
        if (result.replicaToAddPartition_.isEmpty()) {
          result.replicaToAddPartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple>();
        }
        super.addAll(values, result.replicaToAddPartition_);
        return this;
      }

      public Builder clearReplicaToAddPartition() {
        result.replicaToAddPartition_ = java.util.Collections.emptyList();
        return this;
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple> getReplicaToDeletePartitionList() {
        return java.util.Collections.unmodifiableList(result.replicaToDeletePartition_);
      }

      public int getReplicaToDeletePartitionCount() {
        return result.getReplicaToDeletePartitionCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple getReplicaToDeletePartition(int index) {
        return result.getReplicaToDeletePartition(index);
      }

      public Builder setReplicaToDeletePartition(int index, voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.replicaToDeletePartition_.set(index, value);
        return this;
      }

      public Builder setReplicaToDeletePartition(int index, voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.Builder builderForValue) {
        result.replicaToDeletePartition_.set(index, builderForValue.build());
        return this;
      }

      public Builder addReplicaToDeletePartition(voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.replicaToDeletePartition_.isEmpty()) {
          result.replicaToDeletePartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple>();
        }
        result.replicaToDeletePartition_.add(value);
        return this;
      }

      public Builder addReplicaToDeletePartition(voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.Builder builderForValue) {
        if (result.replicaToDeletePartition_.isEmpty()) {
          result.replicaToDeletePartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple>();
        }
        result.replicaToDeletePartition_.add(builderForValue.build());
        return this;
      }

      public Builder addAllReplicaToDeletePartition(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple> values) {
        if (result.replicaToDeletePartition_.isEmpty()) {
          result.replicaToDeletePartition_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple>();
        }
        super.addAll(values, result.replicaToDeletePartition_);
        return this;
      }

      public Builder clearReplicaToDeletePartition() {
        result.replicaToDeletePartition_ = java.util.Collections.emptyList();
        return this;
      }

      public boolean hasInitialCluster() {
        return result.hasInitialCluster();
      }

      public java.lang.String getInitialCluster() {
        return result.getInitialCluster();
      }

      public Builder setInitialCluster(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasInitialCluster = true;
        result.initialCluster_ = value;
        return this;
      }

      public Builder clearInitialCluster() {
        result.hasInitialCluster = false;
        result.initialCluster_ = getDefaultInstance().getInitialCluster();
        return this;
      }
    }

    static {
      defaultInstance = new RebalancePartitionInfoMap(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class InitiateRebalanceNodeRequest extends com.google.protobuf.GeneratedMessage {
    private InitiateRebalanceNodeRequest() {
      initFields();
    }

    private InitiateRebalanceNodeRequest(boolean noInit) {
    }

    private static final InitiateRebalanceNodeRequest defaultInstance;

    public static InitiateRebalanceNodeRequest getDefaultInstance() {
      return defaultInstance;
    }

    public InitiateRebalanceNodeRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_InitiateRebalanceNodeRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_InitiateRebalanceNodeRequest_fieldAccessorTable;
    }

    public static final int REBALANCE_PARTITION_INFO_FIELD_NUMBER = 1;

    private boolean hasRebalancePartitionInfo;

    private voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap rebalancePartitionInfo_;

    public boolean hasRebalancePartitionInfo() {
      return hasRebalancePartitionInfo;
    }

    public voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap getRebalancePartitionInfo() {
      return rebalancePartitionInfo_;
    }

    private void initFields() {
      rebalancePartitionInfo_ = voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (!hasRebalancePartitionInfo) {
        return false;
      }
      if (!getRebalancePartitionInfo().isInitialized()) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasRebalancePartitionInfo()) {
        output.writeMessage(1, getRebalancePartitionInfo());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasRebalancePartitionInfo()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getRebalancePartitionInfo());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasRebalancePartitionInfo()) {
          mergeRebalancePartitionInfo(other.getRebalancePartitionInfo());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.newBuilder();
              if (hasRebalancePartitionInfo()) {
                subBuilder.mergeFrom(getRebalancePartitionInfo());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setRebalancePartitionInfo(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasRebalancePartitionInfo() {
        return result.hasRebalancePartitionInfo();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap getRebalancePartitionInfo() {
        return result.getRebalancePartitionInfo();
      }

      public Builder setRebalancePartitionInfo(voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasRebalancePartitionInfo = true;
        result.rebalancePartitionInfo_ = value;
        return this;
      }

      public Builder setRebalancePartitionInfo(voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.Builder builderForValue) {
        result.hasRebalancePartitionInfo = true;
        result.rebalancePartitionInfo_ = builderForValue.build();
        return this;
      }

      public Builder mergeRebalancePartitionInfo(voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap value) {
        if (result.hasRebalancePartitionInfo() && result.rebalancePartitionInfo_ != voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.getDefaultInstance()) {
          result.rebalancePartitionInfo_ = voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.newBuilder(result.rebalancePartitionInfo_).mergeFrom(value).buildPartial();
        } else {
          result.rebalancePartitionInfo_ = value;
        }
        result.hasRebalancePartitionInfo = true;
        return this;
      }

      public Builder clearRebalancePartitionInfo() {
        result.hasRebalancePartitionInfo = false;
        result.rebalancePartitionInfo_ = voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new InitiateRebalanceNodeRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }


<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
  public static final class InitiateRebalanceNodeOnDonorRequest extends com.google.protobuf.GeneratedMessage {
    private InitiateRebalanceNodeOnDonorRequest() {
      initFields();
    }

    private InitiateRebalanceNodeOnDonorRequest(boolean noInit) {
    }

    private static final InitiateRebalanceNodeOnDonorRequest defaultInstance;

    public static InitiateRebalanceNodeOnDonorRequest getDefaultInstance() {
      return defaultInstance;
    }

    public InitiateRebalanceNodeOnDonorRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_InitiateRebalanceNodeOnDonorRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_InitiateRebalanceNodeOnDonorRequest_fieldAccessorTable;
    }

    public static final int REBALANCE_PARTITION_INFO_FIELD_NUMBER = 1;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap> rebalancePartitionInfo_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap> getRebalancePartitionInfoList() {
      return rebalancePartitionInfo_;
    }

    public int getRebalancePartitionInfoCount() {
      return rebalancePartitionInfo_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap getRebalancePartitionInfo(int index) {
      return rebalancePartitionInfo_.get(index);
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      for (voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap element : getRebalancePartitionInfoList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      for (voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap element : getRebalancePartitionInfoList()) {
        output.writeMessage(1, element);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      for (voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap element : getRebalancePartitionInfoList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, element);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.rebalancePartitionInfo_ != java.util.Collections.EMPTY_LIST) {
          result.rebalancePartitionInfo_ = java.util.Collections.unmodifiableList(result.rebalancePartitionInfo_);
        }
        voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.getDefaultInstance()) {
          return this;
        }
        if (!other.rebalancePartitionInfo_.isEmpty()) {
          if (result.rebalancePartitionInfo_.isEmpty()) {
            result.rebalancePartitionInfo_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap>();
          }
          result.rebalancePartitionInfo_.addAll(other.rebalancePartitionInfo_);
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addRebalancePartitionInfo(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap> getRebalancePartitionInfoList() {
        return java.util.Collections.unmodifiableList(result.rebalancePartitionInfo_);
      }

      public int getRebalancePartitionInfoCount() {
        return result.getRebalancePartitionInfoCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap getRebalancePartitionInfo(int index) {
        return result.getRebalancePartitionInfo(index);
      }

      public Builder setRebalancePartitionInfo(int index, voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.rebalancePartitionInfo_.set(index, value);
        return this;
      }

      public Builder setRebalancePartitionInfo(int index, voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.Builder builderForValue) {
        result.rebalancePartitionInfo_.set(index, builderForValue.build());
        return this;
      }

      public Builder addRebalancePartitionInfo(voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.rebalancePartitionInfo_.isEmpty()) {
          result.rebalancePartitionInfo_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap>();
        }
        result.rebalancePartitionInfo_.add(value);
        return this;
      }

      public Builder addRebalancePartitionInfo(voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.Builder builderForValue) {
        if (result.rebalancePartitionInfo_.isEmpty()) {
          result.rebalancePartitionInfo_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap>();
        }
        result.rebalancePartitionInfo_.add(builderForValue.build());
        return this;
      }

      public Builder addAllRebalancePartitionInfo(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap> values) {
        if (result.rebalancePartitionInfo_.isEmpty()) {
          result.rebalancePartitionInfo_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap>();
        }
        super.addAll(values, result.rebalancePartitionInfo_);
        return this;
      }

      public Builder clearRebalancePartitionInfo() {
        result.rebalancePartitionInfo_ = java.util.Collections.emptyList();
        return this;
      }
    }

    static {
      defaultInstance = new InitiateRebalanceNodeOnDonorRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }
=======
  public static final class NativeBackupRequest extends com.google.protobuf.GeneratedMessage {
    private NativeBackupRequest() {
      initFields();
    }

    private NativeBackupRequest(boolean noInit) {
    }

    private static final NativeBackupRequest defaultInstance;

    public static NativeBackupRequest getDefaultInstance() {
      return defaultInstance;
    }

    public NativeBackupRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_NativeBackupRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_NativeBackupRequest_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private boolean hasStoreName;

    private java.lang.String storeName_ = "";

    public boolean hasStoreName() {
      return hasStoreName;
    }

    public java.lang.String getStoreName() {
      return storeName_;
    }

    public static final int BACKUP_DIR_FIELD_NUMBER = 2;

    private boolean hasBackupDir;

    private java.lang.String backupDir_ = "";

    public boolean hasBackupDir() {
      return hasBackupDir;
    }

    public java.lang.String getBackupDir() {
      return backupDir_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStoreName) {
        return false;
      }
      if (!hasBackupDir) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreName()) {
        output.writeString(1, getStoreName());
      }
      if (hasBackupDir()) {
        output.writeString(2, getBackupDir());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreName());
      }
      if (hasBackupDir()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(2, getBackupDir());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreName()) {
          setStoreName(other.getStoreName());
        }
        if (other.hasBackupDir()) {
          setBackupDir(other.getBackupDir());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreName(input.readString());
              break;
            }
            case 18:
            {
              setBackupDir(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasStoreName() {
        return result.hasStoreName();
      }

      public java.lang.String getStoreName() {
        return result.getStoreName();
      }

      public Builder setStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreName = true;
        result.storeName_ = value;
        return this;
      }

      public Builder clearStoreName() {
        result.hasStoreName = false;
        result.storeName_ = getDefaultInstance().getStoreName();
        return this;
      }

      public boolean hasBackupDir() {
        return result.hasBackupDir();
      }

      public java.lang.String getBackupDir() {
        return result.getBackupDir();
      }

      public Builder setBackupDir(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasBackupDir = true;
        result.backupDir_ = value;
        return this;
      }

      public Builder clearBackupDir() {
        result.hasBackupDir = false;
        result.backupDir_ = getDefaultInstance().getBackupDir();
        return this;
      }
    }

    static {
      defaultInstance = new NativeBackupRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java


  public static final class AsyncOperationStatusResponse extends com.google.protobuf.GeneratedMessage {
    private AsyncOperationStatusResponse() {
      initFields();
    }

    private AsyncOperationStatusResponse(boolean noInit) {
    }

    private static final AsyncOperationStatusResponse defaultInstance;

    public static AsyncOperationStatusResponse getDefaultInstance() {
      return defaultInstance;
    }

    public AsyncOperationStatusResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationStatusResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AsyncOperationStatusResponse_fieldAccessorTable;
    }

    public static final int REQUEST_ID_FIELD_NUMBER = 1;

    private boolean hasRequestId;

    private int requestId_ = 0;

    public boolean hasRequestId() {
      return hasRequestId;
    }

    public int getRequestId() {
      return requestId_;
    }

    public static final int DESCRIPTION_FIELD_NUMBER = 2;

    private boolean hasDescription;

    private java.lang.String description_ = "";

    public boolean hasDescription() {
      return hasDescription;
    }

    public java.lang.String getDescription() {
      return description_;
    }

    public static final int STATUS_FIELD_NUMBER = 3;

    private boolean hasStatus;

    private java.lang.String status_ = "";

    public boolean hasStatus() {
      return hasStatus;
    }

    public java.lang.String getStatus() {
      return status_;
    }

    public static final int COMPLETE_FIELD_NUMBER = 4;

    private boolean hasComplete;

    private boolean complete_ = false;

    public boolean hasComplete() {
      return hasComplete;
    }

    public boolean getComplete() {
      return complete_;
    }

    public static final int ERROR_FIELD_NUMBER = 5;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasRequestId()) {
        output.writeInt32(1, getRequestId());
      }
      if (hasDescription()) {
        output.writeString(2, getDescription());
      }
      if (hasStatus()) {
        output.writeString(3, getStatus());
      }
      if (hasComplete()) {
        output.writeBool(4, getComplete());
      }
      if (hasError()) {
        output.writeMessage(5, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasRequestId()) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(1, getRequestId());
      }
      if (hasDescription()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(2, getDescription());
      }
      if (hasStatus()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(3, getStatus());
      }
      if (hasComplete()) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(4, getComplete());
      }
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(5, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasRequestId()) {
          setRequestId(other.getRequestId());
        }
        if (other.hasDescription()) {
          setDescription(other.getDescription());
        }
        if (other.hasStatus()) {
          setStatus(other.getStatus());
        }
        if (other.hasComplete()) {
          setComplete(other.getComplete());
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8:
            {
              setRequestId(input.readInt32());
              break;
            }
            case 18:
            {
              setDescription(input.readString());
              break;
            }
            case 26:
            {
              setStatus(input.readString());
              break;
            }
            case 32:
            {
              setComplete(input.readBool());
              break;
            }
            case 42:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasRequestId() {
        return result.hasRequestId();
      }

      public int getRequestId() {
        return result.getRequestId();
      }

      public Builder setRequestId(int value) {
        result.hasRequestId = true;
        result.requestId_ = value;
        return this;
      }

      public Builder clearRequestId() {
        result.hasRequestId = false;
        result.requestId_ = 0;
        return this;
      }

      public boolean hasDescription() {
        return result.hasDescription();
      }

      public java.lang.String getDescription() {
        return result.getDescription();
      }

      public Builder setDescription(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasDescription = true;
        result.description_ = value;
        return this;
      }

      public Builder clearDescription() {
        result.hasDescription = false;
        result.description_ = getDefaultInstance().getDescription();
        return this;
      }

      public boolean hasStatus() {
        return result.hasStatus();
      }

      public java.lang.String getStatus() {
        return result.getStatus();
      }

      public Builder setStatus(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStatus = true;
        result.status_ = value;
        return this;
      }

      public Builder clearStatus() {
        result.hasStatus = false;
        result.status_ = getDefaultInstance().getStatus();
        return this;
      }

      public boolean hasComplete() {
        return result.hasComplete();
      }

      public boolean getComplete() {
        return result.getComplete();
      }

      public Builder setComplete(boolean value) {
        result.hasComplete = true;
        result.complete_ = value;
        return this;
      }

      public Builder clearComplete() {
        result.hasComplete = false;
        result.complete_ = false;
        return this;
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new AsyncOperationStatusResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class TruncateEntriesRequest extends com.google.protobuf.GeneratedMessage {
    private TruncateEntriesRequest() {
      initFields();
    }

    private TruncateEntriesRequest(boolean noInit) {
    }

    private static final TruncateEntriesRequest defaultInstance;

    public static TruncateEntriesRequest getDefaultInstance() {
      return defaultInstance;
    }

    public TruncateEntriesRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_TruncateEntriesRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_TruncateEntriesRequest_fieldAccessorTable;
    }

    public static final int STORE_FIELD_NUMBER = 1;

    private boolean hasStore;

    private java.lang.String store_ = "";

    public boolean hasStore() {
      return hasStore;
    }

    public java.lang.String getStore() {
      return store_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStore) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStore()) {
        output.writeString(1, getStore());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStore()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStore());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStore()) {
          setStore(other.getStore());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStore(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasStore() {
        return result.hasStore();
      }

      public java.lang.String getStore() {
        return result.getStore();
      }

      public Builder setStore(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStore = true;
        result.store_ = value;
        return this;
      }

      public Builder clearStore() {
        result.hasStore = false;
        result.store_ = getDefaultInstance().getStore();
        return this;
      }
    }

    static {
      defaultInstance = new TruncateEntriesRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class TruncateEntriesResponse extends com.google.protobuf.GeneratedMessage {
    private TruncateEntriesResponse() {
      initFields();
    }

    private TruncateEntriesResponse(boolean noInit) {
    }

    private static final TruncateEntriesResponse defaultInstance;

    public static TruncateEntriesResponse getDefaultInstance() {
      return defaultInstance;
    }

    public TruncateEntriesResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_TruncateEntriesResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_TruncateEntriesResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new TruncateEntriesResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class AddStoreRequest extends com.google.protobuf.GeneratedMessage {
    private AddStoreRequest() {
      initFields();
    }

    private AddStoreRequest(boolean noInit) {
    }

    private static final AddStoreRequest defaultInstance;

    public static AddStoreRequest getDefaultInstance() {
      return defaultInstance;
    }

    public AddStoreRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AddStoreRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AddStoreRequest_fieldAccessorTable;
    }

    public static final int STOREDEFINITION_FIELD_NUMBER = 1;

    private boolean hasStoreDefinition;

    private java.lang.String storeDefinition_ = "";

    public boolean hasStoreDefinition() {
      return hasStoreDefinition;
    }

    public java.lang.String getStoreDefinition() {
      return storeDefinition_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStoreDefinition) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreDefinition()) {
        output.writeString(1, getStoreDefinition());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreDefinition()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreDefinition());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.AddStoreRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.AddStoreRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.AddStoreRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.AddStoreRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.AddStoreRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.AddStoreRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.AddStoreRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.AddStoreRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.AddStoreRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.AddStoreRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.AddStoreRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.AddStoreRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.AddStoreRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreDefinition()) {
          setStoreDefinition(other.getStoreDefinition());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreDefinition(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasStoreDefinition() {
        return result.hasStoreDefinition();
      }

      public java.lang.String getStoreDefinition() {
        return result.getStoreDefinition();
      }

      public Builder setStoreDefinition(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreDefinition = true;
        result.storeDefinition_ = value;
        return this;
      }

      public Builder clearStoreDefinition() {
        result.hasStoreDefinition = false;
        result.storeDefinition_ = getDefaultInstance().getStoreDefinition();
        return this;
      }
    }

    static {
      defaultInstance = new AddStoreRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class AddStoreResponse extends com.google.protobuf.GeneratedMessage {
    private AddStoreResponse() {
      initFields();
    }

    private AddStoreResponse(boolean noInit) {
    }

    private static final AddStoreResponse defaultInstance;

    public static AddStoreResponse getDefaultInstance() {
      return defaultInstance;
    }

    public AddStoreResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AddStoreResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_AddStoreResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.AddStoreResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.AddStoreResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.AddStoreResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.AddStoreResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.AddStoreResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.AddStoreResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.AddStoreResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.AddStoreResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.AddStoreResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.AddStoreResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.AddStoreResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.AddStoreResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.AddStoreResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.AddStoreResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.AddStoreResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.AddStoreResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.AddStoreResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new AddStoreResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class DeleteStoreRequest extends com.google.protobuf.GeneratedMessage {
    private DeleteStoreRequest() {
      initFields();
    }

    private DeleteStoreRequest(boolean noInit) {
    }

    private static final DeleteStoreRequest defaultInstance;

    public static DeleteStoreRequest getDefaultInstance() {
      return defaultInstance;
    }

    public DeleteStoreRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeleteStoreRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeleteStoreRequest_fieldAccessorTable;
    }

    public static final int STORENAME_FIELD_NUMBER = 1;

    private boolean hasStoreName;

    private java.lang.String storeName_ = "";

    public boolean hasStoreName() {
      return hasStoreName;
    }

    public java.lang.String getStoreName() {
      return storeName_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStoreName) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreName()) {
        output.writeString(1, getStoreName());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreName());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreName()) {
          setStoreName(other.getStoreName());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreName(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasStoreName() {
        return result.hasStoreName();
      }

      public java.lang.String getStoreName() {
        return result.getStoreName();
      }

      public Builder setStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreName = true;
        result.storeName_ = value;
        return this;
      }

      public Builder clearStoreName() {
        result.hasStoreName = false;
        result.storeName_ = getDefaultInstance().getStoreName();
        return this;
      }
    }

    static {
      defaultInstance = new DeleteStoreRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class DeleteStoreResponse extends com.google.protobuf.GeneratedMessage {
    private DeleteStoreResponse() {
      initFields();
    }

    private DeleteStoreResponse(boolean noInit) {
    }

    private static final DeleteStoreResponse defaultInstance;

    public static DeleteStoreResponse getDefaultInstance() {
      return defaultInstance;
    }

    public DeleteStoreResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeleteStoreResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeleteStoreResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new DeleteStoreResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class FetchStoreRequest extends com.google.protobuf.GeneratedMessage {
    private FetchStoreRequest() {
      initFields();
    }

    private FetchStoreRequest(boolean noInit) {
    }

    private static final FetchStoreRequest defaultInstance;

    public static FetchStoreRequest getDefaultInstance() {
      return defaultInstance;
    }

    public FetchStoreRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FetchStoreRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FetchStoreRequest_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private boolean hasStoreName;

    private java.lang.String storeName_ = "";

    public boolean hasStoreName() {
      return hasStoreName;
    }

    public java.lang.String getStoreName() {
      return storeName_;
    }

    public static final int STORE_DIR_FIELD_NUMBER = 2;

    private boolean hasStoreDir;

    private java.lang.String storeDir_ = "";

    public boolean hasStoreDir() {
      return hasStoreDir;
    }

    public java.lang.String getStoreDir() {
      return storeDir_;
    }

    public static final int PUSH_VERSION_FIELD_NUMBER = 3;

    private boolean hasPushVersion;

    private long pushVersion_ = 0L;

    public boolean hasPushVersion() {
      return hasPushVersion;
    }

    public long getPushVersion() {
      return pushVersion_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStoreName) {
        return false;
      }
      if (!hasStoreDir) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreName()) {
        output.writeString(1, getStoreName());
      }
      if (hasStoreDir()) {
        output.writeString(2, getStoreDir());
      }
      if (hasPushVersion()) {
        output.writeInt64(3, getPushVersion());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreName());
      }
      if (hasStoreDir()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(2, getStoreDir());
      }
      if (hasPushVersion()) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(3, getPushVersion());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreName()) {
          setStoreName(other.getStoreName());
        }
        if (other.hasStoreDir()) {
          setStoreDir(other.getStoreDir());
        }
        if (other.hasPushVersion()) {
          setPushVersion(other.getPushVersion());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreName(input.readString());
              break;
            }
            case 18:
            {
              setStoreDir(input.readString());
              break;
            }
            case 24:
            {
              setPushVersion(input.readInt64());
              break;
            }
          }
        }
      }

      public boolean hasStoreName() {
        return result.hasStoreName();
      }

      public java.lang.String getStoreName() {
        return result.getStoreName();
      }

      public Builder setStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreName = true;
        result.storeName_ = value;
        return this;
      }

      public Builder clearStoreName() {
        result.hasStoreName = false;
        result.storeName_ = getDefaultInstance().getStoreName();
        return this;
      }

      public boolean hasStoreDir() {
        return result.hasStoreDir();
      }

      public java.lang.String getStoreDir() {
        return result.getStoreDir();
      }

      public Builder setStoreDir(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreDir = true;
        result.storeDir_ = value;
        return this;
      }

      public Builder clearStoreDir() {
        result.hasStoreDir = false;
        result.storeDir_ = getDefaultInstance().getStoreDir();
        return this;
      }

      public boolean hasPushVersion() {
        return result.hasPushVersion();
      }

      public long getPushVersion() {
        return result.getPushVersion();
      }

      public Builder setPushVersion(long value) {
        result.hasPushVersion = true;
        result.pushVersion_ = value;
        return this;
      }

      public Builder clearPushVersion() {
        result.hasPushVersion = false;
        result.pushVersion_ = 0L;
        return this;
      }
    }

    static {
      defaultInstance = new FetchStoreRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class SwapStoreRequest extends com.google.protobuf.GeneratedMessage {
    private SwapStoreRequest() {
      initFields();
    }

    private SwapStoreRequest(boolean noInit) {
    }

    private static final SwapStoreRequest defaultInstance;

    public static SwapStoreRequest getDefaultInstance() {
      return defaultInstance;
    }

    public SwapStoreRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_SwapStoreRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_SwapStoreRequest_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private boolean hasStoreName;

    private java.lang.String storeName_ = "";

    public boolean hasStoreName() {
      return hasStoreName;
    }

    public java.lang.String getStoreName() {
      return storeName_;
    }

    public static final int STORE_DIR_FIELD_NUMBER = 2;

    private boolean hasStoreDir;

    private java.lang.String storeDir_ = "";

    public boolean hasStoreDir() {
      return hasStoreDir;
    }

    public java.lang.String getStoreDir() {
      return storeDir_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStoreName) {
        return false;
      }
      if (!hasStoreDir) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreName()) {
        output.writeString(1, getStoreName());
      }
      if (hasStoreDir()) {
        output.writeString(2, getStoreDir());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreName());
      }
      if (hasStoreDir()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(2, getStoreDir());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreName()) {
          setStoreName(other.getStoreName());
        }
        if (other.hasStoreDir()) {
          setStoreDir(other.getStoreDir());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreName(input.readString());
              break;
            }
            case 18:
            {
              setStoreDir(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasStoreName() {
        return result.hasStoreName();
      }

      public java.lang.String getStoreName() {
        return result.getStoreName();
      }

      public Builder setStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreName = true;
        result.storeName_ = value;
        return this;
      }

      public Builder clearStoreName() {
        result.hasStoreName = false;
        result.storeName_ = getDefaultInstance().getStoreName();
        return this;
      }

      public boolean hasStoreDir() {
        return result.hasStoreDir();
      }

      public java.lang.String getStoreDir() {
        return result.getStoreDir();
      }

      public Builder setStoreDir(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreDir = true;
        result.storeDir_ = value;
        return this;
      }

      public Builder clearStoreDir() {
        result.hasStoreDir = false;
        result.storeDir_ = getDefaultInstance().getStoreDir();
        return this;
      }
    }

    static {
      defaultInstance = new SwapStoreRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class SwapStoreResponse extends com.google.protobuf.GeneratedMessage {
    private SwapStoreResponse() {
      initFields();
    }

    private SwapStoreResponse(boolean noInit) {
    }

    private static final SwapStoreResponse defaultInstance;

    public static SwapStoreResponse getDefaultInstance() {
      return defaultInstance;
    }

    public SwapStoreResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_SwapStoreResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_SwapStoreResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    public static final int PREVIOUS_STORE_DIR_FIELD_NUMBER = 2;

    private boolean hasPreviousStoreDir;

    private java.lang.String previousStoreDir_ = "";

    public boolean hasPreviousStoreDir() {
      return hasPreviousStoreDir;
    }

    public java.lang.String getPreviousStoreDir() {
      return previousStoreDir_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      if (hasPreviousStoreDir()) {
        output.writeString(2, getPreviousStoreDir());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      if (hasPreviousStoreDir()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(2, getPreviousStoreDir());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        if (other.hasPreviousStoreDir()) {
          setPreviousStoreDir(other.getPreviousStoreDir());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
            case 18:
            {
              setPreviousStoreDir(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }

      public boolean hasPreviousStoreDir() {
        return result.hasPreviousStoreDir();
      }

      public java.lang.String getPreviousStoreDir() {
        return result.getPreviousStoreDir();
      }

      public Builder setPreviousStoreDir(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasPreviousStoreDir = true;
        result.previousStoreDir_ = value;
        return this;
      }

      public Builder clearPreviousStoreDir() {
        result.hasPreviousStoreDir = false;
        result.previousStoreDir_ = getDefaultInstance().getPreviousStoreDir();
        return this;
      }
    }

    static {
      defaultInstance = new SwapStoreResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class RollbackStoreRequest extends com.google.protobuf.GeneratedMessage {
    private RollbackStoreRequest() {
      initFields();
    }

    private RollbackStoreRequest(boolean noInit) {
    }

    private static final RollbackStoreRequest defaultInstance;

    public static RollbackStoreRequest getDefaultInstance() {
      return defaultInstance;
    }

    public RollbackStoreRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RollbackStoreRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RollbackStoreRequest_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private boolean hasStoreName;

    private java.lang.String storeName_ = "";

    public boolean hasStoreName() {
      return hasStoreName;
    }

    public java.lang.String getStoreName() {
      return storeName_;
    }

    public static final int PUSH_VERSION_FIELD_NUMBER = 2;

    private boolean hasPushVersion;

    private long pushVersion_ = 0L;

    public boolean hasPushVersion() {
      return hasPushVersion;
    }

    public long getPushVersion() {
      return pushVersion_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStoreName) {
        return false;
      }
      if (!hasPushVersion) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreName()) {
        output.writeString(1, getStoreName());
      }
      if (hasPushVersion()) {
        output.writeInt64(2, getPushVersion());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreName());
      }
      if (hasPushVersion()) {
        size += com.google.protobuf.CodedOutputStream.computeInt64Size(2, getPushVersion());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreName()) {
          setStoreName(other.getStoreName());
        }
        if (other.hasPushVersion()) {
          setPushVersion(other.getPushVersion());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreName(input.readString());
              break;
            }
            case 16:
            {
              setPushVersion(input.readInt64());
              break;
            }
          }
        }
      }

      public boolean hasStoreName() {
        return result.hasStoreName();
      }

      public java.lang.String getStoreName() {
        return result.getStoreName();
      }

      public Builder setStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreName = true;
        result.storeName_ = value;
        return this;
      }

      public Builder clearStoreName() {
        result.hasStoreName = false;
        result.storeName_ = getDefaultInstance().getStoreName();
        return this;
      }

      public boolean hasPushVersion() {
        return result.hasPushVersion();
      }

      public long getPushVersion() {
        return result.getPushVersion();
      }

      public Builder setPushVersion(long value) {
        result.hasPushVersion = true;
        result.pushVersion_ = value;
        return this;
      }

      public Builder clearPushVersion() {
        result.hasPushVersion = false;
        result.pushVersion_ = 0L;
        return this;
      }
    }

    static {
      defaultInstance = new RollbackStoreRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class RollbackStoreResponse extends com.google.protobuf.GeneratedMessage {
    private RollbackStoreResponse() {
      initFields();
    }

    private RollbackStoreResponse(boolean noInit) {
    }

    private static final RollbackStoreResponse defaultInstance;

    public static RollbackStoreResponse getDefaultInstance() {
      return defaultInstance;
    }

    public RollbackStoreResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RollbackStoreResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RollbackStoreResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new RollbackStoreResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class RepairJobRequest extends com.google.protobuf.GeneratedMessage {
    private RepairJobRequest() {
      initFields();
    }

    private RepairJobRequest(boolean noInit) {
    }

    private static final RepairJobRequest defaultInstance;

    public static RepairJobRequest getDefaultInstance() {
      return defaultInstance;
    }

    public RepairJobRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RepairJobRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RepairJobRequest_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private boolean hasStoreName;

    private java.lang.String storeName_ = "";

    public boolean hasStoreName() {
      return hasStoreName;
    }

    public java.lang.String getStoreName() {
      return storeName_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreName()) {
        output.writeString(1, getStoreName());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreName());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.RepairJobRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.RepairJobRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.RepairJobRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.RepairJobRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.RepairJobRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.RepairJobRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.RepairJobRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.RepairJobRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.RepairJobRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.RepairJobRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.RepairJobRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.RepairJobRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.RepairJobRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreName()) {
          setStoreName(other.getStoreName());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreName(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasStoreName() {
        return result.hasStoreName();
      }

      public java.lang.String getStoreName() {
        return result.getStoreName();
      }

      public Builder setStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreName = true;
        result.storeName_ = value;
        return this;
      }

      public Builder clearStoreName() {
        result.hasStoreName = false;
        result.storeName_ = getDefaultInstance().getStoreName();
        return this;
      }
    }

    static {
      defaultInstance = new RepairJobRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class RepairJobResponse extends com.google.protobuf.GeneratedMessage {
    private RepairJobResponse() {
      initFields();
    }

    private RepairJobResponse(boolean noInit) {
    }

    private static final RepairJobResponse defaultInstance;

    public static RepairJobResponse getDefaultInstance() {
      return defaultInstance;
    }

    public RepairJobResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RepairJobResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RepairJobResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RepairJobResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.RepairJobResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.RepairJobResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.RepairJobResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.RepairJobResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.RepairJobResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.RepairJobResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.RepairJobResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.RepairJobResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.RepairJobResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.RepairJobResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.RepairJobResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.RepairJobResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.RepairJobResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.RepairJobResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.RepairJobResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.RepairJobResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new RepairJobResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class ROStoreVersionDirMap extends com.google.protobuf.GeneratedMessage {
    private ROStoreVersionDirMap() {
      initFields();
    }

    private ROStoreVersionDirMap(boolean noInit) {
    }

    private static final ROStoreVersionDirMap defaultInstance;

    public static ROStoreVersionDirMap getDefaultInstance() {
      return defaultInstance;
    }

    public ROStoreVersionDirMap getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_ROStoreVersionDirMap_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_ROStoreVersionDirMap_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private boolean hasStoreName;

    private java.lang.String storeName_ = "";

    public boolean hasStoreName() {
      return hasStoreName;
    }

    public java.lang.String getStoreName() {
      return storeName_;
    }

    public static final int STORE_DIR_FIELD_NUMBER = 2;

    private boolean hasStoreDir;

    private java.lang.String storeDir_ = "";

    public boolean hasStoreDir() {
      return hasStoreDir;
    }

    public java.lang.String getStoreDir() {
      return storeDir_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStoreName) {
        return false;
      }
      if (!hasStoreDir) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreName()) {
        output.writeString(1, getStoreName());
      }
      if (hasStoreDir()) {
        output.writeString(2, getStoreDir());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreName());
      }
      if (hasStoreDir()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(2, getStoreDir());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreName()) {
          setStoreName(other.getStoreName());
        }
        if (other.hasStoreDir()) {
          setStoreDir(other.getStoreDir());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreName(input.readString());
              break;
            }
            case 18:
            {
              setStoreDir(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasStoreName() {
        return result.hasStoreName();
      }

      public java.lang.String getStoreName() {
        return result.getStoreName();
      }

      public Builder setStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreName = true;
        result.storeName_ = value;
        return this;
      }

      public Builder clearStoreName() {
        result.hasStoreName = false;
        result.storeName_ = getDefaultInstance().getStoreName();
        return this;
      }

      public boolean hasStoreDir() {
        return result.hasStoreDir();
      }

      public java.lang.String getStoreDir() {
        return result.getStoreDir();
      }

      public Builder setStoreDir(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreDir = true;
        result.storeDir_ = value;
        return this;
      }

      public Builder clearStoreDir() {
        result.hasStoreDir = false;
        result.storeDir_ = getDefaultInstance().getStoreDir();
        return this;
      }
    }

    static {
      defaultInstance = new ROStoreVersionDirMap(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class GetROMaxVersionDirRequest extends com.google.protobuf.GeneratedMessage {
    private GetROMaxVersionDirRequest() {
      initFields();
    }

    private GetROMaxVersionDirRequest(boolean noInit) {
    }

    private static final GetROMaxVersionDirRequest defaultInstance;

    public static GetROMaxVersionDirRequest getDefaultInstance() {
      return defaultInstance;
    }

    public GetROMaxVersionDirRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROMaxVersionDirRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROMaxVersionDirRequest_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private java.util.List<java.lang.String> storeName_ = java.util.Collections.emptyList();

    public java.util.List<java.lang.String> getStoreNameList() {
      return storeName_;
    }

    public int getStoreNameCount() {
      return storeName_.size();
    }

    public java.lang.String getStoreName(int index) {
      return storeName_.get(index);
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      for (java.lang.String element : getStoreNameList()) {
        output.writeString(1, element);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      {
        int dataSize = 0;
        for (java.lang.String element : getStoreNameList()) {
          dataSize += com.google.protobuf.CodedOutputStream.computeStringSizeNoTag(element);
        }
        size += dataSize;
        size += 1 * getStoreNameList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.storeName_ != java.util.Collections.EMPTY_LIST) {
          result.storeName_ = java.util.Collections.unmodifiableList(result.storeName_);
        }
        voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.getDefaultInstance()) {
          return this;
        }
        if (!other.storeName_.isEmpty()) {
          if (result.storeName_.isEmpty()) {
            result.storeName_ = new java.util.ArrayList<java.lang.String>();
          }
          result.storeName_.addAll(other.storeName_);
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              addStoreName(input.readString());
              break;
            }
          }
        }
      }

      public java.util.List<java.lang.String> getStoreNameList() {
        return java.util.Collections.unmodifiableList(result.storeName_);
      }

      public int getStoreNameCount() {
        return result.getStoreNameCount();
      }

      public java.lang.String getStoreName(int index) {
        return result.getStoreName(index);
      }

      public Builder setStoreName(int index, java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.storeName_.set(index, value);
        return this;
      }

      public Builder addStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.storeName_.isEmpty()) {
          result.storeName_ = new java.util.ArrayList<java.lang.String>();
        }
        result.storeName_.add(value);
        return this;
      }

      public Builder addAllStoreName(java.lang.Iterable<? extends java.lang.String> values) {
        if (result.storeName_.isEmpty()) {
          result.storeName_ = new java.util.ArrayList<java.lang.String>();
        }
        super.addAll(values, result.storeName_);
        return this;
      }

      public Builder clearStoreName() {
        result.storeName_ = java.util.Collections.emptyList();
        return this;
      }
    }

    static {
      defaultInstance = new GetROMaxVersionDirRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class GetROMaxVersionDirResponse extends com.google.protobuf.GeneratedMessage {
    private GetROMaxVersionDirResponse() {
      initFields();
    }

    private GetROMaxVersionDirResponse(boolean noInit) {
    }

    private static final GetROMaxVersionDirResponse defaultInstance;

    public static GetROMaxVersionDirResponse getDefaultInstance() {
      return defaultInstance;
    }

    public GetROMaxVersionDirResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROMaxVersionDirResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROMaxVersionDirResponse_fieldAccessorTable;
    }

    public static final int RO_STORE_VERSIONS_FIELD_NUMBER = 1;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> roStoreVersions_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> getRoStoreVersionsList() {
      return roStoreVersions_;
    }

    public int getRoStoreVersionsCount() {
      return roStoreVersions_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap getRoStoreVersions(int index) {
      return roStoreVersions_.get(index);
    }

    public static final int ERROR_FIELD_NUMBER = 2;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      for (voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap element : getRoStoreVersionsList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      for (voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap element : getRoStoreVersionsList()) {
        output.writeMessage(1, element);
      }
      if (hasError()) {
        output.writeMessage(2, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      for (voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap element : getRoStoreVersionsList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, element);
      }
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.roStoreVersions_ != java.util.Collections.EMPTY_LIST) {
          result.roStoreVersions_ = java.util.Collections.unmodifiableList(result.roStoreVersions_);
        }
        voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse.getDefaultInstance()) {
          return this;
        }
        if (!other.roStoreVersions_.isEmpty()) {
          if (result.roStoreVersions_.isEmpty()) {
            result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
          }
          result.roStoreVersions_.addAll(other.roStoreVersions_);
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addRoStoreVersions(subBuilder.buildPartial());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> getRoStoreVersionsList() {
        return java.util.Collections.unmodifiableList(result.roStoreVersions_);
      }

      public int getRoStoreVersionsCount() {
        return result.getRoStoreVersionsCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap getRoStoreVersions(int index) {
        return result.getRoStoreVersions(index);
      }

      public Builder setRoStoreVersions(int index, voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.roStoreVersions_.set(index, value);
        return this;
      }

      public Builder setRoStoreVersions(int index, voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.Builder builderForValue) {
        result.roStoreVersions_.set(index, builderForValue.build());
        return this;
      }

      public Builder addRoStoreVersions(voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.roStoreVersions_.isEmpty()) {
          result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
        }
        result.roStoreVersions_.add(value);
        return this;
      }

      public Builder addRoStoreVersions(voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.Builder builderForValue) {
        if (result.roStoreVersions_.isEmpty()) {
          result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
        }
        result.roStoreVersions_.add(builderForValue.build());
        return this;
      }

      public Builder addAllRoStoreVersions(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> values) {
        if (result.roStoreVersions_.isEmpty()) {
          result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
        }
        super.addAll(values, result.roStoreVersions_);
        return this;
      }

      public Builder clearRoStoreVersions() {
        result.roStoreVersions_ = java.util.Collections.emptyList();
        return this;
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new GetROMaxVersionDirResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class GetROCurrentVersionDirRequest extends com.google.protobuf.GeneratedMessage {
    private GetROCurrentVersionDirRequest() {
      initFields();
    }

    private GetROCurrentVersionDirRequest(boolean noInit) {
    }

    private static final GetROCurrentVersionDirRequest defaultInstance;

    public static GetROCurrentVersionDirRequest getDefaultInstance() {
      return defaultInstance;
    }

    public GetROCurrentVersionDirRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROCurrentVersionDirRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROCurrentVersionDirRequest_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private java.util.List<java.lang.String> storeName_ = java.util.Collections.emptyList();

    public java.util.List<java.lang.String> getStoreNameList() {
      return storeName_;
    }

    public int getStoreNameCount() {
      return storeName_.size();
    }

    public java.lang.String getStoreName(int index) {
      return storeName_.get(index);
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      for (java.lang.String element : getStoreNameList()) {
        output.writeString(1, element);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      {
        int dataSize = 0;
        for (java.lang.String element : getStoreNameList()) {
          dataSize += com.google.protobuf.CodedOutputStream.computeStringSizeNoTag(element);
        }
        size += dataSize;
        size += 1 * getStoreNameList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.storeName_ != java.util.Collections.EMPTY_LIST) {
          result.storeName_ = java.util.Collections.unmodifiableList(result.storeName_);
        }
        voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.getDefaultInstance()) {
          return this;
        }
        if (!other.storeName_.isEmpty()) {
          if (result.storeName_.isEmpty()) {
            result.storeName_ = new java.util.ArrayList<java.lang.String>();
          }
          result.storeName_.addAll(other.storeName_);
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              addStoreName(input.readString());
              break;
            }
          }
        }
      }

      public java.util.List<java.lang.String> getStoreNameList() {
        return java.util.Collections.unmodifiableList(result.storeName_);
      }

      public int getStoreNameCount() {
        return result.getStoreNameCount();
      }

      public java.lang.String getStoreName(int index) {
        return result.getStoreName(index);
      }

      public Builder setStoreName(int index, java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.storeName_.set(index, value);
        return this;
      }

      public Builder addStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.storeName_.isEmpty()) {
          result.storeName_ = new java.util.ArrayList<java.lang.String>();
        }
        result.storeName_.add(value);
        return this;
      }

      public Builder addAllStoreName(java.lang.Iterable<? extends java.lang.String> values) {
        if (result.storeName_.isEmpty()) {
          result.storeName_ = new java.util.ArrayList<java.lang.String>();
        }
        super.addAll(values, result.storeName_);
        return this;
      }

      public Builder clearStoreName() {
        result.storeName_ = java.util.Collections.emptyList();
        return this;
      }
    }

    static {
      defaultInstance = new GetROCurrentVersionDirRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class GetROCurrentVersionDirResponse extends com.google.protobuf.GeneratedMessage {
    private GetROCurrentVersionDirResponse() {
      initFields();
    }

    private GetROCurrentVersionDirResponse(boolean noInit) {
    }

    private static final GetROCurrentVersionDirResponse defaultInstance;

    public static GetROCurrentVersionDirResponse getDefaultInstance() {
      return defaultInstance;
    }

    public GetROCurrentVersionDirResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROCurrentVersionDirResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROCurrentVersionDirResponse_fieldAccessorTable;
    }

    public static final int RO_STORE_VERSIONS_FIELD_NUMBER = 1;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> roStoreVersions_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> getRoStoreVersionsList() {
      return roStoreVersions_;
    }

    public int getRoStoreVersionsCount() {
      return roStoreVersions_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap getRoStoreVersions(int index) {
      return roStoreVersions_.get(index);
    }

    public static final int ERROR_FIELD_NUMBER = 2;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      for (voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap element : getRoStoreVersionsList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      for (voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap element : getRoStoreVersionsList()) {
        output.writeMessage(1, element);
      }
      if (hasError()) {
        output.writeMessage(2, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      for (voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap element : getRoStoreVersionsList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, element);
      }
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.roStoreVersions_ != java.util.Collections.EMPTY_LIST) {
          result.roStoreVersions_ = java.util.Collections.unmodifiableList(result.roStoreVersions_);
        }
        voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse.getDefaultInstance()) {
          return this;
        }
        if (!other.roStoreVersions_.isEmpty()) {
          if (result.roStoreVersions_.isEmpty()) {
            result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
          }
          result.roStoreVersions_.addAll(other.roStoreVersions_);
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addRoStoreVersions(subBuilder.buildPartial());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> getRoStoreVersionsList() {
        return java.util.Collections.unmodifiableList(result.roStoreVersions_);
      }

      public int getRoStoreVersionsCount() {
        return result.getRoStoreVersionsCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap getRoStoreVersions(int index) {
        return result.getRoStoreVersions(index);
      }

      public Builder setRoStoreVersions(int index, voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.roStoreVersions_.set(index, value);
        return this;
      }

      public Builder setRoStoreVersions(int index, voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.Builder builderForValue) {
        result.roStoreVersions_.set(index, builderForValue.build());
        return this;
      }

      public Builder addRoStoreVersions(voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.roStoreVersions_.isEmpty()) {
          result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
        }
        result.roStoreVersions_.add(value);
        return this;
      }

      public Builder addRoStoreVersions(voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.Builder builderForValue) {
        if (result.roStoreVersions_.isEmpty()) {
          result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
        }
        result.roStoreVersions_.add(builderForValue.build());
        return this;
      }

      public Builder addAllRoStoreVersions(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> values) {
        if (result.roStoreVersions_.isEmpty()) {
          result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
        }
        super.addAll(values, result.roStoreVersions_);
        return this;
      }

      public Builder clearRoStoreVersions() {
        result.roStoreVersions_ = java.util.Collections.emptyList();
        return this;
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new GetROCurrentVersionDirResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class GetROStorageFormatRequest extends com.google.protobuf.GeneratedMessage {
    private GetROStorageFormatRequest() {
      initFields();
    }

    private GetROStorageFormatRequest(boolean noInit) {
    }

    private static final GetROStorageFormatRequest defaultInstance;

    public static GetROStorageFormatRequest getDefaultInstance() {
      return defaultInstance;
    }

    public GetROStorageFormatRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROStorageFormatRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROStorageFormatRequest_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private java.util.List<java.lang.String> storeName_ = java.util.Collections.emptyList();

    public java.util.List<java.lang.String> getStoreNameList() {
      return storeName_;
    }

    public int getStoreNameCount() {
      return storeName_.size();
    }

    public java.lang.String getStoreName(int index) {
      return storeName_.get(index);
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      for (java.lang.String element : getStoreNameList()) {
        output.writeString(1, element);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      {
        int dataSize = 0;
        for (java.lang.String element : getStoreNameList()) {
          dataSize += com.google.protobuf.CodedOutputStream.computeStringSizeNoTag(element);
        }
        size += dataSize;
        size += 1 * getStoreNameList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.storeName_ != java.util.Collections.EMPTY_LIST) {
          result.storeName_ = java.util.Collections.unmodifiableList(result.storeName_);
        }
        voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.getDefaultInstance()) {
          return this;
        }
        if (!other.storeName_.isEmpty()) {
          if (result.storeName_.isEmpty()) {
            result.storeName_ = new java.util.ArrayList<java.lang.String>();
          }
          result.storeName_.addAll(other.storeName_);
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              addStoreName(input.readString());
              break;
            }
          }
        }
      }

      public java.util.List<java.lang.String> getStoreNameList() {
        return java.util.Collections.unmodifiableList(result.storeName_);
      }

      public int getStoreNameCount() {
        return result.getStoreNameCount();
      }

      public java.lang.String getStoreName(int index) {
        return result.getStoreName(index);
      }

      public Builder setStoreName(int index, java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.storeName_.set(index, value);
        return this;
      }

      public Builder addStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.storeName_.isEmpty()) {
          result.storeName_ = new java.util.ArrayList<java.lang.String>();
        }
        result.storeName_.add(value);
        return this;
      }

      public Builder addAllStoreName(java.lang.Iterable<? extends java.lang.String> values) {
        if (result.storeName_.isEmpty()) {
          result.storeName_ = new java.util.ArrayList<java.lang.String>();
        }
        super.addAll(values, result.storeName_);
        return this;
      }

      public Builder clearStoreName() {
        result.storeName_ = java.util.Collections.emptyList();
        return this;
      }
    }

    static {
      defaultInstance = new GetROStorageFormatRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class GetROStorageFormatResponse extends com.google.protobuf.GeneratedMessage {
    private GetROStorageFormatResponse() {
      initFields();
    }

    private GetROStorageFormatResponse(boolean noInit) {
    }

    private static final GetROStorageFormatResponse defaultInstance;

    public static GetROStorageFormatResponse getDefaultInstance() {
      return defaultInstance;
    }

    public GetROStorageFormatResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROStorageFormatResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_GetROStorageFormatResponse_fieldAccessorTable;
    }

    public static final int RO_STORE_VERSIONS_FIELD_NUMBER = 1;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> roStoreVersions_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> getRoStoreVersionsList() {
      return roStoreVersions_;
    }

    public int getRoStoreVersionsCount() {
      return roStoreVersions_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap getRoStoreVersions(int index) {
      return roStoreVersions_.get(index);
    }

    public static final int ERROR_FIELD_NUMBER = 2;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      for (voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap element : getRoStoreVersionsList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      for (voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap element : getRoStoreVersionsList()) {
        output.writeMessage(1, element);
      }
      if (hasError()) {
        output.writeMessage(2, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      for (voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap element : getRoStoreVersionsList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, element);
      }
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.roStoreVersions_ != java.util.Collections.EMPTY_LIST) {
          result.roStoreVersions_ = java.util.Collections.unmodifiableList(result.roStoreVersions_);
        }
        voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse.getDefaultInstance()) {
          return this;
        }
        if (!other.roStoreVersions_.isEmpty()) {
          if (result.roStoreVersions_.isEmpty()) {
            result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
          }
          result.roStoreVersions_.addAll(other.roStoreVersions_);
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addRoStoreVersions(subBuilder.buildPartial());
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> getRoStoreVersionsList() {
        return java.util.Collections.unmodifiableList(result.roStoreVersions_);
      }

      public int getRoStoreVersionsCount() {
        return result.getRoStoreVersionsCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap getRoStoreVersions(int index) {
        return result.getRoStoreVersions(index);
      }

      public Builder setRoStoreVersions(int index, voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.roStoreVersions_.set(index, value);
        return this;
      }

      public Builder setRoStoreVersions(int index, voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.Builder builderForValue) {
        result.roStoreVersions_.set(index, builderForValue.build());
        return this;
      }

      public Builder addRoStoreVersions(voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.roStoreVersions_.isEmpty()) {
          result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
        }
        result.roStoreVersions_.add(value);
        return this;
      }

      public Builder addRoStoreVersions(voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.Builder builderForValue) {
        if (result.roStoreVersions_.isEmpty()) {
          result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
        }
        result.roStoreVersions_.add(builderForValue.build());
        return this;
      }

      public Builder addAllRoStoreVersions(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap> values) {
        if (result.roStoreVersions_.isEmpty()) {
          result.roStoreVersions_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap>();
        }
        super.addAll(values, result.roStoreVersions_);
        return this;
      }

      public Builder clearRoStoreVersions() {
        result.roStoreVersions_ = java.util.Collections.emptyList();
        return this;
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new GetROStorageFormatResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class FailedFetchStoreRequest extends com.google.protobuf.GeneratedMessage {
    private FailedFetchStoreRequest() {
      initFields();
    }

    private FailedFetchStoreRequest(boolean noInit) {
    }

    private static final FailedFetchStoreRequest defaultInstance;

    public static FailedFetchStoreRequest getDefaultInstance() {
      return defaultInstance;
    }

    public FailedFetchStoreRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FailedFetchStoreRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FailedFetchStoreRequest_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private boolean hasStoreName;

    private java.lang.String storeName_ = "";

    public boolean hasStoreName() {
      return hasStoreName;
    }

    public java.lang.String getStoreName() {
      return storeName_;
    }

    public static final int STORE_DIR_FIELD_NUMBER = 2;

    private boolean hasStoreDir;

    private java.lang.String storeDir_ = "";

    public boolean hasStoreDir() {
      return hasStoreDir;
    }

    public java.lang.String getStoreDir() {
      return storeDir_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStoreName) {
        return false;
      }
      if (!hasStoreDir) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreName()) {
        output.writeString(1, getStoreName());
      }
      if (hasStoreDir()) {
        output.writeString(2, getStoreDir());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreName());
      }
      if (hasStoreDir()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(2, getStoreDir());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreName()) {
          setStoreName(other.getStoreName());
        }
        if (other.hasStoreDir()) {
          setStoreDir(other.getStoreDir());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreName(input.readString());
              break;
            }
            case 18:
            {
              setStoreDir(input.readString());
              break;
            }
          }
        }
      }

      public boolean hasStoreName() {
        return result.hasStoreName();
      }

      public java.lang.String getStoreName() {
        return result.getStoreName();
      }

      public Builder setStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreName = true;
        result.storeName_ = value;
        return this;
      }

      public Builder clearStoreName() {
        result.hasStoreName = false;
        result.storeName_ = getDefaultInstance().getStoreName();
        return this;
      }

      public boolean hasStoreDir() {
        return result.hasStoreDir();
      }

      public java.lang.String getStoreDir() {
        return result.getStoreDir();
      }

      public Builder setStoreDir(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreDir = true;
        result.storeDir_ = value;
        return this;
      }

      public Builder clearStoreDir() {
        result.hasStoreDir = false;
        result.storeDir_ = getDefaultInstance().getStoreDir();
        return this;
      }
    }

    static {
      defaultInstance = new FailedFetchStoreRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class FailedFetchStoreResponse extends com.google.protobuf.GeneratedMessage {
    private FailedFetchStoreResponse() {
      initFields();
    }

    private FailedFetchStoreResponse(boolean noInit) {
    }

    private static final FailedFetchStoreResponse defaultInstance;

    public static FailedFetchStoreResponse getDefaultInstance() {
      return defaultInstance;
    }

    public FailedFetchStoreResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FailedFetchStoreResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_FailedFetchStoreResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new FailedFetchStoreResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class RebalanceStateChangeRequest extends com.google.protobuf.GeneratedMessage {
    private RebalanceStateChangeRequest() {
      initFields();
    }

    private RebalanceStateChangeRequest(boolean noInit) {
    }

    private static final RebalanceStateChangeRequest defaultInstance;

    public static RebalanceStateChangeRequest getDefaultInstance() {
      return defaultInstance;
    }

    public RebalanceStateChangeRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RebalanceStateChangeRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RebalanceStateChangeRequest_fieldAccessorTable;
    }

    public static final int REBALANCE_PARTITION_INFO_LIST_FIELD_NUMBER = 1;

    private java.util.List<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap> rebalancePartitionInfoList_ = java.util.Collections.emptyList();

    public java.util.List<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap> getRebalancePartitionInfoListList() {
      return rebalancePartitionInfoList_;
    }

    public int getRebalancePartitionInfoListCount() {
      return rebalancePartitionInfoList_.size();
    }

    public voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap getRebalancePartitionInfoList(int index) {
      return rebalancePartitionInfoList_.get(index);
    }

    public static final int CLUSTER_STRING_FIELD_NUMBER = 2;

    private boolean hasClusterString;

    private java.lang.String clusterString_ = "";

    public boolean hasClusterString() {
      return hasClusterString;
    }

    public java.lang.String getClusterString() {
      return clusterString_;
    }

    public static final int SWAP_RO_FIELD_NUMBER = 3;

    private boolean hasSwapRo;

    private boolean swapRo_ = false;

    public boolean hasSwapRo() {
      return hasSwapRo;
    }

    public boolean getSwapRo() {
      return swapRo_;
    }

    public static final int CHANGE_CLUSTER_METADATA_FIELD_NUMBER = 4;

    private boolean hasChangeClusterMetadata;

    private boolean changeClusterMetadata_ = false;

    public boolean hasChangeClusterMetadata() {
      return hasChangeClusterMetadata;
    }

    public boolean getChangeClusterMetadata() {
      return changeClusterMetadata_;
    }

    public static final int CHANGE_REBALANCE_STATE_FIELD_NUMBER = 5;

    private boolean hasChangeRebalanceState;

    private boolean changeRebalanceState_ = false;

    public boolean hasChangeRebalanceState() {
      return hasChangeRebalanceState;
    }

    public boolean getChangeRebalanceState() {
      return changeRebalanceState_;
    }

    public static final int ROLLBACK_FIELD_NUMBER = 6;

    private boolean hasRollback;

    private boolean rollback_ = false;

    public boolean hasRollback() {
      return hasRollback;
    }

    public boolean getRollback() {
      return rollback_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasClusterString) {
        return false;
      }
      if (!hasSwapRo) {
        return false;
      }
      if (!hasChangeClusterMetadata) {
        return false;
      }
      if (!hasChangeRebalanceState) {
        return false;
      }
      if (!hasRollback) {
        return false;
      }
      for (voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap element : getRebalancePartitionInfoListList()) {
        if (!element.isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      for (voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap element : getRebalancePartitionInfoListList()) {
        output.writeMessage(1, element);
      }
      if (hasClusterString()) {
        output.writeString(2, getClusterString());
      }
      if (hasSwapRo()) {
        output.writeBool(3, getSwapRo());
      }
      if (hasChangeClusterMetadata()) {
        output.writeBool(4, getChangeClusterMetadata());
      }
      if (hasChangeRebalanceState()) {
        output.writeBool(5, getChangeRebalanceState());
      }
      if (hasRollback()) {
        output.writeBool(6, getRollback());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      for (voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap element : getRebalancePartitionInfoListList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, element);
      }
      if (hasClusterString()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(2, getClusterString());
      }
      if (hasSwapRo()) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(3, getSwapRo());
      }
      if (hasChangeClusterMetadata()) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(4, getChangeClusterMetadata());
      }
      if (hasChangeRebalanceState()) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(5, getChangeRebalanceState());
      }
      if (hasRollback()) {
        size += com.google.protobuf.CodedOutputStream.computeBoolSize(6, getRollback());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        if (result.rebalancePartitionInfoList_ != java.util.Collections.EMPTY_LIST) {
          result.rebalancePartitionInfoList_ = java.util.Collections.unmodifiableList(result.rebalancePartitionInfoList_);
        }
        voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.getDefaultInstance()) {
          return this;
        }
        if (!other.rebalancePartitionInfoList_.isEmpty()) {
          if (result.rebalancePartitionInfoList_.isEmpty()) {
            result.rebalancePartitionInfoList_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap>();
          }
          result.rebalancePartitionInfoList_.addAll(other.rebalancePartitionInfoList_);
        }
        if (other.hasClusterString()) {
          setClusterString(other.getClusterString());
        }
        if (other.hasSwapRo()) {
          setSwapRo(other.getSwapRo());
        }
        if (other.hasChangeClusterMetadata()) {
          setChangeClusterMetadata(other.getChangeClusterMetadata());
        }
        if (other.hasChangeRebalanceState()) {
          setChangeRebalanceState(other.getChangeRebalanceState());
        }
        if (other.hasRollback()) {
          setRollback(other.getRollback());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addRebalancePartitionInfoList(subBuilder.buildPartial());
              break;
            }
            case 18:
            {
              setClusterString(input.readString());
              break;
            }
            case 24:
            {
              setSwapRo(input.readBool());
              break;
            }
            case 32:
            {
              setChangeClusterMetadata(input.readBool());
              break;
            }
            case 40:
            {
              setChangeRebalanceState(input.readBool());
              break;
            }
            case 48:
            {
              setRollback(input.readBool());
              break;
            }
          }
        }
      }

      public java.util.List<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap> getRebalancePartitionInfoListList() {
        return java.util.Collections.unmodifiableList(result.rebalancePartitionInfoList_);
      }

      public int getRebalancePartitionInfoListCount() {
        return result.getRebalancePartitionInfoListCount();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap getRebalancePartitionInfoList(int index) {
        return result.getRebalancePartitionInfoList(index);
      }

      public Builder setRebalancePartitionInfoList(int index, voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.rebalancePartitionInfoList_.set(index, value);
        return this;
      }

      public Builder setRebalancePartitionInfoList(int index, voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.Builder builderForValue) {
        result.rebalancePartitionInfoList_.set(index, builderForValue.build());
        return this;
      }

      public Builder addRebalancePartitionInfoList(voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.rebalancePartitionInfoList_.isEmpty()) {
          result.rebalancePartitionInfoList_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap>();
        }
        result.rebalancePartitionInfoList_.add(value);
        return this;
      }

      public Builder addRebalancePartitionInfoList(voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.Builder builderForValue) {
        if (result.rebalancePartitionInfoList_.isEmpty()) {
          result.rebalancePartitionInfoList_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap>();
        }
        result.rebalancePartitionInfoList_.add(builderForValue.build());
        return this;
      }

      public Builder addAllRebalancePartitionInfoList(java.lang.Iterable<? extends voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap> values) {
        if (result.rebalancePartitionInfoList_.isEmpty()) {
          result.rebalancePartitionInfoList_ = new java.util.ArrayList<voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap>();
        }
        super.addAll(values, result.rebalancePartitionInfoList_);
        return this;
      }

      public Builder clearRebalancePartitionInfoList() {
        result.rebalancePartitionInfoList_ = java.util.Collections.emptyList();
        return this;
      }

      public boolean hasClusterString() {
        return result.hasClusterString();
      }

      public java.lang.String getClusterString() {
        return result.getClusterString();
      }

      public Builder setClusterString(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasClusterString = true;
        result.clusterString_ = value;
        return this;
      }

      public Builder clearClusterString() {
        result.hasClusterString = false;
        result.clusterString_ = getDefaultInstance().getClusterString();
        return this;
      }

      public boolean hasSwapRo() {
        return result.hasSwapRo();
      }

      public boolean getSwapRo() {
        return result.getSwapRo();
      }

      public Builder setSwapRo(boolean value) {
        result.hasSwapRo = true;
        result.swapRo_ = value;
        return this;
      }

      public Builder clearSwapRo() {
        result.hasSwapRo = false;
        result.swapRo_ = false;
        return this;
      }

      public boolean hasChangeClusterMetadata() {
        return result.hasChangeClusterMetadata();
      }

      public boolean getChangeClusterMetadata() {
        return result.getChangeClusterMetadata();
      }

      public Builder setChangeClusterMetadata(boolean value) {
        result.hasChangeClusterMetadata = true;
        result.changeClusterMetadata_ = value;
        return this;
      }

      public Builder clearChangeClusterMetadata() {
        result.hasChangeClusterMetadata = false;
        result.changeClusterMetadata_ = false;
        return this;
      }

      public boolean hasChangeRebalanceState() {
        return result.hasChangeRebalanceState();
      }

      public boolean getChangeRebalanceState() {
        return result.getChangeRebalanceState();
      }

      public Builder setChangeRebalanceState(boolean value) {
        result.hasChangeRebalanceState = true;
        result.changeRebalanceState_ = value;
        return this;
      }

      public Builder clearChangeRebalanceState() {
        result.hasChangeRebalanceState = false;
        result.changeRebalanceState_ = false;
        return this;
      }

      public boolean hasRollback() {
        return result.hasRollback();
      }

      public boolean getRollback() {
        return result.getRollback();
      }

      public Builder setRollback(boolean value) {
        result.hasRollback = true;
        result.rollback_ = value;
        return this;
      }

      public Builder clearRollback() {
        result.hasRollback = false;
        result.rollback_ = false;
        return this;
      }
    }

    static {
      defaultInstance = new RebalanceStateChangeRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class RebalanceStateChangeResponse extends com.google.protobuf.GeneratedMessage {
    private RebalanceStateChangeResponse() {
      initFields();
    }

    private RebalanceStateChangeResponse(boolean noInit) {
    }

    private static final RebalanceStateChangeResponse defaultInstance;

    public static RebalanceStateChangeResponse getDefaultInstance() {
      return defaultInstance;
    }

    public RebalanceStateChangeResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RebalanceStateChangeResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_RebalanceStateChangeResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new RebalanceStateChangeResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class DeleteStoreRebalanceStateRequest extends com.google.protobuf.GeneratedMessage {
    private DeleteStoreRebalanceStateRequest() {
      initFields();
    }

    private DeleteStoreRebalanceStateRequest(boolean noInit) {
    }

    private static final DeleteStoreRebalanceStateRequest defaultInstance;

    public static DeleteStoreRebalanceStateRequest getDefaultInstance() {
      return defaultInstance;
    }

    public DeleteStoreRebalanceStateRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeleteStoreRebalanceStateRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeleteStoreRebalanceStateRequest_fieldAccessorTable;
    }

    public static final int STORE_NAME_FIELD_NUMBER = 1;

    private boolean hasStoreName;

    private java.lang.String storeName_ = "";

    public boolean hasStoreName() {
      return hasStoreName;
    }

    public java.lang.String getStoreName() {
      return storeName_;
    }

    public static final int NODE_ID_FIELD_NUMBER = 2;

    private boolean hasNodeId;

    private int nodeId_ = 0;

    public boolean hasNodeId() {
      return hasNodeId;
    }

    public int getNodeId() {
      return nodeId_;
    }

    private void initFields() {
    }

    public final boolean isInitialized() {
      if (!hasStoreName) {
        return false;
      }
      if (!hasNodeId) {
        return false;
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasStoreName()) {
        output.writeString(1, getStoreName());
      }
      if (hasNodeId()) {
        output.writeInt32(2, getNodeId());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasStoreName()) {
        size += com.google.protobuf.CodedOutputStream.computeStringSize(1, getStoreName());
      }
      if (hasNodeId()) {
        size += com.google.protobuf.CodedOutputStream.computeInt32Size(2, getNodeId());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasStoreName()) {
          setStoreName(other.getStoreName());
        }
        if (other.hasNodeId()) {
          setNodeId(other.getNodeId());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              setStoreName(input.readString());
              break;
            }
            case 16:
            {
              setNodeId(input.readInt32());
              break;
            }
          }
        }
      }

      public boolean hasStoreName() {
        return result.hasStoreName();
      }

      public java.lang.String getStoreName() {
        return result.getStoreName();
      }

      public Builder setStoreName(java.lang.String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStoreName = true;
        result.storeName_ = value;
        return this;
      }

      public Builder clearStoreName() {
        result.hasStoreName = false;
        result.storeName_ = getDefaultInstance().getStoreName();
        return this;
      }

      public boolean hasNodeId() {
        return result.hasNodeId();
      }

      public int getNodeId() {
        return result.getNodeId();
      }

      public Builder setNodeId(int value) {
        result.hasNodeId = true;
        result.nodeId_ = value;
        return this;
      }

      public Builder clearNodeId() {
        result.hasNodeId = false;
        result.nodeId_ = 0;
        return this;
      }
    }

    static {
      defaultInstance = new DeleteStoreRebalanceStateRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class DeleteStoreRebalanceStateResponse extends com.google.protobuf.GeneratedMessage {
    private DeleteStoreRebalanceStateResponse() {
      initFields();
    }

    private DeleteStoreRebalanceStateResponse(boolean noInit) {
    }

    private static final DeleteStoreRebalanceStateResponse defaultInstance;

    public static DeleteStoreRebalanceStateResponse getDefaultInstance() {
      return defaultInstance;
    }

    public DeleteStoreRebalanceStateResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeleteStoreRebalanceStateResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_DeleteStoreRebalanceStateResponse_fieldAccessorTable;
    }

    public static final int ERROR_FIELD_NUMBER = 1;

    private boolean hasError;

    private voldemort.client.protocol.pb.VProto.Error error_;

    public boolean hasError() {
      return hasError;
    }

    public voldemort.client.protocol.pb.VProto.Error getError() {
      return error_;
    }

    private void initFields() {
      error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (hasError()) {
        if (!getError().isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasError()) {
        output.writeMessage(1, getError());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getError());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse.getDefaultInstance()) {
          return this;
        }
        if (other.hasError()) {
          mergeError(other.getError());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10:
            {
              voldemort.client.protocol.pb.VProto.Error.Builder subBuilder = voldemort.client.protocol.pb.VProto.Error.newBuilder();
              if (hasError()) {
                subBuilder.mergeFrom(getError());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setError(subBuilder.buildPartial());
              break;
            }
          }
        }
      }

      public boolean hasError() {
        return result.hasError();
      }

      public voldemort.client.protocol.pb.VProto.Error getError() {
        return result.getError();
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasError = true;
        result.error_ = value;
        return this;
      }

      public Builder setError(voldemort.client.protocol.pb.VProto.Error.Builder builderForValue) {
        result.hasError = true;
        result.error_ = builderForValue.build();
        return this;
      }

      public Builder mergeError(voldemort.client.protocol.pb.VProto.Error value) {
        if (result.hasError() && result.error_ != voldemort.client.protocol.pb.VProto.Error.getDefaultInstance()) {
          result.error_ = voldemort.client.protocol.pb.VProto.Error.newBuilder(result.error_).mergeFrom(value).buildPartial();
        } else {
          result.error_ = value;
        }
        result.hasError = true;
        return this;
      }

      public Builder clearError() {
        result.hasError = false;
        result.error_ = voldemort.client.protocol.pb.VProto.Error.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new DeleteStoreRebalanceStateResponse(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  public static final class VoldemortAdminRequest extends com.google.protobuf.GeneratedMessage {
    private VoldemortAdminRequest() {
      initFields();
    }

    private VoldemortAdminRequest(boolean noInit) {
    }

    private static final VoldemortAdminRequest defaultInstance;

    public static VoldemortAdminRequest getDefaultInstance() {
      return defaultInstance;
    }

    public VoldemortAdminRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_VoldemortAdminRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
      return voldemort.client.protocol.pb.VAdminProto.internal_static_voldemort_VoldemortAdminRequest_fieldAccessorTable;
    }

    public static final int TYPE_FIELD_NUMBER = 1;

    private boolean hasType;

    private voldemort.client.protocol.pb.VAdminProto.AdminRequestType type_;

    public boolean hasType() {
      return hasType;
    }

    public voldemort.client.protocol.pb.VAdminProto.AdminRequestType getType() {
      return type_;
    }

    public static final int GET_METADATA_FIELD_NUMBER = 2;

    private boolean hasGetMetadata;

    private voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest getMetadata_;

    public boolean hasGetMetadata() {
      return hasGetMetadata;
    }

    public voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest getGetMetadata() {
      return getMetadata_;
    }

    public static final int UPDATE_METADATA_FIELD_NUMBER = 3;

    private boolean hasUpdateMetadata;

    private voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest updateMetadata_;

    public boolean hasUpdateMetadata() {
      return hasUpdateMetadata;
    }

    public voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest getUpdateMetadata() {
      return updateMetadata_;
    }

    public static final int UPDATE_PARTITION_ENTRIES_FIELD_NUMBER = 4;

    private boolean hasUpdatePartitionEntries;

    private voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest updatePartitionEntries_;

    public boolean hasUpdatePartitionEntries() {
      return hasUpdatePartitionEntries;
    }

    public voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest getUpdatePartitionEntries() {
      return updatePartitionEntries_;
    }

    public static final int FETCH_PARTITION_ENTRIES_FIELD_NUMBER = 5;

    private boolean hasFetchPartitionEntries;

    private voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest fetchPartitionEntries_;

    public boolean hasFetchPartitionEntries() {
      return hasFetchPartitionEntries;
    }

    public voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest getFetchPartitionEntries() {
      return fetchPartitionEntries_;
    }

    public static final int DELETE_PARTITION_ENTRIES_FIELD_NUMBER = 6;

    private boolean hasDeletePartitionEntries;

    private voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest deletePartitionEntries_;

    public boolean hasDeletePartitionEntries() {
      return hasDeletePartitionEntries;
    }

    public voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest getDeletePartitionEntries() {
      return deletePartitionEntries_;
    }

    public static final int INITIATE_FETCH_AND_UPDATE_FIELD_NUMBER = 7;

    private boolean hasInitiateFetchAndUpdate;

    private voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest initiateFetchAndUpdate_;

    public boolean hasInitiateFetchAndUpdate() {
      return hasInitiateFetchAndUpdate;
    }

    public voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest getInitiateFetchAndUpdate() {
      return initiateFetchAndUpdate_;
    }

    public static final int ASYNC_OPERATION_STATUS_FIELD_NUMBER = 8;

    private boolean hasAsyncOperationStatus;

    private voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest asyncOperationStatus_;

    public boolean hasAsyncOperationStatus() {
      return hasAsyncOperationStatus;
    }

    public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest getAsyncOperationStatus() {
      return asyncOperationStatus_;
    }

    public static final int INITIATE_REBALANCE_NODE_FIELD_NUMBER = 9;

    private boolean hasInitiateRebalanceNode;

    private voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest initiateRebalanceNode_;

    public boolean hasInitiateRebalanceNode() {
      return hasInitiateRebalanceNode;
    }

    public voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest getInitiateRebalanceNode() {
      return initiateRebalanceNode_;
    }

    public static final int ASYNC_OPERATION_STOP_FIELD_NUMBER = 10;

    private boolean hasAsyncOperationStop;

    private voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest asyncOperationStop_;

    public boolean hasAsyncOperationStop() {
      return hasAsyncOperationStop;
    }

    public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest getAsyncOperationStop() {
      return asyncOperationStop_;
    }

    public static final int ASYNC_OPERATION_LIST_FIELD_NUMBER = 11;

    private boolean hasAsyncOperationList;

    private voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest asyncOperationList_;

    public boolean hasAsyncOperationList() {
      return hasAsyncOperationList;
    }

    public voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest getAsyncOperationList() {
      return asyncOperationList_;
    }

    public static final int TRUNCATE_ENTRIES_FIELD_NUMBER = 12;

    private boolean hasTruncateEntries;

    private voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest truncateEntries_;

    public boolean hasTruncateEntries() {
      return hasTruncateEntries;
    }

    public voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest getTruncateEntries() {
      return truncateEntries_;
    }

    public static final int ADD_STORE_FIELD_NUMBER = 13;

    private boolean hasAddStore;

    private voldemort.client.protocol.pb.VAdminProto.AddStoreRequest addStore_;

    public boolean hasAddStore() {
      return hasAddStore;
    }

    public voldemort.client.protocol.pb.VAdminProto.AddStoreRequest getAddStore() {
      return addStore_;
    }

    public static final int DELETE_STORE_FIELD_NUMBER = 14;

    private boolean hasDeleteStore;

    private voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest deleteStore_;

    public boolean hasDeleteStore() {
      return hasDeleteStore;
    }

    public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest getDeleteStore() {
      return deleteStore_;
    }

    public static final int FETCH_STORE_FIELD_NUMBER = 15;

    private boolean hasFetchStore;

    private voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest fetchStore_;

    public boolean hasFetchStore() {
      return hasFetchStore;
    }

    public voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest getFetchStore() {
      return fetchStore_;
    }

    public static final int SWAP_STORE_FIELD_NUMBER = 16;

    private boolean hasSwapStore;

    private voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest swapStore_;

    public boolean hasSwapStore() {
      return hasSwapStore;
    }

    public voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest getSwapStore() {
      return swapStore_;
    }

    public static final int ROLLBACK_STORE_FIELD_NUMBER = 17;

    private boolean hasRollbackStore;

    private voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest rollbackStore_;

    public boolean hasRollbackStore() {
      return hasRollbackStore;
    }

    public voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest getRollbackStore() {
      return rollbackStore_;
    }

    public static final int GET_RO_MAX_VERSION_DIR_FIELD_NUMBER = 18;

    private boolean hasGetRoMaxVersionDir;

    private voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest getRoMaxVersionDir_;

    public boolean hasGetRoMaxVersionDir() {
      return hasGetRoMaxVersionDir;
    }

    public voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest getGetRoMaxVersionDir() {
      return getRoMaxVersionDir_;
    }

    public static final int GET_RO_CURRENT_VERSION_DIR_FIELD_NUMBER = 19;

    private boolean hasGetRoCurrentVersionDir;

    private voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest getRoCurrentVersionDir_;

    public boolean hasGetRoCurrentVersionDir() {
      return hasGetRoCurrentVersionDir;
    }

    public voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest getGetRoCurrentVersionDir() {
      return getRoCurrentVersionDir_;
    }

    public static final int FETCH_PARTITION_FILES_FIELD_NUMBER = 20;

    private boolean hasFetchPartitionFiles;

    private voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest fetchPartitionFiles_;

    public boolean hasFetchPartitionFiles() {
      return hasFetchPartitionFiles;
    }

    public voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest getFetchPartitionFiles() {
      return fetchPartitionFiles_;
    }

    public static final int UPDATE_SLOP_ENTRIES_FIELD_NUMBER = 22;

    private boolean hasUpdateSlopEntries;

    private voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest updateSlopEntries_;

    public boolean hasUpdateSlopEntries() {
      return hasUpdateSlopEntries;
    }

    public voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest getUpdateSlopEntries() {
      return updateSlopEntries_;
    }

    public static final int FAILED_FETCH_STORE_FIELD_NUMBER = 24;

    private boolean hasFailedFetchStore;

    private voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest failedFetchStore_;

    public boolean hasFailedFetchStore() {
      return hasFailedFetchStore;
    }

    public voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest getFailedFetchStore() {
      return failedFetchStore_;
    }

    public static final int GET_RO_STORAGE_FORMAT_FIELD_NUMBER = 25;

    private boolean hasGetRoStorageFormat;

    private voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest getRoStorageFormat_;

    public boolean hasGetRoStorageFormat() {
      return hasGetRoStorageFormat;
    }

    public voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest getGetRoStorageFormat() {
      return getRoStorageFormat_;
    }

    public static final int REBALANCE_STATE_CHANGE_FIELD_NUMBER = 26;

    private boolean hasRebalanceStateChange;

    private voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest rebalanceStateChange_;

    public boolean hasRebalanceStateChange() {
      return hasRebalanceStateChange;
    }

    public voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest getRebalanceStateChange() {
      return rebalanceStateChange_;
    }

    public static final int REPAIR_JOB_FIELD_NUMBER = 27;

    private boolean hasRepairJob;

    private voldemort.client.protocol.pb.VAdminProto.RepairJobRequest repairJob_;

    public boolean hasRepairJob() {
      return hasRepairJob;
    }

    public voldemort.client.protocol.pb.VAdminProto.RepairJobRequest getRepairJob() {
      return repairJob_;
    }

    public static final int 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    INITIATE_REBALANCE_NODE_ON_DONOR_FIELD_NUMBER = 28
=======
    NATIVE_BACKUP_FIELD_NUMBER = 28
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    ;

    private boolean 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    hasInitiateRebalanceNodeOnDonor
=======
    hasNativeBackup
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    ;

    private voldemort.client.protocol.pb.VAdminProto.
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    InitiateRebalanceNodeOnDonorRequest
=======
    NativeBackupRequest
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    initiateRebalanceNodeOnDonor_
=======
    nativeBackup_
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    ;

    public boolean hasInitiateRebalanceNodeOnDonor() {
      return hasInitiateRebalanceNodeOnDonor;
    }

    public boolean hasNativeBackup() {
      return hasNativeBackup;
    }

    public voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest getInitiateRebalanceNodeOnDonor() {
      return initiateRebalanceNodeOnDonor_;
    }

    public voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest getNativeBackup() {
      return nativeBackup_;
    }

    public static final int DELETE_STORE_REBALANCE_STATE_FIELD_NUMBER = 29;

    private boolean hasDeleteStoreRebalanceState;

    private voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest deleteStoreRebalanceState_;

    public boolean hasDeleteStoreRebalanceState() {
      return hasDeleteStoreRebalanceState;
    }

    public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest getDeleteStoreRebalanceState() {
      return deleteStoreRebalanceState_;
    }

    private void initFields() {
      type_ = voldemort.client.protocol.pb.VAdminProto.AdminRequestType.GET_METADATA;
      getMetadata_ = voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.getDefaultInstance();
      updateMetadata_ = voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.getDefaultInstance();
      updatePartitionEntries_ = voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.getDefaultInstance();
      fetchPartitionEntries_ = voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.getDefaultInstance();
      deletePartitionEntries_ = voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.getDefaultInstance();
      initiateFetchAndUpdate_ = voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.getDefaultInstance();
      asyncOperationStatus_ = voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.getDefaultInstance();
      initiateRebalanceNode_ = voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.getDefaultInstance();
      asyncOperationStop_ = voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.getDefaultInstance();
      asyncOperationList_ = voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.getDefaultInstance();
      truncateEntries_ = voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.getDefaultInstance();
      addStore_ = voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.getDefaultInstance();
      deleteStore_ = voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.getDefaultInstance();
      fetchStore_ = voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.getDefaultInstance();
      swapStore_ = voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.getDefaultInstance();
      rollbackStore_ = voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.getDefaultInstance();
      getRoMaxVersionDir_ = voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.getDefaultInstance();
      getRoCurrentVersionDir_ = voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.getDefaultInstance();
      fetchPartitionFiles_ = voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.getDefaultInstance();
      updateSlopEntries_ = voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.getDefaultInstance();
      failedFetchStore_ = voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.getDefaultInstance();
      getRoStorageFormat_ = voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.getDefaultInstance();
      rebalanceStateChange_ = voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.getDefaultInstance();
      repairJob_ = voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.getDefaultInstance();
      initiateRebalanceNodeOnDonor_ = voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.getDefaultInstance();

<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
      deleteStoreRebalanceState_
=======
      nativeBackup_
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
       = voldemort.client.protocol.pb.VAdminProto.
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
      DeleteStoreRebalanceStateRequest
=======
      NativeBackupRequest
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
      .getDefaultInstance();
    }

    public final boolean isInitialized() {
      if (!hasType) {
        return false;
      }
      if (hasGetMetadata()) {
        if (!getGetMetadata().isInitialized()) {
          return false;
        }
      }
      if (hasUpdateMetadata()) {
        if (!getUpdateMetadata().isInitialized()) {
          return false;
        }
      }
      if (hasUpdatePartitionEntries()) {
        if (!getUpdatePartitionEntries().isInitialized()) {
          return false;
        }
      }
      if (hasFetchPartitionEntries()) {
        if (!getFetchPartitionEntries().isInitialized()) {
          return false;
        }
      }
      if (hasDeletePartitionEntries()) {
        if (!getDeletePartitionEntries().isInitialized()) {
          return false;
        }
      }
      if (hasInitiateFetchAndUpdate()) {
        if (!getInitiateFetchAndUpdate().isInitialized()) {
          return false;
        }
      }
      if (hasAsyncOperationStatus()) {
        if (!getAsyncOperationStatus().isInitialized()) {
          return false;
        }
      }
      if (hasInitiateRebalanceNode()) {
        if (!getInitiateRebalanceNode().isInitialized()) {
          return false;
        }
      }
      if (hasAsyncOperationStop()) {
        if (!getAsyncOperationStop().isInitialized()) {
          return false;
        }
      }
      if (hasAsyncOperationList()) {
        if (!getAsyncOperationList().isInitialized()) {
          return false;
        }
      }
      if (hasTruncateEntries()) {
        if (!getTruncateEntries().isInitialized()) {
          return false;
        }
      }
      if (hasAddStore()) {
        if (!getAddStore().isInitialized()) {
          return false;
        }
      }
      if (hasDeleteStore()) {
        if (!getDeleteStore().isInitialized()) {
          return false;
        }
      }
      if (hasFetchStore()) {
        if (!getFetchStore().isInitialized()) {
          return false;
        }
      }
      if (hasSwapStore()) {
        if (!getSwapStore().isInitialized()) {
          return false;
        }
      }
      if (hasRollbackStore()) {
        if (!getRollbackStore().isInitialized()) {
          return false;
        }
      }
      if (hasFetchPartitionFiles()) {
        if (!getFetchPartitionFiles().isInitialized()) {
          return false;
        }
      }
      if (hasUpdateSlopEntries()) {
        if (!getUpdateSlopEntries().isInitialized()) {
          return false;
        }
      }
      if (hasFailedFetchStore()) {
        if (!getFailedFetchStore().isInitialized()) {
          return false;
        }
      }
      if (hasRebalanceStateChange()) {
        if (!getRebalanceStateChange().isInitialized()) {
          return false;
        }
      }
      if (hasInitiateRebalanceNodeOnDonor()) {
        if (!getInitiateRebalanceNodeOnDonor().isInitialized()) {
          return false;
        }
      }
      if (
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
      hasDeleteStoreRebalanceState()
=======
      hasNativeBackup()
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
      ) {
        if (!
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        getDeleteStoreRebalanceState()
=======
        getNativeBackup()
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        .isInitialized()) {
          return false;
        }
      }
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
      getSerializedSize();
      if (hasType()) {
        output.writeEnum(1, getType().getNumber());
      }
      if (hasGetMetadata()) {
        output.writeMessage(2, getGetMetadata());
      }
      if (hasUpdateMetadata()) {
        output.writeMessage(3, getUpdateMetadata());
      }
      if (hasUpdatePartitionEntries()) {
        output.writeMessage(4, getUpdatePartitionEntries());
      }
      if (hasFetchPartitionEntries()) {
        output.writeMessage(5, getFetchPartitionEntries());
      }
      if (hasDeletePartitionEntries()) {
        output.writeMessage(6, getDeletePartitionEntries());
      }
      if (hasInitiateFetchAndUpdate()) {
        output.writeMessage(7, getInitiateFetchAndUpdate());
      }
      if (hasAsyncOperationStatus()) {
        output.writeMessage(8, getAsyncOperationStatus());
      }
      if (hasInitiateRebalanceNode()) {
        output.writeMessage(9, getInitiateRebalanceNode());
      }
      if (hasAsyncOperationStop()) {
        output.writeMessage(10, getAsyncOperationStop());
      }
      if (hasAsyncOperationList()) {
        output.writeMessage(11, getAsyncOperationList());
      }
      if (hasTruncateEntries()) {
        output.writeMessage(12, getTruncateEntries());
      }
      if (hasAddStore()) {
        output.writeMessage(13, getAddStore());
      }
      if (hasDeleteStore()) {
        output.writeMessage(14, getDeleteStore());
      }
      if (hasFetchStore()) {
        output.writeMessage(15, getFetchStore());
      }
      if (hasSwapStore()) {
        output.writeMessage(16, getSwapStore());
      }
      if (hasRollbackStore()) {
        output.writeMessage(17, getRollbackStore());
      }
      if (hasGetRoMaxVersionDir()) {
        output.writeMessage(18, getGetRoMaxVersionDir());
      }
      if (hasGetRoCurrentVersionDir()) {
        output.writeMessage(19, getGetRoCurrentVersionDir());
      }
      if (hasFetchPartitionFiles()) {
        output.writeMessage(20, getFetchPartitionFiles());
      }
      if (hasUpdateSlopEntries()) {
        output.writeMessage(22, getUpdateSlopEntries());
      }
      if (hasFailedFetchStore()) {
        output.writeMessage(24, getFailedFetchStore());
      }
      if (hasGetRoStorageFormat()) {
        output.writeMessage(25, getGetRoStorageFormat());
      }
      if (hasRebalanceStateChange()) {
        output.writeMessage(26, getRebalanceStateChange());
      }
      if (hasRepairJob()) {
        output.writeMessage(27, getRepairJob());
      }
      if (
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
      hasInitiateRebalanceNodeOnDonor()
=======
      hasNativeBackup()
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
      ) {
        output.writeMessage(28, 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        getInitiateRebalanceNodeOnDonor()
=======
        getNativeBackup()
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        );
      }
      if (hasDeleteStoreRebalanceState()) {
        output.writeMessage(29, getDeleteStoreRebalanceState());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) {
        return size;
      }
      size = 0;
      if (hasType()) {
        size += com.google.protobuf.CodedOutputStream.computeEnumSize(1, getType().getNumber());
      }
      if (hasGetMetadata()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getGetMetadata());
      }
      if (hasUpdateMetadata()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, getUpdateMetadata());
      }
      if (hasUpdatePartitionEntries()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(4, getUpdatePartitionEntries());
      }
      if (hasFetchPartitionEntries()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(5, getFetchPartitionEntries());
      }
      if (hasDeletePartitionEntries()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(6, getDeletePartitionEntries());
      }
      if (hasInitiateFetchAndUpdate()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(7, getInitiateFetchAndUpdate());
      }
      if (hasAsyncOperationStatus()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(8, getAsyncOperationStatus());
      }
      if (hasInitiateRebalanceNode()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(9, getInitiateRebalanceNode());
      }
      if (hasAsyncOperationStop()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(10, getAsyncOperationStop());
      }
      if (hasAsyncOperationList()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(11, getAsyncOperationList());
      }
      if (hasTruncateEntries()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(12, getTruncateEntries());
      }
      if (hasAddStore()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(13, getAddStore());
      }
      if (hasDeleteStore()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(14, getDeleteStore());
      }
      if (hasFetchStore()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(15, getFetchStore());
      }
      if (hasSwapStore()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(16, getSwapStore());
      }
      if (hasRollbackStore()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(17, getRollbackStore());
      }
      if (hasGetRoMaxVersionDir()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(18, getGetRoMaxVersionDir());
      }
      if (hasGetRoCurrentVersionDir()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(19, getGetRoCurrentVersionDir());
      }
      if (hasFetchPartitionFiles()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(20, getFetchPartitionFiles());
      }
      if (hasUpdateSlopEntries()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(22, getUpdateSlopEntries());
      }
      if (hasFailedFetchStore()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(24, getFailedFetchStore());
      }
      if (hasGetRoStorageFormat()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(25, getGetRoStorageFormat());
      }
      if (hasRebalanceStateChange()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(26, getRebalanceStateChange());
      }
      if (hasRepairJob()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(27, getRepairJob());
      }
      if (
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
      hasInitiateRebalanceNodeOnDonor()
=======
      hasNativeBackup()
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
      ) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(28, 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        getInitiateRebalanceNodeOnDonor()
=======
        getNativeBackup()
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        );
      }
      if (hasDeleteStoreRebalanceState()) {
        size += com.google.protobuf.CodedOutputStream.computeMessageSize(29, getDeleteStoreRebalanceState());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest parseFrom(java.io.InputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }

    public static voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest result;

      private Builder() {
      }

      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest();
        return builder;
      }

      protected voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest internalGetResult() {
        return result;
      }

      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException("Cannot call clear() after build().");
        }
        result = new voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(result);
      }

      public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
        return voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest.getDescriptor();
      }

      public voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest getDefaultInstanceForType() {
        return voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest.getDefaultInstance();
      }

      public boolean isInitialized() {
        return result.isInitialized();
      }

      public voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }

      private voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest buildParsed() throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }

      public voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException("build() has already been called on this Builder.");
        }
        voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest returnMe = result;
        result = null;
        return returnMe;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest) {
          return mergeFrom((voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest other) {
        if (other == voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest.getDefaultInstance()) {
          return this;
        }
        if (other.hasType()) {
          setType(other.getType());
        }
        if (other.hasGetMetadata()) {
          mergeGetMetadata(other.getGetMetadata());
        }
        if (other.hasUpdateMetadata()) {
          mergeUpdateMetadata(other.getUpdateMetadata());
        }
        if (other.hasUpdatePartitionEntries()) {
          mergeUpdatePartitionEntries(other.getUpdatePartitionEntries());
        }
        if (other.hasFetchPartitionEntries()) {
          mergeFetchPartitionEntries(other.getFetchPartitionEntries());
        }
        if (other.hasDeletePartitionEntries()) {
          mergeDeletePartitionEntries(other.getDeletePartitionEntries());
        }
        if (other.hasInitiateFetchAndUpdate()) {
          mergeInitiateFetchAndUpdate(other.getInitiateFetchAndUpdate());
        }
        if (other.hasAsyncOperationStatus()) {
          mergeAsyncOperationStatus(other.getAsyncOperationStatus());
        }
        if (other.hasInitiateRebalanceNode()) {
          mergeInitiateRebalanceNode(other.getInitiateRebalanceNode());
        }
        if (other.hasAsyncOperationStop()) {
          mergeAsyncOperationStop(other.getAsyncOperationStop());
        }
        if (other.hasAsyncOperationList()) {
          mergeAsyncOperationList(other.getAsyncOperationList());
        }
        if (other.hasTruncateEntries()) {
          mergeTruncateEntries(other.getTruncateEntries());
        }
        if (other.hasAddStore()) {
          mergeAddStore(other.getAddStore());
        }
        if (other.hasDeleteStore()) {
          mergeDeleteStore(other.getDeleteStore());
        }
        if (other.hasFetchStore()) {
          mergeFetchStore(other.getFetchStore());
        }
        if (other.hasSwapStore()) {
          mergeSwapStore(other.getSwapStore());
        }
        if (other.hasRollbackStore()) {
          mergeRollbackStore(other.getRollbackStore());
        }
        if (other.hasGetRoMaxVersionDir()) {
          mergeGetRoMaxVersionDir(other.getGetRoMaxVersionDir());
        }
        if (other.hasGetRoCurrentVersionDir()) {
          mergeGetRoCurrentVersionDir(other.getGetRoCurrentVersionDir());
        }
        if (other.hasFetchPartitionFiles()) {
          mergeFetchPartitionFiles(other.getFetchPartitionFiles());
        }
        if (other.hasUpdateSlopEntries()) {
          mergeUpdateSlopEntries(other.getUpdateSlopEntries());
        }
        if (other.hasFailedFetchStore()) {
          mergeFailedFetchStore(other.getFailedFetchStore());
        }
        if (other.hasGetRoStorageFormat()) {
          mergeGetRoStorageFormat(other.getGetRoStorageFormat());
        }
        if (other.hasRebalanceStateChange()) {
          mergeRebalanceStateChange(other.getRebalanceStateChange());
        }
        if (other.hasRepairJob()) {
          mergeRepairJob(other.getRepairJob());
        }
        if (other.hasInitiateRebalanceNodeOnDonor()) {
          mergeInitiateRebalanceNodeOnDonor(other.getInitiateRebalanceNodeOnDonor());
        }
        if (other.
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        hasDeleteStoreRebalanceState()
=======
        hasNativeBackup()
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        ) {

<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
          mergeDeleteStoreRebalanceState(other.getDeleteStoreRebalanceState())
=======
          mergeNativeBackup(other.getNativeBackup())
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
          ;
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
            default:
            {
              if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8:
            {
              int rawValue = input.readEnum();
              voldemort.client.protocol.pb.VAdminProto.AdminRequestType value = voldemort.client.protocol.pb.VAdminProto.AdminRequestType.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(1, rawValue);
              } else {
                setType(value);
              }
              break;
            }
            case 18:
            {
              voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.newBuilder();
              if (hasGetMetadata()) {
                subBuilder.mergeFrom(getGetMetadata());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setGetMetadata(subBuilder.buildPartial());
              break;
            }
            case 26:
            {
              voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.newBuilder();
              if (hasUpdateMetadata()) {
                subBuilder.mergeFrom(getUpdateMetadata());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setUpdateMetadata(subBuilder.buildPartial());
              break;
            }
            case 34:
            {
              voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.newBuilder();
              if (hasUpdatePartitionEntries()) {
                subBuilder.mergeFrom(getUpdatePartitionEntries());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setUpdatePartitionEntries(subBuilder.buildPartial());
              break;
            }
            case 42:
            {
              voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.newBuilder();
              if (hasFetchPartitionEntries()) {
                subBuilder.mergeFrom(getFetchPartitionEntries());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setFetchPartitionEntries(subBuilder.buildPartial());
              break;
            }
            case 50:
            {
              voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.newBuilder();
              if (hasDeletePartitionEntries()) {
                subBuilder.mergeFrom(getDeletePartitionEntries());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setDeletePartitionEntries(subBuilder.buildPartial());
              break;
            }
            case 58:
            {
              voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.newBuilder();
              if (hasInitiateFetchAndUpdate()) {
                subBuilder.mergeFrom(getInitiateFetchAndUpdate());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setInitiateFetchAndUpdate(subBuilder.buildPartial());
              break;
            }
            case 66:
            {
              voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.newBuilder();
              if (hasAsyncOperationStatus()) {
                subBuilder.mergeFrom(getAsyncOperationStatus());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setAsyncOperationStatus(subBuilder.buildPartial());
              break;
            }
            case 74:
            {
              voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.newBuilder();
              if (hasInitiateRebalanceNode()) {
                subBuilder.mergeFrom(getInitiateRebalanceNode());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setInitiateRebalanceNode(subBuilder.buildPartial());
              break;
            }
            case 82:
            {
              voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.newBuilder();
              if (hasAsyncOperationStop()) {
                subBuilder.mergeFrom(getAsyncOperationStop());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setAsyncOperationStop(subBuilder.buildPartial());
              break;
            }
            case 90:
            {
              voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.newBuilder();
              if (hasAsyncOperationList()) {
                subBuilder.mergeFrom(getAsyncOperationList());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setAsyncOperationList(subBuilder.buildPartial());
              break;
            }
            case 98:
            {
              voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.newBuilder();
              if (hasTruncateEntries()) {
                subBuilder.mergeFrom(getTruncateEntries());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setTruncateEntries(subBuilder.buildPartial());
              break;
            }
            case 106:
            {
              voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.newBuilder();
              if (hasAddStore()) {
                subBuilder.mergeFrom(getAddStore());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setAddStore(subBuilder.buildPartial());
              break;
            }
            case 114:
            {
              voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.newBuilder();
              if (hasDeleteStore()) {
                subBuilder.mergeFrom(getDeleteStore());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setDeleteStore(subBuilder.buildPartial());
              break;
            }
            case 122:
            {
              voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.newBuilder();
              if (hasFetchStore()) {
                subBuilder.mergeFrom(getFetchStore());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setFetchStore(subBuilder.buildPartial());
              break;
            }
            case 130:
            {
              voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.newBuilder();
              if (hasSwapStore()) {
                subBuilder.mergeFrom(getSwapStore());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setSwapStore(subBuilder.buildPartial());
              break;
            }
            case 138:
            {
              voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.newBuilder();
              if (hasRollbackStore()) {
                subBuilder.mergeFrom(getRollbackStore());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setRollbackStore(subBuilder.buildPartial());
              break;
            }
            case 146:
            {
              voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.newBuilder();
              if (hasGetRoMaxVersionDir()) {
                subBuilder.mergeFrom(getGetRoMaxVersionDir());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setGetRoMaxVersionDir(subBuilder.buildPartial());
              break;
            }
            case 154:
            {
              voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.newBuilder();
              if (hasGetRoCurrentVersionDir()) {
                subBuilder.mergeFrom(getGetRoCurrentVersionDir());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setGetRoCurrentVersionDir(subBuilder.buildPartial());
              break;
            }
            case 162:
            {
              voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.newBuilder();
              if (hasFetchPartitionFiles()) {
                subBuilder.mergeFrom(getFetchPartitionFiles());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setFetchPartitionFiles(subBuilder.buildPartial());
              break;
            }
            case 178:
            {
              voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.newBuilder();
              if (hasUpdateSlopEntries()) {
                subBuilder.mergeFrom(getUpdateSlopEntries());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setUpdateSlopEntries(subBuilder.buildPartial());
              break;
            }
            case 194:
            {
              voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.newBuilder();
              if (hasFailedFetchStore()) {
                subBuilder.mergeFrom(getFailedFetchStore());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setFailedFetchStore(subBuilder.buildPartial());
              break;
            }
            case 202:
            {
              voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.newBuilder();
              if (hasGetRoStorageFormat()) {
                subBuilder.mergeFrom(getGetRoStorageFormat());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setGetRoStorageFormat(subBuilder.buildPartial());
              break;
            }
            case 210:
            {
              voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.newBuilder();
              if (hasRebalanceStateChange()) {
                subBuilder.mergeFrom(getRebalanceStateChange());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setRebalanceStateChange(subBuilder.buildPartial());
              break;
            }
            case 218:
            {
              voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.newBuilder();
              if (hasRepairJob()) {
                subBuilder.mergeFrom(getRepairJob());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setRepairJob(subBuilder.buildPartial());
              break;
            }
            case 226:
            {
              voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.newBuilder();
              if (hasInitiateRebalanceNodeOnDonor()) {
                subBuilder.mergeFrom(getInitiateRebalanceNodeOnDonor());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setInitiateRebalanceNodeOnDonor(subBuilder.buildPartial());
              break;
            }
            case 234:
            {
              voldemort.client.protocol.pb.VAdminProto.
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
              DeleteStoreRebalanceStateRequest
=======
              NativeBackupRequest
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
              .Builder subBuilder = voldemort.client.protocol.pb.VAdminProto.
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
              DeleteStoreRebalanceStateRequest
=======
              NativeBackupRequest
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
              .newBuilder();
              if (
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
              hasDeleteStoreRebalanceState()
=======
              hasNativeBackup()
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
              ) {
                subBuilder.mergeFrom(
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
                getDeleteStoreRebalanceState()
=======
                getNativeBackup()
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
                );
              }
              input.readMessage(subBuilder, extensionRegistry);

<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
              setDeleteStoreRebalanceState(subBuilder.buildPartial())
=======
              setNativeBackup(subBuilder.buildPartial())
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
              ;
              break;
            }
          }
        }
      }

      public boolean hasType() {
        return result.hasType();
      }

      public voldemort.client.protocol.pb.VAdminProto.AdminRequestType getType() {
        return result.getType();
      }

      public Builder setType(voldemort.client.protocol.pb.VAdminProto.AdminRequestType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasType = true;
        result.type_ = value;
        return this;
      }

      public Builder clearType() {
        result.hasType = false;
        result.type_ = voldemort.client.protocol.pb.VAdminProto.AdminRequestType.GET_METADATA;
        return this;
      }

      public boolean hasGetMetadata() {
        return result.hasGetMetadata();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest getGetMetadata() {
        return result.getGetMetadata();
      }

      public Builder setGetMetadata(voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasGetMetadata = true;
        result.getMetadata_ = value;
        return this;
      }

      public Builder setGetMetadata(voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.Builder builderForValue) {
        result.hasGetMetadata = true;
        result.getMetadata_ = builderForValue.build();
        return this;
      }

      public Builder mergeGetMetadata(voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest value) {
        if (result.hasGetMetadata() && result.getMetadata_ != voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.getDefaultInstance()) {
          result.getMetadata_ = voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.newBuilder(result.getMetadata_).mergeFrom(value).buildPartial();
        } else {
          result.getMetadata_ = value;
        }
        result.hasGetMetadata = true;
        return this;
      }

      public Builder clearGetMetadata() {
        result.hasGetMetadata = false;
        result.getMetadata_ = voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.getDefaultInstance();
        return this;
      }

      public boolean hasUpdateMetadata() {
        return result.hasUpdateMetadata();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest getUpdateMetadata() {
        return result.getUpdateMetadata();
      }

      public Builder setUpdateMetadata(voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasUpdateMetadata = true;
        result.updateMetadata_ = value;
        return this;
      }

      public Builder setUpdateMetadata(voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.Builder builderForValue) {
        result.hasUpdateMetadata = true;
        result.updateMetadata_ = builderForValue.build();
        return this;
      }

      public Builder mergeUpdateMetadata(voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest value) {
        if (result.hasUpdateMetadata() && result.updateMetadata_ != voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.getDefaultInstance()) {
          result.updateMetadata_ = voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.newBuilder(result.updateMetadata_).mergeFrom(value).buildPartial();
        } else {
          result.updateMetadata_ = value;
        }
        result.hasUpdateMetadata = true;
        return this;
      }

      public Builder clearUpdateMetadata() {
        result.hasUpdateMetadata = false;
        result.updateMetadata_ = voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.getDefaultInstance();
        return this;
      }

      public boolean hasUpdatePartitionEntries() {
        return result.hasUpdatePartitionEntries();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest getUpdatePartitionEntries() {
        return result.getUpdatePartitionEntries();
      }

      public Builder setUpdatePartitionEntries(voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasUpdatePartitionEntries = true;
        result.updatePartitionEntries_ = value;
        return this;
      }

      public Builder setUpdatePartitionEntries(voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.Builder builderForValue) {
        result.hasUpdatePartitionEntries = true;
        result.updatePartitionEntries_ = builderForValue.build();
        return this;
      }

      public Builder mergeUpdatePartitionEntries(voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest value) {
        if (result.hasUpdatePartitionEntries() && result.updatePartitionEntries_ != voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.getDefaultInstance()) {
          result.updatePartitionEntries_ = voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.newBuilder(result.updatePartitionEntries_).mergeFrom(value).buildPartial();
        } else {
          result.updatePartitionEntries_ = value;
        }
        result.hasUpdatePartitionEntries = true;
        return this;
      }

      public Builder clearUpdatePartitionEntries() {
        result.hasUpdatePartitionEntries = false;
        result.updatePartitionEntries_ = voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.getDefaultInstance();
        return this;
      }

      public boolean hasFetchPartitionEntries() {
        return result.hasFetchPartitionEntries();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest getFetchPartitionEntries() {
        return result.getFetchPartitionEntries();
      }

      public Builder setFetchPartitionEntries(voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasFetchPartitionEntries = true;
        result.fetchPartitionEntries_ = value;
        return this;
      }

      public Builder setFetchPartitionEntries(voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.Builder builderForValue) {
        result.hasFetchPartitionEntries = true;
        result.fetchPartitionEntries_ = builderForValue.build();
        return this;
      }

      public Builder mergeFetchPartitionEntries(voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest value) {
        if (result.hasFetchPartitionEntries() && result.fetchPartitionEntries_ != voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.getDefaultInstance()) {
          result.fetchPartitionEntries_ = voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.newBuilder(result.fetchPartitionEntries_).mergeFrom(value).buildPartial();
        } else {
          result.fetchPartitionEntries_ = value;
        }
        result.hasFetchPartitionEntries = true;
        return this;
      }

      public Builder clearFetchPartitionEntries() {
        result.hasFetchPartitionEntries = false;
        result.fetchPartitionEntries_ = voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.getDefaultInstance();
        return this;
      }

      public boolean hasDeletePartitionEntries() {
        return result.hasDeletePartitionEntries();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest getDeletePartitionEntries() {
        return result.getDeletePartitionEntries();
      }

      public Builder setDeletePartitionEntries(voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasDeletePartitionEntries = true;
        result.deletePartitionEntries_ = value;
        return this;
      }

      public Builder setDeletePartitionEntries(voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.Builder builderForValue) {
        result.hasDeletePartitionEntries = true;
        result.deletePartitionEntries_ = builderForValue.build();
        return this;
      }

      public Builder mergeDeletePartitionEntries(voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest value) {
        if (result.hasDeletePartitionEntries() && result.deletePartitionEntries_ != voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.getDefaultInstance()) {
          result.deletePartitionEntries_ = voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.newBuilder(result.deletePartitionEntries_).mergeFrom(value).buildPartial();
        } else {
          result.deletePartitionEntries_ = value;
        }
        result.hasDeletePartitionEntries = true;
        return this;
      }

      public Builder clearDeletePartitionEntries() {
        result.hasDeletePartitionEntries = false;
        result.deletePartitionEntries_ = voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.getDefaultInstance();
        return this;
      }

      public boolean hasInitiateFetchAndUpdate() {
        return result.hasInitiateFetchAndUpdate();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest getInitiateFetchAndUpdate() {
        return result.getInitiateFetchAndUpdate();
      }

      public Builder setInitiateFetchAndUpdate(voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasInitiateFetchAndUpdate = true;
        result.initiateFetchAndUpdate_ = value;
        return this;
      }

      public Builder setInitiateFetchAndUpdate(voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.Builder builderForValue) {
        result.hasInitiateFetchAndUpdate = true;
        result.initiateFetchAndUpdate_ = builderForValue.build();
        return this;
      }

      public Builder mergeInitiateFetchAndUpdate(voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest value) {
        if (result.hasInitiateFetchAndUpdate() && result.initiateFetchAndUpdate_ != voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.getDefaultInstance()) {
          result.initiateFetchAndUpdate_ = voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.newBuilder(result.initiateFetchAndUpdate_).mergeFrom(value).buildPartial();
        } else {
          result.initiateFetchAndUpdate_ = value;
        }
        result.hasInitiateFetchAndUpdate = true;
        return this;
      }

      public Builder clearInitiateFetchAndUpdate() {
        result.hasInitiateFetchAndUpdate = false;
        result.initiateFetchAndUpdate_ = voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.getDefaultInstance();
        return this;
      }

      public boolean hasAsyncOperationStatus() {
        return result.hasAsyncOperationStatus();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest getAsyncOperationStatus() {
        return result.getAsyncOperationStatus();
      }

      public Builder setAsyncOperationStatus(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasAsyncOperationStatus = true;
        result.asyncOperationStatus_ = value;
        return this;
      }

      public Builder setAsyncOperationStatus(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.Builder builderForValue) {
        result.hasAsyncOperationStatus = true;
        result.asyncOperationStatus_ = builderForValue.build();
        return this;
      }

      public Builder mergeAsyncOperationStatus(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest value) {
        if (result.hasAsyncOperationStatus() && result.asyncOperationStatus_ != voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.getDefaultInstance()) {
          result.asyncOperationStatus_ = voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.newBuilder(result.asyncOperationStatus_).mergeFrom(value).buildPartial();
        } else {
          result.asyncOperationStatus_ = value;
        }
        result.hasAsyncOperationStatus = true;
        return this;
      }

      public Builder clearAsyncOperationStatus() {
        result.hasAsyncOperationStatus = false;
        result.asyncOperationStatus_ = voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.getDefaultInstance();
        return this;
      }

      public boolean hasInitiateRebalanceNode() {
        return result.hasInitiateRebalanceNode();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest getInitiateRebalanceNode() {
        return result.getInitiateRebalanceNode();
      }

      public Builder setInitiateRebalanceNode(voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasInitiateRebalanceNode = true;
        result.initiateRebalanceNode_ = value;
        return this;
      }

      public Builder setInitiateRebalanceNode(voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.Builder builderForValue) {
        result.hasInitiateRebalanceNode = true;
        result.initiateRebalanceNode_ = builderForValue.build();
        return this;
      }

      public Builder mergeInitiateRebalanceNode(voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest value) {
        if (result.hasInitiateRebalanceNode() && result.initiateRebalanceNode_ != voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.getDefaultInstance()) {
          result.initiateRebalanceNode_ = voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.newBuilder(result.initiateRebalanceNode_).mergeFrom(value).buildPartial();
        } else {
          result.initiateRebalanceNode_ = value;
        }
        result.hasInitiateRebalanceNode = true;
        return this;
      }

      public Builder clearInitiateRebalanceNode() {
        result.hasInitiateRebalanceNode = false;
        result.initiateRebalanceNode_ = voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.getDefaultInstance();
        return this;
      }

      public boolean hasAsyncOperationStop() {
        return result.hasAsyncOperationStop();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest getAsyncOperationStop() {
        return result.getAsyncOperationStop();
      }

      public Builder setAsyncOperationStop(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasAsyncOperationStop = true;
        result.asyncOperationStop_ = value;
        return this;
      }

      public Builder setAsyncOperationStop(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.Builder builderForValue) {
        result.hasAsyncOperationStop = true;
        result.asyncOperationStop_ = builderForValue.build();
        return this;
      }

      public Builder mergeAsyncOperationStop(voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest value) {
        if (result.hasAsyncOperationStop() && result.asyncOperationStop_ != voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.getDefaultInstance()) {
          result.asyncOperationStop_ = voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.newBuilder(result.asyncOperationStop_).mergeFrom(value).buildPartial();
        } else {
          result.asyncOperationStop_ = value;
        }
        result.hasAsyncOperationStop = true;
        return this;
      }

      public Builder clearAsyncOperationStop() {
        result.hasAsyncOperationStop = false;
        result.asyncOperationStop_ = voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.getDefaultInstance();
        return this;
      }

      public boolean hasAsyncOperationList() {
        return result.hasAsyncOperationList();
      }

      public voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest getAsyncOperationList() {
        return result.getAsyncOperationList();
      }

      public Builder setAsyncOperationList(voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasAsyncOperationList = true;
        result.asyncOperationList_ = value;
        return this;
      }

      public Builder setAsyncOperationList(voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.Builder builderForValue) {
        result.hasAsyncOperationList = true;
        result.asyncOperationList_ = builderForValue.build();
        return this;
      }

      public Builder mergeAsyncOperationList(voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest value) {
        if (result.hasAsyncOperationList() && result.asyncOperationList_ != voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.getDefaultInstance()) {
          result.asyncOperationList_ = voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.newBuilder(result.asyncOperationList_).mergeFrom(value).buildPartial();
        } else {
          result.asyncOperationList_ = value;
        }
        result.hasAsyncOperationList = true;
        return this;
      }

      public Builder clearAsyncOperationList() {
        result.hasAsyncOperationList = false;
        result.asyncOperationList_ = voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.getDefaultInstance();
        return this;
      }

      public boolean hasTruncateEntries() {
        return result.hasTruncateEntries();
      }

      public voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest getTruncateEntries() {
        return result.getTruncateEntries();
      }

      public Builder setTruncateEntries(voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasTruncateEntries = true;
        result.truncateEntries_ = value;
        return this;
      }

      public Builder setTruncateEntries(voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.Builder builderForValue) {
        result.hasTruncateEntries = true;
        result.truncateEntries_ = builderForValue.build();
        return this;
      }

      public Builder mergeTruncateEntries(voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest value) {
        if (result.hasTruncateEntries() && result.truncateEntries_ != voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.getDefaultInstance()) {
          result.truncateEntries_ = voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.newBuilder(result.truncateEntries_).mergeFrom(value).buildPartial();
        } else {
          result.truncateEntries_ = value;
        }
        result.hasTruncateEntries = true;
        return this;
      }

      public Builder clearTruncateEntries() {
        result.hasTruncateEntries = false;
        result.truncateEntries_ = voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.getDefaultInstance();
        return this;
      }

      public boolean hasAddStore() {
        return result.hasAddStore();
      }

      public voldemort.client.protocol.pb.VAdminProto.AddStoreRequest getAddStore() {
        return result.getAddStore();
      }

      public Builder setAddStore(voldemort.client.protocol.pb.VAdminProto.AddStoreRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasAddStore = true;
        result.addStore_ = value;
        return this;
      }

      public Builder setAddStore(voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.Builder builderForValue) {
        result.hasAddStore = true;
        result.addStore_ = builderForValue.build();
        return this;
      }

      public Builder mergeAddStore(voldemort.client.protocol.pb.VAdminProto.AddStoreRequest value) {
        if (result.hasAddStore() && result.addStore_ != voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.getDefaultInstance()) {
          result.addStore_ = voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.newBuilder(result.addStore_).mergeFrom(value).buildPartial();
        } else {
          result.addStore_ = value;
        }
        result.hasAddStore = true;
        return this;
      }

      public Builder clearAddStore() {
        result.hasAddStore = false;
        result.addStore_ = voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.getDefaultInstance();
        return this;
      }

      public boolean hasDeleteStore() {
        return result.hasDeleteStore();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest getDeleteStore() {
        return result.getDeleteStore();
      }

      public Builder setDeleteStore(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasDeleteStore = true;
        result.deleteStore_ = value;
        return this;
      }

      public Builder setDeleteStore(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.Builder builderForValue) {
        result.hasDeleteStore = true;
        result.deleteStore_ = builderForValue.build();
        return this;
      }

      public Builder mergeDeleteStore(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest value) {
        if (result.hasDeleteStore() && result.deleteStore_ != voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.getDefaultInstance()) {
          result.deleteStore_ = voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.newBuilder(result.deleteStore_).mergeFrom(value).buildPartial();
        } else {
          result.deleteStore_ = value;
        }
        result.hasDeleteStore = true;
        return this;
      }

      public Builder clearDeleteStore() {
        result.hasDeleteStore = false;
        result.deleteStore_ = voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.getDefaultInstance();
        return this;
      }

      public boolean hasFetchStore() {
        return result.hasFetchStore();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest getFetchStore() {
        return result.getFetchStore();
      }

      public Builder setFetchStore(voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasFetchStore = true;
        result.fetchStore_ = value;
        return this;
      }

      public Builder setFetchStore(voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.Builder builderForValue) {
        result.hasFetchStore = true;
        result.fetchStore_ = builderForValue.build();
        return this;
      }

      public Builder mergeFetchStore(voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest value) {
        if (result.hasFetchStore() && result.fetchStore_ != voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.getDefaultInstance()) {
          result.fetchStore_ = voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.newBuilder(result.fetchStore_).mergeFrom(value).buildPartial();
        } else {
          result.fetchStore_ = value;
        }
        result.hasFetchStore = true;
        return this;
      }

      public Builder clearFetchStore() {
        result.hasFetchStore = false;
        result.fetchStore_ = voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.getDefaultInstance();
        return this;
      }

      public boolean hasSwapStore() {
        return result.hasSwapStore();
      }

      public voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest getSwapStore() {
        return result.getSwapStore();
      }

      public Builder setSwapStore(voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasSwapStore = true;
        result.swapStore_ = value;
        return this;
      }

      public Builder setSwapStore(voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.Builder builderForValue) {
        result.hasSwapStore = true;
        result.swapStore_ = builderForValue.build();
        return this;
      }

      public Builder mergeSwapStore(voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest value) {
        if (result.hasSwapStore() && result.swapStore_ != voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.getDefaultInstance()) {
          result.swapStore_ = voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.newBuilder(result.swapStore_).mergeFrom(value).buildPartial();
        } else {
          result.swapStore_ = value;
        }
        result.hasSwapStore = true;
        return this;
      }

      public Builder clearSwapStore() {
        result.hasSwapStore = false;
        result.swapStore_ = voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.getDefaultInstance();
        return this;
      }

      public boolean hasRollbackStore() {
        return result.hasRollbackStore();
      }

      public voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest getRollbackStore() {
        return result.getRollbackStore();
      }

      public Builder setRollbackStore(voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasRollbackStore = true;
        result.rollbackStore_ = value;
        return this;
      }

      public Builder setRollbackStore(voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.Builder builderForValue) {
        result.hasRollbackStore = true;
        result.rollbackStore_ = builderForValue.build();
        return this;
      }

      public Builder mergeRollbackStore(voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest value) {
        if (result.hasRollbackStore() && result.rollbackStore_ != voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.getDefaultInstance()) {
          result.rollbackStore_ = voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.newBuilder(result.rollbackStore_).mergeFrom(value).buildPartial();
        } else {
          result.rollbackStore_ = value;
        }
        result.hasRollbackStore = true;
        return this;
      }

      public Builder clearRollbackStore() {
        result.hasRollbackStore = false;
        result.rollbackStore_ = voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.getDefaultInstance();
        return this;
      }

      public boolean hasGetRoMaxVersionDir() {
        return result.hasGetRoMaxVersionDir();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest getGetRoMaxVersionDir() {
        return result.getGetRoMaxVersionDir();
      }

      public Builder setGetRoMaxVersionDir(voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasGetRoMaxVersionDir = true;
        result.getRoMaxVersionDir_ = value;
        return this;
      }

      public Builder setGetRoMaxVersionDir(voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.Builder builderForValue) {
        result.hasGetRoMaxVersionDir = true;
        result.getRoMaxVersionDir_ = builderForValue.build();
        return this;
      }

      public Builder mergeGetRoMaxVersionDir(voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest value) {
        if (result.hasGetRoMaxVersionDir() && result.getRoMaxVersionDir_ != voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.getDefaultInstance()) {
          result.getRoMaxVersionDir_ = voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.newBuilder(result.getRoMaxVersionDir_).mergeFrom(value).buildPartial();
        } else {
          result.getRoMaxVersionDir_ = value;
        }
        result.hasGetRoMaxVersionDir = true;
        return this;
      }

      public Builder clearGetRoMaxVersionDir() {
        result.hasGetRoMaxVersionDir = false;
        result.getRoMaxVersionDir_ = voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.getDefaultInstance();
        return this;
      }

      public boolean hasGetRoCurrentVersionDir() {
        return result.hasGetRoCurrentVersionDir();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest getGetRoCurrentVersionDir() {
        return result.getGetRoCurrentVersionDir();
      }

      public Builder setGetRoCurrentVersionDir(voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasGetRoCurrentVersionDir = true;
        result.getRoCurrentVersionDir_ = value;
        return this;
      }

      public Builder setGetRoCurrentVersionDir(voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.Builder builderForValue) {
        result.hasGetRoCurrentVersionDir = true;
        result.getRoCurrentVersionDir_ = builderForValue.build();
        return this;
      }

      public Builder mergeGetRoCurrentVersionDir(voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest value) {
        if (result.hasGetRoCurrentVersionDir() && result.getRoCurrentVersionDir_ != voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.getDefaultInstance()) {
          result.getRoCurrentVersionDir_ = voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.newBuilder(result.getRoCurrentVersionDir_).mergeFrom(value).buildPartial();
        } else {
          result.getRoCurrentVersionDir_ = value;
        }
        result.hasGetRoCurrentVersionDir = true;
        return this;
      }

      public Builder clearGetRoCurrentVersionDir() {
        result.hasGetRoCurrentVersionDir = false;
        result.getRoCurrentVersionDir_ = voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.getDefaultInstance();
        return this;
      }

      public boolean hasFetchPartitionFiles() {
        return result.hasFetchPartitionFiles();
      }

      public voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest getFetchPartitionFiles() {
        return result.getFetchPartitionFiles();
      }

      public Builder setFetchPartitionFiles(voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasFetchPartitionFiles = true;
        result.fetchPartitionFiles_ = value;
        return this;
      }

      public Builder setFetchPartitionFiles(voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.Builder builderForValue) {
        result.hasFetchPartitionFiles = true;
        result.fetchPartitionFiles_ = builderForValue.build();
        return this;
      }

      public Builder mergeFetchPartitionFiles(voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest value) {
        if (result.hasFetchPartitionFiles() && result.fetchPartitionFiles_ != voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.getDefaultInstance()) {
          result.fetchPartitionFiles_ = voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.newBuilder(result.fetchPartitionFiles_).mergeFrom(value).buildPartial();
        } else {
          result.fetchPartitionFiles_ = value;
        }
        result.hasFetchPartitionFiles = true;
        return this;
      }

      public Builder clearFetchPartitionFiles() {
        result.hasFetchPartitionFiles = false;
        result.fetchPartitionFiles_ = voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.getDefaultInstance();
        return this;
      }

      public boolean hasUpdateSlopEntries() {
        return result.hasUpdateSlopEntries();
      }

      public voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest getUpdateSlopEntries() {
        return result.getUpdateSlopEntries();
      }

      public Builder setUpdateSlopEntries(voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasUpdateSlopEntries = true;
        result.updateSlopEntries_ = value;
        return this;
      }

      public Builder setUpdateSlopEntries(voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.Builder builderForValue) {
        result.hasUpdateSlopEntries = true;
        result.updateSlopEntries_ = builderForValue.build();
        return this;
      }

      public Builder mergeUpdateSlopEntries(voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest value) {
        if (result.hasUpdateSlopEntries() && result.updateSlopEntries_ != voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.getDefaultInstance()) {
          result.updateSlopEntries_ = voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.newBuilder(result.updateSlopEntries_).mergeFrom(value).buildPartial();
        } else {
          result.updateSlopEntries_ = value;
        }
        result.hasUpdateSlopEntries = true;
        return this;
      }

      public Builder clearUpdateSlopEntries() {
        result.hasUpdateSlopEntries = false;
        result.updateSlopEntries_ = voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.getDefaultInstance();
        return this;
      }

      public boolean hasFailedFetchStore() {
        return result.hasFailedFetchStore();
      }

      public voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest getFailedFetchStore() {
        return result.getFailedFetchStore();
      }

      public Builder setFailedFetchStore(voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasFailedFetchStore = true;
        result.failedFetchStore_ = value;
        return this;
      }

      public Builder setFailedFetchStore(voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.Builder builderForValue) {
        result.hasFailedFetchStore = true;
        result.failedFetchStore_ = builderForValue.build();
        return this;
      }

      public Builder mergeFailedFetchStore(voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest value) {
        if (result.hasFailedFetchStore() && result.failedFetchStore_ != voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.getDefaultInstance()) {
          result.failedFetchStore_ = voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.newBuilder(result.failedFetchStore_).mergeFrom(value).buildPartial();
        } else {
          result.failedFetchStore_ = value;
        }
        result.hasFailedFetchStore = true;
        return this;
      }

      public Builder clearFailedFetchStore() {
        result.hasFailedFetchStore = false;
        result.failedFetchStore_ = voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.getDefaultInstance();
        return this;
      }

      public boolean hasGetRoStorageFormat() {
        return result.hasGetRoStorageFormat();
      }

      public voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest getGetRoStorageFormat() {
        return result.getGetRoStorageFormat();
      }

      public Builder setGetRoStorageFormat(voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasGetRoStorageFormat = true;
        result.getRoStorageFormat_ = value;
        return this;
      }

      public Builder setGetRoStorageFormat(voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.Builder builderForValue) {
        result.hasGetRoStorageFormat = true;
        result.getRoStorageFormat_ = builderForValue.build();
        return this;
      }

      public Builder mergeGetRoStorageFormat(voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest value) {
        if (result.hasGetRoStorageFormat() && result.getRoStorageFormat_ != voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.getDefaultInstance()) {
          result.getRoStorageFormat_ = voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.newBuilder(result.getRoStorageFormat_).mergeFrom(value).buildPartial();
        } else {
          result.getRoStorageFormat_ = value;
        }
        result.hasGetRoStorageFormat = true;
        return this;
      }

      public Builder clearGetRoStorageFormat() {
        result.hasGetRoStorageFormat = false;
        result.getRoStorageFormat_ = voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.getDefaultInstance();
        return this;
      }

      public boolean hasRebalanceStateChange() {
        return result.hasRebalanceStateChange();
      }

      public voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest getRebalanceStateChange() {
        return result.getRebalanceStateChange();
      }

      public Builder setRebalanceStateChange(voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasRebalanceStateChange = true;
        result.rebalanceStateChange_ = value;
        return this;
      }

      public Builder setRebalanceStateChange(voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.Builder builderForValue) {
        result.hasRebalanceStateChange = true;
        result.rebalanceStateChange_ = builderForValue.build();
        return this;
      }

      public Builder mergeRebalanceStateChange(voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest value) {
        if (result.hasRebalanceStateChange() && result.rebalanceStateChange_ != voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.getDefaultInstance()) {
          result.rebalanceStateChange_ = voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.newBuilder(result.rebalanceStateChange_).mergeFrom(value).buildPartial();
        } else {
          result.rebalanceStateChange_ = value;
        }
        result.hasRebalanceStateChange = true;
        return this;
      }

      public Builder clearRebalanceStateChange() {
        result.hasRebalanceStateChange = false;
        result.rebalanceStateChange_ = voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.getDefaultInstance();
        return this;
      }

      public boolean hasRepairJob() {
        return result.hasRepairJob();
      }

      public voldemort.client.protocol.pb.VAdminProto.RepairJobRequest getRepairJob() {
        return result.getRepairJob();
      }

      public Builder setRepairJob(voldemort.client.protocol.pb.VAdminProto.RepairJobRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasRepairJob = true;
        result.repairJob_ = value;
        return this;
      }

      public Builder setRepairJob(voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.Builder builderForValue) {
        result.hasRepairJob = true;
        result.repairJob_ = builderForValue.build();
        return this;
      }

      public Builder mergeRepairJob(voldemort.client.protocol.pb.VAdminProto.RepairJobRequest value) {
        if (result.hasRepairJob() && result.repairJob_ != voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.getDefaultInstance()) {
          result.repairJob_ = voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.newBuilder(result.repairJob_).mergeFrom(value).buildPartial();
        } else {
          result.repairJob_ = value;
        }
        result.hasRepairJob = true;
        return this;
      }

      public Builder clearRepairJob() {
        result.hasRepairJob = false;
        result.repairJob_ = voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.getDefaultInstance();
        return this;
      }

      public boolean hasInitiateRebalanceNodeOnDonor() {
        return result.hasInitiateRebalanceNodeOnDonor();
      }

      public boolean hasNativeBackup() {
        return result.hasNativeBackup();
      }

      public voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest getInitiateRebalanceNodeOnDonor() {
        return result.getInitiateRebalanceNodeOnDonor();
      }

      public voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest getNativeBackup() {
        return result.getNativeBackup();
      }

      public Builder setInitiateRebalanceNodeOnDonor(voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasInitiateRebalanceNodeOnDonor = true;
        result.initiateRebalanceNodeOnDonor_ = value;
        return this;
      }

      public Builder setNativeBackup(voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasNativeBackup = true;
        result.nativeBackup_ = value;
        return this;
      }

      public Builder setInitiateRebalanceNodeOnDonor(voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.Builder builderForValue) {
        result.hasInitiateRebalanceNodeOnDonor = true;
        result.initiateRebalanceNodeOnDonor_ = builderForValue.build();
        return this;
      }

      public Builder setNativeBackup(voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest.Builder builderForValue) {
        result.hasNativeBackup = true;
        result.nativeBackup_ = builderForValue.build();
        return this;
      }

      public Builder mergeInitiateRebalanceNodeOnDonor(voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest value) {
        if (result.hasInitiateRebalanceNodeOnDonor() && result.initiateRebalanceNodeOnDonor_ != voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.getDefaultInstance()) {
          result.initiateRebalanceNodeOnDonor_ = voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.newBuilder(result.initiateRebalanceNodeOnDonor_).mergeFrom(value).buildPartial();
        } else {
          result.initiateRebalanceNodeOnDonor_ = value;
        }
        result.hasInitiateRebalanceNodeOnDonor = true;
        return this;
      }

      public Builder mergeNativeBackup(voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest value) {
        if (result.hasNativeBackup() && result.nativeBackup_ != voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest.getDefaultInstance()) {
          result.nativeBackup_ = voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest.newBuilder(result.nativeBackup_).mergeFrom(value).buildPartial();
        } else {
          result.nativeBackup_ = value;
        }
        result.hasNativeBackup = true;
        return this;
      }

      public Builder clearInitiateRebalanceNodeOnDonor() {
        result.hasInitiateRebalanceNodeOnDonor = false;
        result.initiateRebalanceNodeOnDonor_ = voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.getDefaultInstance();
        return this;
      }

      public Builder clearNativeBackup() {
        result.hasNativeBackup = false;
        result.nativeBackup_ = voldemort.client.protocol.pb.VAdminProto.NativeBackupRequest.getDefaultInstance();
        return this;
      }

      public boolean hasDeleteStoreRebalanceState() {
        return result.hasDeleteStoreRebalanceState();
      }

      public voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest getDeleteStoreRebalanceState() {
        return result.getDeleteStoreRebalanceState();
      }

      public Builder setDeleteStoreRebalanceState(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasDeleteStoreRebalanceState = true;
        result.deleteStoreRebalanceState_ = value;
        return this;
      }

      public Builder setDeleteStoreRebalanceState(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest.Builder builderForValue) {
        result.hasDeleteStoreRebalanceState = true;
        result.deleteStoreRebalanceState_ = builderForValue.build();
        return this;
      }

      public Builder mergeDeleteStoreRebalanceState(voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest value) {
        if (result.hasDeleteStoreRebalanceState() && result.deleteStoreRebalanceState_ != voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest.getDefaultInstance()) {
          result.deleteStoreRebalanceState_ = voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest.newBuilder(result.deleteStoreRebalanceState_).mergeFrom(value).buildPartial();
        } else {
          result.deleteStoreRebalanceState_ = value;
        }
        result.hasDeleteStoreRebalanceState = true;
        return this;
      }

      public Builder clearDeleteStoreRebalanceState() {
        result.hasDeleteStoreRebalanceState = false;
        result.deleteStoreRebalanceState_ = voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateRequest.getDefaultInstance();
        return this;
      }
    }

    static {
      defaultInstance = new VoldemortAdminRequest(true);
      voldemort.client.protocol.pb.VAdminProto.internalForceInit();
      defaultInstance.initFields();
    }
  }

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_GetMetadataRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_GetMetadataRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_GetMetadataResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_GetMetadataResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_UpdateMetadataRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_UpdateMetadataRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_UpdateMetadataResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_UpdateMetadataResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_FileEntry_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_FileEntry_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_PartitionEntry_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_PartitionEntry_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_UpdatePartitionEntriesRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_UpdatePartitionEntriesRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_UpdatePartitionEntriesResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_UpdatePartitionEntriesResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_VoldemortFilter_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_VoldemortFilter_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_UpdateSlopEntriesRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_UpdateSlopEntriesRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_UpdateSlopEntriesResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_UpdateSlopEntriesResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_FetchPartitionFilesRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_FetchPartitionFilesRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_FetchPartitionEntriesRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_FetchPartitionEntriesRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_FetchPartitionEntriesResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_FetchPartitionEntriesResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_DeletePartitionEntriesRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_DeletePartitionEntriesRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_DeletePartitionEntriesResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_DeletePartitionEntriesResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_InitiateFetchAndUpdateRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_InitiateFetchAndUpdateRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_AsyncOperationStatusRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_AsyncOperationStatusRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_AsyncOperationStopRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_AsyncOperationStopRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_AsyncOperationStopResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_AsyncOperationStopResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_AsyncOperationListRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_AsyncOperationListRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_AsyncOperationListResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_AsyncOperationListResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_PartitionTuple_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_PartitionTuple_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_PerStorePartitionTuple_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_PerStorePartitionTuple_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_RebalancePartitionInfoMap_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_RebalancePartitionInfoMap_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_InitiateRebalanceNodeRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_InitiateRebalanceNodeRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
  internal_static_voldemort_InitiateRebalanceNodeOnDonorRequest_descriptor
=======
  internal_static_voldemort_NativeBackupRequest_descriptor
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
  ;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_AsyncOperationStatusResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
  internal_static_voldemort_InitiateRebalanceNodeOnDonorRequest_fieldAccessorTable
=======
  internal_static_voldemort_NativeBackupRequest_fieldAccessorTable
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
  ;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_AsyncOperationStatusResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_TruncateEntriesRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_TruncateEntriesRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_TruncateEntriesResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_TruncateEntriesResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_AddStoreRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_AddStoreRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_AddStoreResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_AddStoreResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_DeleteStoreRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_DeleteStoreRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_DeleteStoreResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_DeleteStoreResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_FetchStoreRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_FetchStoreRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_SwapStoreRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_SwapStoreRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_SwapStoreResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_SwapStoreResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_RollbackStoreRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_RollbackStoreRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_RollbackStoreResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_RollbackStoreResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_RepairJobRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_RepairJobRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_RepairJobResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_RepairJobResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_ROStoreVersionDirMap_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_ROStoreVersionDirMap_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_GetROMaxVersionDirRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_GetROMaxVersionDirRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_GetROMaxVersionDirResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_GetROMaxVersionDirResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_GetROCurrentVersionDirRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_GetROCurrentVersionDirRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_GetROCurrentVersionDirResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_GetROCurrentVersionDirResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_GetROStorageFormatRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_GetROStorageFormatRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_GetROStorageFormatResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_GetROStorageFormatResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_FailedFetchStoreRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_FailedFetchStoreRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_FailedFetchStoreResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_FailedFetchStoreResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_RebalanceStateChangeRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_RebalanceStateChangeRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_RebalanceStateChangeResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_RebalanceStateChangeResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_DeleteStoreRebalanceStateRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_DeleteStoreRebalanceStateRequest_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_DeleteStoreRebalanceStateResponse_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_DeleteStoreRebalanceStateResponse_fieldAccessorTable;

  private static com.google.protobuf.Descriptors.Descriptor internal_static_voldemort_VoldemortAdminRequest_descriptor;

  private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_voldemort_VoldemortAdminRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = { "\n\u0015voldemort-admin.proto\u0012\tvoldemort\u001a\u0016vold" + "emort-client.proto\"!\n\u0012GetMetadataRequest" + "\u0012\u000b\n\u0003key\u0018\u0001 \u0002(\f\"]\n\u0013GetMetadataResponse\u0012%\n\u0007" + "version\u0018\u0001 \u0001(\u000b2\u0014.voldemort.Versioned\u0012\u001f\n\u0005e" + "rror\u0018\u0002 \u0001(\u000b2\u0010.voldemort.Error\"M\n\u0015UpdateMe" + "tadataRequest\u0012\u000b\n\u0003key\u0018\u0001 \u0002(\f\u0012\'\n\tversioned\u0018" + "\u0002 \u0002(\u000b2\u0014.voldemort.Versioned\"9\n\u0016UpdateMet" + "adataResponse\u0012\u001f\n\u0005error\u0018\u0001 \u0001(\u000b2\u0010.voldemort" + ".Error\"7\n\tFileEntry\u0012\u0011\n\tfile_name\u0018\u0001 \u0002(\t\u0012\u0017" + "\n\u000ffile_size_bytes\u0018\u0002 \u0002(\u0003\"F\n\u000ePartitionEntr", "y\u0012\u000b\n\u0003key\u0018\u0001 \u0002(\f\u0012\'\n\tversioned\u0018\u0002 \u0002(\u000b2\u0014.vold" + "emort.Versioned\"\u008e\u0001\n\u001dUpdatePartitionEntri" + "esRequest\u0012\r\n\u0005store\u0018\u0001 \u0002(\t\u00122\n\u000fpartition_en" + "try\u0018\u0002 \u0002(\u000b2\u0019.voldemort.PartitionEntry\u0012*\n\u0006" + "filter\u0018\u0003 \u0001(\u000b2\u001a.voldemort.VoldemortFilter" + "\"A\n\u001eUpdatePartitionEntriesResponse\u0012\u001f\n\u0005er" + "ror\u0018\u0001 \u0001(\u000b2\u0010.voldemort.Error\"-\n\u000fVoldemort" + "Filter\u0012\f\n\u0004name\u0018\u0001 \u0002(\t\u0012\f\n\u0004data\u0018\u0002 \u0002(\f\"\u00af\u0001\n\u0018U" + "pdateSlopEntriesRequest\u0012\r\n\u0005store\u0018\u0001 \u0002(\t\u0012\u000b" + "\n\u0003key\u0018\u0002 \u0002(\f\u0012\'\n\u0007version\u0018\u0003 \u0002(\u000b2\u0016.voldemort", ".VectorClock\u0012,\n\frequest_type\u0018\u0004 \u0002(\u000e2\u0016.vol" + "demort.RequestType\u0012\r\n\u0005value\u0018\u0005 \u0001(\f\u0012\u0011\n\ttra" + "nsform\u0018\u0006 \u0001(\f\"<\n\u0019UpdateSlopEntriesRespons" + "e\u0012\u001f\n\u0005error\u0018\u0001 \u0001(\u000b2\u0010.voldemort.Error\"d\n\u001aFe" + "tchPartitionFilesRequest\u0012\r\n\u0005store\u0018\u0001 \u0002(\t\u0012" + "7\n\u0014replica_to_partition\u0018\u0002 \u0003(\u000b2\u0019.voldemor" + "t.PartitionTuple\"\u00d7\u0001\n\u001cFetchPartitionEntri" + "esRequest\u00127\n\u0014replica_to_partition\u0018\u0001 \u0003(\u000b2" + "\u0019.voldemort.PartitionTuple\u0012\r\n\u0005store\u0018\u0002 \u0002(" + "\t\u0012*\n\u0006filter\u0018\u0003 \u0001(\u000b2\u001a.voldemort.VoldemortF", "ilter\u0012\u0014\n\ffetch_values\u0018\u0004 \u0001(\b\u0012\u0014\n\fskip_reco" + "rds\u0018\u0005 \u0001(\u0003\u0012\u0017\n\u000finitial_cluster\u0018\u0006 \u0001(\t\"\u0081\u0001\n\u001dF" + "etchPartitionEntriesResponse\u00122\n\u000fpartitio" + "n_entry\u0018\u0001 \u0001(\u000b2\u0019.voldemort.PartitionEntry" + "\u0012\u000b\n\u0003key\u0018\u0002 \u0001(\f\u0012\u001f\n\u0005error\u0018\u0003 \u0001(\u000b2\u0010.voldemort" + ".Error\"\u00ac\u0001\n\u001dDeletePartitionEntriesRequest" + "\u0012\r\n\u0005store\u0018\u0001 \u0002(\t\u00127\n\u0014replica_to_partition\u0018" + "\u0002 \u0003(\u000b2\u0019.voldemort.PartitionTuple\u0012*\n\u0006filt" + "er\u0018\u0003 \u0001(\u000b2\u001a.voldemort.VoldemortFilter\u0012\u0017\n\u000f" + "initial_cluster\u0018\u0004 \u0001(\t\"P\n\u001eDeletePartition", "EntriesResponse\u0012\r\n\u0005count\u0018\u0001 \u0001(\u0003\u0012\u001f\n\u0005error\u0018" + "\u0002 \u0001(\u000b2\u0010.voldemort.Error\"\u00cf\u0001\n\u001dInitiateFetc" + "hAndUpdateRequest\u0012\u000f\n\u0007node_id\u0018\u0001 \u0002(\u0005\u0012\r\n\u0005st" + "ore\u0018\u0002 \u0002(\t\u0012*\n\u0006filter\u0018\u0003 \u0001(\u000b2\u001a.voldemort.Vo" + "ldemortFilter\u00127\n\u0014replica_to_partition\u0018\u0004 " + "\u0003(\u000b2\u0019.voldemort.PartitionTuple\u0012\u0017\n\u000finitia" + "l_cluster\u0018\u0005 \u0001(\t\u0012\u0010\n\boptimize\u0018\u0006 \u0001(\b\"1\n\u001bAsy" + "ncOperationStatusRequest\u0012\u0012\n\nrequest_id\u0018\u0001" + " \u0002(\u0005\"/\n\u0019AsyncOperationStopRequest\u0012\u0012\n\nreq" + "uest_id\u0018\u0001 \u0002(\u0005\"=\n\u001aAsyncOperationStopRespo", "nse\u0012\u001f\n\u0005error\u0018\u0001 \u0001(\u000b2\u0010.voldemort.Error\"2\n\u0019" + "AsyncOperationListRequest\u0012\u0015\n\rshow_comple" + "te\u0018\u0002 \u0002(\b\"R\n\u001aAsyncOperationListResponse\u0012\u0013" + "\n\u000brequest_ids\u0018\u0001 \u0003(\u0005\u0012\u001f\n\u0005error\u0018\u0002 \u0001(\u000b2\u0010.vol" + "demort.Error\":\n\u000ePartitionTuple\u0012\u0014\n\freplic" + "a_type\u0018\u0001 \u0002(\u0005\u0012\u0012\n\npartitions\u0018\u0002 \u0003(\u0005\"e\n\u0016PerS" + "torePartitionTuple\u0012\u0012\n\nstore_name\u0018\u0001 \u0002(\t\u00127" + "\n\u0014replica_to_partition\u0018\u0002 \u0003(\u000b2\u0019.voldemort" + ".PartitionTuple\"\u00f8\u0001\n\u0019RebalancePartitionIn" + "foMap\u0012\u0012\n\nstealer_id\u0018\u0001 \u0002(\u0005\u0012\u0010\n\bdonor_id\u0018\u0002 ", "\u0002(\u0005\u0012\u000f\n\u0007attempt\u0018\u0003 \u0002(\u0005\u0012C\n\u0018replica_to_add_p" + "artition\u0018\u0004 \u0003(\u000b2!.voldemort.PerStoreParti" + "tionTuple\u0012F\n\u001breplica_to_delete_partition" + "\u0018\u0005 \u0003(\u000b2!.voldemort.PerStorePartitionTupl" + "e\u0012\u0017\n\u000finitial_cluster\u0018\u0006 \u0002(\t\"f\n\u001cInitiateRe" + "balanceNodeRequest\u0012F\n\u0018rebalance_partitio" + "n_info\u0018\u0001 \u0002(\u000b2$.voldemort.RebalancePartit" + "ionInfoMap\"m\n#InitiateRebalanceNodeOnDon" + "orRequest\u0012F\n\u0018rebalance_partition_info\u0018\u0001 " + "\u0003(\u000b2$.voldemort.RebalancePartitionInfoMa", "p\"\u008a\u0001\n\u001cAsyncOperationStatusResponse\u0012\u0012\n\nre" + "quest_id\u0018\u0001 \u0001(\u0005\u0012\u0013\n\u000bdescription\u0018\u0002 \u0001(\t\u0012\u000e\n\u0006s" + "tatus\u0018\u0003 \u0001(\t\u0012\u0010\n\bcomplete\u0018\u0004 \u0001(\b\u0012\u001f\n\u0005error\u0018\u0005" + " \u0001(\u000b2\u0010.voldemort.Error\"\'\n\u0016TruncateEntrie" + "sRequest\u0012\r\n\u0005store\u0018\u0001 \u0002(\t\":\n\u0017TruncateEntri" + "esResponse\u0012\u001f\n\u0005error\u0018\u0001 \u0001(\u000b2\u0010.voldemort.Er" + "ror\"*\n\u000fAddStoreRequest\u0012\u0017\n\u000fstoreDefinitio" + "n\u0018\u0001 \u0002(\t\"3\n\u0010AddStoreResponse\u0012\u001f\n\u0005error\u0018\u0001 \u0001" + "(\u000b2\u0010.voldemort.Error\"\'\n\u0012DeleteStoreReque" + "st\u0012\u0011\n\tstoreName\u0018\u0001 \u0002(\t\"6\n\u0013DeleteStoreResp", "onse\u0012\u001f\n\u0005error\u0018\u0001 \u0001(\u000b2\u0010.voldemort.Error\"P\n" + "\u0011FetchStoreRequest\u0012\u0012\n\nstore_name\u0018\u0001 \u0002(\t\u0012\u0011" + "\n\tstore_dir\u0018\u0002 \u0002(\t\u0012\u0014\n\fpush_version\u0018\u0003 \u0001(\u0003\"" + "9\n\u0010SwapStoreRequest\u0012\u0012\n\nstore_name\u0018\u0001 \u0002(\t\u0012" + "\u0011\n\tstore_dir\u0018\u0002 \u0002(\t\"P\n\u0011SwapStoreResponse\u0012" + "\u001f\n\u0005error\u0018\u0001 \u0001(\u000b2\u0010.voldemort.Error\u0012\u001a\n\u0012prev" + "ious_store_dir\u0018\u0002 \u0001(\t\"@\n\u0014RollbackStoreReq" + "uest\u0012\u0012\n\nstore_name\u0018\u0001 \u0002(\t\u0012\u0014\n\fpush_version" + "\u0018\u0002 \u0002(\u0003\"8\n\u0015RollbackStoreResponse\u0012\u001f\n\u0005error" + "\u0018\u0001 \u0001(\u000b2\u0010.voldemort.Error\"&\n\u0010RepairJobReq", "uest\u0012\u0012\n\nstore_name\u0018\u0001 \u0001(\t\"4\n\u0011RepairJobRes" + "ponse\u0012\u001f\n\u0005error\u0018\u0001 \u0001(\u000b2\u0010.voldemort.Error\"=" + "\n\u0014ROStoreVersionDirMap\u0012\u0012\n\nstore_name\u0018\u0001 \u0002" + "(\t\u0012\u0011\n\tstore_dir\u0018\u0002 \u0002(\t\"/\n\u0019GetROMaxVersion" + "DirRequest\u0012\u0012\n\nstore_name\u0018\u0001 \u0003(\t\"y\n\u001aGetROM" + "axVersionDirResponse\u0012:\n\u0011ro_store_version" + "s\u0018\u0001 \u0003(\u000b2\u001f.voldemort.ROStoreVersionDirMap" + "\u0012\u001f\n\u0005error\u0018\u0002 \u0001(\u000b2\u0010.voldemort.Error\"3\n\u001dGet" + "ROCurrentVersionDirRequest\u0012\u0012\n\nstore_name" + "\u0018\u0001 \u0003(\t\"}\n\u001eGetROCurrentVersionDirResponse", "\u0012:\n\u0011ro_store_versions\u0018\u0001 \u0003(\u000b2\u001f.voldemort." + "ROStoreVersionDirMap\u0012\u001f\n\u0005error\u0018\u0002 \u0001(\u000b2\u0010.vo" + "ldemort.Error\"/\n\u0019GetROStorageFormatReque" + "st\u0012\u0012\n\nstore_name\u0018\u0001 \u0003(\t\"y\n\u001aGetROStorageFo" + "rmatResponse\u0012:\n\u0011ro_store_versions\u0018\u0001 \u0003(\u000b2" + "\u001f.voldemort.ROStoreVersionDirMap\u0012\u001f\n\u0005erro" + "r\u0018\u0002 \u0001(\u000b2\u0010.voldemort.Error\"@\n\u0017FailedFetch" + "StoreRequest\u0012\u0012\n\nstore_name\u0018\u0001 \u0002(\t\u0012\u0011\n\tstor" + "e_dir\u0018\u0002 \u0002(\t\";\n\u0018FailedFetchStoreResponse\u0012" + "\u001f\n\u0005error\u0018\u0001 \u0001(\u000b2\u0010.voldemort.Error\"\u00e6\u0001\n\u001bReb", "alanceStateChangeRequest\u0012K\n\u001drebalance_pa" + "rtition_info_list\u0018\u0001 \u0003(\u000b2$.voldemort.Reba" + "lancePartitionInfoMap\u0012\u0016\n\u000ecluster_string\u0018" + "\u0002 \u0002(\t\u0012\u000f\n\u0007swap_ro\u0018\u0003 \u0002(\b\u0012\u001f\n\u0017change_cluster" + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "_metadata\u0018\u0004 \u0002(\b\u0012\u001e\n\u0016change_rebalance_stat"
=======
    "\u000b2\u0010.voldemort.Error\"=\n\u0013NativeBackupReque"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "e\u0018\u0005 \u0002(\b\u0012\u0010\n\brollback\u0018\u0006 \u0002(\b\"?\n\u001cRebalanceSt"
=======
    "st\u0012\u0012\n\nstore_name\u0018\u0001 \u0002(\t\u0012\u0012\n\nbackup_dir\u0018\u0002 \u0002"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "ateChangeResponse\u0012\u001f\n\u0005error\u0018\u0001 \u0001(\u000b2\u0010.volde"
=======
    "(\t\"\u008a\r\n\u0015VoldemortAdminRequest\u0012)\n\u0004type\u0018\u0001 \u0002"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "mort.Error\"G\n DeleteStoreRebalanceStateR"
=======
    "(\u000e2\u001b.voldemort.AdminRequestType\u00123\n\fget_m"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "equest\u0012\u0012\n\nstore_name\u0018\u0001 \u0002(\t\u0012\u000f\n\u0007node_id\u0018\u0002 "
=======
    "etadata\u0018\u0002 \u0001(\u000b2\u001d.voldemort.GetMetadataReq"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u0002(\u0005\"D\n!DeleteStoreRebalanceStateResponse"
=======
    "uest\u00129\n\u000fupdate_metadata\u0018\u0003 \u0001(\u000b2 .voldemor"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    , 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u0012\u001f\n\u0005error\u0018\u0001 \u0001(\u000b2\u0010.voldemort.Error\"\u0080\u000e\n\u0015Vo"
=======
    "t.UpdateMetadataRequest\u0012J\n\u0018update_partit"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "ldemortAdminRequest\u0012)\n\u0004type\u0018\u0001 \u0002(\u000e2\u001b.vold"
=======
    "ion_entries\u0018\u0004 \u0001(\u000b2(.voldemort.UpdatePart"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "emort.AdminRequestType\u00123\n\fget_metadata\u0018\u0002"
=======
    "itionEntriesRequest\u0012H\n\u0017fetch_partition_e"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    " \u0001(\u000b2\u001d.voldemort.GetMetadataRequest\u00129\n\u000fu"
=======
    "ntries\u0018\u0005 \u0001(\u000b2\'.voldemort.FetchPartitionE"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "pdate_metadata\u0018\u0003 \u0001(\u000b2 .voldemort.UpdateM"
=======
    "ntriesRequest\u0012J\n\u0018delete_partition_entrie"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "etadataRequest\u0012J\n\u0018update_partition_entri"
=======
    "s\u0018\u0006 \u0001(\u000b2(.voldemort.DeletePartitionEntri"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "es\u0018\u0004 \u0001(\u000b2(.voldemort.UpdatePartitionEntr"
=======
    "esRequest\u0012K\n\u0019initiate_fetch_and_update\u0018\u0007"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "iesRequest\u0012H\n\u0017fetch_partition_entries\u0018\u0005 "
=======
    " \u0001(\u000b2(.voldemort.InitiateFetchAndUpdateR"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u0001(\u000b2\'.voldemort.FetchPartitionEntriesReq"
=======
    "equest\u0012F\n\u0016async_operation_status\u0018\b \u0001(\u000b2&"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "uest\u0012J\n\u0018delete_partition_entries\u0018\u0006 \u0001(\u000b2("
=======
    ".voldemort.AsyncOperationStatusRequest\u0012H"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    , 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    ".voldemort.DeletePartitionEntriesRequest"
=======
    "\n\u0017initiate_rebalance_node\u0018\t \u0001(\u000b2\'.voldem"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u0012K\n\u0019initiate_fetch_and_update\u0018\u0007 \u0001(\u000b2(.vo"
=======
    "ort.InitiateRebalanceNodeRequest\u0012B\n\u0014asyn"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "ldemort.InitiateFetchAndUpdateRequest\u0012F\n"
=======
    "c_operation_stop\u0018\n \u0001(\u000b2$.voldemort.Async"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u0016async_operation_status\u0018\b \u0001(\u000b2&.voldemor"
=======
    "OperationStopRequest\u0012B\n\u0014async_operation_"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "t.AsyncOperationStatusRequest\u0012H\n\u0017initiat"
=======
    "list\u0018\u000b \u0001(\u000b2$.voldemort.AsyncOperationLis"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "e_rebalance_node\u0018\t \u0001(\u000b2\'.voldemort.Initi"
=======
    "tRequest\u0012;\n\u0010truncate_entries\u0018\f \u0001(\u000b2!.vol"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "ateRebalanceNodeRequest\u0012B\n\u0014async_operati"
=======
    "demort.TruncateEntriesRequest\u0012-\n\tadd_sto"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "on_stop\u0018\n \u0001(\u000b2$.voldemort.AsyncOperation"
=======
    "re\u0018\r \u0001(\u000b2\u001a.voldemort.AddStoreRequest\u00123\n\f"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "StopRequest\u0012B\n\u0014async_operation_list\u0018\u000b \u0001("
=======
    "delete_store\u0018\u000e \u0001(\u000b2\u001d.voldemort.DeleteSto"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u000b2$.voldemort.AsyncOperationListRequest\u0012"
=======
    "reRequest\u00121\n\u000bfetch_store\u0018\u000f \u0001(\u000b2\u001c.voldemo"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    , 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    ";\n\u0010truncate_entries\u0018\f \u0001(\u000b2!.voldemort.Tr"
=======
    "rt.FetchStoreRequest\u0012/\n\nswap_store\u0018\u0010 \u0001(\u000b"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "uncateEntriesRequest\u0012-\n\tadd_store\u0018\r \u0001(\u000b2"
=======
    "2\u001b.voldemort.SwapStoreRequest\u00127\n\u000erollbac"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u001a.voldemort.AddStoreRequest\u00123\n\fdelete_st"
=======
    "k_store\u0018\u0011 \u0001(\u000b2\u001f.voldemort.RollbackStoreR"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "ore\u0018\u000e \u0001(\u000b2\u001d.voldemort.DeleteStoreRequest"
=======
    "equest\u0012D\n\u0016get_ro_max_version_dir\u0018\u0012 \u0001(\u000b2$"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u00121\n\u000bfetch_store\u0018\u000f \u0001(\u000b2\u001c.voldemort.FetchS"
=======
    ".voldemort.GetROMaxVersionDirRequest\u0012L\n\u001a"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "toreRequest\u0012/\n\nswap_store\u0018\u0010 \u0001(\u000b2\u001b.voldem"
=======
    "get_ro_current_version_dir\u0018\u0013 \u0001(\u000b2(.volde"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "ort.SwapStoreRequest\u00127\n\u000erollback_store\u0018\u0011"
=======
    "mort.GetROCurrentVersionDirRequest\u0012D\n\u0015fe"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    " \u0001(\u000b2\u001f.voldemort.RollbackStoreRequest\u0012D\n"
=======
    "tch_partition_files\u0018\u0014 \u0001(\u000b2%.voldemort.Fe"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u0016get_ro_max_version_dir\u0018\u0012 \u0001(\u000b2$.voldemor"
=======
    "tchPartitionFilesRequest\u0012@\n\u0013update_slop_"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "t.GetROMaxVersionDirRequest\u0012L\n\u001aget_ro_cu"
=======
    "entries\u0018\u0016 \u0001(\u000b2#.voldemort.UpdateSlopEntr"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    , 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "rrent_version_dir\u0018\u0013 \u0001(\u000b2(.voldemort.GetR"
=======
    "iesRequest\u0012>\n\u0012failed_fetch_store\u0018\u0018 \u0001(\u000b2\""
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "OCurrentVersionDirRequest\u0012D\n\u0015fetch_parti"
=======
    ".voldemort.FailedFetchStoreRequest\u0012C\n\u0015ge"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "tion_files\u0018\u0014 \u0001(\u000b2%.voldemort.FetchPartit"
=======
    "t_ro_storage_format\u0018\u0019 \u0001(\u000b2$.voldemort.Ge"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "ionFilesRequest\u0012@\n\u0013update_slop_entries\u0018\u0016"
=======
    "tROStorageFormatRequest\u0012F\n\u0016rebalance_sta"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    " \u0001(\u000b2#.voldemort.UpdateSlopEntriesReques"
=======
    "te_change\u0018\u001a \u0001(\u000b2&.voldemort.RebalanceSta"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "t\u0012>\n\u0012failed_fetch_store\u0018\u0018 \u0001(\u000b2\".voldemor"
=======
    "teChangeRequest\u0012/\n\nrepair_job\u0018\u001b \u0001(\u000b2\u001b.vo"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "t.FailedFetchStoreRequest\u0012C\n\u0015get_ro_stor"
=======
    "ldemort.RepairJobRequest\u00125\n\rnative_backu"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "age_format\u0018\u0019 \u0001(\u000b2$.voldemort.GetROStorag"
=======
    "p\u0018\u001c \u0001(\u000b2\u001e.voldemort.NativeBackupRequest*"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "eFormatRequest\u0012F\n\u0016rebalance_state_change"
=======
    "\u00ec\u0004\n\u0010AdminRequestType\u0012\u0010\n\fGET_METADATA\u0010\u0000\u0012\u0013"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u0018\u001a \u0001(\u000b2&.voldemort.RebalanceStateChangeR"
=======
    "\n\u000fUPDATE_METADATA\u0010\u0001\u0012\u001c\n\u0018UPDATE_PARTITION_"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    , 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "equest\u0012/\n\nrepair_job\u0018\u001b \u0001(\u000b2\u001b.voldemort.R"
=======
    "ENTRIES\u0010\u0002\u0012\u001b\n\u0017FETCH_PARTITION_ENTRIES\u0010\u0003\u0012\u001c"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "epairJobRequest\u0012X\n initiate_rebalance_no"
=======
    "\n\u0018DELETE_PARTITION_ENTRIES\u0010\u0004\u0012\u001d\n\u0019INITIATE"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "de_on_donor\u0018\u001c \u0001(\u000b2..voldemort.InitiateRe"
=======
    "_FETCH_AND_UPDATE\u0010\u0005\u0012\u001a\n\u0016ASYNC_OPERATION_S"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "balanceNodeOnDonorRequest\u0012Q\n\u001cdelete_stor"
=======
    "TATUS\u0010\u0006\u0012\u001b\n\u0017INITIATE_REBALANCE_NODE\u0010\u0007\u0012\u0018\n\u0014"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "e_rebalance_state\u0018\u001d \u0001(\u000b2+.voldemort.Dele"
=======
    "ASYNC_OPERATION_STOP\u0010\b\u0012\u0018\n\u0014ASYNC_OPERATIO"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "teStoreRebalanceStateRequest*\u00a1\u0005\n\u0010AdminRe"
=======
    "N_LIST\u0010\t\u0012\u0014\n\u0010TRUNCATE_ENTRIES\u0010\n\u0012\r\n\tADD_ST"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "questType\u0012\u0010\n\fGET_METADATA\u0010\u0000\u0012\u0013\n\u000fUPDATE_ME"
=======
    "ORE\u0010\u000b\u0012\u0010\n\fDELETE_STORE\u0010\f\u0012\u000f\n\u000bFETCH_STORE\u0010\r"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "TADATA\u0010\u0001\u0012\u001c\n\u0018UPDATE_PARTITION_ENTRIES\u0010\u0002\u0012\u001b"
=======
    "\u0012\u000e\n\nSWAP_STORE\u0010\u000e\u0012\u0012\n\u000eROLLBACK_STORE\u0010\u000f\u0012\u001a\n\u0016"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\n\u0017FETCH_PARTITION_ENTRIES\u0010\u0003\u0012\u001c\n\u0018DELETE_PA"
=======
    "GET_RO_MAX_VERSION_DIR\u0010\u0010\u0012\u001e\n\u001aGET_RO_CURRE"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "RTITION_ENTRIES\u0010\u0004\u0012\u001d\n\u0019INITIATE_FETCH_AND_"
=======
    "NT_VERSION_DIR\u0010\u0011\u0012\u0019\n\u0015FETCH_PARTITION_FILE"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    , 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "UPDATE\u0010\u0005\u0012\u001a\n\u0016ASYNC_OPERATION_STATUS\u0010\u0006\u0012\u001b\n\u0017" + "INITIATE_REBALANCE_NODE\u0010\u0007\u0012\u0018\n\u0014ASYNC_OPERA" + "TION_STOP\u0010\b\u0012\u0018\n\u0014ASYNC_OPERATION_LIST\u0010\t\u0012\u0014\n" + "\u0010TRUNCATE_ENTRIES\u0010\n\u0012\r\n\tADD_STORE\u0010\u000b\u0012\u0010\n\fDE" + "LETE_STORE\u0010\f\u0012\u000f\n\u000bFETCH_STORE\u0010\r\u0012\u000e\n\nSWAP_ST" + "ORE\u0010\u000e\u0012\u0012\n\u000eROLLBACK_STORE\u0010\u000f\u0012\u001a\n\u0016GET_RO_MAX_" + "VERSION_DIR\u0010\u0010\u0012\u001e\n\u001aGET_RO_CURRENT_VERSION_"
=======
    "S\u0010\u0012\u0012\u0017\n\u0013UPDATE_SLOP_ENTRIES\u0010\u0014\u0012\u0016\n\u0012FAILED_F" + "ETCH_STORE\u0010\u0016\u0012\u0019\n\u0015GET_RO_STORAGE_FORMAT\u0010\u0017\u0012"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "DIR\u0010\u0011\u0012\u0019\n\u0015FETCH_PARTITION_FILES\u0010\u0012\u0012\u0017\n\u0013UPDA"
=======
    "\u001a\n\u0016REBALANCE_STATE_CHANGE\u0010\u0018\u0012\u000e\n\nREPAIR_JO"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "TE_SLOP_ENTRIES\u0010\u0014\u0012\u0016\n\u0012FAILED_FETCH_STORE\u0010"
=======
    "B\u0010\u0019\u0012\u0011\n\rNATIVE_BACKUP\u0010\u001aB-\n\u001cvoldemort.clie"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
     + 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "\u0016\u0012\u0019\n\u0015GET_RO_STORAGE_FORMAT\u0010\u0017\u0012\u001a\n\u0016REBALANC"
=======
    "nt.protocol.pbB\u000bVAdminProtoH\u0001"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
    , 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
    "E_STATE_CHANGE\u0010\u0018\u0012\u000e\n\nREPAIR_JOB\u0010\u0019\u0012$\n INIT" + "IATE_REBALANCE_NODE_ON_DONOR\u0010\u001a\u0012 \n\u001cDELETE" + "_STORE_REBALANCE_STATE\u0010\u001bB-\n\u001cvoldemort.cl" + "ient.protocol.pbB\u000bVAdminProtoH\u0001"
=======
>>>>>>> Unknown file: This is a bug in JDime.
     };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
      public com.google.protobuf.ExtensionRegistry assignDescriptors(com.google.protobuf.Descriptors.FileDescriptor root) {
        descriptor = root;
        internal_static_voldemort_GetMetadataRequest_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_voldemort_GetMetadataRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_GetMetadataRequest_descriptor, new java.lang.String[] { "Key" }, voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.class, voldemort.client.protocol.pb.VAdminProto.GetMetadataRequest.Builder.class);
        internal_static_voldemort_GetMetadataResponse_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_voldemort_GetMetadataResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_GetMetadataResponse_descriptor, new java.lang.String[] { "Version", "Error" }, voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse.class, voldemort.client.protocol.pb.VAdminProto.GetMetadataResponse.Builder.class);
        internal_static_voldemort_UpdateMetadataRequest_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_voldemort_UpdateMetadataRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_UpdateMetadataRequest_descriptor, new java.lang.String[] { "Key", "Versioned" }, voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.class, voldemort.client.protocol.pb.VAdminProto.UpdateMetadataRequest.Builder.class);
        internal_static_voldemort_UpdateMetadataResponse_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_voldemort_UpdateMetadataResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_UpdateMetadataResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse.class, voldemort.client.protocol.pb.VAdminProto.UpdateMetadataResponse.Builder.class);
        internal_static_voldemort_FileEntry_descriptor = getDescriptor().getMessageTypes().get(4);
        internal_static_voldemort_FileEntry_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_FileEntry_descriptor, new java.lang.String[] { "FileName", "FileSizeBytes" }, voldemort.client.protocol.pb.VAdminProto.FileEntry.class, voldemort.client.protocol.pb.VAdminProto.FileEntry.Builder.class);
        internal_static_voldemort_PartitionEntry_descriptor = getDescriptor().getMessageTypes().get(5);
        internal_static_voldemort_PartitionEntry_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_PartitionEntry_descriptor, new java.lang.String[] { "Key", "Versioned" }, voldemort.client.protocol.pb.VAdminProto.PartitionEntry.class, voldemort.client.protocol.pb.VAdminProto.PartitionEntry.Builder.class);
        internal_static_voldemort_UpdatePartitionEntriesRequest_descriptor = getDescriptor().getMessageTypes().get(6);
        internal_static_voldemort_UpdatePartitionEntriesRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_UpdatePartitionEntriesRequest_descriptor, new java.lang.String[] { "Store", "PartitionEntry", "Filter" }, voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.class, voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesRequest.Builder.class);
        internal_static_voldemort_UpdatePartitionEntriesResponse_descriptor = getDescriptor().getMessageTypes().get(7);
        internal_static_voldemort_UpdatePartitionEntriesResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_UpdatePartitionEntriesResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse.class, voldemort.client.protocol.pb.VAdminProto.UpdatePartitionEntriesResponse.Builder.class);
        internal_static_voldemort_VoldemortFilter_descriptor = getDescriptor().getMessageTypes().get(8);
        internal_static_voldemort_VoldemortFilter_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_VoldemortFilter_descriptor, new java.lang.String[] { "Name", "Data" }, voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.class, voldemort.client.protocol.pb.VAdminProto.VoldemortFilter.Builder.class);
        internal_static_voldemort_UpdateSlopEntriesRequest_descriptor = getDescriptor().getMessageTypes().get(9);
        internal_static_voldemort_UpdateSlopEntriesRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_UpdateSlopEntriesRequest_descriptor, new java.lang.String[] { "Store", "Key", "Version", "RequestType", "Value", "Transform" }, voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.class, voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesRequest.Builder.class);
        internal_static_voldemort_UpdateSlopEntriesResponse_descriptor = getDescriptor().getMessageTypes().get(10);
        internal_static_voldemort_UpdateSlopEntriesResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_UpdateSlopEntriesResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse.class, voldemort.client.protocol.pb.VAdminProto.UpdateSlopEntriesResponse.Builder.class);
        internal_static_voldemort_FetchPartitionFilesRequest_descriptor = getDescriptor().getMessageTypes().get(11);
        internal_static_voldemort_FetchPartitionFilesRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_FetchPartitionFilesRequest_descriptor, new java.lang.String[] { "Store", "ReplicaToPartition" }, voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.class, voldemort.client.protocol.pb.VAdminProto.FetchPartitionFilesRequest.Builder.class);
        internal_static_voldemort_FetchPartitionEntriesRequest_descriptor = getDescriptor().getMessageTypes().get(12);
        internal_static_voldemort_FetchPartitionEntriesRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_FetchPartitionEntriesRequest_descriptor, new java.lang.String[] { "ReplicaToPartition", "Store", "Filter", "FetchValues", "SkipRecords", "InitialCluster" }, voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.class, voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesRequest.Builder.class);
        internal_static_voldemort_FetchPartitionEntriesResponse_descriptor = getDescriptor().getMessageTypes().get(13);
        internal_static_voldemort_FetchPartitionEntriesResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_FetchPartitionEntriesResponse_descriptor, new java.lang.String[] { "PartitionEntry", "Key", "Error" }, voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse.class, voldemort.client.protocol.pb.VAdminProto.FetchPartitionEntriesResponse.Builder.class);
        internal_static_voldemort_DeletePartitionEntriesRequest_descriptor = getDescriptor().getMessageTypes().get(14);
        internal_static_voldemort_DeletePartitionEntriesRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_DeletePartitionEntriesRequest_descriptor, new java.lang.String[] { "Store", "ReplicaToPartition", "Filter", "InitialCluster" }, voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.class, voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesRequest.Builder.class);
        internal_static_voldemort_DeletePartitionEntriesResponse_descriptor = getDescriptor().getMessageTypes().get(15);
        internal_static_voldemort_DeletePartitionEntriesResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_DeletePartitionEntriesResponse_descriptor, new java.lang.String[] { "Count", "Error" }, voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse.class, voldemort.client.protocol.pb.VAdminProto.DeletePartitionEntriesResponse.Builder.class);
        internal_static_voldemort_InitiateFetchAndUpdateRequest_descriptor = getDescriptor().getMessageTypes().get(16);
        internal_static_voldemort_InitiateFetchAndUpdateRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_InitiateFetchAndUpdateRequest_descriptor, new java.lang.String[] { "NodeId", "Store", "Filter", "ReplicaToPartition", "InitialCluster", "Optimize" }, voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.class, voldemort.client.protocol.pb.VAdminProto.InitiateFetchAndUpdateRequest.Builder.class);
        internal_static_voldemort_AsyncOperationStatusRequest_descriptor = getDescriptor().getMessageTypes().get(17);
        internal_static_voldemort_AsyncOperationStatusRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_AsyncOperationStatusRequest_descriptor, new java.lang.String[] { "RequestId" }, voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.class, voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusRequest.Builder.class);
        internal_static_voldemort_AsyncOperationStopRequest_descriptor = getDescriptor().getMessageTypes().get(18);
        internal_static_voldemort_AsyncOperationStopRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_AsyncOperationStopRequest_descriptor, new java.lang.String[] { "RequestId" }, voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.class, voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopRequest.Builder.class);
        internal_static_voldemort_AsyncOperationStopResponse_descriptor = getDescriptor().getMessageTypes().get(19);
        internal_static_voldemort_AsyncOperationStopResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_AsyncOperationStopResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse.class, voldemort.client.protocol.pb.VAdminProto.AsyncOperationStopResponse.Builder.class);
        internal_static_voldemort_AsyncOperationListRequest_descriptor = getDescriptor().getMessageTypes().get(20);
        internal_static_voldemort_AsyncOperationListRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_AsyncOperationListRequest_descriptor, new java.lang.String[] { "ShowComplete" }, voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.class, voldemort.client.protocol.pb.VAdminProto.AsyncOperationListRequest.Builder.class);
        internal_static_voldemort_AsyncOperationListResponse_descriptor = getDescriptor().getMessageTypes().get(21);
        internal_static_voldemort_AsyncOperationListResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_AsyncOperationListResponse_descriptor, new java.lang.String[] { "RequestIds", "Error" }, voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse.class, voldemort.client.protocol.pb.VAdminProto.AsyncOperationListResponse.Builder.class);
        internal_static_voldemort_PartitionTuple_descriptor = getDescriptor().getMessageTypes().get(22);
        internal_static_voldemort_PartitionTuple_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_PartitionTuple_descriptor, new java.lang.String[] { "ReplicaType", "Partitions" }, voldemort.client.protocol.pb.VAdminProto.PartitionTuple.class, voldemort.client.protocol.pb.VAdminProto.PartitionTuple.Builder.class);
        internal_static_voldemort_PerStorePartitionTuple_descriptor = getDescriptor().getMessageTypes().get(23);
        internal_static_voldemort_PerStorePartitionTuple_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_PerStorePartitionTuple_descriptor, new java.lang.String[] { "StoreName", "ReplicaToPartition" }, voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.class, voldemort.client.protocol.pb.VAdminProto.PerStorePartitionTuple.Builder.class);
        internal_static_voldemort_RebalancePartitionInfoMap_descriptor = getDescriptor().getMessageTypes().get(24);
        internal_static_voldemort_RebalancePartitionInfoMap_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_RebalancePartitionInfoMap_descriptor, new java.lang.String[] { "StealerId", "DonorId", "Attempt", "ReplicaToAddPartition", "ReplicaToDeletePartition", "InitialCluster" }, voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.class, voldemort.client.protocol.pb.VAdminProto.RebalancePartitionInfoMap.Builder.class);
        internal_static_voldemort_InitiateRebalanceNodeRequest_descriptor = getDescriptor().getMessageTypes().get(25);
        internal_static_voldemort_InitiateRebalanceNodeRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_InitiateRebalanceNodeRequest_descriptor, new java.lang.String[] { "RebalancePartitionInfo" }, voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.class, voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeRequest.Builder.class);
        internal_static_voldemort_InitiateRebalanceNodeOnDonorRequest_descriptor = getDescriptor().getMessageTypes().get(26);
        internal_static_voldemort_InitiateRebalanceNodeOnDonorRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_InitiateRebalanceNodeOnDonorRequest_descriptor, new java.lang.String[] { "RebalancePartitionInfo" }, voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.class, voldemort.client.protocol.pb.VAdminProto.InitiateRebalanceNodeOnDonorRequest.Builder.class);
        internal_static_voldemort_AsyncOperationStatusResponse_descriptor = getDescriptor().getMessageTypes().get(27);
        internal_static_voldemort_AsyncOperationStatusResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_AsyncOperationStatusResponse_descriptor, new java.lang.String[] { "RequestId", "Description", "Status", "Complete", "Error" }, voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse.class, voldemort.client.protocol.pb.VAdminProto.AsyncOperationStatusResponse.Builder.class);
        internal_static_voldemort_TruncateEntriesRequest_descriptor = getDescriptor().getMessageTypes().get(28);
        internal_static_voldemort_TruncateEntriesRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_TruncateEntriesRequest_descriptor, new java.lang.String[] { "Store" }, voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.class, voldemort.client.protocol.pb.VAdminProto.TruncateEntriesRequest.Builder.class);
        internal_static_voldemort_TruncateEntriesResponse_descriptor = getDescriptor().getMessageTypes().get(29);
        internal_static_voldemort_TruncateEntriesResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_TruncateEntriesResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse.class, voldemort.client.protocol.pb.VAdminProto.TruncateEntriesResponse.Builder.class);
        internal_static_voldemort_AddStoreRequest_descriptor = getDescriptor().getMessageTypes().get(30);
        internal_static_voldemort_AddStoreRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_AddStoreRequest_descriptor, new java.lang.String[] { "StoreDefinition" }, voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.class, voldemort.client.protocol.pb.VAdminProto.AddStoreRequest.Builder.class);
        internal_static_voldemort_AddStoreResponse_descriptor = getDescriptor().getMessageTypes().get(31);
        internal_static_voldemort_AddStoreResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_AddStoreResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.AddStoreResponse.class, voldemort.client.protocol.pb.VAdminProto.AddStoreResponse.Builder.class);
        internal_static_voldemort_DeleteStoreRequest_descriptor = getDescriptor().getMessageTypes().get(32);
        internal_static_voldemort_DeleteStoreRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_DeleteStoreRequest_descriptor, new java.lang.String[] { "StoreName" }, voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.class, voldemort.client.protocol.pb.VAdminProto.DeleteStoreRequest.Builder.class);
        internal_static_voldemort_DeleteStoreResponse_descriptor = getDescriptor().getMessageTypes().get(33);
        internal_static_voldemort_DeleteStoreResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_DeleteStoreResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse.class, voldemort.client.protocol.pb.VAdminProto.DeleteStoreResponse.Builder.class);
        internal_static_voldemort_FetchStoreRequest_descriptor = getDescriptor().getMessageTypes().get(34);
        internal_static_voldemort_FetchStoreRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_FetchStoreRequest_descriptor, new java.lang.String[] { "StoreName", "StoreDir", "PushVersion" }, voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.class, voldemort.client.protocol.pb.VAdminProto.FetchStoreRequest.Builder.class);
        internal_static_voldemort_SwapStoreRequest_descriptor = getDescriptor().getMessageTypes().get(35);
        internal_static_voldemort_SwapStoreRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_SwapStoreRequest_descriptor, new java.lang.String[] { "StoreName", "StoreDir" }, voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.class, voldemort.client.protocol.pb.VAdminProto.SwapStoreRequest.Builder.class);
        internal_static_voldemort_SwapStoreResponse_descriptor = getDescriptor().getMessageTypes().get(36);
        internal_static_voldemort_SwapStoreResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_SwapStoreResponse_descriptor, new java.lang.String[] { "Error", "PreviousStoreDir" }, voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse.class, voldemort.client.protocol.pb.VAdminProto.SwapStoreResponse.Builder.class);
        internal_static_voldemort_RollbackStoreRequest_descriptor = getDescriptor().getMessageTypes().get(37);
        internal_static_voldemort_RollbackStoreRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_RollbackStoreRequest_descriptor, new java.lang.String[] { "StoreName", "PushVersion" }, voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.class, voldemort.client.protocol.pb.VAdminProto.RollbackStoreRequest.Builder.class);
        internal_static_voldemort_RollbackStoreResponse_descriptor = getDescriptor().getMessageTypes().get(38);
        internal_static_voldemort_RollbackStoreResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_RollbackStoreResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse.class, voldemort.client.protocol.pb.VAdminProto.RollbackStoreResponse.Builder.class);
        internal_static_voldemort_RepairJobRequest_descriptor = getDescriptor().getMessageTypes().get(39);
        internal_static_voldemort_RepairJobRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_RepairJobRequest_descriptor, new java.lang.String[] { "StoreName" }, voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.class, voldemort.client.protocol.pb.VAdminProto.RepairJobRequest.Builder.class);
        internal_static_voldemort_RepairJobResponse_descriptor = getDescriptor().getMessageTypes().get(40);
        internal_static_voldemort_RepairJobResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_RepairJobResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.RepairJobResponse.class, voldemort.client.protocol.pb.VAdminProto.RepairJobResponse.Builder.class);
        internal_static_voldemort_ROStoreVersionDirMap_descriptor = getDescriptor().getMessageTypes().get(41);
        internal_static_voldemort_ROStoreVersionDirMap_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_ROStoreVersionDirMap_descriptor, new java.lang.String[] { "StoreName", "StoreDir" }, voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.class, voldemort.client.protocol.pb.VAdminProto.ROStoreVersionDirMap.Builder.class);
        internal_static_voldemort_GetROMaxVersionDirRequest_descriptor = getDescriptor().getMessageTypes().get(42);
        internal_static_voldemort_GetROMaxVersionDirRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_GetROMaxVersionDirRequest_descriptor, new java.lang.String[] { "StoreName" }, voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.class, voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirRequest.Builder.class);
        internal_static_voldemort_GetROMaxVersionDirResponse_descriptor = getDescriptor().getMessageTypes().get(43);
        internal_static_voldemort_GetROMaxVersionDirResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_GetROMaxVersionDirResponse_descriptor, new java.lang.String[] { "RoStoreVersions", "Error" }, voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse.class, voldemort.client.protocol.pb.VAdminProto.GetROMaxVersionDirResponse.Builder.class);
        internal_static_voldemort_GetROCurrentVersionDirRequest_descriptor = getDescriptor().getMessageTypes().get(44);
        internal_static_voldemort_GetROCurrentVersionDirRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_GetROCurrentVersionDirRequest_descriptor, new java.lang.String[] { "StoreName" }, voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.class, voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirRequest.Builder.class);
        internal_static_voldemort_GetROCurrentVersionDirResponse_descriptor = getDescriptor().getMessageTypes().get(45);
        internal_static_voldemort_GetROCurrentVersionDirResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_GetROCurrentVersionDirResponse_descriptor, new java.lang.String[] { "RoStoreVersions", "Error" }, voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse.class, voldemort.client.protocol.pb.VAdminProto.GetROCurrentVersionDirResponse.Builder.class);
        internal_static_voldemort_GetROStorageFormatRequest_descriptor = getDescriptor().getMessageTypes().get(46);
        internal_static_voldemort_GetROStorageFormatRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_GetROStorageFormatRequest_descriptor, new java.lang.String[] { "StoreName" }, voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.class, voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatRequest.Builder.class);
        internal_static_voldemort_GetROStorageFormatResponse_descriptor = getDescriptor().getMessageTypes().get(47);
        internal_static_voldemort_GetROStorageFormatResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_GetROStorageFormatResponse_descriptor, new java.lang.String[] { "RoStoreVersions", "Error" }, voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse.class, voldemort.client.protocol.pb.VAdminProto.GetROStorageFormatResponse.Builder.class);
        internal_static_voldemort_FailedFetchStoreRequest_descriptor = getDescriptor().getMessageTypes().get(48);
        internal_static_voldemort_FailedFetchStoreRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_FailedFetchStoreRequest_descriptor, new java.lang.String[] { "StoreName", "StoreDir" }, voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.class, voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreRequest.Builder.class);
        internal_static_voldemort_FailedFetchStoreResponse_descriptor = getDescriptor().getMessageTypes().get(49);
        internal_static_voldemort_FailedFetchStoreResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_FailedFetchStoreResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse.class, voldemort.client.protocol.pb.VAdminProto.FailedFetchStoreResponse.Builder.class);
        internal_static_voldemort_RebalanceStateChangeRequest_descriptor = getDescriptor().getMessageTypes().get(50);
        internal_static_voldemort_RebalanceStateChangeRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_RebalanceStateChangeRequest_descriptor, new java.lang.String[] { "RebalancePartitionInfoList", "ClusterString", "SwapRo", "ChangeClusterMetadata", "ChangeRebalanceState", "Rollback" }, voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.class, voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeRequest.Builder.class);
        internal_static_voldemort_RebalanceStateChangeResponse_descriptor = getDescriptor().getMessageTypes().get(51);
        internal_static_voldemort_RebalanceStateChangeResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_RebalanceStateChangeResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse.class, voldemort.client.protocol.pb.VAdminProto.RebalanceStateChangeResponse.Builder.class);

<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        internal_static_voldemort_DeleteStoreRebalanceStateRequest_descriptor
=======
        internal_static_voldemort_NativeBackupRequest_descriptor
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
         = getDescriptor().getMessageTypes().get(
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        52
=======
        51
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        );

<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        internal_static_voldemort_DeleteStoreRebalanceStateRequest_fieldAccessorTable
=======
        internal_static_voldemort_NativeBackupRequest_fieldAccessorTable
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
         = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        internal_static_voldemort_DeleteStoreRebalanceStateRequest_descriptor
=======
        internal_static_voldemort_NativeBackupRequest_descriptor
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        , new java.lang.String[] { "StoreName", 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        "NodeId"
=======
        "BackupDir"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
         }, voldemort.client.protocol.pb.VAdminProto.
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        DeleteStoreRebalanceStateRequest
=======
        NativeBackupRequest
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        .class, voldemort.client.protocol.pb.VAdminProto.
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        DeleteStoreRebalanceStateRequest
=======
        NativeBackupRequest
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        .Builder.class);
        internal_static_voldemort_DeleteStoreRebalanceStateResponse_descriptor = getDescriptor().getMessageTypes().get(53);
        internal_static_voldemort_DeleteStoreRebalanceStateResponse_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_DeleteStoreRebalanceStateResponse_descriptor, new java.lang.String[] { "Error" }, voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse.class, voldemort.client.protocol.pb.VAdminProto.DeleteStoreRebalanceStateResponse.Builder.class);
        internal_static_voldemort_VoldemortAdminRequest_descriptor = getDescriptor().getMessageTypes().get(
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        54
=======
        52
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        );
        internal_static_voldemort_VoldemortAdminRequest_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_voldemort_VoldemortAdminRequest_descriptor, new java.lang.String[] { "Type", "GetMetadata", "UpdateMetadata", "UpdatePartitionEntries", "FetchPartitionEntries", "DeletePartitionEntries", "InitiateFetchAndUpdate", "AsyncOperationStatus", "InitiateRebalanceNode", "AsyncOperationStop", "AsyncOperationList", "TruncateEntries", "AddStore", "DeleteStore", "FetchStore", "SwapStore", "RollbackStore", "GetRoMaxVersionDir", "GetRoCurrentVersionDir", "FetchPartitionFiles", "UpdateSlopEntries", "FailedFetchStore", "GetRoStorageFormat", "RebalanceStateChange", "RepairJob", 
<<<<<<< commits-mn5_100/voldemort/voldemort/aee112d9ef0ed960c7bc9955d7e85e6ed6ac91a0/VAdminProto-145b6f8.java
        "InitiateRebalanceNodeOnDonor"
=======
        "NativeBackup"
>>>>>>> commits-mn5_100/voldemort/voldemort/fd5dbeb5113ffed51cf1836ac78b129a4bea4cb6/VAdminProto-d702c2c.java
        , "DeleteStoreRebalanceState" }, voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest.class, voldemort.client.protocol.pb.VAdminProto.VoldemortAdminRequest.Builder.class);
        return null;
      }
    };
    com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new com.google.protobuf.Descriptors.FileDescriptor[] { voldemort.client.protocol.pb.VProto.getDescriptor() }, assigner);
  }

  public static void internalForceInit() {
  }
}
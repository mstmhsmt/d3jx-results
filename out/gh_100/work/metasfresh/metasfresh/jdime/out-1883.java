package de.metas.cache.model;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Trace;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import javax.annotation.Nullable;

@Value @JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE) public class CacheInvalidateRequest {
  public static Builder builder() {
    return new Builder();
  }

  public static CacheInvalidateRequest all() {
    return ALL;
  }

  public static CacheInvalidateRequest allRecordsForTable(@NonNull final String rootTableName) {
    final int rootRecordId = RECORD_ID_ALL;
    final String childTableName = null;
    final int childRecordId = RECORD_ID_ALL;
    final String debugFrom = DEBUG ? Trace.toOneLineStackTraceString() : null;
    return new CacheInvalidateRequest(rootTableName, rootRecordId, childTableName, childRecordId, debugFrom);
  }

  public static CacheInvalidateRequest rootRecord(@NonNull final String rootTableName, final int rootRecordId) {
    Check.assume(rootRecordId >= 0, "rootRecordId >= 0");
    final String childTableName = null;
    final int childRecordId = RECORD_ID_ALL;
    final String debugFrom = DEBUG ? Trace.toOneLineStackTraceString() : null;
    return new CacheInvalidateRequest(rootTableName, rootRecordId, childTableName, childRecordId, debugFrom);
  }

  public static CacheInvalidateRequest rootRecord(@NonNull final String rootTableName, @NonNull final RepoIdAware rootId) {
    return rootRecord(rootTableName, rootId.getRepoId());
  }

  public static CacheInvalidateRequest allChildRecords(@NonNull final String rootTableName, @NonNull final RepoIdAware rootId, @NonNull final String childTableName) {
    return allChildRecords(rootTableName, rootId.getRepoId(), childTableName);
  }

  public static CacheInvalidateRequest allChildRecords(@NonNull final String rootTableName, final int rootRecordId, @NonNull final String childTableName) {
    Check.assume(rootRecordId >= 0, "rootRecordId >= 0");
    final int childRecordId = RECORD_ID_ALL;
    final String debugFrom = DEBUG ? Trace.toOneLineStackTraceString() : null;
    return new CacheInvalidateRequest(rootTableName, rootRecordId, childTableName, childRecordId, debugFrom);
  }

  public static CacheInvalidateRequest fromTableNameAndRecordId(final String tableName, final int recordId) {
    if (tableName == null) {
      return all();
    } else {
      if (recordId < 0) {
        return allRecordsForTable(tableName);
      } else {
        return rootRecord(tableName, recordId);
      }
    }
  }

  private static final boolean DEBUG = false;

  private static final int RECORD_ID_ALL = -1;

  private static final CacheInvalidateRequest ALL = new CacheInvalidateRequest(null, RECORD_ID_ALL, null, RECORD_ID_ALL, "ALL");

  @JsonProperty(value = "rootTableName") private final String rootTableName;

  @JsonProperty(value = "rootRecordId") private final int rootRecordId;

  @JsonProperty(value = "childTableName") private final String childTableName;

  @JsonProperty(value = "childRecordId") private final int childRecordId;

  @JsonProperty(value = "debugFrom") final String debugFrom;

  @JsonCreator private CacheInvalidateRequest(@JsonProperty(value = "rootTableName") final String rootTableName, @JsonProperty(value = "rootRecordId") final int rootRecordId, @JsonProperty(value = "childTableName") final String childTableName, @JsonProperty(value = "childRecordId") final int childRecordId, @JsonProperty(value = "debugFrom") final String debugFrom) {
    this.rootTableName = rootTableName;
    this.rootRecordId = rootRecordId >= 0 ? rootRecordId : RECORD_ID_ALL;
    this.childTableName = childTableName;
    this.childRecordId = childRecordId >= 0 ? childRecordId : RECORD_ID_ALL;
    this.debugFrom = debugFrom;
  }

  public boolean isAll() {
    return this == ALL;
  }

  public boolean isAllRecords() {
    if (!Check.isEmpty(childTableName)) {
      return childRecordId == RECORD_ID_ALL;
    } else {
      if (!Check.isEmpty(rootTableName)) {
        return rootRecordId == RECORD_ID_ALL;
      } else {
        return false;
      }
    }
  }

  @Nullable public TableRecordReference getRootRecordOrNull() {
    if (rootTableName != null && rootRecordId >= 0) {
      return TableRecordReference.of(rootTableName, rootRecordId);
    } else {
      return null;
    }
  }

  @Nullable public TableRecordReference getChildRecordOrNull() {
    if (childTableName != null && childRecordId >= 0) {
      return TableRecordReference.of(childTableName, childRecordId);
    } else {
      return null;
    }
  }

  public TableRecordReference getRecordEffective() {
    if (childTableName != null && childRecordId >= 0) {
      return TableRecordReference.of(childTableName, childRecordId);
    } else {
      if (rootTableName != null && rootRecordId >= 0) {
        return TableRecordReference.of(rootTableName, rootRecordId);
      } else {
        throw new AdempiereException("Cannot extract effective record from " + this);
      }
    }
  }

  public String getTableNameEffective() {
    return childTableName != null ? childTableName : rootTableName;
  }

  public int getRecordIdEffective() {
    return childTableName != null ? childRecordId : rootRecordId;
  }

  public static final class Builder {
    private String rootTableName;

    private int rootRecordId = -1;

    private String childTableName;

    private int childRecordId = -1;

    private Builder() {
    }

    public CacheInvalidateRequest build() {
      final String debugFrom = DEBUG ? Trace.toOneLineStackTraceString() : null;
      return new CacheInvalidateRequest(rootTableName, rootRecordId, childTableName, childRecordId, debugFrom);
    }

    public Builder rootRecord(@NonNull final String tableName, final int recordId) {
      Check.assume(recordId >= 0, "recordId >= 0");
      rootTableName = tableName;
      rootRecordId = recordId;
      return this;
    }

    public Builder childRecord(@NonNull final String tableName, final int recordId) {
      Check.assume(recordId >= 0, "recordId >= 0");
      childTableName = tableName;
      childRecordId = recordId;
      return this;
    }
  }
}
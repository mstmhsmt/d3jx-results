package org.apache.cassandra.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.cassandra.config.DatabaseDescriptor;

public interface StorageProxyMBean {

    public long getTotalHints();

    public boolean getHintedHandoffEnabled();

    public void setHintedHandoffEnabled(boolean b);

    public void enableHintsForDC(String dc);

    public void disableHintsForDC(String dc);

    public Set<String> getHintedHandoffDisabledDCs();

    public int getMaxHintWindow();

    public void setMaxHintWindow(int ms);

    public int getMaxHintsInProgress();

    public void setMaxHintsInProgress(int qs);

    public int getHintsInProgress();

    public Long getRpcTimeout();

    public void setRpcTimeout(Long timeoutInMillis);

    public Long getReadRpcTimeout();

    public void setReadRpcTimeout(Long timeoutInMillis);

    public Long getWriteRpcTimeout();

    public void setWriteRpcTimeout(Long timeoutInMillis);

    public Long getCounterWriteRpcTimeout();

    public void setCounterWriteRpcTimeout(Long timeoutInMillis);

    public Long getCasContentionTimeout();

    public void setCasContentionTimeout(Long timeoutInMillis);

    public Long getRangeRpcTimeout();

    public void setRangeRpcTimeout(Long timeoutInMillis);

    public Long getTruncateRpcTimeout();

    public void setTruncateRpcTimeout(Long timeoutInMillis);

    public void setNativeTransportMaxConcurrentConnections(Long nativeTransportMaxConcurrentConnections);

    public Long getNativeTransportMaxConcurrentConnections();

    public void reloadTriggerClasses();

    public long getReadRepairAttempted();

    public long getReadRepairRepairedBlocking();

    public long getReadRepairRepairedBackground();

    @Deprecated
    public int getOtcBacklogExpirationInterval();

    @Deprecated
    public void setOtcBacklogExpirationInterval(int intervalInMillis);

    @Deprecated
    public Map<String, List<String>> getSchemaVersions();

    public int getNumberOfTables();

    boolean getRepairedDataTrackingEnabledForPartitionReads();

    boolean getRepairedDataTrackingEnabledForRangeReads();

    public String getIdealConsistencyLevel();

    void disableCheckForDuplicateRowsDuringCompaction();

    void enableCheckForDuplicateRowsDuringCompaction();

    void disableCheckForDuplicateRowsDuringReads();

    public String setIdealConsistencyLevel(String cl);

    void enableCheckForDuplicateRowsDuringReads();

    void disableSnapshotOnDuplicateRowDetection();

    void enableSnapshotOnDuplicateRowDetection();

    void disableSnapshotOnRepairedDataMismatch();

    void enableSnapshotOnRepairedDataMismatch();

    void disableReportingUnconfirmedRepairedDataMismatches();

    void enableReportingUnconfirmedRepairedDataMismatches();

    void disableRepairedDataTrackingForPartitionReads();

    void enableRepairedDataTrackingForPartitionReads();

    void disableRepairedDataTrackingForRangeReads();

    void enableRepairedDataTrackingForRangeReads();

    boolean getCheckForDuplicateRowsDuringCompaction();

    boolean getCheckForDuplicateRowsDuringReads();

    public Map<String, List<String>> getSchemaVersionsWithPort();

    boolean getSnapshotOnDuplicateRowDetectionEnabled();

    boolean getSnapshotOnRepairedDataMismatchEnabled();

    boolean getReportingUnconfirmedRepairedDataMismatchesEnabled();
}

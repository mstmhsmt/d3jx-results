package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.user.UserId;
import java.time.ZonedDateTime;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
final public class PickingJob {

    @Getter
    @NonNull
    private final PickingJobId id;

    @NonNull
    private final PickingJobHeader header;

    @Getter
    @NonNull
    private final Optional<PickingSlotIdAndCaption> pickingSlot;

    @Getter
    @NonNull
    private final ImmutableList<PickingJobLine> lines;

    @Getter
    @NonNull
    private final ImmutableSet<PickingJobPickFromAlternative> pickFromAlternatives;

    @Getter
    private final PickingJobDocStatus docStatus;

    @Getter
    private final PickingJobProgress progress;

    @Builder(toBuilder = true)
    private PickingJob(@NonNull final PickingJobId id, @NonNull final PickingJobHeader header, @Nullable final Optional<PickingSlotIdAndCaption> pickingSlot, @NonNull final ImmutableList<PickingJobLine> lines, @NonNull final ImmutableSet<PickingJobPickFromAlternative> pickFromAlternatives, @NonNull final PickingJobDocStatus docStatus, final boolean isReadyToReview, final boolean isApproved) {
        Check.assumeNotEmpty(lines, "lines not empty");
        this.id = id;
        this.header = header;
        this.pickingSlot = pickingSlot != null ? pickingSlot : Optional.empty();
        this.lines = lines;
        this.pickFromAlternatives = pickFromAlternatives;
        this.docStatus = docStatus;
        this.isReadyToReview = isReadyToReview;
        this.isApproved = isApproved;
        this.progress = computeProgress(lines);
    }

    private PickingJobProgress computeProgress(@NonNull final ImmutableList<PickingJobLine> lines) {
        final ImmutableSet<PickingJobProgress> lineProgresses = lines.stream().map(PickingJobLine::getProgress).collect(ImmutableSet.toImmutableSet());
        return PickingJobProgress.reduce(lineProgresses);
    }

    public void assertNotProcessed() {
        if (docStatus.isProcessed()) {
            throw new AdempiereException("Picking Job was already processed");
        }
    }

    public Optional<PickingSlotId> getPickingSlotId() {
        return pickingSlot.map(PickingSlotIdAndCaption::getPickingSlotId);
    }

    public PickingJob withPickingSlot(@Nullable final PickingSlotIdAndCaption pickingSlot) {
        return PickingSlotIdAndCaption.equals(this.pickingSlot.orElse(null), pickingSlot) ? this : toBuilder().pickingSlot(Optional.ofNullable(pickingSlot)).build();
    }

    public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds() {
        return streamShipmentScheduleIds().collect(ImmutableSet.toImmutableSet());
    }

    public Stream<ShipmentScheduleId> streamShipmentScheduleIds() {
        return lines.stream().flatMap(PickingJobLine::streamShipmentScheduleId);
    }

    public Stream<PickingJobStep> streamSteps() {
        return lines.stream().flatMap(PickingJobLine::streamSteps);
    }

    public PickingJobStep getStepById(final PickingJobStepId stepId) {
        return lines.stream().flatMap(PickingJobLine::streamSteps).filter(step -> PickingJobStepId.equals(step.getId(), stepId)).findFirst().orElseThrow(() -> new AdempiereException("No step found for " + stepId));
    }

    public PickingJob withDocStatus(final PickingJobDocStatus docStatus) {
        return !Objects.equals(this.docStatus, docStatus) ? toBuilder().docStatus(docStatus).build() : this;
    }

    public PickingJob withChangedLines(final UnaryOperator<PickingJobLine> lineMapper) {
        final ImmutableList<PickingJobLine> changedLines = CollectionUtils.map(lines, lineMapper);
        return changedLines.equals(lines) ? this : toBuilder().lines(changedLines).build();
    }

    public PickingJob withChangedStep(@NonNull final PickingJobStepId stepId, @NonNull final UnaryOperator<PickingJobStep> stepMapper) {
        return withChangedLines(line -> line.withChangedStep(stepId, stepMapper));
    }

    public PickingJob withChangedSteps(@NonNull final Set<PickingJobStepId> stepIds, @NonNull final UnaryOperator<PickingJobStep> stepMapper) {
        if (stepIds.isEmpty()) {
            return this;
        }
        return withChangedLines(line -> line.withChangedSteps(stepIds, stepMapper));
    }

    public PickingJob withIsReadyToReview() {
        return isReadyToReview ? this : toBuilder().isReadyToReview(true).build();
    }

    public UserId getLockedBy() {
        return header.getLockedBy();
    }

    public String getSalesOrderDocumentNo() {
        return header.getSalesOrderDocumentNo();
    }

    @Getter
    private final boolean isApproved;

    public String getCustomerName() {
        return header.getCustomerName();
    }

    @Getter
    private final boolean isReadyToReview;

    public String getDeliveryRenderedAddress() {
        return header.getDeliveryRenderedAddress();
    }

    public BPartnerLocationId getDeliveryBPLocationId() {
        return header.getDeliveryBPLocationId();
    }

    public ZonedDateTime getPreparationDate() {
        return header.getPreparationDate();
    }

    public PickingJob withLockedBy(@Nullable final UserId lockedBy) {
        return UserId.equals(header.getLockedBy(), lockedBy) ? this : toBuilder().header(header.toBuilder().lockedBy(lockedBy).build()).build();
    }

    public PickingJob withApproved() {
        return toBuilder().isReadyToReview(false).isApproved(true).build();
    }
}

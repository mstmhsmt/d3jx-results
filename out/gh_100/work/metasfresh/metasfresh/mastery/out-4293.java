package de.metas.bpartner.composite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.OrgMappingId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.LocationId;
import de.metas.util.lang.ExternalId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.table.RecordChangeLog;
import javax.annotation.Nullable;
import java.util.HashSet;
import static de.metas.util.Check.isBlank;

@Data
@JsonPropertyOrder(alphabetic = true)
public class BPartnerLocation {

    public static final String ID = "id";

    public static final String EXTERNAL_ID = "externalId";

    public static final String GLN = "gln";

    public static final String NAME = "name";

    public static final String BPARTNERNAME = " bpartnerName";

    public static final String ACTIVE = "active";

    public static final String ADDRESS_1 = "address1";

    public static final String ADDRESS_2 = "address2";

    public static final String ADDRESS_3 = "address3";

    public static final String ADDRESS_4 = "address4";

    public static final String PO_BOX = "poBox";

    public static final String POSTAL = "postal";

    public static final String CITY = "city";

    public static final String DISTRICT = "district";

    public static final String REGION = "region";

    public static final String COUNTRYCODE = "countryCode";

    @Nullable
    private BPartnerLocationId id;

    @Nullable
    private ExternalId externalId;

    @Nullable
    private GLN gln;

    @Nullable
    private String name;

    @Nullable
    private String bpartnerName;

    private boolean active;

    @Nullable
    private LocationId existingLocationId;

    @Nullable
    private String address1;

    @Nullable
    private String address2;

    @Nullable
    private String address3;

    @Nullable
    private String address4;

    @Nullable
    private String poBox;

    @Nullable
    private String postal;

    @Nullable
    private String city;

    @Nullable
    private String region;

    @Nullable
    private String district;

    @Nullable
    private String countryCode;

    @Nullable
    private BPartnerLocationType locationType;

    @Nullable
    final private RecordChangeLog changeLog;

    @Nullable
    private OrgMappingId orgMappingId;

    @Getter(AccessLevel.NONE)
    final private HashSet<String> handles = new HashSet<>();

    @JsonCreator
    @Builder(toBuilder = true)
    private BPartnerLocation(@Nullable final BPartnerLocationId id, @Nullable final ExternalId externalId, @Nullable final GLN gln, @Nullable final Boolean active, @Nullable final String name, @Nullable final String bpartnerName, @Nullable final LocationId existingLocationId, @Nullable final String address1, @Nullable final String address2, @Nullable final String address3, @Nullable final String address4, @Nullable final String postal, @Nullable final String poBox, @Nullable final String district, @Nullable final String region, @Nullable final String city, @Nullable final String countryCode, @Nullable final String phone, @Nullable final String email, @Nullable final BPartnerLocationType locationType, @Nullable final RecordChangeLog changeLog, @Nullable final OrgMappingId orgMappingId, @Nullable final String phone, @Nullable final String mobile, @Nullable final String fax, @Nullable final String email, @Nullable final String setupPlaceNo, @Nullable final Boolean remitTo, @Nullable final Boolean handOverLocation, @Nullable final Boolean replicationLookupDefault, @Nullable final Boolean visitorsAddress) {
        this.id = id;
        this.gln = gln;
        this.externalId = externalId;
        this.active = active != null ? active : true;
        this.name = name;
        this.bpartnerName = bpartnerName;
        this.existingLocationId = existingLocationId;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.address4 = address4;
        this.postal = postal;
        this.poBox = poBox;
        this.district = district;
        this.region = region;
        this.city = city;
        this.countryCode = countryCode;
        this.phone = phone;
        this.email = email;
        this.locationType = locationType;
        this.changeLog = changeLog;
        this.orgMappingId = orgMappingId;
        this.phone = phone;
        this.mobile = mobile;
        this.fax = fax;
        this.email = email;
        this.setupPlaceNo = setupPlaceNo;
        this.handOverLocation = handOverLocation != null ? handOverLocation : false;
        this.replicationLookupDefault = replicationLookupDefault != null ? replicationLookupDefault : false;
        this.remitTo = remitTo != null ? remitTo : false;
        this.visitorsAddress = visitorsAddress != null ? visitorsAddress : false;
    }

    public BPartnerLocation deepCopy() {
        final BPartnerLocationBuilder builder = toBuilder();
        if (locationType != null) {
            builder.locationType(locationType.deepCopy());
        }
        return builder.build();
    }

    public ImmutableList<ITranslatableString> validate() {
        if (!isActive()) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();
        if (!isAddressSpecifiedByExistingLocationIdOnly()) {
            if (isBlank(countryCode)) {
                result.add(TranslatableStrings.constant("Missing location.countryCode"));
            }
            if (!isBlank(district) && isBlank(postal)) {
                result.add(TranslatableStrings.constant("Missing location.postal (required if location.district is set)"));
            }
        }
        return result.build();
    }

    @JsonIgnore
    public boolean isAddressSpecifiedByExistingLocationIdOnly() {
        return toAddress().isOnlyExistingLocationIdSet();
    }

    public void addHandle(@NonNull final String handle) {
        handles.add(handle);
    }

    public boolean containsHandle(@NonNull final String handle) {
        return handles.contains(handle);
    }

    public BPartnerLocationAddressPart toAddress() {
        return BPartnerLocationAddressPart.builder().existingLocationId(getExistingLocationId()).address1(getAddress1()).address2(getAddress2()).address3(getAddress3()).address4(getAddress4()).poBox(getPoBox()).postal(getPostal()).city(getCity()).region(getRegion()).district(getDistrict()).countryCode(getCountryCode()).build();
    }

    public void setFromAddress(@NonNull final BPartnerLocationAddressPart address) {
        setExistingLocationId(address.getExistingLocationId());
        setAddress1(address.getAddress1());
        setAddress2(address.getAddress2());
        setAddress3(address.getAddress3());
        setAddress4(address.getAddress4());
        setCity(address.getCity());
        setCountryCode(address.getCountryCode());
        setPoBox(address.getPoBox());
        setPostal(address.getPostal());
        setRegion(address.getRegion());
        setDistrict(address.getDistrict());
    }

    public static final String PHONE = "phone";

    final boolean visitorsAddress;

    final boolean replicationLookupDefault;

    final boolean handOverLocation;

    @Nullable
    final String setupPlaceNo;

    final boolean remitTo;

    @Nullable
    private String email;

    @Nullable
    private String fax;

    @Nullable
    private String mobile;

    @Nullable
    private String phone;

    public static final String EMAIL = "email";
}

package de.metas.document.sequence.impl;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MSequence;
import org.compiere.util.Env;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import de.metas.document.DocTypeSequenceList;
import de.metas.document.sequence.ICountryIdProvider;
import de.metas.location.CountryId;
import java.util.List;

final class PreliminaryDocumentNoBuilder implements IPreliminaryDocumentNoBuilder {
  private final transient IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);

  private static final String DOCSUBTYPE_NONE = "--";

  private I_C_DocType _newDocType;

  private int _oldDocType_ID = -1;

  private I_C_DocType _oldDocType;

  private String _oldDocumentNo = null;

  private Object _documentModel;

  private ClientId _adClientId;

  private OrgId _adOrgId;

  private final AtomicBoolean _built = new AtomicBoolean(false);

  private DocSequenceId _oldSequence_ID = null;

  PreliminaryDocumentNoBuilder(final List<ICountryIdProvider> countryIdProviders) {
    super();
    this.countryIdProviders = countryIdProviders;
  }

  @Override public IDocumentNoInfo buildOrNull() {
    if (_built.getAndSet(true)) {
      throw new IllegalStateException("Already built");
    }
    try {
      return buildOrNull0();
    } catch (final Exception ex) {
      throw DocumentNoBuilderException.wrapIfNeeded(ex);
    }
  }

  private IDocumentNoInfo buildOrNull0() {
    final I_C_DocType newDocType = getNewDocType();
    if (newDocType == null) {
      return null;
    }
    final String docBaseType = newDocType.getDocBaseType();
    final String docSubType = CoalesceUtil.coalesce(newDocType.getDocSubType(), DOCSUBTYPE_NONE);
    final boolean isSOTrx = newDocType.isSOTrx();
    final boolean hasChanges = newDocType.isHasCharges();
    final String newDocumentNo;
    final boolean isDocNoControlled = newDocType.isDocNoControlled();
    if (isDocNoControlled) {
      final DocTypeSequenceList newDocTypeSequenceList = documentSequenceDAO.retrieveDocTypeSequenceList(newDocType);
      final DocSequenceId newDocSequenceId = newDocTypeSequenceList.getDocNoSequenceId(getClientId(), getOrgId(), getCountryId());
      final boolean isNewDocumentNo = isNewDocumentNo() || !DocSequenceId.equals(newDocSequenceId, getOldSequenceId());
      if (isNewDocumentNo) {
        newDocumentNo = retrieveCurrentDocumentNo(newDocSequenceId);
      } else {
        newDocumentNo = getOldDocumentNo();
      }
    } else {
      newDocumentNo = null;
    }
    return DocumentNoInfo.builder().setDocumentNo(newDocumentNo).setDocBaseType(docBaseType).setDocSubType(docSubType).setIsSOTrx(isSOTrx).setHasChanges(hasChanges).setDocNoControlled(isDocNoControlled).build();
  }

  private String retrieveCurrentDocumentNo(final DocSequenceId docSequenceId) {
    final ClientId adClientId = getClientId();
    if (MSequence.isAdempiereSys(adClientId.getRepoId())) {
      final String documentNo = documentSequenceDAO.retrieveDocumentNoSys(docSequenceId.getRepoId());
      return IPreliminaryDocumentNoBuilder.withPreliminaryMarkers(documentNo);
    } else {
      final DocumentSequenceInfo newDocumentSeqInfo = documentSequenceDAO.retriveDocumentSequenceInfo(docSequenceId);
      final boolean isStartNewYear = newDocumentSeqInfo != null && newDocumentSeqInfo.isStartNewYear();
      if (isStartNewYear) {
        final String dateColumnName = newDocumentSeqInfo.getDateColumn();
        final Date date = getDocumentDate(dateColumnName);
        final boolean isStartNewMonth = newDocumentSeqInfo.isStartNewMonth();
        final String documentNo;
        if (isStartNewMonth) {
          documentNo = documentSequenceDAO.retrieveDocumentNoByYearAndMonth(docSequenceId.getRepoId(), date);
        } else {
          documentNo = documentSequenceDAO.retrieveDocumentNoByYear(docSequenceId.getRepoId(), date);
        }
        return IPreliminaryDocumentNoBuilder.withPreliminaryMarkers(documentNo);
      } else {
        final String documentNo = documentSequenceDAO.retrieveDocumentNo(docSequenceId.getRepoId());
        return IPreliminaryDocumentNoBuilder.withPreliminaryMarkers(documentNo);
      }
    }
  }

  private void assertNotBuilt() {
    Check.assume(!_built.get(), "not already built");
  }

  private DocSequenceId getOldSequenceId() {
    if (_oldSequence_ID == null) {
      _oldSequence_ID = retrieveOldSequenceId();
    }
    return _oldSequence_ID;
  }

  private DocSequenceId retrieveOldSequenceId() {
    final boolean isNewDocumentNo = isNewDocumentNo();
    if (isNewDocumentNo) {
      return null;
    }
    final I_C_DocType oldDocType = getOldDocType();
    if (oldDocType == null) {
      return null;
    }
    final DocTypeSequenceList oldDocTypeSequenceList = documentSequenceDAO.retrieveDocTypeSequenceList(oldDocType);
    return oldDocTypeSequenceList.getDocNoSequenceId(getClientId(), getOrgId(), getCountryId());
  }

  private Properties getCtx() {
    return Env.getCtx();
  }

  private ClientId getClientId() {
    if (_adClientId == null) {
      _adClientId = extractClientId();
    }
    return _adClientId;
  }

  private ClientId extractClientId() {
    final Object documentModel = getDocumentModel();
    if (documentModel != null) {
      final Integer adClientId = InterfaceWrapperHelper.getValueOrNull(documentModel, "AD_Client_ID");
      if (adClientId != null) {
        return ClientId.ofRepoId(adClientId);
      }
    }
    throw new DocumentNoBuilderException("Could not get AD_Client_ID");
  }

  private OrgId getOrgId() {
    if (_adOrgId == null) {
      _adOrgId = extractOrgId();
    }
    return _adOrgId;
  }

  private OrgId extractOrgId() {
    final Object documentModel = getDocumentModel();
    if (documentModel != null) {
      final Integer adOrgId = InterfaceWrapperHelper.getValueOrNull(documentModel, "AD_Org_ID");
      if (adOrgId != null) {
        return OrgId.ofRepoId(adOrgId);
      } else {
        return OrgId.ANY;
      }
    }
    throw new DocumentNoBuilderException("Could not get AD_Org_ID");
  }

  @Override public IPreliminaryDocumentNoBuilder setNewDocType(@Nullable final I_C_DocType newDocType) {
    assertNotBuilt();
    _newDocType = newDocType;
    return this;
  }

  private I_C_DocType getNewDocType() {
    return _newDocType;
  }

  @Override public IPreliminaryDocumentNoBuilder setOldDocType_ID(final int oldDocType_ID) {
    assertNotBuilt();
    _oldDocType_ID = oldDocType_ID > 0 ? oldDocType_ID : -1;
    return this;
  }

  private I_C_DocType getOldDocType() {
    if (_oldDocType == null) {
      final int oldDocTypeId = _oldDocType_ID;
      if (oldDocTypeId > 0) {
        _oldDocType = InterfaceWrapperHelper.create(getCtx(), oldDocTypeId, I_C_DocType.class, ITrx.TRXNAME_None);
      }
    }
    return _oldDocType;
  }

  @Override public IPreliminaryDocumentNoBuilder setOldDocumentNo(final String oldDocumentNo) {
    assertNotBuilt();
    _oldDocumentNo = oldDocumentNo;
    return this;
  }

  private String getOldDocumentNo() {
    return _oldDocumentNo;
  }

  private boolean isNewDocumentNo() {
    final String oldDocumentNo = getOldDocumentNo();
    if (oldDocumentNo == null) {
      return true;
    }
    if (IPreliminaryDocumentNoBuilder.hasPreliminaryMarkers(oldDocumentNo)) {
      return true;
    }
    return false;
  }

  @Override public IPreliminaryDocumentNoBuilder setDocumentModel(final Object documentModel) {
    _documentModel = documentModel;
    return this;
  }

  private Object getDocumentModel() {
    Check.assumeNotNull(_documentModel, "_documentModel not null");
    return _documentModel;
  }

  private java.util.Date getDocumentDate(final String dateColumnName) {
    final Object documentModel = getDocumentModel();
    final Optional<java.util.Date> date = InterfaceWrapperHelper.getValue(documentModel, dateColumnName);
    return date.orElse(null);
  }

  private final List<ICountryIdProvider> countryIdProviders;

  private CountryId getCountryId() {
    ICountryIdProvider.ProviderResult billToProviderResult = ICountryIdProvider.ProviderResult.EMPTY;
    for (final ICountryIdProvider countryIdProvider : countryIdProviders) {
      billToProviderResult = countryIdProvider.computeValueInfo(getDocumentModel());
      if (billToProviderResult.hasCountryId()) {
        break;
      }
    }
    return billToProviderResult.getCountryIdOrNull();
  }
}
package com.redhat.rhn.domain.errata;

import com.redhat.rhn.common.db.DatabaseException;
import com.redhat.rhn.common.db.datasource.DataResult;
import com.redhat.rhn.common.db.datasource.ModeFactory;
import com.redhat.rhn.common.db.datasource.SelectMode;
import com.redhat.rhn.common.db.datasource.WriteMode;
import com.redhat.rhn.common.hibernate.HibernateFactory;
import com.redhat.rhn.common.hibernate.HibernateRuntimeException;
import com.redhat.rhn.domain.channel.Channel;
import com.redhat.rhn.domain.channel.ChannelFactory;
import com.redhat.rhn.domain.common.ChecksumFactory;
import com.redhat.rhn.domain.errata.impl.PublishedBug;
import com.redhat.rhn.domain.errata.impl.PublishedClonedErrata;
import com.redhat.rhn.domain.errata.impl.PublishedErrata;
import com.redhat.rhn.domain.errata.impl.PublishedErrataFile;
import com.redhat.rhn.domain.errata.impl.UnpublishedBug;
import com.redhat.rhn.domain.errata.impl.UnpublishedClonedErrata;
import com.redhat.rhn.domain.errata.impl.UnpublishedErrata;
import com.redhat.rhn.domain.errata.impl.UnpublishedErrataFile;
import com.redhat.rhn.domain.org.Org;
import com.redhat.rhn.domain.rhnpackage.Package;
import com.redhat.rhn.domain.rhnpackage.PackageFactory;
import com.redhat.rhn.domain.user.User;
import com.redhat.rhn.frontend.action.channel.manage.PublishErrataHelper;
import com.redhat.rhn.frontend.dto.ErrataOverview;
import com.redhat.rhn.frontend.dto.ErrataPackageFile;
import com.redhat.rhn.frontend.dto.OwnedErrata;
import com.redhat.rhn.frontend.dto.PackageOverview;
import com.redhat.rhn.frontend.xmlrpc.InvalidChannelException;
import com.redhat.rhn.manager.channel.ChannelManager;
import com.redhat.rhn.manager.errata.ErrataManager;
import com.redhat.rhn.manager.errata.cache.ErrataCacheManager;
import org.apache.commons.collections.IteratorUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class ErrataFactory extends HibernateFactory {

    private static ErrataFactory singleton = new ErrataFactory();

    private static Logger log = Logger.getLogger(ErrataFactory.class);

    static public final String ERRATA_TYPE_BUG = "Bug Fix Advisory";

    static public final String ERRATA_TYPE_ENHANCEMENT = "Product Enhancement Advisory";

    static public final String ERRATA_TYPE_SECURITY = "Security Advisory";

    private ErrataFactory() {
        super();
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    static public List<Long> listErrataChannelPackages(Long cid, Long eid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("channel_id", cid);
        params.put("errata_id", eid);
        DataResult<ErrataPackageFile> dr = executeSelectMode("ErrataCache_queries", "package_associated_to_errata_and_channel", params);
        List toReturn = new ArrayList<Long>();
        for (ErrataPackageFile file : dr) {
            toReturn.add(file.getPackageId());
        }
        return toReturn;
    }

    static public List lookupByIdentifier(String identifier, Org org) {
        Long eid = null;
        List retval = new LinkedList();
        Errata errata = null;
        try {
            eid = new Long(Long.parseLong(identifier));
        } catch (NumberFormatException e) {
            eid = null;
        }
        if (eid != null) {
            errata = ErrataFactory.lookupPublishedErrataById(eid);
            if (errata != null) {
                retval.add(errata);
            }
        } else if (identifier.length() > 4) {
            String prefix = null;
            errata = ErrataFactory.lookupByAdvisoryId(identifier, org);
            if (errata != null) {
                retval.add(errata);
            } else {
                errata = ErrataFactory.lookupByAdvisory(identifier, org);
                if (errata != null) {
                    retval.add(errata);
                }
            }
            if (errata == null) {
                prefix = identifier.substring(0, 4);
                if (prefix.matches("RH.A")) {
                    StringTokenizer strtok = new StringTokenizer(identifier, "-");
                    StringBuilder buf = new StringBuilder();
                    boolean foundFirst = false;
                    while (strtok.hasMoreTokens()) {
                        buf.append(strtok.nextToken());
                        if (!foundFirst) {
                            buf.append("-");
                            foundFirst = true;
                        } else {
                            if (strtok.hasMoreTokens()) {
                                buf.append(":");
                            }
                        }
                    }
                    identifier = buf.toString();
                    errata = ErrataFactory.lookupByAdvisoryId(identifier, org);
                }
                if (errata != null) {
                    retval.add(errata);
                }
            }
            if (errata == null) {
                prefix = identifier.substring(0, 3);
                if ((prefix.equals("CVE") || prefix.equals("CAN")) && identifier.length() > 7 && identifier.indexOf('-') == -1) {
                    identifier = identifier.substring(0, 3) + "-" + identifier.substring(3, 7) + "-" + identifier.substring(7);
                }
                List erratas = ErrataFactory.lookupByCVE(identifier);
                retval.addAll(erratas);
            }
        }
        return retval;
    }

    static public Errata publish(Errata unpublished) {
        if (unpublished.isPublished()) {
            return unpublished;
        }
        Errata published;
        if (unpublished.isCloned()) {
            published = new PublishedClonedErrata();
            ((PublishedClonedErrata) published).setOriginal(((UnpublishedClonedErrata) unpublished).getOriginal());
        } else {
            published = ErrataFactory.createPublishedErrata();
        }
        copyDetails(published, unpublished, false);
        save(published);
        try {
            Session session = HibernateFactory.getSession();
            session.delete(unpublished);
        } catch (HibernateException e) {
            throw new HibernateRuntimeException("Errors occurred while publishing errata", e);
        }
        return published;
    }

    static public List<Errata> publishToChannel(List<Errata> errataList, Channel chan, User user, boolean inheritPackages) {
        return publishToChannel(errataList, chan, user, inheritPackages, true);
    }

    static public List<Errata> publishToChannel(List<Errata> errataList, Channel chan, User user, boolean inheritPackages, boolean performPostActions) {
        List<com.redhat.rhn.domain.errata.Errata> toReturn = new ArrayList<Errata>();
        for (Errata errata : errataList) {
            if (!errata.isPublished()) {
                errata = publish(errata);
            }
            errata.addChannel(chan);
            errata.addChannelNotification(chan, new Date());
            Set<Package> packagesToPush = new HashSet<Package>();
            DataResult<PackageOverview> packs;
            if (inheritPackages) {
                if (!chan.isCloned()) {
                    throw new InvalidChannelException("Cloned channel expected: " + chan.getLabel());
                }
                Channel original = chan.getOriginal();
                Set<Channel> associatedChannels = errata.getChannels();
                while (original.isCloned() && !associatedChannels.contains(original)) {
                    original = ChannelFactory.lookupOriginalChannel(original);
                }
                packs = ErrataManager.listErrataChannelPacks(original, errata, user);
            } else {
                packs = ErrataManager.lookupPacksFromErrataForChannel(chan, errata, user);
            }
            for (PackageOverview packOver : packs) {
                Package pack = PackageFactory.lookupByIdAndUser(packOver.getId().longValue(), user);
                packagesToPush.add(pack);
            }
            Errata e = publishErrataPackagesToChannel(errata, chan, user, packagesToPush);
            toReturn.add(e);
        }
        if (performPostActions) {
            postPublishActions(chan, user);
        }
        return toReturn;
    }

    static public Errata publishToChannel(Errata errata, Channel chan, User user, Set<Package> packages) {
        if (!errata.isPublished()) {
            errata = publish(errata);
        }
        errata.addChannel(chan);
        errata = publishErrataPackagesToChannel(errata, chan, user, packages);
        postPublishActions(chan, user);
        return errata;
    }

    private static void postPublishActions(Channel chan, User user) {
        ChannelManager.refreshWithNewestPackages(chan, "java::publishErrataPackagesToChannel");
    }

    private static Errata publishErrataPackagesToChannel(Errata errata, Channel chan, User user, Set<Package> packages) {
        List<Long> pids = new ArrayList<Long>();
        for (Package pack : packages) {
            pids.add(pack.getId());
        }
        ChannelManager.addPackages(chan, pids, user);
        for (Package pack : packages) {
            List<ErrataFile> publishedFiles = ErrataFactory.lookupErrataFile(errata, pack);
            Map<String, ErrataFile> toAdd = new HashMap();
            if (publishedFiles.size() == 0) {
                String path = pack.getPath();
                if (path == null) {
                    throw new DatabaseException("Package " + pack.getId() + " has NULL path, please run spacewalk-data-fsck");
                }
                ErrataFile publishedFile = ErrataFactory.createPublishedErrataFile(ErrataFactory.lookupErrataFileType("RPM"), pack.getChecksum().getChecksum(), path);
                publishedFile.addPackage(pack);
                publishedFile.setErrata(errata);
                publishedFile.setModified(new Date());
                ((PublishedErrataFile) publishedFile).addChannel(chan);
                singleton.saveObject(publishedFile);
            } else {
                for (ErrataFile publishedFile : publishedFiles) {
                    String fileName = publishedFile.getFileName().substring(publishedFile.getFileName().lastIndexOf("/") + 1);
                    if (!toAdd.containsKey(fileName)) {
                        toAdd.put(fileName, publishedFile);
                        ((PublishedErrataFile) publishedFile).addChannel(chan);
                        singleton.saveObject(publishedFile);
                    }
                }
            }
        }
        ChannelFactory.save(chan);
        List chanList = new ArrayList();
        chanList.add(chan.getId());
        ErrataCacheManager.insertCacheForChannelErrataAsync(chanList, errata);
        return errata;
    }

    static public Errata createClone(Org org, Errata e) {
        UnpublishedClonedErrata clone = new UnpublishedClonedErrata();
        copyDetails(clone, e, true);
        PublishErrataHelper.setUniqueAdvisoryCloneName(e, clone);
        clone.setOriginal(e);
        clone.setOrg(org);
        save(clone);
        return clone;
    }

    private static void copyDetails(Errata copy, Errata original, boolean clone) {
        if (!clone) {
            copy.setAdvisory(original.getAdvisory());
            copy.setAdvisoryName(original.getAdvisoryName());
            copy.setOrg(original.getOrg());
        }
        copy.setAdvisoryType(original.getAdvisoryType());
        copy.setProduct(original.getProduct());
        copy.setErrataFrom(original.getErrataFrom());
        copy.setDescription(original.getDescription());
        copy.setSynopsis(original.getSynopsis());
        copy.setTopic(original.getTopic());
        copy.setSolution(original.getSolution());
        copy.setIssueDate(original.getIssueDate());
        copy.setUpdateDate(original.getUpdateDate());
        copy.setNotes(original.getNotes());
        copy.setRefersTo(original.getRefersTo());
        copy.setAdvisoryRel(original.getAdvisoryRel());
        copy.setLocallyModified(original.getLocallyModified());
        copy.setLastModified(original.getLastModified());
        copy.setSeverity(original.getSeverity());
        copy.setPackages(new HashSet(original.getPackages()));
        Iterator keysItr = IteratorUtils.getIterator(original.getKeywords());
        while (keysItr.hasNext()) {
            Keyword k = (Keyword) keysItr.next();
            copy.addKeyword(k.getKeyword());
        }
        Iterator bugsItr = IteratorUtils.getIterator(original.getBugs());
        while (bugsItr.hasNext()) {
            Bug bugIn = (Bug) bugsItr.next();
            Bug cloneB;
            if (copy.isPublished()) {
                cloneB = ErrataManager.createNewPublishedBug(bugIn.getId(), bugIn.getSummary(), bugIn.getUrl());
            } else {
                cloneB = ErrataManager.createNewUnpublishedBug(bugIn.getId(), bugIn.getSummary(), bugIn.getUrl());
            }
            copy.addBug(cloneB);
        }
    }

    static public Errata createPublishedErrata() {
        return new PublishedErrata();
    }

    static public Errata createUnpublishedErrata() {
        return new UnpublishedErrata();
    }

    static public Bug createUnpublishedBug(Long id, String summary, String url) {
        Bug bug = new UnpublishedBug();
        bug.setId(id);
        bug.setSummary(summary);
        bug.setUrl(url);
        return bug;
    }

    static public Bug createPublishedBug(Long id, String summary, String url) {
        Bug bug = new PublishedBug();
        bug.setId(id);
        bug.setSummary(summary);
        bug.setUrl(url);
        return bug;
    }

    static public ErrataFile createUnpublishedErrataFile(ErrataFileType ft, String cs, String name, Set packages) {
        ErrataFile file = new UnpublishedErrataFile();
        file.setFileType(ft);
        file.setChecksum(ChecksumFactory.safeCreate(cs, "md5"));
        file.setFileName(name);
        file.setPackages(packages);
        return file;
    }

    static public ErrataFile createPublishedErrataFile(ErrataFileType ft, String cs, String name) {
        return createPublishedErrataFile(ft, cs, name, new HashSet());
    }

    static public ErrataFile createPublishedErrataFile(ErrataFileType ft, String cs, String name, Set packages) {
        ErrataFile file = new PublishedErrataFile();
        file.setFileType(ft);
        file.setChecksum(ChecksumFactory.safeCreate(cs, "md5"));
        file.setFileName(name);
        file.setPackages(packages);
        return file;
    }

    static public ErrataFileType lookupErrataFileType(String label) {
        Session session = null;
        ErrataFileType retval = null;
        try {
            session = HibernateFactory.getSession();
            retval = (ErrataFileType) session.getNamedQuery("ErrataFileType.findByLabel").setString("label", label).setCacheable(true).uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateRuntimeException(e.getMessage(), e);
        }
        return retval;
    }

    static public List lookupErrataFilesByErrataAndFileType(Long errataId, String fileType) {
        Session session = null;
        List retval = null;
        try {
            session = HibernateFactory.getSession();
            Query q = session.getNamedQuery("PublishedErrataFile.listByErrataAndFileType");
            q.setLong("errata_id", errataId.longValue());
            q.setString("file_type", fileType.toUpperCase());
            retval = q.list();
            if (retval == null) {
                q = session.getNamedQuery("UnpublishedErrataFile.listByErrataAndFileType");
                q.setLong("errata_id", errataId.longValue());
                q.setString("file_type", fileType.toUpperCase());
                retval = q.list();
            }
        } catch (HibernateException e) {
            throw new HibernateRuntimeException(e.getMessage(), e);
        }
        return retval;
    }

    static public Errata lookupById(Long id) {
        Session session = HibernateFactory.getSession();
        Errata errata = (Errata) session.get(PublishedErrata.class, id);
        if (errata == null) {
            errata = (Errata) session.get(UnpublishedErrata.class, id);
        }
        return errata;
    }

    static public List lookupErratasByAdvisoryType(String advisoryType) {
        Session session = null;
        List retval = null;
        try {
            session = HibernateFactory.getSession();
            retval = session.getNamedQuery("PublishedErrata.findByAdvisoryType").setString("type", advisoryType).setCacheable(true).list();
        } catch (HibernateException he) {
            log.error("Error loading ActionArchTypes from DB", he);
            throw new HibernateRuntimeException("Error loading ActionArchTypes from db");
        }
        return retval;
    }

    static public Errata lookupPublishedErrataById(Long id) {
        Session session = null;
        Errata retval = null;
        try {
            session = HibernateFactory.getSession();
            retval = (Errata) session.getNamedQuery("PublishedErrata.findById").setLong("id", id.longValue()).uniqueResult();
        } catch (HibernateException he) {
            log.error("Error loading ActionArchTypes from DB", he);
            throw new HibernateRuntimeException("Error loading ActionArchTypes from db");
        }
        return retval;
    }

    static public Errata lookupByAdvisory(String advisory, Org org) {
        Session session = null;
        Errata retval = null;
        session = HibernateFactory.getSession();
        retval = (Errata) session.getNamedQuery("PublishedErrata.findByAdvisoryName").setParameter("advisory", advisory).setParameter("org", org).uniqueResult();
        if (retval == null) {
            retval = (Errata) session.getNamedQuery("UnpublishedErrata.findByAdvisoryName").setParameter("advisory", advisory).setParameter("org", org).uniqueResult();
        }
        return retval;
    }

    static public Errata lookupByAdvisoryId(String advisoryId, Org org) {
        Session session = null;
        Errata retval = null;
        try {
            session = HibernateFactory.getSession();
            retval = (Errata) session.getNamedQuery("PublishedErrata.findByAdvisory").setParameter("advisory", advisoryId).setParameter("org", org).uniqueResult();
            if (retval == null) {
                retval = (Errata) session.getNamedQuery("UnpublishedErrata.findByAdvisory").setParameter("advisory", advisoryId).setParameter("org", org).uniqueResult();
            }
        } catch (HibernateException e) {
            throw new HibernateRuntimeException("Error looking up errata by advisory name");
        }
        return retval;
    }

    static public List lookupByCVE(String cve) {
        List retval = new LinkedList();
        SelectMode mode = ModeFactory.getMode("Errata_queries", "erratas_for_cve");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cve", cve);
        List result = mode.execute(params);
        Session session = HibernateFactory.getSession();
        for (Iterator iter = result.iterator(); iter.hasNext(); ) {
            Map row = (Map) iter.next();
            Long rawId = (Long) row.get("id");
            retval.add(session.load(PublishedErrata.class, rawId));
        }
        return retval;
    }

    static public List lookupByOriginal(Org org, Errata original) {
        Session session = null;
        List retval = null;
        try {
            session = HibernateFactory.getSession();
            retval = session.getNamedQuery("UnpublishedClonedErrata.findByOriginal").setParameter("original", original).setParameter("org", org).list();
            if (retval == null) {
                retval = lookupPublishedByOriginal(org, original);
            }
        } catch (HibernateException e) {
            throw new HibernateRuntimeException("Error looking up errata by original errata");
        }
        return retval;
    }

    static public List lookupPublishedByOriginal(Org org, Errata original) {
        Session session = null;
        List retval = null;
        try {
            session = HibernateFactory.getSession();
            retval = session.getNamedQuery("PublishedClonedErrata.findByOriginal").setParameter("original", original).setParameter("org", org).list();
        } catch (HibernateException e) {
            throw new HibernateRuntimeException("Error looking up errata by original errata");
        }
        return retval;
    }

    static public List listSamePublishedInChannels(Org org, Channel channelFrom, Channel channelTo) {
        Session session = null;
        List retval = null;
        try {
            session = HibernateFactory.getSession();
            retval = session.getNamedQuery("PublishedErrata.findSameInChannels").setParameter("channel_from", channelFrom).setParameter("channel_to", channelTo).list();
        } catch (HibernateException e) {
            throw new HibernateRuntimeException("Error looking up errata by original errata");
        }
        return retval;
    }

    static public List listPublishedBrothersInChannels(Org org, Channel channelFrom, Channel channelTo) {
        Session session = null;
        List retval = null;
        try {
            session = HibernateFactory.getSession();
            retval = session.getNamedQuery("PublishedClonedErrata.findBrothersInChannel").setParameter("channel_from", channelFrom).setParameter("channel_to", channelTo).list();
        } catch (HibernateException e) {
            throw new HibernateRuntimeException("Error looking up errata by original errata");
        }
        return retval;
    }

    static public List listPublishedClonesInChannels(Org org, Channel channelFrom, Channel channelTo) {
        Session session = null;
        List retval = null;
        try {
            session = HibernateFactory.getSession();
            retval = session.getNamedQuery("PublishedErrata.findClonesInChannel").setParameter("channel_from", channelFrom).setParameter("channel_to", channelTo).list();
        } catch (HibernateException e) {
            throw new HibernateRuntimeException("Error looking up errata by original errata");
        }
        return retval;
    }

    static public void save(Errata errataIn) {
        singleton.saveObject(errataIn);
    }

    static public void removeBug(Bug deleteme) {
        singleton.removeObject(deleteme);
    }

    static public void remove(Keyword deleteme) {
        singleton.removeObject(deleteme);
    }

    static public void removeFile(ErrataFile deleteme) {
        singleton.removeObject(deleteme);
    }

    static public List lookupByChannelSorted(Org org, Channel channel) {
        return HibernateFactory.getSession().getNamedQuery("PublishedErrata.lookupSortedByChannel").setParameter("org", org).setParameter("channel", channel).list();
    }

    static public List<Errata> lookupByChannelBetweenDates(Org org, Channel channel, String startDate, String endDate) {
        return HibernateFactory.getSession().getNamedQuery("PublishedErrata.lookupByChannelBetweenDates").setParameter("org", org).setParameter("channel", channel).setParameter("start_date", startDate).setParameter("end_date", endDate).list();
    }

    static public List<ErrataFile> lookupErrataFile(Errata errata, Package pack) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("errata", errata);
        params.put("package", pack);
        return singleton.listObjectsByNamedQuery("PublishedErrataFile.lookupByErrataAndPackage", params);
    }

    static public List<ErrataOverview> search(List eids, Org org) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("eids", eids);
        params.put("org_id", org.getId());
        List results = singleton.listObjectsByNamedQuery("PublishedErrata.searchById", params);
        List<ErrataOverview> errata = new ArrayList<ErrataOverview>();
        for (Object result : results) {
            Object[] values = (Object[]) result;
            ErrataOverview eo = new ErrataOverview();
            eo.setId((Long) values[0]);
            eo.setAdvisory((String) values[1]);
            eo.setAdvisoryName((String) values[2]);
            eo.setAdvisoryType((String) values[3]);
            eo.setAdvisorySynopsis((String) values[4]);
            eo.setUpdateDate((Date) values[5]);
            eo.setIssueDate((Date) values[6]);
            eo.setRebootSuggested((Boolean) values[7]);
            eo.setRestartSuggested((Boolean) values[8]);
            errata.add(eo);
        }
        return errata;
    }

    static public List<ErrataOverview> searchByPackageIds(List pids) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pids", pids);
        if (log.isDebugEnabled()) {
            log.debug("pids = " + pids);
        }
        List results = singleton.listObjectsByNamedQuery("PublishedErrata.searchByPackageIds", params);
        if (log.isDebugEnabled()) {
            log.debug("Query 'PublishedErrata.searchByPackageIds' returned " + results.size() + " entries");
        }
        List<ErrataOverview> errata = new ArrayList<ErrataOverview>();
        Long lastId = null;
        ErrataOverview eo = null;
        for (Object result : results) {
            Object[] values = (Object[]) result;
            Long curId = (Long) values[0];
            if (!curId.equals(lastId)) {
                eo = new ErrataOverview();
            }
            eo.setId((Long) values[0]);
            eo.setAdvisory((String) values[1]);
            eo.setAdvisoryName((String) values[2]);
            eo.setAdvisoryType((String) values[3]);
            eo.setAdvisorySynopsis((String) values[4]);
            eo.setUpdateDate((Date) values[5]);
            eo.setIssueDate((Date) values[6]);
            eo.addPackageName((String) values[7]);
            if (!curId.equals(lastId)) {
                errata.add(eo);
                lastId = curId;
            }
            if (log.isDebugEnabled()) {
                log.debug("curId = " + curId + ", lastId = " + lastId);
                log.debug("ErrataOverview formed: " + eo.getAdvisoryName() + " for " + eo.getPackageNames());
            }
        }
        return errata;
    }

    static public List<ErrataOverview> searchByPackageIdsWithOrg(List pids, Org org) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pids", pids);
        params.put("org_id", org.getId());
        if (log.isDebugEnabled()) {
            log.debug("org_id = " + org.getId());
            log.debug("pids = " + pids);
        }
        List results = singleton.listObjectsByNamedQuery("PublishedErrata.searchByPackageIdsWithOrg", params);
        if (log.isDebugEnabled()) {
            log.debug("Query 'PublishedErrata.searchByPackageIdsWithOrg' returned " + results.size() + " entries");
        }
        List<ErrataOverview> errata = new ArrayList<ErrataOverview>();
        Long lastId = null;
        ErrataOverview eo = null;
        for (Object result : results) {
            Object[] values = (Object[]) result;
            Long curId = (Long) values[0];
            if (!curId.equals(lastId)) {
                eo = new ErrataOverview();
            }
            eo.setId((Long) values[0]);
            eo.setAdvisory((String) values[1]);
            eo.setAdvisoryName((String) values[2]);
            eo.setAdvisoryType((String) values[3]);
            eo.setAdvisorySynopsis((String) values[4]);
            eo.setUpdateDate((Date) values[5]);
            eo.setIssueDate((Date) values[6]);
            eo.addPackageName((String) values[7]);
            eo.setRebootSuggested((Boolean) values[8]);
            eo.setRestartSuggested((Boolean) values[9]);
            if (!curId.equals(lastId)) {
                errata.add(eo);
                lastId = curId;
            }
            if (log.isDebugEnabled()) {
                log.debug("curId = " + curId + ", lastId = " + lastId);
                log.debug("ErrataOverview formed: " + eo.getAdvisoryName() + " for " + eo.getPackageNames());
            }
        }
        return errata;
    }

    static public void syncErrataDetails(PublishedClonedErrata cloned) {
        copyDetails(cloned, cloned.getOriginal(), true);
    }

    static public List<Errata> listErrata(Collection<Long> ids) {
        return singleton.listObjectsByNamedQuery("PublishedErrata.listByIds", new HashMap(), ids, "list");
    }

    static public DataResult<ErrataOverview> relevantToOneChannelButNotAnother(Long fromCid, Long toCid) {
        SelectMode mode = ModeFactory.getMode("Errata_queries", "relevant_to_one_channel_but_not_another");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("from_cid", fromCid);
        params.put("to_cid", toCid);
        DataResult<ErrataOverview> results = mode.execute(params);
        return results;
    }

    static public DataResult<OwnedErrata> listPublishedOwnedUnmodifiedClonedErrata(Long orgId) {
        SelectMode mode = ModeFactory.getMode("Errata_queries", "published_owned_unmodified_cloned_errata");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("org_id", orgId);
        DataResult<OwnedErrata> results = mode.execute(params);
        return results;
    }

    static public Set<String> listAdvisoriesEndingWith(String ending) {
        SelectMode mode = ModeFactory.getMode("Errata_queries", "advisories_ending_with");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ending", "%" + ending);
        List<Map<String, Object>> results = mode.execute(params);
        Set<String> ret = new HashSet<String>();
        for (Map<String, Object> result : results) {
            ret.add((String) result.get("advisory"));
        }
        return ret;
    }

    static public Set<String> listAdvisoryNamesEndingWith(String ending) {
        SelectMode mode = ModeFactory.getMode("Errata_queries", "advisory_names_ending_with");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ending", "%" + ending);
        List<Map<String, Object>> results = mode.execute(params);
        Set<String> ret = new HashSet<String>();
        for (Map<String, Object> result : results) {
            ret.add((String) result.get("advisory_name"));
        }
        return ret;
    }

    static public ErrataOverview getOverviewById(Long eid) {
        SelectMode mode = ModeFactory.getMode("Errata_queries", "overview_by_id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("eid", eid);
        DataResult<ErrataOverview> results = mode.execute(params);
        if (results.size() == 0) {
            return null;
        }
        results.elaborate();
        return results.get(0);
    }

    static public ErrataOverview getOverviewByAdvisory(String advisory) {
        SelectMode mode = ModeFactory.getMode("Errata_queries", "overview_by_advisory");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("advisory", advisory);
        DataResult<ErrataOverview> results = mode.execute(params);
        if (results.size() == 0) {
            return null;
        }
        results.elaborate();
        return results.get(0);
    }

    static public ErrataOverview cloneErratum(Long originalEid, String advisory, String advisoryName, Long orgId) {
        WriteMode m = ModeFactory.getWriteMode("Errata_queries", "clone_erratum");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("eid", originalEid);
        params.put("advisory", advisory);
        params.put("name", advisoryName);
        params.put("org_id", orgId);
        m.executeUpdate(params);
        ErrataOverview clone = getOverviewByAdvisory(advisory);
        m = ModeFactory.getWriteMode("Errata_queries", "set_original");
        params = new HashMap<String, Object>();
        params.put("original_id", originalEid);
        params.put("clone_id", clone.getId());
        m.executeUpdate(params);
        m = ModeFactory.getWriteMode("Errata_queries", "clone_bugs");
        m.executeUpdate(params);
        m = ModeFactory.getWriteMode("Errata_queries", "clone_keywords");
        m.executeUpdate(params);
        m = ModeFactory.getWriteMode("Errata_queries", "clone_packages");
        m.executeUpdate(params);
        m = ModeFactory.getWriteMode("Errata_queries", "clone_cves");
        m.executeUpdate(params);
        m = ModeFactory.getWriteMode("Errata_queries", "clone_files");
        m.executeUpdate(params);
        return clone;
    }
}

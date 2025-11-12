package org.mskcc.cbio.portal.dao;
import org.mskcc.cbio.portal.model.*;
import org.mskcc.cbio.portal.scripts.ResetDatabase;
import junit.framework.TestCase;
import java.util.*;

/**
 * Junit tests for DaoMicroRnaAlteration class.
 */
public class TestDaoMicroRnaAlteration extends TestCase {
  public void testDaoMicroRnaAlteration() throws DaoException {
    MySQLbulkLoader.bulkLoadOff();
    runTheTest();
    MySQLbulkLoader.bulkLoadOn();
    runTheTest();
  }

  private void runTheTest() throws DaoException {
    ResetDatabase.resetDatabase();
    ArrayList<Integer> internalSampleIds = createSamples();

<<<<<<< commits-gh_100/cBioPortal/cbioportal/6c8494e8ecd36d6073db67850b14677458c0dd9c/TestDaoMicroRnaAlteration-99857ce.java
    DaoGeneticProfileSamples daoGeneticProfileSamples = new DaoGeneticProfileSamples();
=======
>>>>>>> Unknown file: This is a bug in JDime.

    int numRows = 
<<<<<<< commits-gh_100/cBioPortal/cbioportal/6c8494e8ecd36d6073db67850b14677458c0dd9c/TestDaoMicroRnaAlteration-99857ce.java
    daoGeneticProfileSamples.addGeneticProfileSamples(1, internalSampleIds)
=======
    DaoGeneticProfileCases.addGeneticProfileCases(1, orderedCaseList)
>>>>>>> commits-gh_100/cBioPortal/cbioportal/8b0e9780f15f50ad7ada539be709f6fc9b3cdd54/TestDaoMicroRnaAlteration-bb5b7c0.java
    ;
    assertEquals(1, numRows);
    String data = "1.2:1.4:1.6:1.8";
    String values[] = data.split(":");
    DaoMicroRnaAlteration dao = DaoMicroRnaAlteration.getInstance();
    int num = dao.addMicroRnaAlterations(1, "hsa-123", values);
    if (MySQLbulkLoader.isBulkLoad()) {
      MySQLbulkLoader.flushAll();
    }
    String value = dao.getMicroRnaAlteration(1, internalSampleIds.get(0), "hsa-123");
    assertEquals("1.2", value);
    value = dao.getMicroRnaAlteration(1, internalSampleIds.get(1), "hsa-123");
    assertEquals("1.4", value);
    HashMap<Integer, String> map = dao.getMicroRnaAlterationMap(1, "hsa-123");
    assertEquals(4, map.size());
    assertTrue(map.containsKey(internalSampleIds.get(1)));
    assertTrue(map.containsKey(internalSampleIds.get(2)));
    Set<String> microRnaSet = dao.getGenesInProfile(1);
    assertEquals(1, microRnaSet.size());
    dao.deleteAllRecords();
  }

  private ArrayList<Integer> createSamples() throws DaoException {
    ArrayList<Integer> toReturn = new ArrayList<Integer>();
    CancerStudy study = new CancerStudy("study", "description", "id", "brca", true);
    Patient p = new Patient(study, "TCGA-1");
    int pId = DaoPatient.addPatient(p);
    Sample s = new Sample("TCGA-1-1-01", pId, "type");
    toReturn.add(DaoSample.addSample(s));
    s = new Sample("TCGA-1-2-01", pId, "type");
    toReturn.add(DaoSample.addSample(s));
    s = new Sample("TCGA-1-3-01", pId, "type");
    toReturn.add(DaoSample.addSample(s));
    s = new Sample("TCGA-1-4-01", pId, "type");
    toReturn.add(DaoSample.addSample(s));
    return toReturn;
  }
}
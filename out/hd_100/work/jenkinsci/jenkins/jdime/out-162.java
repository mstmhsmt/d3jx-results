package hudson.logging;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jvnet.hudson.test.Bug;

public class LogRecorderTest {
  @Bug(value = 17983) @Test public void targetIncludes() {
    assertTrue(includes("hudson", "hudson"));
    assertFalse(includes("hudson", "hudsone"));
    assertFalse(includes("hudson", "hudso"));
    assertTrue(includes("hudson", "hudson.model.Hudson"));
    assertFalse(includes("hudson", "jenkins.model.Jenkins"));
    assertTrue(includes("", "hudson.model.Hudson"));
  }

  @Test public void targetMatches() {
    assertTrue(matches("hudson", "hudson"));
    assertFalse(matches("hudson", "hudson", Level.FINE));
    assertNull(matches("hudson", "hudsone"));
    assertNull(matches("hudson", "hudso"));
    assertTrue(matches("hudson", "hudson.model.Hudson"));
    assertFalse(matches("hudson", "hudson.model.Hudson", Level.FINE));
    assertNull(matches("hudson", "jenkins.model.Jenkins"));
    assertTrue(matches("", "hudson.model.Hudson"));
    assertFalse(matches("", "hudson.model.Hudson", Level.FINE));
  }

  @Test public void testSpecificExclusion() {
    LogRecorder lr = new LogRecorder("foo");
    LogRecorder.Target 
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    targetLevel0 = new LogRecorder.Target("", Level.FINE)
=======
    targetH = new LogRecorder.Target("foo.bar", Level.SEVERE)
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ;
    LogRecorder.Target 
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    targetLevel1 = new LogRecorder.Target("foo", Level.INFO)
=======
    targetM = new LogRecorder.Target("foo", Level.INFO)
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ;
    LogRecorder.Target 
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    targetLevel2 = new LogRecorder.Target("foo.bar", Level.SEVERE)
=======
    targetL = new LogRecorder.Target("", Level.FINE)
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ;
    lr.targets.add(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    targetLevel1
=======
    targetH
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    );
    lr.targets.add(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    targetLevel2
=======
    targetM
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    );
    lr.targets.add(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    targetLevel0
=======
    targetL
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    );

<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    assertEquals(lr.orderedTargets()[0], targetLevel2);
=======
    LogRecord r1h = createLogRecord("foo.bar.baz", Level.INFO, "hidden");
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java

    assertEquals(lr.orderedTargets()[1], targetLevel1);
    assertEquals(lr.orderedTargets()[2], targetLevel0);
    LogRecord 
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r1 = createLogRecord("baz", Level.INFO, "visible")
=======
    r1v = createLogRecord("foo.bar.baz", Level.SEVERE, "visible")
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ;
    LogRecord 
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r2 = createLogRecord("foo", Level.FINE, "hidden")
=======
    r2h = createLogRecord("foo.bar", Level.INFO, "hidden")
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ;
    LogRecord 
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r3 = createLogRecord("foo.bar", Level.INFO, "hidden")
=======
    r2v = createLogRecord("foo.bar", Level.SEVERE, "hidden")
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ;
    LogRecord 
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r4 = createLogRecord("foo.bar.baz", Level.INFO, "hidden")
=======
    r3h = createLogRecord("foo", Level.FINE, "hidden")
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ;
    LogRecord 
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r5 = createLogRecord("foo.bar.baz", Level.SEVERE, "visible")
=======
    r3v = createLogRecord("foo", Level.INFO, "visible")
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ;
    LogRecord 
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r6 = createLogRecord("foo", Level.INFO, "visible")
=======
    r4v = createLogRecord("baz", Level.INFO, "visible")
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ;
    lr.handler.publish(r1h);
    lr.handler.publish(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r1
=======
    r1v
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    );
    lr.handler.publish(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r2
=======
    r2h
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    );
    lr.handler.publish(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r3
=======
    r2v
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    );
    lr.handler.publish(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r4
=======
    r3v
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    );
    lr.handler.publish(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r5
=======
    r3h
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    );
    lr.handler.publish(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r6
=======
    r4v
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    );
    assertTrue(lr.handler.getView().contains(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r1
=======
    r1v
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ));
    assertFalse(lr.handler.getView().contains(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r2
=======
    r1h
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ));
    assertFalse(lr.handler.getView().contains(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r3
=======
    r2h
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ));
    assertTrue(lr.handler.getView().contains(r2v));
    assertFalse(lr.handler.getView().contains(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r4
=======
    r3h
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ));
    assertTrue(lr.handler.getView().contains(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r5
=======
    r3v
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ));
    assertTrue(lr.handler.getView().contains(
<<<<<<< commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/A.java
    r6
=======
    r4v
>>>>>>> commits-hd_100/jenkinsci/jenkins/1fda95b6aea55cb216074b18bbb34fd8708c64c0-5a9043e6d2607fd0f7089b70859fe52f9721a07e/B.java
    ));
  }

  private static LogRecord createLogRecord(String logger, Level level, String message) {
    LogRecord r = new LogRecord(level, message);
    r.setLoggerName(logger);
    return r;
  }

  private static boolean includes(String target, String logger) {
    LogRecord r = createLogRecord(logger, Level.INFO, "whatever");
    return new LogRecorder.Target(target, Level.INFO).includes(r);
  }

  private static Boolean matches(String target, String logger) {
    return matches(target, logger, Level.INFO);
  }

  private static Boolean matches(String target, String logger, Level loggerLevel) {
    LogRecord r = createLogRecord(logger, loggerLevel, "whatever");
    return new LogRecorder.Target(target, Level.INFO).matches(r);
  }
}
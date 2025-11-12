package org.junit.tests;
import static org.junit.Assert.assertEquals;
import java.util.Comparator;
import org.junit.Test;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.RunnerInterceptor;

public class ParentRunnerTest {
  public private static 
<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
  String
=======
  StringBuffer
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java
   log = 
<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
  ""
=======
  new StringBuffer()
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java
  ;


<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
  public static class FruitTest {
    @Test public void apple() {
      log += "apple ";
    }

    @Test public void banana() {
      log += "banana ";
    }
  }
=======
  public static class FruitTests {
    @Test public void apple() {
      log.append("apple ");
    }

    @Test public void banana() {
      log.append("banana ");
    }

    @Test public void pear() {
      log.append("pear ");
    }
  }
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java


  @Test public void useChildHarvester() throws InitializationError {
    log = "";
    ParentRunner<?> runner = new BlockJUnit4ClassRunner(FruitTest.class);
    runner.setRunnerInterceptor(new RunnerInterceptor() {
      public void runChild(Runnable childStatement) {
        log += "before ";
        childStatement.run();
        log += "after ";
      }

      public void finished() {
        log += "afterAll ";
      }
    });
    runner.run(new RunNotifier());
    assertEquals("before apple after before banana after afterAll ", log);
  }

  @Test public void installDecorator() throws Throwable {
    log.setLength(0);
    ParentRunner<FrameworkMethod> runner = (BlockJUnit4ClassRunner) new AllDefaultPossibilitiesBuilder(true).runnerForClass(FruitTests.class);
    runner.sort(new Sorter(new Comparator<Description>() {
      public int compare(Description o1, Description o2) {
        return o1.toString().compareTo(o2.toString());
      }
    }));
    runner.installDecorator(new ParentRunner.Decorator() {
      public void runChild(SafeStatement statement) {
        log.append("before ");
        statement.execute();
        log.append("after ");
      }

      public void runAll(SafeStatement statement) {
        log.append("beforeAll ");
        statement.execute();
        log.append("afterAll ");
      }
    });
    runner.run(new RunNotifier());
    assertEquals("beforeAll before apple after before banana after before pear after afterAll ", log.toString());
  }
}
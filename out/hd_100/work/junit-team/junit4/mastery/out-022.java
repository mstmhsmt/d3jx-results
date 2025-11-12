package org.junit.tests;

import org.junit.runner.notification.RunNotifier;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.Test;
import org.junit.runners.model.RunnerInterceptor;
import static org.junit.Assert.assertEquals;
import org.junit.runners.ParentRunner;
import org.junit.runners.BlockJUnit4ClassRunner;

public class ParentRunnerTest {

    
<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java
@Test
    public void useChildHarvester() throws InitializationError {
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
=======
@Test
    public void installDecorator() throws Throwable {
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
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java


    
<<<<<<< commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/A.java

=======
@Test
    public void installDecorator() throws Throwable {
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
>>>>>>> commits-hd_100/junit-team/junit4/956f86fa4c406a923d5366b5906e3431e9d0717d-7d061f3af085d90a672f1618deb9bf6ed9979f7a/B.java


    public static class FruitTest {

        @Test
        public void apple() {
            log += "apple ";
        }

        @Test
        public void banana() {
            log += "banana ";
        }
    }

    public static class FruitTests {

        @Test
        public void apple() {
            log.append("apple ");
        }

        @Test
        public void banana() {
            log.append("banana ");
        }

        @Test
        public void pear() {
            log.append("pear ");
        }
    }

    public static String log = "";

    private static StringBuffer log = new StringBuffer();
}

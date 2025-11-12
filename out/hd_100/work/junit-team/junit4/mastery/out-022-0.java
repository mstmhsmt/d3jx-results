package org.junit.tests;

import org.junit.runner.notification.RunNotifier;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.Test;
import org.junit.runners.model.RunnerInterceptor;
import static org.junit.Assert.assertEquals;
import org.junit.runners.ParentRunner;
import org.junit.runners.BlockJUnit4ClassRunner;

public class ParentRunnerTest {

    
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

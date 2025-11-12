package org.gradle.testkit.runner.internal;

import java.io.File;
import java.util.List;
import java.net.URI;
import org.gradle.tooling.internal.consumer.DefaultBuildLauncherInternal;

public interface GradleExecutor {

    
<<<<<<< commits-rmx_100/gradle/gradle/cf5d03e9cf13a33811b66e183119bf81090812bc/GradleExecutor-489065e.java
public GradleExecutionResult run(File gradleHome, File gradleUserHome, File projectDir, List<String> buildArgs, List<String> jvmArgs, List<URI> classpath) {
        final ByteArrayOutputStream standardOutput = new ByteArrayOutputStream();
        final ByteArrayOutputStream standardError = new ByteArrayOutputStream();
        final List<BuildTask> tasks = new ArrayList<BuildTask>();
        GradleConnector gradleConnector = buildConnector(gradleHome, gradleUserHome, projectDir);
        ProjectConnection connection = null;
        try {
            connection = gradleConnector.connect();
            DefaultBuildLauncherInternal launcher = (DefaultBuildLauncherInternal) connection.newBuild();
            launcher.setStandardOutput(standardOutput);
            launcher.setStandardError(standardError);
            launcher.addProgressListener(new TaskExecutionProgressListener(tasks));
            launcher.withArguments(buildArgs.toArray(new String[buildArgs.size()]));
            launcher.setJvmArguments(jvmArgs.toArray(new String[jvmArgs.size()]));
            launcher.withClasspath(classpath);
            launcher.run();
        } catch (BuildException t) {
            return new GradleExecutionResult(standardOutput, standardError, tasks, t);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return new GradleExecutionResult(standardOutput, standardError, tasks);
    }
=======

>>>>>>> commits-rmx_100/gradle/gradle/c77c1f5376563b5a683f245708bf8e47edb7ac1f/GradleExecutor-3e5e039.java


    GradleExecutionResult run(File gradleHome, File gradleUserHome, File projectDir, List<String> buildArgs, List<String> jvmArgs);
}

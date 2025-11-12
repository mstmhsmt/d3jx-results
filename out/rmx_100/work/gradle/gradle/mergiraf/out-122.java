/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.testkit.runner.internal;

<<<<<<< commits-rmx_100/gradle/gradle/cf5d03e9cf13a33811b66e183119bf81090812bc/GradleExecutor-489065e.java
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.TaskOutcome;
import org.gradle.tooling.BuildException;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.events.ProgressEvent;
import org.gradle.tooling.events.ProgressListener;
import org.gradle.tooling.events.task.*;
import org.gradle.tooling.internal.consumer.DefaultBuildLauncherInternal;
import org.gradle.tooling.internal.consumer.DefaultGradleConnector;

import java.io.ByteArrayOutputStream;
||||||| commits-rmx_100/gradle/gradle/abc791b0f9d17c562e86db289b826194715b4825/GradleExecutor-68c450d.java
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.TaskOutcome;
import org.gradle.tooling.BuildException;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.events.ProgressEvent;
import org.gradle.tooling.events.ProgressListener;
import org.gradle.tooling.events.task.*;
import org.gradle.tooling.internal.consumer.DefaultGradleConnector;

import java.io.ByteArrayOutputStream;
=======
>>>>>>> commits-rmx_100/gradle/gradle/c77c1f5376563b5a683f245708bf8e47edb7ac1f/GradleExecutor-3e5e039.java
import java.io.File;
<<<<<<< commits-rmx_100/gradle/gradle/cf5d03e9cf13a33811b66e183119bf81090812bc/GradleExecutor-489065e.java
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
||||||| commits-rmx_100/gradle/gradle/abc791b0f9d17c562e86db289b826194715b4825/GradleExecutor-68c450d.java
import java.util.ArrayList;
import java.util.HashMap;
=======
>>>>>>> commits-rmx_100/gradle/gradle/c77c1f5376563b5a683f245708bf8e47edb7ac1f/GradleExecutor-3e5e039.java
import java.util.List;

<<<<<<< commits-rmx_100/gradle/gradle/cf5d03e9cf13a33811b66e183119bf81090812bc/GradleExecutor-489065e.java
import static org.gradle.testkit.runner.TaskOutcome.*;

public class GradleExecutor {

    public static final String TEST_KIT_DAEMON_DIR_NAME = "test-kit-daemon";

    public GradleExecutor() {
        registerShutdownHook();
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                DefaultGradleConnector.close();
            }
        }));
    }

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

    private GradleConnector buildConnector(File gradleHome, File gradleUserHome, File projectDir) {
        DefaultGradleConnector gradleConnector = (DefaultGradleConnector) GradleConnector.newConnector();
        gradleConnector.useGradleUserHomeDir(gradleUserHome);
        gradleConnector.daemonBaseDir(new File(gradleUserHome, TEST_KIT_DAEMON_DIR_NAME));
        gradleConnector.forProjectDirectory(projectDir);
        gradleConnector.searchUpwards(false);
        gradleConnector.daemonMaxIdleTime(120, TimeUnit.SECONDS);
        gradleConnector.useInstallation(gradleHome);
        return gradleConnector;
    }

    private class TaskExecutionProgressListener implements ProgressListener {
        private final List<BuildTask> tasks;
        private final Map<String, Integer> order = new HashMap<String, Integer>();

        public TaskExecutionProgressListener(List<BuildTask> tasks) {
            this.tasks = tasks;
        }

        public void statusChanged(ProgressEvent event) {
            if (event instanceof TaskStartEvent) {
                TaskStartEvent taskStartEvent = (TaskStartEvent) event;
                order.put(taskStartEvent.getDescriptor().getTaskPath(), tasks.size());
                tasks.add(null);
            }
            if (event instanceof TaskFinishEvent) {
                TaskFinishEvent taskFinishEvent = (TaskFinishEvent) event;
                String taskPath = taskFinishEvent.getDescriptor().getTaskPath();
                TaskOperationResult result = taskFinishEvent.getResult();
                final Integer index = order.get(taskPath);
                if (index == null) {
                    throw new IllegalStateException("Received task finish event for task " + taskPath + " without first receiving task start event");
                }
                tasks.set(index, determineBuildTask(result, taskPath));
            }
        }

        private BuildTask determineBuildTask(TaskOperationResult result, String taskPath) {
            if (isFailed(result)) {
                return createBuildTask(taskPath, FAILED);
            } else if (isSkipped(result)) {
                return createBuildTask(taskPath, SKIPPED);
            } else if (isUpToDate(result)) {
                return createBuildTask(taskPath, UP_TO_DATE);
            }

            return createBuildTask(taskPath, SUCCESS);
        }

        private BuildTask createBuildTask(String taskPath, TaskOutcome outcome) {
            return new DefaultBuildTask(taskPath, outcome);
        }

        private boolean isFailed(TaskOperationResult result) {
            return result instanceof TaskFailureResult;
        }

        private boolean isSkipped(TaskOperationResult result) {
            return result instanceof TaskSkippedResult;
        }

        private boolean isUpToDate(TaskOperationResult result) {
            return result instanceof TaskSuccessResult && ((TaskSuccessResult) result).isUpToDate();
        }
    }
||||||| commits-rmx_100/gradle/gradle/abc791b0f9d17c562e86db289b826194715b4825/GradleExecutor-68c450d.java
import static org.gradle.testkit.runner.TaskOutcome.*;

public class GradleExecutor {

    public static final String TEST_KIT_DAEMON_DIR_NAME = "test-kit-daemon";

    public GradleExecutor() {
        registerShutdownHook();
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                DefaultGradleConnector.close();
            }
        }));
    }

    public GradleExecutionResult run(File gradleHome, File gradleUserHome, File projectDir, List<String> buildArgs, List<String> jvmArgs) {
        final ByteArrayOutputStream standardOutput = new ByteArrayOutputStream();
        final ByteArrayOutputStream standardError = new ByteArrayOutputStream();
        final List<BuildTask> tasks = new ArrayList<BuildTask>();

        GradleConnector gradleConnector = buildConnector(gradleHome, gradleUserHome, projectDir);
        ProjectConnection connection = null;

        try {
            connection = gradleConnector.connect();
            BuildLauncher launcher = connection.newBuild();
            launcher.setStandardOutput(standardOutput);
            launcher.setStandardError(standardError);
            launcher.addProgressListener(new TaskExecutionProgressListener(tasks));

            launcher.withArguments(buildArgs.toArray(new String[buildArgs.size()]));
            launcher.setJvmArguments(jvmArgs.toArray(new String[jvmArgs.size()]));

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

    private GradleConnector buildConnector(File gradleHome, File gradleUserHome, File projectDir) {
        DefaultGradleConnector gradleConnector = (DefaultGradleConnector) GradleConnector.newConnector();
        gradleConnector.useGradleUserHomeDir(gradleUserHome);
        gradleConnector.daemonBaseDir(new File(gradleUserHome, TEST_KIT_DAEMON_DIR_NAME));
        gradleConnector.forProjectDirectory(projectDir);
        gradleConnector.searchUpwards(false);
        gradleConnector.daemonMaxIdleTime(120, TimeUnit.SECONDS);
        gradleConnector.useInstallation(gradleHome);
        return gradleConnector;
    }

    private class TaskExecutionProgressListener implements ProgressListener {
        private final List<BuildTask> tasks;
        private final Map<String, Integer> order = new HashMap<String, Integer>();

        public TaskExecutionProgressListener(List<BuildTask> tasks) {
            this.tasks = tasks;
        }

        public void statusChanged(ProgressEvent event) {
            if (event instanceof TaskStartEvent) {
                TaskStartEvent taskStartEvent = (TaskStartEvent) event;
                order.put(taskStartEvent.getDescriptor().getTaskPath(), tasks.size());
                tasks.add(null);
            }
            if (event instanceof TaskFinishEvent) {
                TaskFinishEvent taskFinishEvent = (TaskFinishEvent) event;
                String taskPath = taskFinishEvent.getDescriptor().getTaskPath();
                TaskOperationResult result = taskFinishEvent.getResult();
                final Integer index = order.get(taskPath);
                if (index == null) {
                    throw new IllegalStateException("Received task finish event for task " + taskPath + " without first receiving task start event");
                }
                tasks.set(index, determineBuildTask(result, taskPath));
            }
        }

        private BuildTask determineBuildTask(TaskOperationResult result, String taskPath) {
            if (isFailed(result)) {
                return createBuildTask(taskPath, FAILED);
            } else if (isSkipped(result)) {
                return createBuildTask(taskPath, SKIPPED);
            } else if (isUpToDate(result)) {
                return createBuildTask(taskPath, UP_TO_DATE);
            }

            return createBuildTask(taskPath, SUCCESS);
        }

        private BuildTask createBuildTask(String taskPath, TaskOutcome outcome) {
            return new DefaultBuildTask(taskPath, outcome);
        }

        private boolean isFailed(TaskOperationResult result) {
            return result instanceof TaskFailureResult;
        }

        private boolean isSkipped(TaskOperationResult result) {
            return result instanceof TaskSkippedResult;
        }

        private boolean isUpToDate(TaskOperationResult result) {
            return result instanceof TaskSuccessResult && ((TaskSuccessResult) result).isUpToDate();
        }
    }
=======
public interface GradleExecutor {
    GradleExecutionResult run(File gradleHome, File gradleUserHome, File projectDir, List<String> buildArgs, List<String> jvmArgs);
>>>>>>> commits-rmx_100/gradle/gradle/c77c1f5376563b5a683f245708bf8e47edb7ac1f/GradleExecutor-3e5e039.java
}

package com.google.cloud.dataflow.sdk.options;

import com.google.api.services.dataflow.Dataflow;
import com.google.cloud.dataflow.sdk.annotations.Experimental;
import com.google.cloud.dataflow.sdk.util.DataflowPathValidator;
import com.google.cloud.dataflow.sdk.util.GcsStager;
import com.google.cloud.dataflow.sdk.util.InstanceBuilder;
import com.google.cloud.dataflow.sdk.util.PathValidator;
import com.google.cloud.dataflow.sdk.util.Stager;
import com.google.cloud.dataflow.sdk.util.Transport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Map;

@Description("[Internal] Options used to control execution of the Dataflow SDK for " + "debugging and testing purposes.")
@Hidden
public interface DataflowPipelineDebugOptions extends PipelineOptions {

    @Description("[Experimental] Dataflow provides a number of experimental features that can " + "be enabled with this flag. Please sync with the Dataflow team before enabling any " + "experiments.")
    @Experimental
    List<String> getExperiments();

    void setExperiments(List<String> value);

    @Description("The root URL for the Dataflow API. dataflowEndpoint can override this " + "value if it contains an absolute URL, otherwise apiRootUrl will be combined with " + "dataflowEndpoint to generate the full URL to communicate with the Dataflow API.")
    @Default.String(Dataflow.DEFAULT_ROOT_URL)
    String getApiRootUrl();

    void setApiRootUrl(String value);

    @Description("The URL for the Dataflow API. If the string contains \"://\", this" + " will be treated as the entire URL, otherwise will be treated relative to apiRootUrl.")
    @Default.String(Dataflow.DEFAULT_SERVICE_PATH)
    String getDataflowEndpoint();

    void setDataflowEndpoint(String value);

    @Description("The path to write the translated Dataflow job specification out to " + "at job submission time. The Dataflow job specification will be represented in JSON " + "format.")
    String getDataflowJobFile();

    void setDataflowJobFile(String value);

    @Description("The class of the validator that should be created and used to validate paths. " + "If pathValidator has not been set explicitly, an instance of this class will be " + "constructed and used as the path validator.")
    @Default.Class(DataflowPathValidator.class)
    Class<? extends PathValidator> getPathValidatorClass();

    void setPathValidatorClass(Class<? extends PathValidator> validatorClass);

    @JsonIgnore
    @Description("The path validator instance that should be used to validate paths. " + "If no path validator has been set explicitly, the default is to use the instance factory " + "that constructs a path validator based upon the currently set pathValidatorClass.")
    @Default.InstanceFactory(PathValidatorFactory.class)
    PathValidator getPathValidator();

    void setPathValidator(PathValidator validator);

    @Description("The class of the stager that should be created and used to stage resources. " + "If stager has not been set explicitly, an instance of the this class will be created " + "and used as the resource stager.")
    @Default.Class(GcsStager.class)
    Class<? extends Stager> getStagerClass();

    void setStagerClass(Class<? extends Stager> stagerClass);

    @JsonIgnore
    @Description("The resource stager instance that should be used to stage resources. " + "If no stager has been set explicitly, the default is to use the instance factory " + "that constructs a resource stager based upon the currently set stagerClass.")
    @Default.InstanceFactory(StagerFactory.class)
    Stager getStager();

    void setStager(Stager stager);

    @JsonIgnore
    @Description("An instance of the Dataflow client. Defaults to creating a Dataflow client " + "using the current set of options.")
    @Default.InstanceFactory(DataflowClientFactory.class)
    Dataflow getDataflowClient();

    void setDataflowClient(Dataflow value);

    public static class DataflowClientFactory implements DefaultValueFactory<Dataflow> {

        @Override
        public Dataflow create(PipelineOptions options) {
            return Transport.newDataflowClient(options.as(DataflowPipelineOptions.class)).build();
        }
    }

    @Description("Root URL for use with the Pubsub API")
    @Default.String("https://pubsub.googleapis.com")
    String getPubsubRootUrl();

    void setPubsubRootUrl(String value);

    @Description("If set, replace the existing pipeline with the name specified by --jobName with " + "this pipeline, preserving state.")
    @Deprecated
    boolean getUpdate();

    @Deprecated
    void setUpdate(boolean value);

    @JsonIgnore
    @Description("Mapping of old PTranform names to new ones, specified as JSON " + "{\"oldName\":\"newName\",...}. To mark a transform as deleted, make newName the empty " + "string.")
    Map<String, String> getTransformNameMapping();

    void setTransformNameMapping(Map<String, String> value);

    @Description("Custom windmill_main binary to use with the streaming runner")
    String getOverrideWindmillBinary();

    void setOverrideWindmillBinary(String value);

    @Description("Number of threads to use on the Dataflow worker harness. If left unspecified, " + "the Dataflow service will compute an appropriate number of threads to use.")
    int getNumberOfWorkerHarnessThreads();

    void setNumberOfWorkerHarnessThreads(int value);

    public static class PathValidatorFactory implements DefaultValueFactory<PathValidator> {

        @Override
        public PathValidator create(PipelineOptions options) {
            DataflowPipelineDebugOptions debugOptions = options.as(DataflowPipelineDebugOptions.class);
            return InstanceBuilder.ofType(PathValidator.class).fromClass(debugOptions.getPathValidatorClass()).fromFactoryMethod("fromOptions").withArg(PipelineOptions.class, options).build();
        }
    }

    public static class StagerFactory implements DefaultValueFactory<Stager> {

        @Override
        public Stager create(PipelineOptions options) {
            DataflowPipelineDebugOptions debugOptions = options.as(DataflowPipelineDebugOptions.class);
            return InstanceBuilder.ofType(Stager.class).fromClass(debugOptions.getStagerClass()).fromFactoryMethod("fromOptions").withArg(PipelineOptions.class, options).build();
        }
    }

    void setDumpHeapOnOOM(boolean dumpHeapBeforeExit);

    @Description("If {@literal true}, save a heap dump before killing a thread or process " + "which is GC thrashing or out of memory.")
    boolean getDumpHeapOnOOM();
}

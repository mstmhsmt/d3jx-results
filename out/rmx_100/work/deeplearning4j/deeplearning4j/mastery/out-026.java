package org.deeplearning4j.nn.modelimport.keras;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import lombok.extern.slf4j.Slf4j;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
@Deprecated
public class ModelConfiguration {

    private ModelConfiguration() {
    }

    @Deprecated
    static public MultiLayerConfiguration importSequentialModelConfigFromFile(String modelJsonFilename) throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {
        return KerasModelImport.importKerasSequentialConfiguration(modelJsonFilename);
    }

    @Deprecated
    static public ComputationGraphConfiguration importFunctionalApiConfigFromFile(String modelJsonFilename) throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {
        return KerasModelImport.importKerasModelConfiguration(modelJsonFilename);
    }

    static public MultiLayerConfiguration importSequentialModelConfigFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, byteArrayOutputStream);
        String configJson = new String(byteArrayOutputStream.toByteArray());
        return importSequentialModelConfig(configJson);
    }

    @Deprecated
    static public ComputationGraphConfiguration importFunctionalApiConfig(String modelJson) throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {
        KerasModel kerasModel = new KerasModel.ModelBuilder().modelJson(modelJson).buildSequential();
        return kerasModel.getComputationGraphConfiguration();
    }

    @Deprecated
    static public MultiLayerConfiguration importSequentialModelConfig(String modelJson) throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {
        KerasSequentialModel kerasModel = new KerasSequentialModel.ModelBuilder().modelJson(modelJson).buildSequential();
        return kerasModel.getMultiLayerConfiguration();
    }

    static public ComputationGraphConfiguration importFunctionalApiConfigFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, byteArrayOutputStream);
        String configJson = new String(byteArrayOutputStream.toByteArray());
        return importFunctionalApiConfig(configJson);
    }
}

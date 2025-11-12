package org.deeplearning4j.nn.modelimport.keras;
import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Routines for importing saved Keras model configurations.
 *
 * @author dave@skymind.io
 *
 * @deprecated Use {@link org.deeplearning4j.nn.modelimport.keras.KerasModel} and
 *             {@link org.deeplearning4j.nn.modelimport.keras.KerasSequentialModel} instead.
 */
@Deprecated @Slf4j public class ModelConfiguration {
  private ModelConfiguration() {
  }

  /**
     * Imports a Keras Sequential model configuration saved using call to model.to_json().
     *
     * @param modelJsonFilename    Path to text file storing Keras Sequential configuration as valid JSON.
     * @return                     DL4J MultiLayerConfiguration
     * @throws IOException
     * @deprecated Use {@link KerasModelImport#importKerasSequentialConfiguration} instead
     */
  @Deprecated public static MultiLayerConfiguration importSequentialModelConfigFromFile(String modelJsonFilename) throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {
    return KerasModelImport.importKerasSequentialConfiguration(modelJsonFilename);
  }

  /**
     * Imports a Keras Functional API model configuration saved using call to model.to_json().
     *
     * @param modelJsonFilename    Path to text file storing Keras Model configuration as valid JSON.
     * @return                     DL4J ComputationGraphConfiguration
     * @throws IOException
     * @deprecated Use {@link KerasModelImport#importKerasModelConfiguration} instead
     */
  @Deprecated public static ComputationGraphConfiguration importFunctionalApiConfigFromFile(String modelJsonFilename) throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {
    return KerasModelImport.importKerasModelConfiguration(modelJsonFilename);
  }

  /**
     * Imports a Keras Sequential model configuration saved using call to model.to_json().
     *
     * @param modelJson    String storing Keras Sequential configuration as valid JSON.
     * @return             DL4J MultiLayerConfiguration
     * @throws IOException
     * @deprecated Use {@link org.deeplearning4j.nn.modelimport.keras.KerasSequentialModel} instead
     */
  @Deprecated public static MultiLayerConfiguration importSequentialModelConfig(String modelJson) throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {
    KerasSequentialModel kerasModel = new KerasSequentialModel.ModelBuilder().modelJson(modelJson).buildSequential();
    return kerasModel.getMultiLayerConfiguration();
  }

  /**
     * Imports a Keras Functional API model configuration saved using call to model.to_json().
     *
     * @param modelJson    String storing Keras Model configuration as valid JSON.
     * @return             DL4J ComputationGraphConfiguration
     * @throws IOException
     * @deprecated Use {@link org.deeplearning4j.nn.modelimport.keras.KerasModel} instead
     */
  @Deprecated public static ComputationGraphConfiguration importFunctionalApiConfig(String modelJson) throws IOException, InvalidKerasConfigurationException, UnsupportedKerasConfigurationException {
    KerasModel kerasModel = new KerasModel.ModelBuilder().modelJson(modelJson).buildSequential();
    return kerasModel.getComputationGraphConfiguration();
  }

  /**
     * Imports a Keras Sequential model configuration saved using call to model.to_json().
     *
     * @param inputStream    The input stream to parse for json
     * @return                      DL4J MultiLayerConfiguration
     * @throws IOException
     */
  public static MultiLayerConfiguration importSequentialModelConfigFromInputStream(InputStream inputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    IOUtils.copy(inputStream, byteArrayOutputStream);
    String configJson = new String(byteArrayOutputStream.toByteArray());
    return importSequentialModelConfig(configJson);
  }

  /**
     * Imports a Keras Functional API model configuration saved using call to model.to_json().
     *
     * @param inputStream    Path to text file storing Keras configuration as valid JSON.
     * @return                      DL4J ComputationGraphConfiguration
     * @throws IOException
     */
  public static ComputationGraphConfiguration importFunctionalApiConfigFromInputStream(InputStream inputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    IOUtils.copy(inputStream, byteArrayOutputStream);
    String configJson = new String(byteArrayOutputStream.toByteArray());
    return importFunctionalApiConfig(configJson);
  }


}

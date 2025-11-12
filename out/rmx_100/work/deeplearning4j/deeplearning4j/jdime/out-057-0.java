package org.deeplearning4j.nn.layers.recurrent;
import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.gradient.DefaultGradient;
import org.deeplearning4j.nn.gradient.Gradient;
import org.deeplearning4j.nn.layers.BaseLayer;
import org.deeplearning4j.nn.params.DefaultParamInitializer;
import org.deeplearning4j.nn.params.GravesLSTMParamInitializer;
import org.deeplearning4j.util.Dropout;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;
import static org.nd4j.linalg.indexing.NDArrayIndex.interval;

/**
 *
 * LSTM layer implementation.
 * Based on Graves: Supervised Sequence Labelling with Recurrent Neural Networks
 * http://www.cs.toronto.edu/~graves/phd.pdf
 * See also for full/vectorized equations (and a comparison to other LSTM variants):
 * Greff et al. 2015, "LSTM: A Search Space Odyssey", pg11. This is the "vanilla" variant in said paper
 * http://arxiv.org/pdf/1503.04069.pdf
 *
 *
 * @author Alex Black
 */
public class GravesLSTM extends BaseLayer {
  private static final long serialVersionUID = 4115420413387754109L;

  public GravesLSTM(NeuralNetConfiguration conf) {
    super(conf);
  }

  @Override public INDArray transform(INDArray data) {
    return activate(data);
  }

  @Override public Gradient gradient() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override public Gradient calcGradient(Gradient layerError, INDArray activation) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override public Gradient backpropGradient(Gradient gradient, Layer layers) {
    INDArray[] activations = activateHelper(input(), true);
    INDArray outputActivations = activations[0];
    INDArray memCellActivations = activations[1];
    INDArray ifogZs = activations[2];
    INDArray ifogAs = activations[3];
    INDArray inputWeights = getParam(GravesLSTMParamInitializer.INPUT_WEIGHTS);
    INDArray recurrentWeights = getParam(GravesLSTMParamInitializer.RECURRENT_WEIGHTS);
    int hiddenLayerSize = recurrentWeights.rows();
    int prevLayerSize = getParam(GravesLSTMParamInitializer.INPUT_WEIGHTS).shape()[0];
    INDArray weights = getParam(DefaultParamInitializer.WEIGHT_KEY);
    INDArray epsilon = weights.mmul(gradient.getGradientFor(DefaultParamInitializer.BIAS_KEY).transpose()).transpose();
    int miniBatchSize = epsilon.size(0);
    int timeSeriesLength = (epsilon.rank() < 3 ? 1 : epsilon.size(2));
    INDArray wi = inputWeights.get(interval(0, prevLayerSize), interval(0, hiddenLayerSize));
    INDArray wI = recurrentWeights.get(interval(0, hiddenLayerSize), interval(0, hiddenLayerSize));
    INDArray wf = inputWeights.get(interval(0, prevLayerSize), interval(hiddenLayerSize, 2 * hiddenLayerSize));
    INDArray wF = recurrentWeights.get(interval(0, hiddenLayerSize), interval(hiddenLayerSize, 2 * hiddenLayerSize));
    INDArray wFF = recurrentWeights.get(interval(0, hiddenLayerSize), interval(4 * hiddenLayerSize, 4 * hiddenLayerSize + 1));
    INDArray wo = inputWeights.get(interval(0, prevLayerSize), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize));
    INDArray wO = recurrentWeights.get(interval(0, hiddenLayerSize), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize));
    INDArray wOO = recurrentWeights.get(interval(0, hiddenLayerSize), interval(4 * hiddenLayerSize + 1, 4 * hiddenLayerSize + 2));
    INDArray wg = inputWeights.get(interval(0, prevLayerSize), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize));
    INDArray wG = recurrentWeights.get(interval(0, hiddenLayerSize), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize));
    INDArray wGG = recurrentWeights.get(interval(0, hiddenLayerSize), interval(4 * hiddenLayerSize + 2, 4 * hiddenLayerSize + 3));
    INDArray biasGradients = Nd4j.zeros(new int[] { miniBatchSize, 4 * hiddenLayerSize, timeSeriesLength });
    INDArray inputWeightGradients = Nd4j.zeros(new int[] { prevLayerSize, 4 * hiddenLayerSize, timeSeriesLength });
    INDArray recurrentWeightGradients = Nd4j.zeros(new int[] { hiddenLayerSize, 4 * hiddenLayerSize + 3, timeSeriesLength });
    INDArray epsilonNext = Nd4j.zeros(miniBatchSize, prevLayerSize, timeSeriesLength);
    for (int t = timeSeriesLength - 1; t >= 0; t--) {
      INDArray prevMemCellActivations = (t == 0 ? Nd4j.zeros(hiddenLayerSize, hiddenLayerSize) : memCellActivations.slice(t - 1, 2));
      INDArray prevHiddenUnitActivation = (t == 0 ? Nd4j.zeros(hiddenLayerSize, hiddenLayerSize) : outputActivations.slice(t - 1, 2));
      INDArray deltaiNext = (t == timeSeriesLength - 1 ? Nd4j.zeros(miniBatchSize, hiddenLayerSize) : biasGradients.slice(t + 1, 2).get(new NDArrayIndex[] { interval(0, miniBatchSize), interval(0, hiddenLayerSize) }));
      INDArray deltafNext = (t == timeSeriesLength - 1 ? Nd4j.zeros(miniBatchSize, hiddenLayerSize) : biasGradients.slice(t + 1, 2).get(new NDArrayIndex[] { interval(0, miniBatchSize), interval(hiddenLayerSize, 2 * hiddenLayerSize) }));
      INDArray deltaoNext = (t == timeSeriesLength - 1 ? Nd4j.zeros(miniBatchSize, hiddenLayerSize) : biasGradients.slice(t + 1, 2).get(new NDArrayIndex[] { interval(0, miniBatchSize), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize) }));
      INDArray deltagNext = (t == timeSeriesLength - 1 ? Nd4j.zeros(miniBatchSize, hiddenLayerSize) : biasGradients.slice(t + 1, 2).get(new NDArrayIndex[] { interval(0, miniBatchSize), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize) }));
      INDArray epsilonSlice = epsilon.slice(t, 2);
      INDArray nablaOut = epsilonSlice.addi(deltaiNext.mmul(wI.transpose())).addi(deltafNext.mmul(wF.transpose())).addi(deltaoNext.mmul(wO.transpose())).addi(deltagNext.mmul(wG.transpose()));
      INDArray sigmahOfS = Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform(conf.getActivationFunction(), prevMemCellActivations.dup()));
      INDArray zo = ifogZs.slice(t, 2).get(NDArrayIndex.all(), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize));
      INDArray sigmaoPrimeOfZo = Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform("sigmoid", zo).derivative());
      INDArray deltao = nablaOut.mul(sigmahOfS).muli(sigmaoPrimeOfZo);
      INDArray sigmahPrimeOfS = Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform(conf.getActivationFunction(), prevMemCellActivations.dup()));
      INDArray nextForgetGateAs = (t == timeSeriesLength - 1 ? Nd4j.zeros(miniBatchSize, hiddenLayerSize) : ifogAs.slice(t, 2).get(NDArrayIndex.all(), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize)));
      INDArray nablaCellState = nablaOut.mul(prevHiddenUnitActivation).muli(sigmahPrimeOfS).addi(nextForgetGateAs.mul(prevMemCellActivations)).addi(deltafNext.mmul(Nd4j.diag(wFF))).addi(deltaoNext.mmul(Nd4j.diag(wOO))).addi(deltagNext.mmul(Nd4j.diag(wGG)));
      INDArray zf = ifogZs.slice(t, 0).get(NDArrayIndex.all(), interval(hiddenLayerSize, 2 * hiddenLayerSize));
      INDArray deltaf = nablaCellState.mul(prevMemCellActivations).muli(Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform("sigmoid", zf).derivative()));
      INDArray zg = ifogZs.slice(t, 0).get(NDArrayIndex.all(), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize));
      INDArray ai = ifogAs.slice(t, 0).get(NDArrayIndex.all(), interval(hiddenLayerSize, 2 * hiddenLayerSize));
      INDArray deltag = nablaCellState.mul(ai).muli(Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform("tanh", zg).derivative()));
      INDArray zi = ifogZs.slice(t, 0).get(NDArrayIndex.all(), interval(hiddenLayerSize, 2 * hiddenLayerSize));
      INDArray ag = ifogAs.slice(t, 0).get(NDArrayIndex.all(), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize));
      INDArray deltai = nablaCellState.mul(ag).muli(Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform("tanh", zi).derivative()));
      INDArray prevLayerActivationSlice = input.slice(t, 2);
      inputWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(0, hiddenLayerSize) }, deltai.transpose().mmul(prevLayerActivationSlice).transpose());
      inputWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(hiddenLayerSize, 2 * hiddenLayerSize) }, deltaf.transpose().mmul(prevLayerActivationSlice).transpose());
      inputWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize) }, deltao.transpose().mmul(prevLayerActivationSlice).transpose());
      inputWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize) }, deltag.transpose().mmul(prevLayerActivationSlice).transpose());
      if (t > 0) {
        recurrentWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(0, hiddenLayerSize) }, deltai.transpose().mmul(prevHiddenUnitActivation).transpose());
        recurrentWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(hiddenLayerSize, 2 * hiddenLayerSize) }, deltaf.transpose().mmul(prevHiddenUnitActivation).transpose());
        recurrentWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize) }, deltao.transpose().mmul(prevHiddenUnitActivation).transpose());
        recurrentWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize) }, deltag.transpose().mmul(prevHiddenUnitActivation).transpose());
        INDArray dLdwFF = deltaf.mul(prevMemCellActivations);
        recurrentWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), new NDArrayIndex(4 * hiddenLayerSize) }, dLdwFF);
        INDArray dLdwGG = deltag.mul(prevMemCellActivations);
        recurrentWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), new NDArrayIndex(4 * hiddenLayerSize + 2) }, dLdwGG);
      }
      INDArray dLdwOO = deltao.transpose().mul(memCellActivations.slice(t, 2)).transpose();
      recurrentWeightGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), new NDArrayIndex(4 * hiddenLayerSize + 1) }, dLdwOO);
      if (miniBatchSize == 1) {
        biasGradients.slice(t, 2).put(new NDArrayIndex[] { interval(0, hiddenLayerSize) }, deltai);
        biasGradients.slice(t, 2).put(new NDArrayIndex[] { interval(hiddenLayerSize, 2 * hiddenLayerSize) }, deltaf);
        biasGradients.slice(t, 2).put(new NDArrayIndex[] { interval(2 * hiddenLayerSize, 3 * hiddenLayerSize) }, deltao);
        biasGradients.slice(t, 2).put(new NDArrayIndex[] { interval(3 * hiddenLayerSize, 4 * hiddenLayerSize) }, deltag);
      } else {
        biasGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(0, hiddenLayerSize) }, deltai);
        biasGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(hiddenLayerSize, 2 * hiddenLayerSize) }, deltaf);
        biasGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize) }, deltao);
        biasGradients.slice(t, 2).put(new NDArrayIndex[] { NDArrayIndex.all(), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize) }, deltag);
      }
      INDArray epsilonNextSlice = wi.mmul(deltai.transpose()).transpose().addi(wf.mmul(deltaf.transpose()).transpose()).addi(wo.mmul(deltao.transpose()).transpose()).addi(wg.mmul(deltag.transpose()).transpose());
      epsilonNext.slice(t, 2).assign(epsilonNextSlice);
    }
    Gradient ret = new DefaultGradient();
    ret.gradientForVariable().put(GravesLSTMParamInitializer.INPUT_WEIGHTS, inputWeightGradients.sum(2));
    ret.gradientForVariable().put(GravesLSTMParamInitializer.RECURRENT_WEIGHTS, recurrentWeightGradients.sum(2));
    ret.gradientForVariable().put(GravesLSTMParamInitializer.BIAS, biasGradients.sum(2).sum(0));
    return ret;
  }




  @Override public INDArray preOutput(INDArray x) {
    return activate(x, true);
  }

  @Override public INDArray preOutput(INDArray x, boolean training) {
    return activate(x, training);
  }

  @Override public INDArray activate(INDArray input, boolean training) {
    return activateHelper(input, training)[0];
  }

  /**Returns 4 INDArrays: [outputActivations, memCellActivations, ifogZs, ifogAs] in that order.
	 * Need all 4 to do backward pass, but only care about the first one for forward pass.
	 */
  private INDArray[] activateHelper(INDArray input, boolean training) {
    INDArray recurrentWeights = getParam(GravesLSTMParamInitializer.RECURRENT_WEIGHTS);
    INDArray inputWeights = getParam(GravesLSTMParamInitializer.INPUT_WEIGHTS);
    INDArray biases = getParam(GravesLSTMParamInitializer.BIAS);
    int[] dataShape = input.shape();
    boolean is2dInput = dataShape.length < 3;
    int timeSeriesLength = (is2dInput ? 1 : dataShape[2]);
    int hiddenLayerSize = recurrentWeights.rows();
    int miniBatchSize = dataShape[0];
    int nIn = inputWeights.shape()[0];
    if (conf.isUseDropConnect() && training) {
      if (conf.getDropOut() > 0) {
        inputWeights = Dropout.applyDropConnect(this, GravesLSTMParamInitializer.RECURRENT_WEIGHTS);
      }
    }
    INDArray wi = inputWeights.get(interval(0, nIn), interval(0, hiddenLayerSize));
    INDArray wI = recurrentWeights.get(interval(0, hiddenLayerSize), interval(0, hiddenLayerSize));
    INDArray bi = biases.get(interval(0, hiddenLayerSize));
    INDArray wf = inputWeights.get(interval(0, nIn), interval(hiddenLayerSize, 2 * hiddenLayerSize));
    INDArray wF = recurrentWeights.get(interval(0, hiddenLayerSize), interval(hiddenLayerSize, 2 * hiddenLayerSize));
    INDArray wFF = recurrentWeights.get(interval(0, hiddenLayerSize), interval(4 * hiddenLayerSize, 4 * hiddenLayerSize + 1));
    INDArray bf = biases.get(interval(hiddenLayerSize, 2 * hiddenLayerSize));
    INDArray wo = inputWeights.get(interval(0, nIn), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize));
    INDArray wO = recurrentWeights.get(interval(0, hiddenLayerSize), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize));
    INDArray wOO = recurrentWeights.get(interval(0, hiddenLayerSize), interval(4 * hiddenLayerSize + 1, 4 * hiddenLayerSize + 2));
    INDArray bo = biases.get(interval(2 * hiddenLayerSize, 3 * hiddenLayerSize));
    INDArray wg = inputWeights.get(interval(0, nIn), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize));
    INDArray wG = recurrentWeights.get(interval(0, hiddenLayerSize), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize));
    INDArray wGG = recurrentWeights.get(interval(0, hiddenLayerSize), interval(4 * hiddenLayerSize + 2, 4 * hiddenLayerSize + 3));
    INDArray bg = biases.get(interval(3 * hiddenLayerSize, 4 * hiddenLayerSize));
    INDArray outputActivations = Nd4j.zeros(new int[] { miniBatchSize, hiddenLayerSize, timeSeriesLength });
    INDArray ifogZ = Nd4j.zeros(new int[] { miniBatchSize, 4 * hiddenLayerSize, timeSeriesLength });
    INDArray ifogA = Nd4j.zeros(new int[] { miniBatchSize, 4 * hiddenLayerSize, timeSeriesLength });
    INDArray memCellActivations = Nd4j.zeros(new int[] { miniBatchSize, hiddenLayerSize, timeSeriesLength });
    for (int t = 0; t < timeSeriesLength; t++) {
      INDArray miniBatchData = (is2dInput ? input : input.slice(t, 2));
      INDArray prevOutputActivations = (t == 0 ? Nd4j.zeros(new int[] { miniBatchSize, hiddenLayerSize }) : outputActivations.slice(t - 1, 2));
      INDArray prevMemCellActivations = (t == 0 ? Nd4j.zeros(new int[] { miniBatchSize, hiddenLayerSize }) : memCellActivations.slice(t - 1, 2));
      INDArray inputActivations = miniBatchData.mmul(wi).addi(prevOutputActivations.mmul(wI)).addiRowVector(bi);
      NDArrayIndex[] iIndexes = (miniBatchSize == 1 ? new NDArrayIndex[] { interval(0, hiddenLayerSize) } : new NDArrayIndex[] { NDArrayIndex.all(), interval(0, hiddenLayerSize) });
      ifogZ.slice(t, 2).put(iIndexes, inputActivations);
      ifogA.slice(t, 2).put(iIndexes, Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform(conf.getActivationFunction(), inputActivations)));
      INDArray forgetGateActivations = miniBatchData.mmul(wf).addi(prevOutputActivations.mmul(wF)).addi(prevMemCellActivations.mmul(Nd4j.diag(wFF))).addiRowVector(bf);
      NDArrayIndex[] fIndexes = (miniBatchSize == 1 ? new NDArrayIndex[] { interval(hiddenLayerSize, 2 * hiddenLayerSize) } : new NDArrayIndex[] { NDArrayIndex.all(), interval(hiddenLayerSize, 2 * hiddenLayerSize) });
      ifogZ.slice(t, 2).put(fIndexes, forgetGateActivations);
      ifogA.slice(t, 2).put(fIndexes, Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform("sigmoid", forgetGateActivations)));
      INDArray inputModGateActivations = miniBatchData.mmul(wg).addi(prevOutputActivations.mmul(wG)).addi(prevMemCellActivations.mmul(Nd4j.diag(wGG))).addiRowVector(bg);
      NDArrayIndex[] gIndexes = (miniBatchSize == 1 ? new NDArrayIndex[] { interval(3 * hiddenLayerSize, 4 * hiddenLayerSize) } : new NDArrayIndex[] { NDArrayIndex.all(), interval(3 * hiddenLayerSize, 4 * hiddenLayerSize) });
      ifogZ.slice(t, 2).put(gIndexes, inputModGateActivations);
      ifogA.slice(t, 2).put(gIndexes, Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform("sigmoid", inputModGateActivations)));
      INDArray currentMemoryCellActivations = forgetGateActivations.mul(prevMemCellActivations).addi(inputModGateActivations.mul(inputActivations));
      currentMemoryCellActivations = Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform(conf.getActivationFunction(), currentMemoryCellActivations));
      INDArray outputGateActivations = miniBatchData.mmul(wo).addi(prevOutputActivations.mmul(wO)).addi(currentMemoryCellActivations.mmul(Nd4j.diag(wOO))).addiRowVector(bo);
      NDArrayIndex[] oIndexes = (miniBatchSize == 1 ? new NDArrayIndex[] { interval(2 * hiddenLayerSize, 3 * hiddenLayerSize) } : new NDArrayIndex[] { NDArrayIndex.all(), interval(2 * hiddenLayerSize, 3 * hiddenLayerSize) });
      ifogZ.slice(t, 2).put(oIndexes, outputGateActivations);
      ifogA.slice(t, 2).put(oIndexes, Nd4j.getExecutioner().execAndReturn(Nd4j.getOpFactory().createTransform("sigmoid", outputGateActivations)));
      INDArray currHiddenUnitActivations = outputGateActivations.mul(currentMemoryCellActivations);
      outputActivations.slice(t, 2).assign(currHiddenUnitActivations);
      memCellActivations.slice(t, 2).assign(currentMemoryCellActivations);
    }
    if (is2dInput) {
      int[] shape = outputActivations.shape();
      outputActivations = outputActivations.reshape(shape[0], shape[1]);
    }
    return new INDArray[] { outputActivations, memCellActivations, ifogZ, ifogA };
  }

  @Override public INDArray activationMean() {
    return activate();
  }

  @Override public Type type() {
    return Type.RECURRENT;
  }

  @Override public Layer transpose() {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}

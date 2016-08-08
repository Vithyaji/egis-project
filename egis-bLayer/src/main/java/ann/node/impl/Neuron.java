package ann.node.impl;

import java.util.LinkedList;
import java.util.List;

import ann.node.NeuralNode;
import ann.function.Function;
import java.util.Random;

public class Neuron implements NeuralNode {

    private static double learningRate;
    private static Function function;
    @SuppressWarnings("unused")
	private String neuronId;
    private List<Double> inputWeights = new LinkedList<Double>();
    private List<NeuralNode> inputNeurons = new LinkedList<NeuralNode>();
    private double output;
    private double errorSignal = 0.0;

    public Neuron(final String neuronIdParam) {
        this.neuronId = neuronIdParam;
    }

    @Override
    public void addInputNeuron(final NeuralNode neuronParam) {
        inputNeurons.add(neuronParam);
        Random rand = new Random();
        inputWeights.add((double) rand.nextFloat());
    }

    @Override
    public double getOutput() {
        return this.output;
    }

    @Override
    public void setOutputValue(double value) {
        this.output = value;
    }

    @Override
    public double getNetworkOutput() {
        this.output = 0.0;
        for (int i = 0; i < inputNeurons.size(); i++) {
            output += inputNeurons.get(i).getNetworkOutput() * inputWeights.get(i);
        }
        this.setOutputValue(function.performActivationFunction(output));
        return this.output;
    }

    @Override
    public void propagateErrorSignal(final double errorSignalParam) {
        this.errorSignal += errorSignalParam;
        for (int i = 0; i < inputNeurons.size(); i++) {
            inputNeurons.get(i).propagateErrorSignal(inputWeights.get(i) * this.errorSignal);
        }
    }

    @Override
    public void adjustWeightsForError() {
        for (int i = 0; i < inputWeights.size(); i++) {
            Double newWeight =
                    (inputWeights.get(i) + (Neuron.learningRate * this.errorSignal * function.performDerivativeFunction(this.output) * inputNeurons.get(i).getOutput()));
            inputWeights.set(i, newWeight);
        }
        for (int i = 0; i < inputNeurons.size(); i++) {
            inputNeurons.get(i).adjustWeightsForError();
        }
    }

    @Override
    public void resetErrorSignal() {
        this.errorSignal = 0.0;
        for (int i = 0; i < inputNeurons.size(); i++) {
            inputNeurons.get(i).resetErrorSignal();
        }
    }

    public static void setActivationFunction(final Function functionParam) {
        Neuron.function = functionParam;
    }

    public static final void setLearningRate(final double learningRateParam) {
        Neuron.learningRate = learningRateParam;
    }
}

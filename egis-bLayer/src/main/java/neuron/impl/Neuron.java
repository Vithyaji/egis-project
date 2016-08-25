package neuron.impl;

import java.util.LinkedList;
import java.util.List;

import neuron.NeuralNode;
import function.Function;
import java.util.Random;

public class Neuron implements NeuralNode {

    private double learningRate;
    private Function function;
    private String neuronId;
    private List<Double> inputWeights = new LinkedList<Double>();
    private List<NeuralNode> inputNeurons = new LinkedList<NeuralNode>();
    private double output;
    private double errorSignal = 0.0;

    public Neuron(final String neuronIdParam, Function functionParam, double learningRateParam) {
        this.neuronId = neuronIdParam;
        this.function = functionParam;
        this.learningRate = learningRateParam;
    }

    @Override
    public void addInputNeuron(final NeuralNode neuronParam) {
        inputNeurons.add(neuronParam);
        Random rand = new Random();
        inputWeights.add((double) rand.nextFloat());
    }

    @Override
    public void addInputNeuron(NeuralNode neuronParam, double weightParam) {
        inputNeurons.add(neuronParam);
        inputWeights.add(weightParam);
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
                    (inputWeights.get(i) + (this.learningRate * this.errorSignal * function.performDerivativeFunction(this.output) * inputNeurons.get(i).getOutput()));
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

    @Override
    public List<Double> getWeights() {
        return inputWeights;
    }
}

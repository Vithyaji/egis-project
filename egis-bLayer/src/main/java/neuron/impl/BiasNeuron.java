package neuron.impl;

import java.util.ArrayList;
import java.util.List;
import neuron.NeuralNode;

public class BiasNeuron implements NeuralNode {

    public double getOutput() {
        return 1;
    }

    public void addInputNeuron(NeuralNode neuron) {
        return;
    }

    public void addInputNeuron(NeuralNode neuron, double weight) {
        return;
    }

    public void setOutputValue(double value) {
        return;
    }

    public double getNetworkOutput() {
        return getOutput();
    }

    public void propagateErrorSignal(double errorSignal) {
        return;
    }

    public void adjustWeightsForError() {
        return;
    }

    public void resetErrorSignal() {
        return;
    }

    @Override
    public List<Double> getWeights(){
        return new ArrayList<Double>();
    }
}

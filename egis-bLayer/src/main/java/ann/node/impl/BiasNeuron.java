package ann.node.impl;

import ann.node.NeuralNode;

public class BiasNeuron implements NeuralNode {

    public double getOutput() {
        return 1;
    }

    public void addInputNeuron(NeuralNode neuron) {
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
}

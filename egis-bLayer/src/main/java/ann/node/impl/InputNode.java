package ann.node.impl;

import ann.node.NeuralNode;

public class InputNode implements NeuralNode {

    private double value;

    public double getValue() {
        return value;
    }

    @Override
    public void addInputNeuron(final NeuralNode neuronParam) {
        return;
    }

    @Override
    public double getOutput() {
        return this.getValue();
    }

    @Override
    public void setOutputValue(double valueParam) {
        this.value = valueParam;
    }

    @Override
    public double getNetworkOutput() {
        return this.getValue();
    }

    @Override
    public void propagateErrorSignal(final double errorSignal) {
        return;
    }

    @Override
    public void adjustWeightsForError() {
        return;
    }

    @Override
    public void resetErrorSignal() {
        return;
    }
}

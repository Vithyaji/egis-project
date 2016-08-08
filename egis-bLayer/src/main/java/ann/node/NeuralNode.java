package ann.node;

public interface NeuralNode {

    public double getOutput();

    public void addInputNeuron(final NeuralNode neuron);

    public void setOutputValue(double value);

    public double getNetworkOutput();

    public void propagateErrorSignal(double errorSignal);

    public void adjustWeightsForError();

    public void resetErrorSignal();
}

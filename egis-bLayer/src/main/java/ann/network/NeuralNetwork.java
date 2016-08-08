package ann.network;

import ann.function.Function;
import java.util.LinkedList;
import ann.node.NeuralNode;
import ann.node.impl.BiasNeuron;
import ann.node.impl.InputNode;
import ann.node.impl.Neuron;

public class NeuralNetwork {

    private LinkedList<LinkedList<NeuralNode>> network = new LinkedList<LinkedList<NeuralNode>>();

    public NeuralNetwork(int[] layout, Function function, double learningRate) {

        Neuron.setActivationFunction(function);
        Neuron.setLearningRate(learningRate);

        for (int i = 0; i < layout.length; i++) {
            LinkedList<NeuralNode> layer = new LinkedList<NeuralNode>();
            for (int j = 0; j < layout[i]; j++) {
                if (i == 0) {
                    layer.add(new InputNode());
                } else {
                    layer.add(new Neuron("[" + i + ":" + j + "]"));
                }
            }
            //Add one bias neuron to each layer except the output layer
            if (i != (layout.length - 1)) {
                layer.add(new BiasNeuron());
            }
            network.add(layer);
        }
        connectNeurons();
    }

    public double[] getOutputArray(double[] inputArray) {
        setInputArray(inputArray);
        int outputArraySize = network.getLast().size();
        double[] output = new double[outputArraySize];
        for (int i = 0; i < outputArraySize; i++) {
            output[i] = network.getLast().get(i).getNetworkOutput();
        }
        return output;
    }

    public void train(double[] input, double[] expectedOutput) {

        double[] output = getOutputArray(input);
        for (int j = 0; j < output.length; j++) {
            double error = expectedOutput[j] - output[j];
            network.getLast().get(j).propagateErrorSignal(error);
        }

        for (int j = 0; j < output.length; j++) {
            network.getLast().get(j).adjustWeightsForError();
        }

        for (int j = 0; j < output.length; j++) {
            network.getLast().get(j).resetErrorSignal();
        }
    }

    private void setInputArray(double[] inputArray) {
        //Iterate for size - 1 due to the presence of a bias neuron
        for (int i = 0; i < (network.getFirst().size() - 1); i++) {
            network.getFirst().get(i).setOutputValue(inputArray[i]);
        }
    }

    private void connectNeurons() {
        for (int i = 0; i < network.size(); i++) {
            for (int j = 0; j < network.get(i).size(); j++) {
                if (i != (network.size() - 1)) {
                    for (int k = 0; k < network.get(i + 1).size(); k++) {
                        network.get(i + 1).get(k).addInputNeuron(network.get(i).get(j));
                    }
                }
            }
        }
    }
}

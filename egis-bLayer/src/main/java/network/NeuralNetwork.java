package network;

import java.io.IOException;
import java.util.LinkedList;

import neuron.NeuralNode;
import neuron.impl.BiasNeuron;
import neuron.impl.InputNeuron;
import neuron.impl.Neuron;
import function.Function;
import function.FunctionFactory;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class NeuralNetwork {

    private static final String FILE_EXT = ".nn";
    private static final String ACTIVATION_FUNCTION = "activationFunction";
    private static final String LEARNING_RATE = "learningRate";
    private static final String LAYOUT = "layout";
    private static final String WEIGHTS = "weights";
    private static final String STRING_SEPERATOR = ",";
    private LinkedList<LinkedList<NeuralNode>> network = new LinkedList<LinkedList<NeuralNode>>();
    private Function function;
    private double learningRate;

    /**
     * Constructor to create a new Neural Network.
     * @param layout
     *          An array which defines the number of neurons required per layer
     *          (Ex: [2, 3, 1] specifies that the network should have 2 inputArrayParam neurons, 3 hidden neurons and 1 output neuron)
     * @param functionParam
     *          The activation function for the neurons
     *          (Ex: "Sigmoid", "Tanh")
     * @param learningRateParam
     *          The rate at which the neuron weightsParam are adjusted per iteration
     */
    public NeuralNetwork(final int[] layout, final String functionParam, final double learningRateParam) {
        this.function = FunctionFactory.getFunction(functionParam);
        this.learningRate = learningRateParam;
        createNueralLayout(layout);
        connectNeurons();
    }

    /**
     * Constructor to load a Neural Network from a .nn file.
     * @param fileNameParam
     *          The name of the file which is to be loaded, file extension is not required
     */
    public NeuralNetwork(final String fileNameParam) {

        Properties file = new Properties();
        try {
            file.load(new FileInputStream(fileNameParam + FILE_EXT));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        int[] layout = getIntArray(file.getProperty(LAYOUT).split(STRING_SEPERATOR));
        double[] weights = getDoubleArray(file.getProperty(WEIGHTS).split(STRING_SEPERATOR));
        this.function = FunctionFactory.getFunction(file.getProperty(ACTIVATION_FUNCTION));
        this.learningRate = Double.parseDouble(file.getProperty(LEARNING_RATE));

        createNueralLayout(layout);

        connectNeurons(weights);
    }

    /**
     * Returns the output of the neural  network for each output neuron based on the inputArrayParam set provided.
     * @param inputArrayParam
     *          Input vales for the network
     * @return
     *          Output values of the network
     */
    public double[] getOutputArray(final double[] inputArrayParam) {
        setInputArray(inputArrayParam);
        int outputArraySize = network.getLast().size();
        double[] output = new double[outputArraySize];
        for (int i = 0; i < outputArraySize; i++) {
            output[i] = network.getLast().get(i).getNetworkOutput();
        }
        return output;
    }

    /**
     * Runs a single training iteration on the neural network based on the parameters provided
     * @param inputArrayParam
     *          Input vales for the network
     * @param expectedOutputArrayParam
     *          Ideal output values of the network
     */
    public void train(final double[] inputArrayParam, final double[] expectedOutputArrayParam) {

        double[] output = getOutputArray(inputArrayParam);
        for (int j = 0; j < output.length; j++) {
            double error = expectedOutputArrayParam[j] - output[j];
            System.out.println("Error: "+error);
            network.getLast().get(j).propagateErrorSignal(error);
        }

        for (int j = 0; j < output.length; j++) {
            network.getLast().get(j).adjustWeightsForError();
        }

        for (int j = 0; j < output.length; j++) {
            network.getLast().get(j).resetErrorSignal();
        }
    }

    /**
     * Runs a number of training iterations on the network based on the parameters provided
     * @param inputArrayParam
     *          An array of training sets where one training set is a double[]
     * @param expectedOutputArrayParam
     *          An array of ideal output sets where one output set is a double[]
     * @param epochsParam
     *          The number of training iterations to perform
     */
    public void train(final double[][] inputSetArrayParam, final double[][] expectedOutputSetArrayParam, final int epochsParam) {
        if (inputSetArrayParam.length != expectedOutputSetArrayParam.length) {
            System.out.println("Inconsistant training sets. Training aborted!");
        } else {
            for (int i = 0; i < epochsParam; i++) {
                for (int j = 0; j < inputSetArrayParam.length; j++) {
                    for (int k = 0; k < (epochsParam / 10); k++) {
                        train(inputSetArrayParam[j], expectedOutputSetArrayParam[j]);
                    }
                }
            }
        }
    }

    /**
     * Saves the current state of the network to a file with the extension .nn
     * @param fileNameParam
     *          Name of the file to be created, file extension is not required
     */
    public void saveToFile(String fileNameParam) {
        int layers = network.size();
        String nodesPerLayer = "";
        String weights = "";
        Properties file = new Properties();

        for (int i = 0; i < layers; i++) {
            if (i == (layers - 1)) {
                nodesPerLayer += network.get(i).size() + STRING_SEPERATOR;
            } else {
                nodesPerLayer += (network.get(i).size() - 1) + STRING_SEPERATOR;
            }

            if (i != 0) {
                for (int j = 0; j < network.get(i).size(); j++) {
                    for (int k = 0; k < network.get(i).get(j).getWeights().size(); k++) {
                        weights += network.get(i).get(j).getWeights().get(k) + STRING_SEPERATOR;
                    }
                }
            }
        }
        file.setProperty(ACTIVATION_FUNCTION, this.function.getFunctionName());
        file.setProperty(LEARNING_RATE, String.valueOf(this.learningRate));
        file.setProperty(LAYOUT, nodesPerLayer.substring(0, nodesPerLayer.length() - 1));
        file.setProperty(WEIGHTS, weights.substring(0, weights.length() - 1));

        try {
            file.store(new FileOutputStream(fileNameParam + FILE_EXT), null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setInputArray(final double[] inputArrayParam) {
        //Iterate for size - 1 due to the presence of a bias neuron
        for (int i = 0; i < (network.getFirst().size() - 1); i++) {
            network.getFirst().get(i).setOutputValue(inputArrayParam[i]);
        }
    }

    private void connectNeurons() {
        for (int i = 0; i < network.size(); i++) {
            if (i != (network.size() - 1)) {
                for (int j = 0; j < network.get(i + 1).size(); j++) {
                    for (int k = 0; k < network.get(i).size(); k++) {
                        if ((j != (network.get(i + 1).size() - 1)) || (i == (network.size() - 2))) {
                            network.get(i + 1).get(j).addInputNeuron(network.get(i).get(k));
                        }
                    }
                }
            }
        }
    }

    private void connectNeurons(double[] weightsParam) {
        int weightIndex = 0;
        for (int i = 0; i < network.size(); i++) {
            if (i != (network.size() - 1)) {
                for (int j = 0; j < network.get(i + 1).size(); j++) {
                    for (int k = 0; k < network.get(i).size(); k++) {
                        if ((j != (network.get(i + 1).size() - 1)) || (i == (network.size() - 2))) {
                            network.get(i + 1).get(j).addInputNeuron(network.get(i).get(k), weightsParam[weightIndex]);
                            weightIndex++;
                        }
                    }
                }
            }
        }
    }

    private double[] getDoubleArray(String[] stringArrayParam) {

        double[] arrDouble = new double[stringArrayParam.length];
        for (int i = 0; i < stringArrayParam.length; i++) {
            arrDouble[i] = Double.parseDouble(stringArrayParam[i]);
        }
        return arrDouble;
    }

    private int[] getIntArray(String[] stringArrayParam) {

        int[] arrInt = new int[stringArrayParam.length];
        for (int i = 0; i < stringArrayParam.length; i++) {
            arrInt[i] = Integer.parseInt(stringArrayParam[i]);
        }
        return arrInt;
    }

    private void createNueralLayout(int[] layoutParam) {
        for (int i = 0; i < layoutParam.length; i++) {
            LinkedList<NeuralNode> layer = new LinkedList<NeuralNode>();
            for (int j = 0; j < layoutParam[i]; j++) {
                if (i == 0) {
                    layer.add(new InputNeuron());
                } else {
                    layer.add(new Neuron("[" + i + ":" + j + "]", this.function, this.learningRate));
                }
            }
            //Add one bias neuron to each layer except the output layer
            if (i != (layoutParam.length - 1)) {
                layer.add(new BiasNeuron());
            }
            network.add(layer);
        }
    }
}

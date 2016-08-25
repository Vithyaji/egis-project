package core;

import function.impl.TanhFunction;
import network.NeuralNetwork;

public class EgisNNManager {
	
	private int[] networkLayout = {48, 40, 3};
    private NeuralNetwork EgisNetwork = new NeuralNetwork(networkLayout, "Tanh" , 0.1);
	
    
    private static EgisNNManager instance;
	private EgisNNManager(){}
	
	public static EgisNNManager getInstance(){
		if(instance==null){
			instance = new EgisNNManager();
		}
		return instance;
		
		
	}
	
	public double[] getOutput(double[] nnInputs) {		
		double[] outputValue = EgisNetwork.getOutputArray(nnInputs);	
		return outputValue;
		
	}
	
	
	public void trainEgisNN(double[] nnInputs, double[] nnOutputs){
		for (int j = 0; j < 10000; j++) {
            EgisNetwork.train(nnInputs, nnOutputs);
        }
		EgisNetwork.saveToFile("C:\\Project\\nn-weights\\nnDetails");
	}

}

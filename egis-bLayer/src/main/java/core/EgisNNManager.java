package core;

import network.NeuralNetwork;

public class EgisNNManager {
	
	private NeuralNetwork raceNetwork = null;
	private NeuralNetwork genderNetwork = null;
	
    private static EgisNNManager instance;
	private EgisNNManager(){}
	
	public static EgisNNManager getInstance(){
		if(instance==null){
			instance = new EgisNNManager();
		}
		return instance;
		
		
	}
	
	public double[] getRaceOutput(double[] nnInputs) {	
		
		String raceNNfile = Utils.loadPropfromFile("nnLocation.properties").getProperty("race-file");
		raceNetwork = new NeuralNetwork(raceNNfile);
		double[] outputValue = raceNetwork.getOutputArray(nnInputs);
		for(int i=0;i<outputValue.length;i++){
			if(outputValue[i]<0){
				outputValue[i] =  Math.abs(outputValue[i]);
			}
		}
		return outputValue;
		
	}
	
	public double[] getGenderOutput(double[] nnInputs) {	
	
		String genderNNfile = Utils.loadPropfromFile("nnLocation.properties").getProperty("gender-file");
		genderNetwork = new NeuralNetwork(genderNNfile);
		double[] outputValue = genderNetwork.getOutputArray(nnInputs);
		for(int i=0;i<outputValue.length;i++){
			if(outputValue[i]<0){
				outputValue[i] =  Math.abs(outputValue[i]);
			}
		}
		return outputValue;
		
	}
	
	
	public void trainRaceNetwork(double[][] nnInputs, double[][] nnOutputs, String nnSaveLocation) {
		
		int[] networkLayout = { 48, 7, 1 };
		raceNetwork = new NeuralNetwork(networkLayout,"Tanh", 0.1);
		
		if (nnInputs.length == nnOutputs.length) {
			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < nnInputs.length; j++) {
					for (int k = 0; k < 10; k++) {
						raceNetwork.train(nnInputs[j], nnOutputs[j]);
					}
				}
			}
		}
		raceNetwork.saveToFile(nnSaveLocation+"-race"+Utils.getCurrentTime());
	}
	
	public void trainGenderNetwork(double[][] nnInputs, double[][] nnOutputs, String nnSaveLocation) {
		
		int[] networkLayout = { 3, 2, 1 };
		genderNetwork = new NeuralNetwork(networkLayout,"Tanh", 0.1);
		
		if (nnInputs.length == nnOutputs.length) {
			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < nnInputs.length; j++) {
					for (int k = 0; k < 10; k++) {
						genderNetwork.train(nnInputs[j], nnOutputs[j]);
					}
				}
			}
		}
		genderNetwork.saveToFile(nnSaveLocation+"-gender"+Utils.getCurrentTime());
	}
	
}

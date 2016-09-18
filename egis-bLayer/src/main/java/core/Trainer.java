package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Trainer {
	
	private static final Logger LOGGER = Logger.getLogger(Trainer.class);
	
	public void train(String fileLocation, String nnSaveLocation){
		
		File trainingDataSetFile = new File(fileLocation);
		
		try { 
            Scanner scanner = new Scanner(trainingDataSetFile);
    		ArrayList<String> inputsFromFile = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                inputsFromFile.add(scanner.nextLine());
            }
            scanner.close();
            
    		double[][] expectedRaceOutputs = new double[inputsFromFile.size()][1];
    		double[][] expectedGenderOutputs = new double[inputsFromFile.size()][1];
    		double[][] raceTraingInputs = new double[inputsFromFile.size()][48];
    		double[][] genderTraingInputs = new double[inputsFromFile.size()][3];
            
    		ImageDetector id = new ImageDetector();
    		EgisNNManager egisManager = EgisNNManager.getInstance();
    		
    		int i=0;
            for(String currentLine: inputsFromFile){
            	
            	
            	String[] inputValues = currentLine.split(",");
            	
            	LOGGER.debug("File Location " + inputValues[0]);
            	LOGGER.debug("Expected Race " + inputValues[1]);
            	LOGGER.debug("Expected Gender " + inputValues[2]);
            	
            	expectedRaceOutputs[i][0] = Double.parseDouble(inputValues[1]);
            	expectedGenderOutputs[i][0] = Double.parseDouble(inputValues[2]);
            	
            	
    			double[] inputs = id.identifyNNInputs(inputValues[0]);
    			if (inputs != null) {
    				for (int j = 0; j < 3; j++) {
    					genderTraingInputs[i][j]=inputs[j];
    				}
    				
    				for (int j = 0; j < inputs.length; j++) {
    					raceTraingInputs[i][j]=inputs[j];
    					
    				}
    			}
    			i++;
            }
            LOGGER.debug("Training...");
    		egisManager.trainRaceNetwork(raceTraingInputs, expectedRaceOutputs, nnSaveLocation);
    		egisManager.trainGenderNetwork(genderTraingInputs, expectedGenderOutputs, nnSaveLocation);
    		
            
            
        } catch (FileNotFoundException e) {
        	LOGGER.error(e.getMessage());
        }
		
		
		
	}

}

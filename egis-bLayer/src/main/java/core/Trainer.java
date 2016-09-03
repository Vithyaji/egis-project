package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trainer {
	
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
            	
            	System.out.println("File Location " + inputValues[0]);
            	System.out.println("Expected Race " + inputValues[1]);
            	System.out.println("Expected Gender " + inputValues[2]);
            	
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
            System.out.println("Training...");
    		egisManager.trainRaceNetwork(raceTraingInputs, expectedRaceOutputs, nnSaveLocation);
    		egisManager.trainGenderNetwork(genderTraingInputs, expectedGenderOutputs, nnSaveLocation);
    		
            
            
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
		
		
		
	}

}

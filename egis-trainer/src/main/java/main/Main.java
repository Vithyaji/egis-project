package main;

import org.apache.log4j.PropertyConfigurator;

import core.Trainer;
import core.Utils;

public class Main {

	private static final String LOG4J_PROP = "./config/log4j.properties";
	
	public static void main(String[] args) {
		
		PropertyConfigurator.configure(System.getProperty("trainer-log",LOG4J_PROP));
		
		Trainer trainer = new Trainer();
		String inputsFileLocation = Utils.loadPropfromFile("trainDetails.properties").getProperty("training-inputs");
		String saveLocation = Utils.loadPropfromFile("trainDetails.properties").getProperty("nn-save");
		trainer.train(inputsFileLocation,saveLocation);
	}

}

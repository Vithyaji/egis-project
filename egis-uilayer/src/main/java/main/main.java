package main;

import core.Trainer;
import core.Utils;

public class main {

	public static void main(String[] args) {
		Trainer trainer = new Trainer();
		String inputsFileLocation = Utils.loadPropfromFile("trainDetails.properties").getProperty("training-inputs");
		String saveLocation = Utils.loadPropfromFile("trainDetails.properties").getProperty("nn-save");
		trainer.train(inputsFileLocation,saveLocation);
	}

}

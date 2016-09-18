package core;

import org.apache.log4j.Logger;



public class Identifier {
	
	private static final Logger LOGGER = Logger.getLogger(Identifier.class);
	
	
	public String[] identify(String fileLocation){
		
		
		
		ImageDetector id = new ImageDetector();
		EgisNNManager egisManager = EgisNNManager.getInstance();
		String[] rng = {"cannot determined","cannot determined"};
		
		double[] inputs = id.identifyNNInputs(fileLocation);
		if (inputs != null) {
			
			double[] genderInput = new double[3];
			double[] raceInput = new double[48];
			
			for (int j = 0; j < 3; j++) {
				genderInput[j]=inputs[j];
			}
			
			
			for (int j = 0; j < inputs.length; j++) {
				raceInput[j]=inputs[j];
			}
			
			double[] raceOutputArray = egisManager.getRaceOutput(raceInput);
			double[] genderOutputArray = egisManager.getGenderOutput(genderInput);
			
			LOGGER.debug("Race Output 0 :" + raceOutputArray[0]);
			LOGGER.debug("Gender Output :" + genderOutputArray[0]);
			
			rng[0] = getRace(raceOutputArray);
			rng[1] = getGender(genderOutputArray);
			
		} else {
			LOGGER.debug("Could not detect face");
		}
		
		return rng;
	}
	
	private String getRace(double[] nnOutput) {

		String race = "unknown";

		if (nnOutput[0] > 0.8) {
			race = "Caucasian";
		} else if (nnOutput[0] > 0.3 && nnOutput[0] < 0.6 ) {
			race = "Mangolian";
		} else if (nnOutput[0] < 0.8 && nnOutput[0] > 0.6) {
			race = "Anglo Asian";
		} else if (nnOutput[0] < 0.3) {
			race = "African";
		}

		return race;
	}

	private String getGender(double[] nnOutput) {

		String gender = "unknown";

		if (nnOutput[0] > 0.8) {
			gender = "Female";
		} else if (nnOutput[0] < 0.2) {
			gender = "Male";
		}

		return gender;
	}

}

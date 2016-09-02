package core;

public class Trainer {
	
	public void train(){
		ImageDetector id = new ImageDetector();
		EgisNNManager egisManager = EgisNNManager.getInstance();
		double[][] expectedRaceOutputs = new double[16][1];
		double[][] expectedGenderOutputs = new double[16][1];
		
		double[][] raceTraingInputs = new double[16][48];
		double[][] genderTraingInputs = new double[16][3];
		
		expectedRaceOutputs[0][0] = 0;
		expectedRaceOutputs[1][0] = 0;
		expectedRaceOutputs[2][0] = 0;
		expectedRaceOutputs[3][0] = 0;
		expectedRaceOutputs[4][0] = 1;
		expectedRaceOutputs[5][0] = 1;
		expectedRaceOutputs[6][0] = 1;
		expectedRaceOutputs[7][0] = 1;
		expectedRaceOutputs[8][0] = 0.66;
		expectedRaceOutputs[9][0] = 0.66;
		expectedRaceOutputs[10][0] = 0.66;
		expectedRaceOutputs[11][0] = 0.66;
		expectedRaceOutputs[12][0] = 0.33;
		expectedRaceOutputs[13][0] = 0.33;
		expectedRaceOutputs[14][0] = 0.33;
		expectedRaceOutputs[15][0] = 0.33;
		
/*		expectedRaceOutputs[0][0] = 0;
		expectedRaceOutputs[1][1] = 0;
		expectedRaceOutputs[2][1] = 0;
		expectedRaceOutputs[3][1] = 0;
		expectedRaceOutputs[4][1] = 1;
		expectedRaceOutputs[5][1] = 1;
		expectedRaceOutputs[6][1] = 1;
		expectedRaceOutputs[7][1] = 1;
		expectedRaceOutputs[8][1] = 0;
		expectedRaceOutputs[9][1] = 0;
		expectedRaceOutputs[10][1] = 0;
		expectedRaceOutputs[11][1] = 0;
		expectedRaceOutputs[12][1] = 1;
		expectedRaceOutputs[13][1] = 1;
		expectedRaceOutputs[14][1] = 1;
		expectedRaceOutputs[15][1] = 1;*/

		
		
		for (int i = 0; i < 16; i++) {

			System.out.println("Scanning features from Image " + i);
			double[] inputs = id.identifyNNInputs("C:\\Project\\sample-images\\training\\original" + i + ".jpg");

			if (inputs != null) {
				for (int j = 0; j < 3; j++) {
					genderTraingInputs[i][j]=inputs[j];
				}
				
				
				for (int j = 0; j < inputs.length; j++) {
					raceTraingInputs[i][j]=inputs[j];
					
				}
			}
		}
		
		System.out.println("Training...");
		egisManager.trainRaceNetwork(raceTraingInputs, expectedRaceOutputs);
		//egisManager.trainGenderNetwork(genderTraingInputs, expectedGenderOutputs);
	}

}

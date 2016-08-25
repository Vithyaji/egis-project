package core;

public class Test {

	public static void main(String[] args)  {

		ImageDetector id = new ImageDetector();
		EgisNNManager egisManager = EgisNNManager.getInstance();
		double[][] expectedOutputs = new double[20][3];
		double[][] allTraingInputs = new double[20][48];
		
		expectedOutputs[0][0] = 0;
		expectedOutputs[0][1] = 1;
		expectedOutputs[0][2] = 0;
		
		expectedOutputs[1][0] = 1;
		expectedOutputs[1][1] = 0;
		expectedOutputs[1][2] = 1;
		
		expectedOutputs[2][0] = 0;
		expectedOutputs[2][1] = 0;
		expectedOutputs[2][2] = 0;
		
		expectedOutputs[3][0] = 0;
		expectedOutputs[3][1] = 1;
		expectedOutputs[3][2] = 0;
		
		expectedOutputs[4][0] = 0;
		expectedOutputs[4][1] = 1;
		expectedOutputs[4][2] = 0;
		
		expectedOutputs[5][0] = 0;
		expectedOutputs[5][1] = 1;
		expectedOutputs[5][2] = 0;
		
		expectedOutputs[6][0] = 1;
		expectedOutputs[6][1] = 1;
		expectedOutputs[6][2] = 1;
		
		expectedOutputs[7][0] = 0;
		expectedOutputs[7][1] = 1;
		expectedOutputs[7][2] = 0;
		
		expectedOutputs[8][0] = 1;
		expectedOutputs[8][1] = 1;
		expectedOutputs[8][2] = 0;
		
		expectedOutputs[9][0] = 1;
		expectedOutputs[9][1] = 0;
		expectedOutputs[9][2] = 0;
		
		expectedOutputs[10][0] = 1;
		expectedOutputs[10][1] = 0;
		expectedOutputs[10][2] = 0;
		
		expectedOutputs[11][0] = 0;
		expectedOutputs[11][1] = 1;
		expectedOutputs[11][2] = 0;
		
		expectedOutputs[12][0] = 0;
		expectedOutputs[12][1] = 1;
		expectedOutputs[12][2] = 0;
		
		expectedOutputs[13][0] = 1;
		expectedOutputs[13][1] = 1;
		expectedOutputs[13][2] = 1;
		
		expectedOutputs[14][0] = 1;
		expectedOutputs[14][1] = 1;
		expectedOutputs[14][2] = 1;
		
		expectedOutputs[15][0] = 0;
		expectedOutputs[15][1] = 1;
		expectedOutputs[15][2] = 0;
		
		expectedOutputs[16][0] = 1;
		expectedOutputs[16][1] = 1;
		expectedOutputs[16][2] = 1;

		expectedOutputs[17][0] = 1;
		expectedOutputs[17][1] = 0;
		expectedOutputs[17][2] = 1;
		
		expectedOutputs[18][0] = 1;
		expectedOutputs[18][1] = 1;
		expectedOutputs[18][2] = 0;
		
		expectedOutputs[19][0] = 1;
		expectedOutputs[19][1] = 1;
		expectedOutputs[19][2] = 0;
		

		
		for (int i = 1; i < 21; i++) {

			System.out.println("Scanning features from Image " + i);
			double[] inputs = id.identifyNNInputs("C:\\Project\\sample-images\\original" + i + ".jpg");

			if (inputs != null) {
				for (int j = 0; j < inputs.length; j++) {
					allTraingInputs[i-1]=inputs;
				}
			}
		}

		for(int j=0;j<allTraingInputs.length;j++){
			System.out.println("Trraining from image "+(j+1));
			egisManager.trainEgisNN(allTraingInputs[j], expectedOutputs[j]);
		}
		
		double[] inputs = id.identifyNNInputs("C:\\Project\\sample-images\\input.jpg");
		if (inputs != null) {
			double[] outputArray = egisManager.getOutput(inputs);
			for (int k = 0; k < outputArray.length; k++) {
				System.out.println("Output " + k + ":" + outputArray[k]);
			}
			System.out.println("Race :" + getRace(outputArray));
			System.out.println("Gender :" + getGender(outputArray));
		} else {
			System.out.println("Could not detect face");
		}
	}

	private static String getRace(double[] nnOutput) {

		String race = "unknown";

		if (nnOutput[0] > 0.8 && nnOutput[1] > 0.8) {
			race = "Cacasian";
		} else if (nnOutput[0] > 0.8 && nnOutput[1] < 0.2) {
			race = "Mangolian";
		} else if (nnOutput[0] < 0.2 && nnOutput[1] > 0.8) {
			race = "Anglo-Asian";
		} else if (nnOutput[0] < 0.2 && nnOutput[1] < 0.2) {
			race = "African";
		}

		return race;
	}

	private static String getGender(double[] nnOutput) {

		String gender = "unknown";

		if (nnOutput[2] > 0.8) {
			gender = "Female";
		} else if (nnOutput[2] < 0.2) {
			gender = "Male";
		}

		return gender;
	}

}

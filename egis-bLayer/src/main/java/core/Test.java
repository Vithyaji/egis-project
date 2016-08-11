package core;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		
		
		for (int i = 1; i < 22; i++) {
			ImageDetector id = new ImageDetector();
			double[] inputs = id.identifyNNInputs("C:\\Project\\sample-images\\original" + i + ".jpg");
			

			System.out.println("Nureal network inputs: Image " + i);
			System.out.println("Hair Region Edge Count: " + inputs[0]);
			System.out.println("Eye Region Edge Count: " + inputs[1]);
			System.out.println("LF Region Edge Count: " + inputs[2]);

			System.out.println("Hair Region Colors: " + inputs[3] + " " + inputs[4] + " " + inputs[5]);
			System.out.println("Eye Region Colors: " + inputs[6] + " " + inputs[7] + " " + inputs[8]);
			System.out.println("LF Region Colors: " + inputs[9] + " " + inputs[10] + " " + inputs[11]);

		}
	}
}

package core;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		
		for (int i = 1; i < 21; i++) {
			
			System.out.println("Image "+i);
			
			ImageDetector id = new ImageDetector();
			
			double[] inputs = id.identifyNNInputs("C:\\Project\\sample-images\\original" + i + ".jpg");
			
			if(inputs!=null){
				for(int j=0;j<inputs.length;j++){
					System.out.println("Input "+j+": "+inputs[j]);
				}
			}
			
		}
	}
	
}

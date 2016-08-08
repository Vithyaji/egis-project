package imageprocessing;

import java.io.IOException;

public class Test {
	
	public static void main(String[] args) throws IOException{
		ImageProcessing imgP = new ImageProcessing();
		System.out.println(imgP.processImage("C:\\Project\\sample-images\\original2.jpg").length);
		
	}
}

package imageprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Test {
	
	public static void main(String[] args) throws IOException {
		ImageProcessing imgP = new ImageProcessing();
		BufferedImage rawImg =  imgP.readImage("C:\\Project\\sample-images\\passport.jpg");
		BufferedImage processedImg = imgP.processImage(rawImg);
		BufferedImage mergedImage = imgP.mergeImgs(rawImg, processedImg);
		
		
		 File outputfile1 = new File("C:\\Project\\sample-images\\edges.png");
		 ImageIO.write(processedImg, "png", outputfile1);
		 
		 File outputfile2 = new File("C:\\Project\\sample-images\\mergered.png");
		 ImageIO.write(mergedImage, "png", outputfile2);
		
	}

	private static void printArray(double[] items) {
		for(int i=0;i< items.length;i++){
			System.out.println(items[i]);
		}
		
	}
}

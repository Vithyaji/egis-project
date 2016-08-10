package core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import imageprocessing.ImageProcessing;
import imageprocessing.Utils;

public class ImageDetector {
	
	public static void main(String[] args) throws IOException {
		ImageProcessing imgP = new ImageProcessing();
		
		ImageAnalyzer ia = new ImageAnalyzer();
		
		for(int i=23;i<24;i++){
			
			BufferedImage rawImage = Utils.readImage("C:\\Project\\sample-images\\original"+i+".jpg");
			BufferedImage detectionImage = imgP.processImageForDetetion(rawImage);
			
			Region headRegion = ia.getHeadRegion(detectionImage);
			Region eyeRegion = ia.getEyeRegion(headRegion);
			Region hairRegion = ia.getHairRegion(headRegion);
			Region lfRegion = ia.getLowerFaceRegion(headRegion);
			
			BufferedImage linedImage = ia.drawLine(detectionImage, headRegion, 0,0, 0);
			linedImage = ia.drawLine(linedImage, eyeRegion, 0, 225, 0);
			linedImage = ia.drawLine(linedImage, hairRegion, 225, 0, 0);
			linedImage = ia.drawLine(linedImage, lfRegion, 0, 0, 225);
			
			ImageIO.write(linedImage, "png", new File("C:\\Project\\sample-images\\DetectionLine"+i+".png"));
			
			BufferedImage detailsImage = imgP.processImageForDetails(rawImage);
			//ImageIO.write(detailsImage, "png", new File("/Nirujan/Detail"+i+".png"));
			
			ia.getIntensityCount(detailsImage,hairRegion);
			ia.getIntensityCount(detailsImage,eyeRegion);
			ia.getIntensityCount(detailsImage,lfRegion);
			ia.getCommonColors(rawImage,hairRegion);
			ia.getCommonColors(rawImage,eyeRegion);
			ia.getCommonColors(rawImage,lfRegion);
			
			System.out.println("Nureal network inputs: Image "+i);
			System.out.println("Hair Region Intensity: ");
			System.out.println("Eye Region Intensity: ");
			System.out.println("LF Region Intensity: ");
			System.out.println("Hair Region Colors: ");
			System.out.println("Eye Region Colors: ");
			System.out.println("LF Region Colors: ");
			
			
		}
		
	}
	
}

  package core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import imageprocessing.ImageProcessing;
import imageprocessing.Utils;

public class ImageDetector {
	
	public double[] identifyNNInputs(String fileLocation) throws IOException{
		
		double[] nnInputs = new double[12];
		ImageProcessing imgP = new ImageProcessing();
		
		ImageAnalyzer ia = new ImageAnalyzer();
		
			
			BufferedImage rawImage = Utils.readImage(fileLocation);
			BufferedImage resizedRawImage = Utils.resizeImage(rawImage, rawImage.getType(), 500, 500);
			BufferedImage detectionImage = imgP.processImageForDetetion(resizedRawImage);
			
			Region headRegion = ia.getHeadRegion(detectionImage);
			Region eyeRegion = ia.getEyeRegion(headRegion);
			Region hairRegion = ia.getHairRegion(headRegion);
			Region lfRegion = ia.getLowerFaceRegion(headRegion);
			
			BufferedImage linedImage = ia.drawLine(detectionImage, headRegion, 0,0, 0);
			linedImage = ia.drawLine(linedImage, eyeRegion, 0, 225, 0);
			linedImage = ia.drawLine(linedImage, hairRegion, 225, 0, 0);
			linedImage = ia.drawLine(linedImage, lfRegion, 0, 0, 225);
			ImageIO.write(linedImage, "png", new File(fileLocation+"LineImage.png"));
			
			
			int hairEdgeCount = ia.getIntensityCount(detectionImage,hairRegion);
			int eyeEdgeCount = ia.getIntensityCount(detectionImage,eyeRegion);
			int lfEdgeCount = ia.getIntensityCount(detectionImage,lfRegion);
			int hairColors[] = ia.getCommonColors(resizedRawImage,hairRegion);
			int eyeColors[] = ia.getCommonColors(resizedRawImage,eyeRegion);
			int lfColors[] = ia.getCommonColors(resizedRawImage,lfRegion);
			nnInputs[0]=hairEdgeCount;
			nnInputs[1]=eyeEdgeCount;
			nnInputs[2] = lfEdgeCount; 
			nnInputs[3]=hairColors[0];
			nnInputs[4]=hairColors[1];
			nnInputs[5] = hairColors[2]; 
			nnInputs[6]=eyeColors[0];
			nnInputs[7]=eyeColors[1];
			nnInputs[8] = eyeColors[2];
			nnInputs[9]=lfColors[0];
			nnInputs[10]=lfColors[1];
			nnInputs[11] = lfColors[2];
 
				
			return nnInputs;
			
		}
		
	
}

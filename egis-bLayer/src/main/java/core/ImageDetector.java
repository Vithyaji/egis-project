package core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import imageprocessing.ImageProcessing;
import imageprocessing.Utils;

public class ImageDetector {

	public double[] identifyNNInputs(String fileLocation) {

		double[] nnInputs = null;
		ImageProcessing imgP = new ImageProcessing();
		ImageAnalyzer ia = new ImageAnalyzer();

		BufferedImage rawImage = Utils.readImage(fileLocation);
		
		BufferedImage resizedRawImage = Utils.resizeImage(rawImage,
				rawImage.getType(), 500, 500);
		BufferedImage detectionImage = imgP
				.processImageForDetetion(resizedRawImage);

		Region headRegion = ia.getHeadRegion(detectionImage);
		Region eyeRegion = ia.getEyeRegion(headRegion);
		Region hairRegion = ia.getHairRegion(headRegion);
		Region lfRegion = ia.getLowerFaceRegion(headRegion);


		int hairEdgeCount = ia.getIntensityCount(detectionImage, hairRegion);
		int eyeEdgeCount = ia.getIntensityCount(detectionImage, eyeRegion);
		int lfEdgeCount = ia.getIntensityCount(detectionImage, lfRegion);
		int hairColors[] = ia.getCommonColors(resizedRawImage, hairRegion);
		int eyeColors[] = ia.getCommonColors(resizedRawImage, eyeRegion);
		int lfColors[] = ia.getCommonColors(resizedRawImage, lfRegion);

		if (hairColors.length >0 && eyeColors.length >0 && lfColors.length >0) {
			
			nnInputs = new double[48];
			int noOfColors = 5;
			int currentPosition = 0;
			nnInputs[currentPosition] = hairEdgeCount;
			currentPosition++;
			copyColorValuestoNNInputs(hairColors, nnInputs, currentPosition, noOfColors);
			currentPosition=currentPosition+15;
			nnInputs[currentPosition] = eyeEdgeCount;
			currentPosition++;
			copyColorValuestoNNInputs(eyeColors, nnInputs, currentPosition, noOfColors);
			currentPosition=currentPosition+15;
			nnInputs[currentPosition] = lfEdgeCount;
			currentPosition++;
			copyColorValuestoNNInputs(lfColors, nnInputs, currentPosition, noOfColors);
			
		} else {
			System.out.println("Coudn't Identify regions on "+fileLocation);
		}
		
		return nnInputs;

	}
	
	private double[] copyColorValuestoNNInputs(int[] colorArray, double[] nnInputs, int currentPosition, int noOfCOlors){
		
		Color currentColor = null;
		int redPosition;
		int greenPosition;
		int bluePosition;
		
		for(int i=0;i<noOfCOlors;i++){
			redPosition = currentPosition+i;
			greenPosition = redPosition+noOfCOlors;
			bluePosition = greenPosition+noOfCOlors;
			currentColor = new Color(colorArray[i]);
			
			nnInputs[redPosition] = currentColor.getRed();
			nnInputs[greenPosition] = currentColor.getGreen();
			nnInputs[bluePosition] = currentColor.getBlue();
			
		}
		
		return nnInputs;
	}

	

}

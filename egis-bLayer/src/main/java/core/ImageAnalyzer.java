package core;

import imageprocessing.Utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageAnalyzer {

	public double[] captureInputs(BufferedImage image) {
		double[] inputSet = new double[10];

		return inputSet;
	}

	public BufferedImage drawLine(BufferedImage image, Region region, int r, int g, int b) {
		
		BufferedImage linedImage = Utils.deepCopy(image);
		
		Color color = new Color(r, g, b);
		
		for (int i = region.getStartX(); i < region.getEndX(); i++) {
			linedImage.setRGB(i, region.getStartY(), color.getRGB());
		}
		
		for (int i = region.getStartX(); i < region.getEndX(); i++) {
			linedImage.setRGB(i, region.getEndY(), color.getRGB());
		}
		
		for (int i = region.getStartY(); i < region.getEndY(); i++) {
			linedImage.setRGB(region.getStartX(), i, color.getRGB());
		}
		
		for (int i = region.getStartY(); i < region.getEndY(); i++) {
			linedImage.setRGB(region.getEndX(), i, color.getRGB());
		}
		
		return linedImage;
	}
	
	public Region getHeadRegion(BufferedImage image){
		
		Region headRegion = new Region();
		
		int headStartHeight = getHeadStartHeight(image);
		int headStartWidth = getHeadStartWidth(image, headStartHeight);
		int headEndHeight = getHeadEndHeight(image, headStartWidth, headStartHeight + 1);
		int headEndWidth = getHeadEndWidth(image, headStartHeight);
		
		headRegion.setStartX(headStartWidth);
		headRegion.setStartY(headStartHeight);
		headRegion.setEndX(headEndWidth);
		headRegion.setEndY(headEndHeight);
		
		return headRegion;
	}
	
	public Region getEyeRegion(Region headRegion) {
		Region eyeRegion = new Region();
		eyeRegion.setStartX(headRegion.getStartX());
		eyeRegion.setEndX(headRegion.getEndX());
		
		int headMiddle = findMiddle(headRegion.getStartY(), headRegion.getEndY());
		
		eyeRegion.setStartY(findMiddle(findMiddle(headRegion.getStartY(), headMiddle),headMiddle));
		eyeRegion.setEndY(findMiddle(headMiddle,findMiddle(headMiddle, headRegion.getEndY())));
		
		return eyeRegion;
		
	}
	
	public Region getHairRegion(Region headRegion) {
		
		Region hairRegion = new Region();
		hairRegion.setStartX(headRegion.getStartX());
		hairRegion.setEndX(headRegion.getEndX());
		hairRegion.setStartY(headRegion.getStartY());
		
		int headMiddle = findMiddle(headRegion.getStartY(), headRegion.getEndY());
		hairRegion.setEndY(findMiddle(headRegion.getStartY(), headMiddle));
		
		return hairRegion;
		
	}
	
	public Region getLowerFaceRegion(Region headRegion) {
		Region lfRegion = new Region();
		lfRegion.setStartX(headRegion.getStartX());
		lfRegion.setEndX(headRegion.getEndX());
		int headMiddle = findMiddle(headRegion.getStartY(), headRegion.getEndY());
		lfRegion.setStartY(findMiddle(headMiddle,findMiddle(headMiddle, headRegion.getEndY())));
		lfRegion.setEndY(headRegion.getEndY());
		return lfRegion;
	}

	private int getHeadStartHeight(BufferedImage image) {
		int startHeight = 0;
		for (int i = 0; i < image.getHeight(); i++) {
			int colorPixelCount = 0;
			for (int j = 0; j < image.getWidth(); j++) {
				Color pc = new Color(image.getRGB(j, i));
				if (pc.getRed() > 0 || pc.getGreen() > 0 || pc.getBlue() > 0) {
					colorPixelCount++;
				}
			}
			if (colorPixelCount > 10) {
				startHeight = i;
				break;
			}
		}
		return startHeight;
	}

	private int getHeadStartWidth(BufferedImage image, int hairStartHeight) {

		int startWidth = 0;
		for (int i = 0; i < image.getWidth() / 2; i++) {
			int colorPixelCount = 0;
			int halfHead = (image.getHeight() / 2) + hairStartHeight;
			for (int j = hairStartHeight; j < halfHead; j++) {
				Color pc = new Color(image.getRGB(i, j));
				if (pc.getRed() > 0 || pc.getGreen() > 0 || pc.getBlue() > 0) {
					colorPixelCount++;
				}
			}
			if (colorPixelCount > 10) {
				startWidth = i;
				break;
			}
		}
		return startWidth;
	}

	private int getHeadEndWidth(BufferedImage image, int hairStartHeight) {
		int endWidth = 0;
		for (int i = image.getWidth() - 1; i > image.getWidth() / 2; i--) {
			int colorPixelCount = 0;
			int halfHead = (image.getHeight() / 2) + hairStartHeight;
			for (int j = hairStartHeight; j < halfHead; j++) {
				Color pc = new Color(image.getRGB(i, j));
				if (pc.getRed() > 0 || pc.getGreen() > 0 || pc.getBlue() > 0) {
					colorPixelCount++;
				}
			}
			if (colorPixelCount > 5) {
				endWidth = i;
				break;
			}
		}
		return endWidth;
	}

	private int getHeadEndHeight(BufferedImage image, int hairStartWidth,
			int hairStartHeight) {

		int endHeight = 0;
		for (int i = hairStartHeight; i < image.getHeight(); i++) {
			int colorPixelCount = 0;
			for (int j = 0; j < hairStartWidth; j++) {
				Color pc = new Color(image.getRGB(j, i));
				if (pc.getRed() > 0 || pc.getGreen() > 0 || pc.getBlue() > 0) {
					colorPixelCount++;
				}
			}
			if (colorPixelCount > 3) {
				endHeight = i;
				break;
			}
		}
		return endHeight;

	}
	
	private int findMiddle(int start, int end){
		int middleLength = (end-start)/2;
		return start+middleLength;
	}

	public int getIntensityCount(BufferedImage detailsImage, Region hairRegion) {
		return 0;
		
		
	}

	public int[] getCommonColors(BufferedImage rawImage, Region hairRegion) {
		return null;
		
	}

}

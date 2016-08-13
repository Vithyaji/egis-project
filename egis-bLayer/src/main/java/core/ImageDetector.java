package core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import imageprocessing.ImageProcessing;
import imageprocessing.Utils;

public class ImageDetector {

	public double[] identifyNNInputs(String fileLocation) throws IOException {

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

		BufferedImage linedImage = drawLine(detectionImage, headRegion, 0,
				0, 0);
		linedImage = drawLine(linedImage, eyeRegion, 0, 225, 0);
		linedImage = drawLine(linedImage, hairRegion, 225, 0, 0);
		linedImage = drawLine(linedImage, lfRegion, 0, 0, 225);

		int hairEdgeCount = ia.getIntensityCount(detectionImage, hairRegion);
		int eyeEdgeCount = ia.getIntensityCount(detectionImage, eyeRegion);
		int lfEdgeCount = ia.getIntensityCount(detectionImage, lfRegion);
		int hairColors[] = ia.getCommonColors(resizedRawImage, hairRegion);
		int eyeColors[] = ia.getCommonColors(resizedRawImage, eyeRegion);
		int lfColors[] = ia.getCommonColors(resizedRawImage, lfRegion);

		if (hairColors.length >0 && eyeColors.length >0 && lfColors.length >0) {
			
			nnInputs = new double[18];
			
			linedImage = printColorSq(linedImage, hairRegion, hairColors);
			linedImage = printColorSq(linedImage, eyeRegion, eyeColors);
			linedImage = printColorSq(linedImage, lfRegion, lfColors);
			
			nnInputs[0] = hairEdgeCount;
			nnInputs[1] = eyeEdgeCount;
			nnInputs[2] = lfEdgeCount;
			
			nnInputs[3] = hairColors[0];
			nnInputs[4] = hairColors[1];
			nnInputs[5] = hairColors[2];
			nnInputs[6] = hairColors[3];
			nnInputs[7] = hairColors[4];
			
			nnInputs[8] = eyeColors[0];
			nnInputs[9] = eyeColors[1];
			nnInputs[10] = eyeColors[2];
			nnInputs[11] = eyeColors[3];
			nnInputs[12] = eyeColors[4];
			
			nnInputs[13] = lfColors[0];
			nnInputs[14] = lfColors[1];
			nnInputs[15] = lfColors[2];
			nnInputs[16] = lfColors[3];
			nnInputs[17] = lfColors[4];
			
		} else {
			System.out.println("Coudn't Identify regions on "+fileLocation);
		}
		
		ImageIO.write(linedImage, "png", new File(fileLocation + "lined.jpg"));
		return nnInputs;

	}

	private BufferedImage drawLine(BufferedImage image, Region region, int r,
			int g, int b) {

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

	private BufferedImage printColorSq(BufferedImage image, Region region,
			int[] colors) {

		int squareSize = 10;
		int noOfColors = 5;
		if(colors.length<noOfColors){
			noOfColors = colors.length;
		}
		for (int i = 0; i < noOfColors; i++) {
			for (int j = region.getStartX() + (squareSize * (i)); j < region
					.getStartX() + (squareSize * (i)) + squareSize; j++) {
				for (int k = region.getStartY(); k < region.getStartY()
						+ squareSize; k++) {
					image.setRGB(j, k, colors[i]);
				}
			}
		}
		return image;
	}

}

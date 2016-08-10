package imageprocessing;

import java.awt.image.BufferedImage;

public class ImageProcessing {

	
	private BufferedImage generateEdgeImage(BufferedImage image, float lowT, float hT) {
		BufferedImage processedImage = null;

		CannyEdgeDetector detector = new CannyEdgeDetector();

		detector.setLowThreshold(lowT);
		detector.setHighThreshold(hT);

		detector.setSourceImage(image);
		detector.process();
		processedImage = detector.getEdgesImage();

		return processedImage;
	}

	public BufferedImage processImageForDetetion(BufferedImage image){
		ImageProcessing imgP = new ImageProcessing();
		BufferedImage processedImg =  Utils.deepCopy(image);
		processedImg = imgP.generateEdgeImage(processedImg,2f,2f);
		
		return processedImg;
	}
	
	public BufferedImage processImageForDetails(BufferedImage image){
		ImageProcessing imgP = new ImageProcessing();
		BufferedImage processedImg =  Utils.deepCopy(image);
		processedImg = imgP.generateEdgeImage(processedImg,0.5f,1f);
		
		return processedImg;
	}

}

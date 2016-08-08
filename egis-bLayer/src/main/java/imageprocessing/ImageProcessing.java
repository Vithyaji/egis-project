package imageprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageProcessing {

	private static final int IMG_W = 300;
	private static final int IMG_H = 300;

	private BufferedImage readImage(String fileLocation) {
		BufferedImage image = null;
		try {
			File input = new File(fileLocation);
			image = ImageIO.read(input);
		} catch (IOException e) {
			System.out.println("Cannot read file. Following exection was thrown");
			System.out.println(e.getMessage());
		}
		return image;
	}

	private double[] captureColours(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();

		List<Double> colors = new ArrayList<Double>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color c = new Color(image.getRGB(j, i));
				colors.add(Double.parseDouble("" + c.getRed() + c.getGreen() + c.getBlue()));
			}
		}
		return Utils.convertDoubles(colors);
	}

	private BufferedImage generateEdgeImage(BufferedImage image) {
		BufferedImage processedImage = null;

		CannyEdgeDetector detector = new CannyEdgeDetector();

		detector.setLowThreshold(0.5f);
		detector.setHighThreshold(1f);

		detector.setSourceImage(image);
		detector.process();
		processedImage = detector.getEdgesImage();

		return processedImage;
	}

	private BufferedImage mergeImgs(BufferedImage rawImg, BufferedImage processedImg){
		
		BufferedImage mergedImg = new BufferedImage(rawImg.getWidth(), rawImg.getHeight(), rawImg.getType());
		
		int width = rawImg.getWidth();
        int height = rawImg.getHeight();
        int pimgRed = 0;
 	   	int pimgGreen = 0;
 	   	int pimgBlue = 0;

        for(int i=0; i<height; i++){
           for(int j=0; j<width; j++){
        	   Color pc = new Color(processedImg.getRGB(j, i));
        	   pimgRed = pc.getRed();
        	   pimgGreen = pc.getGreen();
        	   pimgBlue = pc.getBlue();
              
        	   if(pimgRed > 0 && pimgGreen > 0 && pimgBlue > 0){
        		   mergedImg.setRGB(j, i, rawImg.getRGB(j, i));
        	   } else {
        		   mergedImg.setRGB(j, i, processedImg.getRGB(j, i));
        	   }
           }
        }
		return mergedImg;	
	}
	
	public double[] processImage(String fileLocation) throws IOException{
		ImageProcessing imgP = new ImageProcessing();
		BufferedImage rawImg =  imgP.readImage(fileLocation);
		BufferedImage resizedRawImg = Utils.resizeImage(rawImg, rawImg.getType(), IMG_W, IMG_H);
		
		BufferedImage processedImg = imgP.generateEdgeImage(resizedRawImg);
		BufferedImage mergedImg = imgP.mergeImgs(resizedRawImg, processedImg);
		
		//ImageIO.write(mergedImg, "jpg", new File("c:\\Project\\sample-images\\resized-raw-merged3.jpg"));
		
		return imgP.captureColours(mergedImg);
	}

}

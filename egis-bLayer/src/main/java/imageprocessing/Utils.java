package imageprocessing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int type, int targetWidth, int targetHeight){
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		g.dispose();
		return resizedImage;
	    }
	
	public static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	
	public static BufferedImage readImage(String fileLocation) {
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

}

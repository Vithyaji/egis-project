package imageprocessing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import core.Trainer;

public class Utils {
	
	private static final Logger LOGGER = Logger.getLogger(Utils.class);
	
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
			LOGGER.error("Cannot read file. Following exection was thrown");
			LOGGER.error(e.getMessage());
		}
		return image;
	}

}

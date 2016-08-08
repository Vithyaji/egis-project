package imageprocessing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Utils {
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int type, int targetWidth, int targetHeight){
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		g.dispose();
		return resizedImage;
	    }

}

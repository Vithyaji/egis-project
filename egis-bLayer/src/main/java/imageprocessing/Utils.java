package imageprocessing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

public class Utils {

	public static double[] convertDoubles(List<Double> doubles) {
		double[] ret = new double[doubles.size()];
		Iterator<Double> iterator = doubles.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			ret[i] = iterator.next();
			i++;
		}
		return ret;
	}
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int type, int targetWidth, int targetHeight){
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		g.dispose();
		return resizedImage;
	    }

}

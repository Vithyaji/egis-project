package core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class ImageAnalyzer {

	public double[] captureInputs(BufferedImage image) {
		double[] inputSet = new double[10];

		return inputSet;
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
		
		int headWidth = headRegion.getEndX()-headRegion.getStartX();
		int headWPortion = headWidth/8;
		
		eyeRegion.setStartX(headRegion.getStartX()+headWPortion);
		eyeRegion.setEndX(headRegion.getEndX()-headWPortion);
		
		int headHeight = headRegion.getEndY()-headRegion.getStartY();
		int headHPortion = headHeight/8;
		
		eyeRegion.setStartY(headRegion.getStartY()+(headHPortion*3));
		eyeRegion.setEndY(headRegion.getEndY()-(headHPortion*3));
		
		
		return eyeRegion;
		
	}
	
	public Region getHairRegion(Region headRegion) {
		
		Region hairRegion = new Region();
		int headWidth = headRegion.getEndX()-headRegion.getStartX();
		int headWPortion = headWidth/8;
		
		hairRegion.setStartX(headRegion.getStartX()+headWPortion);
		hairRegion.setEndX(headRegion.getEndX()-headWPortion);
		
		int headHeight = headRegion.getEndY()-headRegion.getStartY();
		int headHPortion = headHeight/8;
		
		hairRegion.setStartY(headRegion.getStartY()+(headHPortion*0));
		hairRegion.setEndY(headRegion.getStartY()+(headHPortion*2));
		
		return hairRegion;
		
	}
	
	
	public Region getLowerFaceRegion(Region headRegion) {
		Region lfRegion = new Region();
		int headWidth = headRegion.getEndX()-headRegion.getStartX();
		int headWPortion = headWidth/8;
		
		lfRegion.setStartX(headRegion.getStartX()+(headWPortion*1));
		lfRegion.setEndX(headRegion.getEndX()-(headWPortion*1));
		
		int headHeight = headRegion.getEndY()-headRegion.getStartY();
		int headHPortion = headHeight/8;
		
		lfRegion.setStartY(headRegion.getEndY()-(headHPortion*3));
		lfRegion.setEndY(headRegion.getEndY()-(headHPortion*0));
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

	public int getIntensityCount(BufferedImage image, Region region) {
		
		int edgeCount = 0;
		for (int i = region.getStartY(); i < region.getEndY(); i++) {
			for (int j = region.getStartX(); j < region.getEndX(); j++) {
				Color pc = new Color(image.getRGB(j, i));
				if (pc.getRed() > 0 || pc.getGreen() > 0 || pc.getBlue() > 0) {
					edgeCount++;
				}
			}
		}
		return edgeCount;
		
	}

	public int[] getCommonColors(BufferedImage image, Region region) {
		List<Integer> topColors = new ArrayList<Integer>();
		int colorInt;
		Map<Integer, Integer> allColors = new HashMap<Integer, Integer>();
		for (int i = region.getStartY(); i < region.getEndY(); i++) {
			for (int j = region.getStartX(); j < region.getEndX(); j++) {
				colorInt = image.getRGB(j, i);
				colorInt = normalizeColor(colorInt);
				if(!allColors.containsKey(colorInt)) {
					allColors.put(colorInt, 1);
				} else {
					allColors.put(colorInt, allColors.get(colorInt) + 1);
				}
			}
		}
		allColors = sortByValue(allColors);
		for (Entry<Integer, Integer> entry : allColors.entrySet())
		{
			topColors.add(entry.getKey());
		}
		
		int[] arr = new int[topColors.size()];
		for (int i=0;i<arr.length; i++) {
			arr[i] = topColors.get(i).intValue();
		}
		return arr;
	}
	
	private  <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		Map<K, V> result = new LinkedHashMap<>();
		Stream<Map.Entry<K, V>> st = map.entrySet().stream();
		st.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));

		return result;
	}
	
	private int normalizeColor(int color) {

		int normalizeConstatnt = 10;
		Color orginalColor = new Color(color);

		Color normalizedColor = new Color(round(orginalColor.getRed(),
				normalizeConstatnt), round(orginalColor.getGreen(),
				normalizeConstatnt), round(orginalColor.getBlue(),
				normalizeConstatnt));

		return normalizedColor.getRGB();
	}
	
	private int round(int number, int div){
		int deno = number%div;
		return number-deno;
	}
	

}

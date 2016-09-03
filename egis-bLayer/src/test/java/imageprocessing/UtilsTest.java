package imageprocessing;

import java.awt.image.BufferedImage;
import java.io.File;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testResizeImage(){	
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("test3.jpg").getFile());
		
		BufferedImage testBufferedImage = Utils.readImage(file.getAbsolutePath());
		BufferedImage resizedTestImage = Utils.resizeImage(testBufferedImage, testBufferedImage.getType(), 800, 800);
		assertEquals(800, resizedTestImage.getWidth());
		assertEquals(800, resizedTestImage.getHeight());
		
	}
	
	public void testDeepCopyImage (){
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("test3.jpg").getFile());
		
		BufferedImage testBufferedImage = Utils.readImage(file.getAbsolutePath());
		BufferedImage copiedImage = Utils.deepCopy(testBufferedImage);
		assertEquals(testBufferedImage.getWidth(), copiedImage.getWidth());
		assertEquals(testBufferedImage.getHeight(), copiedImage.getHeight());
		
	}
}

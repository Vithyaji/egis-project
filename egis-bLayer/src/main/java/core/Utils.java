package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class Utils {
	
	private static final Logger LOGGER = Logger.getLogger(Utils.class);
	
	public static Properties loadPropfromFile(String fileLocation){

		Properties prop = new Properties();
		InputStream input = Utils.class.getClassLoader().getResourceAsStream(fileLocation);

		try {
			prop.load(input);

		} catch (IOException ex) {
			LOGGER.debug(ex.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOGGER.debug(e.getMessage());
				}
			}
		}
		return prop;
		
	}
	
	public static void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

			try {
				OutputStream out = new FileOutputStream(new File(
						uploadedFileLocation));
				int read = 0;
				byte[] bytes = new byte[1024];

				out = new FileOutputStream(new File(uploadedFileLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			} catch (IOException e) {

				LOGGER.debug(e.getMessage());
			}

		}
	
	public static String getCurrentTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String fileToString(String fileName){
		
		
		String path = Utils.class.getClassLoader().getResource(fileName).getFile();
        File file = new File(path);
        String fileAsStr = null;
		try {
			fileAsStr = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			LOGGER.debug(e.getMessage());
		}
		
		return fileAsStr;
		
	}

}

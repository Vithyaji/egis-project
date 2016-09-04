package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
	
	public static Properties loadPropfromFile(String fileLocation){
		
		
		
		
		Properties prop = new Properties();
		InputStream input = Utils.class.getClassLoader().getResourceAsStream(fileLocation);

		try {
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
		
	}

}

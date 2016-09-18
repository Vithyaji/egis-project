package service;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import core.Identifier;
import core.Utils;
import model.RnG;

@Path("/identifier")
public class ImageIdentifierWS {
	

	private static final String LOG4J_PROP = "./config/log4j.properties";
	
	@POST
	@Path("/identify")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	
	public String identifyImage(@FormDataParam("image") InputStream uploadedInputStream, @FormDataParam("image") FormDataContentDisposition fileDetail){
		
		PropertyConfigurator.configure(System.getProperty("web-service-log",LOG4J_PROP));
		
		String saveFileLocation = "C://Project//uploaded-images/" + fileDetail.getFileName();
		Utils.writeToFile(uploadedInputStream, saveFileLocation);
		
		Identifier identifier = new Identifier();
		String[] rng = identifier.identify(saveFileLocation);
		
		String htmlFileAsString = Utils.fileToString("result.html");
		String replaced = htmlFileAsString.replace("race-value", rng[0]).replace("gender-value", rng[1]);
		
		
		return replaced;
	}

}

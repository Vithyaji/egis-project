package service;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import core.Identifier;
import core.Utils;
import model.RnG;

@Path("/identifier")
public class ImageIdentifierWS {
	
	@POST
	@Path("/identify")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public RnG identifyImage(@FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition fileDetail){
		
		String saveFileLocation = "C://Project//uploaded-images/" + fileDetail.getFileName();
		Utils.writeToFile(uploadedInputStream, saveFileLocation);
		
		Identifier identifier = new Identifier();
		String[] rng = identifier.identify(saveFileLocation);
		RnG raceAndGender = new RnG(rng[0], rng[1]);
		return raceAndGender;
	}

}

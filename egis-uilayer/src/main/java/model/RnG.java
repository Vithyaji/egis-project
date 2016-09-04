package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RnG {
	
	private String race;
	private String gender;
	
	public RnG(){}
	
	public RnG(String paasedRace, String passedGender){
		this.race = paasedRace;
		this.gender = passedGender;
	}
	
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}

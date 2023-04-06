package le.ac.uk.co3102.cw2.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/*
 * Creates a resident table in the data base to store all the authorised users of the system
 * Has a one to one relationship with SNI
 */

@Entity
public class ResidentUsers {
	
	@Id
	private String userID;
	private String fullName;
	
	private String dob;
	private String homeAddress;
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "sni_number", referencedColumnName = "sniNumber")
	private Sni sni;
	
	private Boolean coucilOfficer = false;
	
	
	public ResidentUsers() {
		
	}	
	
	public ResidentUsers(String userID) {
		this.userID = userID;
		
	}

	public ResidentUsers(String userID, String fullName, String dob, String homeAddress, String password) {
		this.userID = userID;
		this.dob = dob;
		this.homeAddress = homeAddress;
		this.password = password;
		this.fullName = fullName;
	}
	
	public Boolean getCoucilOfficer() {
		return coucilOfficer;
	}
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public void setCoucilOfficer(Boolean coucilOfficer) {
		this.coucilOfficer = coucilOfficer;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Sni getSNI() {
		return sni;
	}
	public void setSNI(Sni sni) {
		this.sni = sni;
	}

}

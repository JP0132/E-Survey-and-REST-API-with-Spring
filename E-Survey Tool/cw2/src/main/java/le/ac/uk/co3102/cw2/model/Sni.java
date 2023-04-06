package le.ac.uk.co3102.cw2.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


/*
 * Create a table in the database for the SNI numbers that are valid.
 * 
 */

@Entity
public class Sni {
	
	@Id
	private String sniNumber;
	
	private Boolean used = false;
	
	@OneToOne(mappedBy="sni",fetch = FetchType.LAZY)
	@JoinColumn(name = "sni_number", referencedColumnName = "sniNumber")
	private ResidentUsers user;
	
	public Sni() {
		
	}
	
	public String getSniNumber() {
		return sniNumber;
	}
	
	public void setSniNumber(String sniNumber) {
		this.sniNumber = sniNumber;
	}
	
	
	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public ResidentUsers getUser() {
		return user;
	}
	
	public void setUser(ResidentUsers user) {
		this.user = user;
	}
	
	

}

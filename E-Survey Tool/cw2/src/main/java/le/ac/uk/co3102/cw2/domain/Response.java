package le.ac.uk.co3102.cw2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * Creates the response table to store all the responses
 * Stores the userID for each answered question along with its selected optionID
 */


@Entity
public class Response {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer responseID;
	
	private String userID;
	
	private Integer questionID;
	
	private Integer optionID;
	
	public Integer getResponseID() {
		return responseID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Integer getQuestionID() {
		return questionID;
	}

	public void setQuestionID(Integer questionID) {
		this.questionID = questionID;
	}

	public Integer getOptionID() {
		return optionID;
	}

	public void setOptionID(Integer optionID) {
		this.optionID = optionID;
	}


}

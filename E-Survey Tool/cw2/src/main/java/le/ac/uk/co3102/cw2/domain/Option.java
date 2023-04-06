package le.ac.uk.co3102.cw2.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/*
 * Used to create the Option table in the database
 * ID is auto generated
 * Has a ManyToOne relationship with Question
 */

@Entity
@Table(name="MyOptions")
public class Option {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer optionsID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="questionID")
	private Question questionID;
	
	public Question getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Question questionID) {
		this.questionID = questionID;
	}
	private String optionsText;

	public int getOptionID() {
		return optionsID;
	}
	public void setOptionID(int optionID) {
		this.optionsID = optionID;
	}
	public String getOptionText() {
		return optionsText;
	}
	public void setOptionText(String optionText) {
		this.optionsText = optionText;
	}

}

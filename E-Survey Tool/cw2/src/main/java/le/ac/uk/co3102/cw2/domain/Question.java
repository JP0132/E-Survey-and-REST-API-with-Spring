package le.ac.uk.co3102.cw2.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/*
 * Create a table in the database for questions
 * ID is auto generated
 * Has a one to many relationship with Options -> stores a list of Options
 * 
 */

@Entity
public class Question{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer questionID;
	

	private String questionText;
	

	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "questionID")
	private List<Option> options;
	
	public Question() {
		this.options = new ArrayList<Option>();
	}
	
	
	public String getQuestionText() {
		return questionText;
	}
	
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
	
	public Integer getQuestionID() {
		return questionID;
	}
	
	public List<Option> getOptions() {
		return options;
	}
	
	public void setOptions(List<Option> options) {
		if(this.options != null || !this.options.isEmpty()) {
			this.options.clear();
		}
		
		this.options.addAll(options);
	}
	
	public void addOption(Option o) {
		this.options.add(o);
		
	}
	
	public void removeOption(Option o) {
		this.options.remove(o);
	}
	
	

}

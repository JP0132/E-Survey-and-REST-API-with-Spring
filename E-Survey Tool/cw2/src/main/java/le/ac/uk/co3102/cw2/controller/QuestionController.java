package le.ac.uk.co3102.cw2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import le.ac.uk.co3102.cw2.domain.Question;
import le.ac.uk.co3102.cw2.domain.Response;
import le.ac.uk.co3102.cw2.model.ResidentUsers;
import le.ac.uk.co3102.cw2.repo.OptionRepository;
import le.ac.uk.co3102.cw2.repo.QuestionRepository;
import le.ac.uk.co3102.cw2.repo.ResidentUserRepository;
import le.ac.uk.co3102.cw2.repo.ResponseRepository;
import le.ac.uk.co3102.cw2.domain.Option;

/*
 * RestController for question requests
 * 
 * POST /dashboard/saveQuestion?questionText=randomQuestion&options=yes,no
 * @return List<List<String>>
 * Saves a new question
 * 
 * GET /dashboard/getAllQuestions
 * @return List<List<List<String>>>
 * Returns all the questions
 * 
 * Deletes questions
 * Get questions that have been answered
 * Saves a response to a question
 * 
 */

@RestController
@CrossOrigin
public class QuestionController {
	
	@Autowired
	ResidentUserRepository userRepo;
	
	@Autowired
	QuestionRepository questionRepo;
	
	@Autowired
	OptionRepository optionRepo;
	
	@Autowired
	ResponseRepository responseRepo;
	
	
	@PostMapping("/dashboard/saveQuestion")
	public ResponseEntity<List<List<String>>> saveQuestion(@RequestParam(name="questionText") String questionText, @RequestParam(name="options") String[] options){
		Question newQuestion = new Question();
		
		List<Option> newOptions = new ArrayList<Option>();
		
		for(int i = 0; i < options.length; i++) {
			Option newOp = new Option();
			newOp.setOptionText(options[i]);
			newOp.setQuestionID(newQuestion);
			newOptions.add(newOp);
			
		}
		
		newQuestion.setQuestionText(questionText);
		newQuestion.setOptions(newOptions);
		
		questionRepo.save(newQuestion);
		
		List<List<String>> newQuestionData = new ArrayList<List<String>>();
		
		List<String> questionData = new ArrayList<String>();
		questionData.add(newQuestion.getQuestionID().toString());
		questionData.add(newQuestion.getQuestionText());
		newQuestionData.add(questionData);
		
		List<Option> newoptions = newQuestion.getOptions();
		for(Option o : newoptions) {
			List<String> optionData = new ArrayList<String>();
			String optionId = Integer.toString(o.getOptionID());
			optionData.add(optionId);
			optionData.add(o.getOptionText());
			newQuestionData.add(optionData);
		}
		
		return new ResponseEntity<>(newQuestionData,HttpStatus.OK);
		
	}
	
	@GetMapping("/dashboard/getAllQuestions")
	public ResponseEntity<List<List<List<String>>>>getAllQuestions(@RequestParam(name="userID") String userID){
		ResidentUsers currentUser = userRepo.findByUserID(userID);
		List<Integer> answered;
		String currentRole;
		if(currentUser.getCoucilOfficer()) {
			currentRole = "COUNCIL";
			answered = getAllAnsweredQuestions();
			
		}
		
		else {
			currentRole = "RESIDENT";
			answered = getAnsweredQuestions(userID);
			
		}
		
		
		
		List<List<List<String>>> questionList= new ArrayList<List<List<String>>>();
		List<Question> allQuestions = questionRepo.findAll();
		
		for(Question question : allQuestions) {
			List<List<String>> questionLine = new ArrayList<List<String>>();
			List<String> questionData = new ArrayList<String>();
			questionData.add(question.getQuestionID().toString());
			questionData.add(question.getQuestionText());
			List<Option> options = question.getOptions();
			if(currentRole.equals("COUNCIL")) {
				if(answered.contains(question.getQuestionID())) {
					String ans = "answered";
					questionData.add(ans);
				}
				questionLine.add(questionData);
			}
			
			else {
				if(!(answered.contains(question.getQuestionID()))) {
					questionLine.add(questionData);
				}
				else {
					continue;
				}
				
			}
		
			
			for(Option o : options) {
				List<String> optionData = new ArrayList<String>();
				String optionId = Integer.toString(o.getOptionID());
				optionData.add(optionId);
				optionData.add(o.getOptionText());
				questionLine.add(optionData);
			}
			
			questionList.add(questionLine);
			
		}
		
		return new ResponseEntity<>(questionList,HttpStatus.OK);
		
	}
	
	private List<Integer> getAllAnsweredQuestions() {
		Iterable<Response> allResponses = responseRepo.findAll();
		List<Integer> questionsAnswered = new ArrayList<Integer>();
		for(Response ans : allResponses) {
			if(!questionsAnswered.contains(ans.getQuestionID())) {
				questionsAnswered.add(ans.getQuestionID());
			}
			
		}
		return questionsAnswered;
	}

	@DeleteMapping(value = "/dashboard/deleteQuestion/{id}")
	public ResponseEntity<?> deleteQuestion(@PathVariable Integer id){
		if(!(questionRepo.existsById(id))){
			return new ResponseEntity<>("Question is not found",HttpStatus.NOT_FOUND);
		}
		
		questionRepo.deleteById(id);
		return new ResponseEntity<>("Question has been deleted",HttpStatus.OK);
		
	}
	
	@GetMapping("/dashboard/updateQuestion")
	public ResponseEntity<?> updateQuestion(@RequestParam(name="questionID") Integer questionID, @RequestParam(name="questionText") String questionText, @RequestParam(name="options") String[] options){
		if(questionRepo.findById(questionID).orElse(null) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Question oldQuestion = questionRepo.findByQuestionID(questionID);
		if(oldQuestion.getQuestionText() != questionText) {
			oldQuestion.setQuestionText(questionText);
		}
		
		List<Option> newOptions = new ArrayList<Option>();
		
		for(int i = 0; i < options.length; i++) {
			Option newOp = new Option();
			newOp.setOptionText(options[i]);
			newOp.setQuestionID(oldQuestion);
			newOptions.add(newOp);
		}
		

		oldQuestion.setOptions(newOptions);
		
		questionRepo.save(oldQuestion);
		
		List<List<String>> updatedQuestionData = new ArrayList<List<String>>();
		
		List<String> questionData = new ArrayList<String>();
		questionData.add(oldQuestion.getQuestionID().toString());
		questionData.add(oldQuestion.getQuestionText());
		updatedQuestionData.add(questionData);
		
		
		List<Option> newoptions = oldQuestion.getOptions();
		for(Option o : newoptions) {
			List<String> optionData = new ArrayList<String>();
			String optionId = Integer.toString(o.getOptionID());
			optionData.add(optionId);
			optionData.add(o.getOptionText());
			updatedQuestionData.add(optionData);
		}
		
		
		return new ResponseEntity<>(updatedQuestionData,HttpStatus.OK);
		
	}
	

	
	@PostMapping("/dashboard/saveResponse")
	public ResponseEntity<?> submitAnswer(@RequestParam(name="questionID") String questionID, @RequestParam(name="optionID") String optionID, @RequestParam(name="userID") String userID){
		Integer qID = Integer.parseInt(questionID.substring(1));
		Integer opID = Integer.parseInt(optionID);
		
		Response newResponse = new Response();
		
		newResponse.setUserID(userID);
		newResponse.setQuestionID(qID);
		newResponse.setOptionID(opID);
		
		responseRepo.save(newResponse);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	public List<Integer> getAnsweredQuestions(String userID){
		List<Response> ans = responseRepo.findByUserID(userID);
		
		List<Integer> questionsAnswered = new ArrayList<Integer>();
		for(Response r: ans) {
			questionsAnswered.add(r.getQuestionID());
		}
		
		return questionsAnswered;
	}

}







package le.ac.uk.co3102.restapi.restController;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import le.ac.uk.co3102.restapi.domain.Option;
import le.ac.uk.co3102.restapi.domain.Question;
import le.ac.uk.co3102.restapi.domain.Response;
import le.ac.uk.co3102.restapi.repository.OptionRepository;
import le.ac.uk.co3102.restapi.repository.QuestionRepository;
import le.ac.uk.co3102.restapi.repository.ResponseRepository;

@RestController
@RequestMapping("/")
public class QuestionRestController {
	@Autowired
	QuestionRepository qRepo;
	
	@Autowired
	OptionRepository oRepo;
	
	@Autowired
	ResponseRepository rRepo;
	
	@GetMapping(value="/GetAllQuestions", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> listAllQuestions(){
		List<Question> questions = qRepo.findAll();
	
		JSONObject conJSON = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray question = new JSONArray();
		for(Question q : questions) {
			JSONObject newQuestion = new JSONObject();
			newQuestion.put("id", Integer.toString(q.getQuestionID()));
			newQuestion.put("text", q.getQuestionText());
			question.put(newQuestion);
		}
		
		json.put("Questions", question);
		
		conJSON.put("consulations",json);
		
		return new ResponseEntity<>(conJSON.toMap(),HttpStatus.OK);
		
	}
	
	@GetMapping(value="/GetQuestionOptions/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> listAllOptions(@PathVariable("id") Integer id){
		if(qRepo.findById(id).orElse(null) == null) {
			return new ResponseEntity<>("Question with requested ID is not found",HttpStatus.NOT_FOUND);
		}
	
		Question getQ = qRepo.findByQuestionID(id);
		
		
		List<Option> qOp = getQ.getOptions();
		
		JSONObject question = new JSONObject();
		
		
		JSONObject optionJSON = new JSONObject();
		JSONArray optionArray = new JSONArray();
		for(Option o : qOp) {
			JSONObject newOp = new JSONObject();
			newOp.put("Text", o.getOptionText());
			newOp.put("id", Integer.toString(o.getOptionID()));
			optionArray.put(newOp);
		}
		
		//optionJSON.put("Options", optionArray);
		
		question.put("Options", optionArray);
		question.put("Question", Integer.toString(id));
		
		
		return new ResponseEntity<>(question.toMap(),HttpStatus.OK);
		
	}
	
	@GetMapping(value="/GetQuestionResponse/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> listQuestionResponses(@PathVariable("id") Integer id){
		if(qRepo.findById(id).orElse(null) == null) {
			return new ResponseEntity<>("Question with requested ID does not exist",HttpStatus.NOT_FOUND);
		}
	
		
		List<Response> qr = rRepo.findAllByQuestionID(id);
		
		
		JSONObject question = new JSONObject();
		
		
		JSONObject answerJSON = new JSONObject();
		JSONArray answerArray = new JSONArray();
		Question q = qRepo.findByQuestionID(id);
		List<Option> o = q.getOptions();
		
		
		List<List<String>> optionResponses = new ArrayList<List<String>>();
		for(Option os : o) {
			Integer currentID = os.getOptionID();
			Integer counter = 0;
			for(Response r : qr) {
				if(r.getOptionID() == currentID) {
					counter++;
				}
			}
			
			List<String> newR = new ArrayList<String>();
			newR.add(Integer.toString(currentID));
			newR.add(Integer.toString(counter));
			optionResponses.add(newR);
		}
		
		for(List<String> or : optionResponses) {
			JSONObject newRes = new JSONObject();
			newRes.put("id", or.get(0));
			if(or.get(1).equals("0")) {
				newRes.put("count", "X");
			}
			else {
				newRes.put("count", or.get(1));
			}
			
			answerArray.put(newRes);
		}
	
		
		question.put("Question", Integer.toString(id));
		question.put("Answers", answerArray);
		
		
		return new ResponseEntity<>(question.toMap(),HttpStatus.OK);
		
	}
	
	


}

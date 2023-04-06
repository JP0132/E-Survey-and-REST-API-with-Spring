package le.ac.uk.co3102.cw2.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import le.ac.uk.co3102.cw2.domain.Option;
import le.ac.uk.co3102.cw2.domain.Question;
import le.ac.uk.co3102.cw2.domain.Response;
import le.ac.uk.co3102.cw2.repo.OptionRepository;
import le.ac.uk.co3102.cw2.repo.QuestionRepository;
import le.ac.uk.co3102.cw2.repo.ResponseRepository;

/*
 * Response Controller handles requests to do with responses
 * Returns the data for responses
 * 
 */

@RestController
@CrossOrigin
public class ResponseController {
	@Autowired
	QuestionRepository questionRepo;
	
	@Autowired
	OptionRepository optionRepo;
	
	@Autowired
	ResponseRepository responseRepo;
	
	@GetMapping("/getTotalResponses")
	public ResponseEntity<?> getTotalResponses(@RequestParam(name="questionID") Integer questionID){
		List<Response> listofans = responseRepo.findAllByQuestionID(questionID);
		Question q = questionRepo.findByQuestionID(questionID);
		
		
		int numberOfResponses = 0;
		if(!listofans.isEmpty()) {
			numberOfResponses = listofans.size();
		}
		
		String questionText = q.getQuestionText();
		List<String> qr = new ArrayList<String>();
		qr.add(Integer.toString(numberOfResponses));
		qr.add(questionText);
		
		
		return new ResponseEntity<>(qr,HttpStatus.OK);
		
	}
	
	@GetMapping("/getOptionTextResponses")
	public ResponseEntity<?> getOptionTextResponses(@RequestParam(name="questionID") Integer questionID){
		
		Question q = questionRepo.findByQuestionID(questionID);
		List<Option> o = q.getOptions();

		List<String> optionTextList = new ArrayList<String>();
		
		for(Option ot : o) {
			
			optionTextList.add(ot.getOptionText());
	
		}
		
	
		
		
		
		return new ResponseEntity<>(optionTextList,HttpStatus.OK);
		
	}
	
	@GetMapping("/getOptionResponses")
	public ResponseEntity<?> getOptionResponses(@RequestParam(name="questionID") Integer questionID){
		List<Response> listofans = responseRepo.findAllByQuestionID(questionID);
		Question q = questionRepo.findByQuestionID(questionID);
		List<Option> o = q.getOptions();
		
		
		List<Integer> optionResponses = new ArrayList<Integer>();
		for(Option os : o) {
			Integer currentID = os.getOptionID();
			Integer counter = 0;
			for(Response r : listofans) {
				if(r.getOptionID() == currentID) {
					counter++;
				}
			}
			
			
			optionResponses.add(counter);
			
		}
		
		
		
		return new ResponseEntity<>(optionResponses,HttpStatus.OK);
		
	}
	

}

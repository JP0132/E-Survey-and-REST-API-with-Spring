package le.ac.uk.co3102.cw2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * A Controller that handles the requests to send the correct JSPs 
 * 
 * GET /login?error=true
 * Adds an error message to the form if login credentials were incorrect
 * 
 */



@Controller
@CrossOrigin
public class MainController {
	
	@GetMapping("/")
	public String showMainPage()
	{	
		return "signIn";
	}
	
	@GetMapping("/signIn")
	public String showLoginForm() {
		return "signIn";
	}
	
	@GetMapping("/login")
	public String signUpError(@RequestParam(value = "error", defaultValue = "false") boolean loginError, Model model) {
		String error = "Username or Password was incorrect";
		model.addAttribute("error", error);
		return "signIn";
		
	}
	
	@GetMapping("/dashboard")
	public String showDashboard() {
		return "dashboard";
	}
	
	@GetMapping("/responses")
	public String showResponses() {
		return "responses";
	}
	
	
	@GetMapping("/question")
	public String showQuestions() {
		return "questions";
	}
	
}

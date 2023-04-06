package le.ac.uk.co3102.cw2.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import le.ac.uk.co3102.cw2.model.ResidentUsers;
import le.ac.uk.co3102.cw2.model.Sni;
import le.ac.uk.co3102.cw2.repo.ResidentUserRepository;
import le.ac.uk.co3102.cw2.repo.SniRepository;

/*
 * Rest controller for resident user controller
 * Saves a new residents.
 * Validates if the SNI number exists or is taken.
 * Validates if userID exists already or not
 * 
 * Gets the current user role if they are an admin or resident
 * Gets the current user ID
 */

@RestController
@CrossOrigin
public class ResidentUserController {
	
	@Autowired
	ResidentUserRepository userRepo;
	
	@Autowired
	SniRepository sniRepo;
	
	@Autowired
	private PasswordEncoder pe;
	
	@PostMapping("/signIn/newUser")
	public ResponseEntity<?> saveNewResident(@RequestParam(name = "userID") String userID, @RequestParam(name = "fullname") String fullname,@RequestParam(name = "dob") String dob,@RequestParam(name = "address") String address,@RequestParam(name = "password") String password, @RequestParam(name = "sni") String sni){
		
		if(userRepo.existsById(userID)) {
			return new ResponseEntity<>("User ID Already Exists", HttpStatus.CONFLICT);
		}
		
		
		if(sniRepo.existsById(sni)) {
			Sni newSni = sniRepo.findBySniNumber(sni);
			if(newSni.getUsed()) {
				return new ResponseEntity<>("SNI number is already used", HttpStatus.CONFLICT);
			}
		}
		
		else {
			return new ResponseEntity<>("SNI number does not exist", HttpStatus.NOT_FOUND);
		}
		

		
		Sni newSni = sniRepo.findBySniNumber(sni);
		ResidentUsers newResident = new ResidentUsers();
		newResident.setUserID(userID);
		newResident.setFullName(fullname);
		newResident.setDob(dob);
		newResident.setHomeAddress(address);
			
		newSni.setUsed(true);
			
		newResident.setSNI(newSni);
		newResident.setPassword(pe.encode(password));
			
		userRepo.save(newResident);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/dashboard/getRole")
	public ResponseEntity<?> getUserRole(Principal principal){
		ResidentUsers checkUser = userRepo.findById(principal.getName()).get();
		if(checkUser.getCoucilOfficer()) {
			return new ResponseEntity<>("COUNCIL", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("RESIDENT", HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/dashboard/getUserID")
	public ResponseEntity<?> getUserID(Principal principal){
		ResidentUsers user = userRepo.findById(principal.getName()).get();
	
		return new ResponseEntity<>(user.getUserID(), HttpStatus.OK);
		
	}
	

	

}

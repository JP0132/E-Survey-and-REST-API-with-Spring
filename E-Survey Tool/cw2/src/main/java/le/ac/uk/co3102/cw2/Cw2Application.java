package le.ac.uk.co3102.cw2;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import le.ac.uk.co3102.cw2.repo.ResidentUserRepository;
import le.ac.uk.co3102.cw2.repo.SniRepository;
import le.ac.uk.co3102.cw2.model.ResidentUsers;
import le.ac.uk.co3102.cw2.model.Sni;
/*
 * Initialises the database with the council admin officers and sets up the sni number database
 */

@SpringBootApplication
public class Cw2Application implements ApplicationRunner{
	
	@Autowired
	ResidentUserRepository userRepo;
	
	@Autowired
	SniRepository sniRepo;
	
	@Autowired
	private PasswordEncoder pe;

	public static void main(String[] args) {
		SpringApplication.run(Cw2Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ResidentUsers councilAdminOfficer = new ResidentUsers("admin@shangrila.gov.un");
		councilAdminOfficer.setCoucilOfficer(true);
		councilAdminOfficer.setPassword(pe.encode("shangrila@2021$"));
		userRepo.save(councilAdminOfficer);
		
		ResidentUsers councilAdminOfficer2 = new ResidentUsers("jsmith@gmail.com");
		councilAdminOfficer2.setCoucilOfficer(true);
		councilAdminOfficer2.setPassword(pe.encode("12345"));
		userRepo.save(councilAdminOfficer2);
		
		List<String> sniNumbers = Arrays.asList("MM2874Z6", "FEQQ6UUG", "34GC829B", "CB8FBCCM");
		
		for(String sniNum : sniNumbers) {
			Sni newSni = new Sni();
			newSni.setSniNumber(sniNum);
			sniRepo.save(newSni);
		}
		
		
	}
	
	

}

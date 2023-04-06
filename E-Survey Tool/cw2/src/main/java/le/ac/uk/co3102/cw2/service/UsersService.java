package le.ac.uk.co3102.cw2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import le.ac.uk.co3102.cw2.model.ResidentUsers;
import le.ac.uk.co3102.cw2.repo.ResidentUserRepository;

import java.util.ArrayList;
import java.util.List;

/*
 * User service to check if the userID is in the data base or not
 * if so allow the user access and
 * @return new User for security access of this site with the roles granted to it
 * If userID is not found
 * @throw a exception the user ID is not found.
 * 
 */


@Service
public class UsersService implements UserDetailsService {
	
	@Autowired
	private ResidentUserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ResidentUsers residentUsers = userRepo.findByUserID(username);
		
		if(residentUsers == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_RESIDENT"));
		if(residentUsers.getCoucilOfficer()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_COUNCIL"));
		}
		
		return new User(residentUsers.getUserID(), residentUsers.getPassword(), true, true,
				true, true, authorities);
		
	}
	


}

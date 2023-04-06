package le.ac.uk.co3102.cw2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/*
 * Configures the  security for the application, ensures only authenticated users can access urls.
 * Handles what happens when a user tries to login.
 * Defines which requests each role are authenticated for.
 * 
 * @Autowireds 
 * userDetailsService + configureGlobal which handles the login, checks against the database.
 * 
 * @Bean
 * Password Encoder encodes the password safely in the database
 * @return new BCryptPasswordEncoder()
 * 
 */


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	protected void configure(HttpSecurity http) throws Exception {
		http
	      .csrf().disable()
	      .authorizeRequests()
	      .antMatchers(HttpMethod.DELETE, "/dashboard/**").authenticated()
	      .antMatchers("/signIn/**").anonymous();
		http.requiresChannel().anyRequest().requiresSecure()
		.and().formLogin()
			.loginPage("/") 
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/dashboard",true)
			.failureUrl("/login?error=true")
			.permitAll()
		.and().logout()
			.invalidateHttpSession(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")
			.permitAll()
		.and().authorizeRequests()
			.antMatchers("/dashboard","/dashboard/saveResponse","/dashboard/getUserID","/dashboard/getRole","/dashboard/getAllQuestions").hasAnyRole("RESIDENT","COUNCIL")
			.antMatchers("/dashboard/**","/responses").hasAnyRole("COUNCIL")
			.antMatchers("/public/**", "/signIn/newUser").permitAll()
			.anyRequest().authenticated()
		.and().exceptionHandling().accessDeniedPage("/");
		http.cors();
	}
	
	@Autowired 
	private UserDetailsService userDetailsService;

    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

package ca.sheridancollege.hohoan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	@Lazy
	private UserDetailsService userDetailService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        // ===== AUTHORIZE REQUESTS =====
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(
	                "/", 
	                "/js/**", 
	                "/css/**", 
	                "/images/**", 
	                "/permission-denied",
	                "/h2-console/**"
	            ).permitAll()

	            .requestMatchers("/secure/**").hasRole("ADMIN")

	            .requestMatchers(HttpMethod.POST, "/delete").permitAll()
	            .requestMatchers(HttpMethod.GET, "/delete").permitAll()

	            .anyRequest().denyAll()
	        )

	        // ===== CSRF ===== (Cross-Site Request Forgery)
        	// If we are entering to the site with request like 
	        // http://mybank.com/transfer?amount=1000&to=hacker
	        // <img src="http://mybank.com/transfer?amount=1000&to=hacker" /> 
	        // => Send request to end point of server 
	        
	       
	        .csrf(csrf -> csrf
	            .ignoringRequestMatchers("/h2-console/**") // For all the request in the h2-console
	            .disable() // Alow send POST/PUT/DELETE without CSRF token
	        )

	        // ===== HEADERS =====
	        .headers(headers -> headers
	            .frameOptions(FrameOptionsConfig::disable)
	        )

	        // ===== FORM LOGIN =====
	        .formLogin(form -> form
	            .loginPage("/login")
	            .permitAll()
	        )

	        // ===== EXCEPTION HANDLING =====
	        .exceptionHandling(ex -> ex
	            .accessDeniedPage("/permission-denied")
	        )

	        // ===== LOGOUT =====
	        .logout(LogoutConfigurer::permitAll);

	    return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;
	}
}

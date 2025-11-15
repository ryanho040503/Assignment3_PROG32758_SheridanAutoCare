package ca.sheridancollege.hohoan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ca.sheridancollege.hohoan.database.DatabaseAccess;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	DatabaseAccess da;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UserNameNotFoundExeption // the throws here have a "s"
	{
//		Find the user based on the username (read email)
		ca.sheridancollege.hohoan.beans.User user = da.
		
		return userDetails;
	}
}

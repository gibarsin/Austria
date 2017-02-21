package ar.com.nameless.webapp.auth;

import ar.com.nameless.interfaces.service.UserService;
import ar.com.nameless.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

/**
 * Implementation of Spring Security's UserDetailsService
 * which obtains the data from UserService
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService us;

	/**
	 * Returns user's username, password and authorities
	 * @param username the username from which to retrieve the user's details
	 * @return the user details
	 * @throws UsernameNotFoundException if no user exists with that username
	 */
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final User user = us.getByUsername(username);

		if(user == null) {
			throw new UsernameNotFoundException("No user by the name of " + username);
		}

		Collection<? extends GrantedAuthority> grantedAuthorities = getGrantedAuthorities();

		return new org.springframework.security.core.userdetails.User(
						username,
						user.getPassword(),
						grantedAuthorities
		);
	}

	/**
	 * Get the user's granted authorities
	 * @return a collection of the granted authorities for every user
	 */
	private Collection<? extends GrantedAuthority> getGrantedAuthorities() {
		return Arrays.asList(
						new SimpleGrantedAuthority("ROLE_USER") // Every user is considered to have the role User
		);
	}
}

package ar.com.nameless.webapp.auth;

import ar.com.nameless.webapp.form.UserLoginForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
class TokenAuthenticationService {
	private static final String AUTH_HEADER = "X-AUTH-TOKEN"; // Header used to retrieve the Authentication Token

	@Autowired
	private UserDetailsService userDetailsService;

	private final TokenHandler tokenHandler;

	TokenAuthenticationService(final TokenHandler tokenHandler) {
		this.tokenHandler = tokenHandler;
	}

	Authentication getAuthentication(final HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER);
		if(token == null) {
			return null;
		}

		final String username = tokenHandler.getUsername(token);
		if(username == null) {
			return null;
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		final Authentication authentication = new UsernamePasswordAuthenticationToken(
						username,
						userDetails.getPassword(),
						userDetails.getAuthorities()
		);

		return authentication;
	}

	/**
	 * In case that authentication is successful a response header is populated with a new token
	 * @param response the HTTP response to put the {@link #AUTH_HEADER} with the created token
	 * @param userDetails information of the user that may be used to put in the token.
	 */
	void addAuthentication(final HttpServletResponse response, final UserDetails userDetails) {
		final String token = tokenHandler.createToken(userDetails.getUsername());

		response.setHeader(AUTH_HEADER, token);
	}

	Authentication getAuthenticationForLogin(final HttpServletRequest request) {
		final UserLoginForm userLoginForm;

		try {
			userLoginForm = new ObjectMapper().readValue(request.getInputStream(), UserLoginForm.class);
		} catch (final Exception e) {
			return null;
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginForm.getUsername());

		if(userDetails == null) {
			return null;
		} else if(!userDetails.getPassword().equals(userLoginForm.getPassword())) { // Validate password
			return null;
		}

		return new UsernamePasswordAuthenticationToken(
						userDetails.getUsername(),
						userDetails.getPassword(),
						userDetails.getAuthorities());
	}
}

package ar.com.nameless.webapp.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final UserDetailsService userDetailsService;
	private final TokenAuthenticationService tokenAuthenticationService;

	public StatelessLoginFilter(final String urlMapping, final TokenAuthenticationService tokenAuthenticationService,
	                            final UserDetailsService userDetailsService, final AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.userDetailsService = userDetailsService;
		this.tokenAuthenticationService = tokenAuthenticationService;
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
					throws AuthenticationException, IOException, ServletException {
		final Authentication authentication = tokenAuthenticationService.getAuthenticationForLogin(request);

		if(authentication == null) {
			throw new UserAuthenticationException("Authentication failed");
		}

		return authentication;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	                                        FilterChain chain, Authentication authentication) throws IOException, ServletException {
		// Lookup the complete User object from the database and create an Authentication for it
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

		tokenAuthenticationService.addAuthentication(response, userDetails); // Add the token to a header in the HTTP Response
		SecurityContextHolder.getContext().setAuthentication(authentication); // TODO check if its necessary
	}

	private class UserAuthenticationException extends AuthenticationException {
		UserAuthenticationException(final String msg) {
			super(msg);
		}
	}
}

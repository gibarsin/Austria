package ar.com.nameless.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.com.nameless.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable(); //Configure CSRF Token

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Do not create a session

		http.authorizeRequests()
						.antMatchers(HttpMethod.POST, "/posts").authenticated()
						.antMatchers(HttpMethod.PUT, "/posts").authenticated()
						.antMatchers("/**").permitAll();

		/** Custom Stateless Login Filter **/
		http.addFilterBefore(new StatelessLoginFilter(
										"/login",
										tokenAuthenticationService,
										userDetailsService,
										authenticationManager()),
						UsernamePasswordAuthenticationFilter.class);

		/** Custom Stateless Authentication Filter **/
		http.addFilterBefore(
						new StatelessAuthenticationFilter(tokenAuthenticationService),
						UsernamePasswordAuthenticationFilter.class);
	}
}

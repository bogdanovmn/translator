package com.github.bogdanovmn.translator.web.app.config.security;


import com.github.bogdanovmn.translator.web.orm.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class TranslateSecurityService {
	private static final Logger LOG = LogManager.getLogger(TranslateSecurityService.class);

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;

	@Autowired
	public TranslateSecurityService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
	}

	public boolean isLogged() {
		return this.getLoggedInUser() != null;
	}

	public User getLoggedInUser() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof TranslateUserDetails) {
			return ((TranslateUserDetails)userDetails).getUser();
		}

		return null;
	}

	public void login(String username, String password) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
			= new UsernamePasswordAuthenticationToken(
				userDetails,
				password,
				userDetails.getAuthorities()
		);

		this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			LOG.info(String.format("Auto login %s successfully!", username));
		}
	}
}
package com.github.bogdanovmn.translator.web.app.security;

import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import com.github.bogdanovmn.translator.web.orm.repository.app.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TranslateUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Autowired
	public TranslateUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String name)
		throws UsernameNotFoundException
	{
		User user = userRepository.findFirstByName(name);

		if (user == null) {
			throw new UsernameNotFoundException(
				String.format("User '%s' not found", name)
			);
		}

		return new TranslateUserDetails(user);
	}
}

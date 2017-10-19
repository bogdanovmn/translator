package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.EntityFactory;
import com.github.bogdanovmn.translator.web.orm.User;
import com.github.bogdanovmn.translator.web.orm.UserRepository;
import com.github.bogdanovmn.translator.web.orm.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashSet;

@Service
public class RegistrationService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EntityFactory entityFactory;

	public User addUser(UserRegistrationForm userForm) {
		return this.userRepository.save(
			new User(userForm.getName())
				.setEmail(
					userForm.getEmail()
				)
				.setPasswordHash(
					DigestUtils.md5DigestAsHex(
						userForm.getPassword().getBytes()
					)
				)
				.setRegisterDate(new Date())
				.setRoles(
					new HashSet<UserRole>() {{
						add(
							(UserRole) entityFactory.getPersistBaseEntityWithUniqueName(
								new UserRole("User")
							)
						);
					}}
				)
		);
	}

	public boolean isUserExists(String email) {
		return userRepository.findFirstByEmail(email) != null;
	}

	public boolean isUserNameExists(String name) {
		return userRepository.findFirstByName(name) != null;
	}
}

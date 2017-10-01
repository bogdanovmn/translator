package com.github.bogdanovmn.translator.web.app.service;

import com.github.bogdanovmn.translator.web.app.controller.domain.form.UserRegistrationForm;
import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import com.github.bogdanovmn.translator.web.orm.entity.app.UserRole;
import com.github.bogdanovmn.translator.web.orm.factory.EntityFactory;
import com.github.bogdanovmn.translator.web.orm.repository.app.UserRepository;
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
			(User) new User()
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
								new UserRole().setName("User")
							)
						);
					}}
				)
				.setName(
					userForm.getName()
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

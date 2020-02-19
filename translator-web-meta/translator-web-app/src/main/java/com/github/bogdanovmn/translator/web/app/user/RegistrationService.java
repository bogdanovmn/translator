package com.github.bogdanovmn.translator.web.app.user;

import com.github.bogdanovmn.common.spring.jpa.EntityFactory;
import com.github.bogdanovmn.translator.web.orm.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@Service
class RegistrationService {
	private final UserRepository userRepository;
	private final UserOAuth2Repository userOAuth2Repository;
	private final EntityFactory entityFactory;

	@Autowired
	public RegistrationService(UserRepository userRepository, UserOAuth2Repository userOAuth2Repository, EntityFactory entityFactory) {
		this.userRepository = userRepository;
		this.userOAuth2Repository = userOAuth2Repository;
		this.entityFactory = entityFactory;
	}

	User addUser(UserRegistrationForm userForm) {
		return userRepository.save(
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
							entityFactory.getPersistBaseEntityWithUniqueName(
								new UserRole("User")
							)
						);
					}}
				)
		);
	}

	User updateUserPassword(User user, String newPassword) {
		return userRepository.save(
			user.setPasswordHash(
				DigestUtils.md5DigestAsHex(
					newPassword.getBytes()
				)
			)
		);
	}

	@Transactional(rollbackFor = Exception.class)
	public User addUserBySocialId(Oauth2UserInfoResponse userInfo, OAuth2Provider provider, String password) {
		User newUser = addUser(
			new UserRegistrationForm()
				.setName(userInfo.getExternalId())
				.setEmail(
					Optional.ofNullable(
						userInfo.getEmail()
					).orElse(
						DigestUtils.md5DigestAsHex(password.getBytes())
					)
				)
				.setPassword(password)
		);
		userOAuth2Repository.save(
			new UserOAuth2()
				.setProviderName(provider.name())
				.setUpdated(LocalDateTime.now())
				.setUser(newUser)
				.setExternalId(userInfo.getExternalId())
				.setExternalName(userInfo.getName())
		);

		return newUser;
	}

	User userBySocialId(String providerName, String externalId) {
		UserOAuth2 userOauth2 = userOAuth2Repository.findFirstByExternalIdAndAndProviderName(externalId, providerName);
		return userOauth2 != null
			? userOauth2.getUser()
			: null;
	}

	boolean isUserExists(String email) {
		return userRepository.findFirstByEmail(email) != null;
	}

	boolean isUserNameExists(String name) {
		return userRepository.findFirstByName(name) != null;
	}
}

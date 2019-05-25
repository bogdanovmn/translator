package com.github.bogdanovmn.translator.web.app.admin.export;

import com.github.bogdanovmn.common.spring.jpa.EntityFactory;
import com.github.bogdanovmn.translator.web.orm.entity.User;
import com.github.bogdanovmn.translator.web.orm.entity.UserRememberedWord;
import com.github.bogdanovmn.translator.web.orm.entity.UserRememberedWordRepository;
import com.github.bogdanovmn.translator.web.orm.entity.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
class ImportService {
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private EntityFactory entityFactory;
	@Autowired
	private UserRepository userRepository;

	private List<User> importUsers(ImportSchema importSchema, ExportWordCache exportWordCache) {
		List<User> resultUsers = new ArrayList<>();
		for (ImportSchema.ImportUser importUser : importSchema.getUsers()) {
			User user = userRepository.findFirstByEmail(importUser.getEmail());
			if (user != null) {
				LOG.info("User {} lists import", user.getEmail());
				userRememberedWordRepository.removeAllByUser(user);
				userRememberedWordRepository.flush();
				userRememberedWordRepository.saveAll(
					importUser.getRememberedWords().stream()
						.map(x ->
							new UserRememberedWord()
								.setWord(exportWordCache.getByExportId(x))
								.setUser(user)
						)
						.collect(Collectors.toList())
				);
				LOG.info("Remembered words import: {}", importUser.getRememberedWords().size());

				userRepository.save(user);
				resultUsers.add(user);
				LOG.info("User '{}' import done", user.getEmail());
			}
		}
		return resultUsers;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public synchronized Map<String, Object> fromFile(InputStream inputStream) throws JAXBException {
		LOG.info("Start import from file");

		// Load XML

		ImportSchema importSchema = (ImportSchema) JAXBContext.newInstance(ImportSchema.class)
			.createUnmarshaller().unmarshal(inputStream);

		LOG.info("Unmarhalling done");

		ExportWordCache exportWordCache = new ExportWordCache(
			importSchema.getWords(),
			entityFactory
		);

		LOG.info("Import words cache init done");

		// User words lists

		List<User> resultUsers = importUsers(importSchema, exportWordCache);

		return new HashMap<String, Object>() {{
			put("users", resultUsers);
		}};
	}
}

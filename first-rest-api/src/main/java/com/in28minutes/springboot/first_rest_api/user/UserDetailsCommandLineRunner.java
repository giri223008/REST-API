package com.in28minutes.springboot.first_rest_api.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(UserDetailsCommandLineRunner.class);
	private UserDetailsRepository repository;

	public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void run(String... args) throws Exception {

		repository.save(new UserDetails("bramha", "creator"));
		repository.save(new UserDetails("vishnu", "preserver"));
		repository.save(new UserDetails("maheswar", "destroyer"));
		repository.save(new UserDetails("Matha kali", "destroyer"));

		List<UserDetails> usersByRole = repository.findByRole("destroyer");
		
		usersByRole.forEach(user->logger.info(user.toString()));
		
	}

}

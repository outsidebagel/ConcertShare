package com.nick.auth.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nick.auth.entity.User;
import com.nick.auth.exception.DuplicateRegistrationException;
import com.nick.auth.repo.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User registerUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new DuplicateRegistrationException("An account with this email exists");
		}
		
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException ex) {
			if (userRepository.existsByEmail(user.getEmail())) {
				throw new DuplicateRegistrationException("An account with this email exists");
			}
			throw ex;
		}
	}
}

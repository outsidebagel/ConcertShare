package com.nick.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nick.auth.entity.User;
import com.nick.auth.model.CustomerUserDetails;
import com.nick.auth.repo.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepo;
	
	CustomUserDetailsService(UserRepository userRepo){
		this.userRepo = userRepo;
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
			return new CustomerUserDetails(user);
	}
}


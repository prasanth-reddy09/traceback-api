package com.traceback.api.service;

import org.springframework.stereotype.Service;

import com.traceback.api.entity.User;
import com.traceback.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	public User registerUser(User user) {
		
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("A user with this email already exists!");
        }

        // Note: We will add Password Encryption (Bcrypt) here in a future step!
        
        // Save the user to the TiDB database
        return userRepository.save(user);
    }
	
	
	
}

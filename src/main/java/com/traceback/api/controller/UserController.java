package com.traceback.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traceback.api.dto.AuthResponse;
import com.traceback.api.dto.LoginRequest;
import com.traceback.api.entity.User;
import com.traceback.api.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		
		User savedUser = userService.registerUser(user);
		
		System.out.println(user.getPasswordHash() + "from controller");
		
		return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
	}
	

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }
}

package com.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bankapp.dto.LoginRequest;
import com.bankapp.dto.LoginResponse;
import com.bankapp.entity.User;
import com.bankapp.security.JwtUtil;
import com.bankapp.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	// ================= REGISTER =================
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		User savedUser = userService.registerUser(user);
		return ResponseEntity.ok(savedUser);
	}

	// ================= LOGIN (JWT) =================

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

	    User user = userService.login(request.getEmail(), request.getPassword());

	    String token = JwtUtil.generateToken(user.getEmail(), user.getRole());

	    return ResponseEntity.ok(new LoginResponse(token));
	}
}

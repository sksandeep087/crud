package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.User;
import com.example.demo.exception.ResourceNotFountException;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFountException("user Not Exits By id" + id));
		return ResponseEntity.ok(user);
	}

	//Add User
	@PostMapping
	public User CreateUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	// update User By id

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User user1) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFountException("user Not Exits By id" + id));
		
		user.setFirstname(user1.getFirstname());
		user.setLastname(user1.getLastname());
		user.setEmailId(user1.getEmailId());
		
		User updateUserById = userRepository.save(user);
		
		return ResponseEntity.ok(updateUserById);
	}
	
	// delete user by id
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFountException("user Not Exits By id" + id));
		
		userRepository.delete(user);
		
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("delete", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
	}
	
}

package com.diatozsample.mongo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.diatozsample.mongo.bean.User;
import com.diatozsample.mongo.repository.UserRepository;
import com.diatozsample.mongo.service.UserDAL;


@RestController
@RequestMapping(value = "/api")
public class UserController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final UserRepository userRepository;
	
	private final UserDAL userDAL;

	public UserController(UserRepository userRepository,UserDAL userDAL) {
		this.userRepository = userRepository;
		this.userDAL= userDAL;
	}
	

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {
		LOG.info("Getting all users.");
		return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
	}


	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable String userId) {
		LOG.info("Getting user with ID: {}.", userId);
		return new ResponseEntity<>(userRepository.findById(userId).get(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<User> addNewUsers(@RequestBody User user) {
		LOG.info("Saving user.");
		return new ResponseEntity<>(userRepository.save(user),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/settings/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllUserSettings(@PathVariable String userId) {
		if (userRepository.existsById(userId)) {
			return new ResponseEntity<>(userDAL.getAllUserSettings(userId),HttpStatus.OK);
		}
			return new ResponseEntity<>("User not found.",HttpStatus.BAD_REQUEST);
	}
	
	
	@RequestMapping(value = "/settings/{userId}/{key}", method = RequestMethod.GET)
	public ResponseEntity<String> getUserSetting(@PathVariable String userId, @PathVariable String key) {
		User user = userRepository.findById(userId).get();
		if (user != null) {
			return new ResponseEntity<>(user.getUserSettings().get(key),HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User not found.",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/settings/{userId}/{key}/{value}", method = RequestMethod.GET)
	public ResponseEntity<String> addUserSetting(@PathVariable String userId, @PathVariable String key, @PathVariable String value) {
		User user = userRepository.findById(userId).get();
		if (user != null) {
			user.getUserSettings().put(key, value);
			userRepository.save(user);
			return new ResponseEntity<>("Key added",HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User not found.",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value="/check-hystrix")
	public String getSampleText() {
		LOG.info("CALL AMDE");
		return "Actual REST response";
	}

}

package com.blogapp.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//POST-create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUserDto = userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	
	//PUT-update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto
			,@PathVariable("userId") Integer userId){
		
		UserDto updateUserDto=userService.updateUser(userDto, userId);
		return  ResponseEntity.ok(updateUserDto);
	}
	
	
	//DELETE-delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
		
	userService.deleteUser(userId);
	return new ResponseEntity(new ApiResponse("User Deleted Succesfully!",true),HttpStatus.OK);
		
	}
	
	
	//GET-get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllusers(){
		return ResponseEntity.ok(userService.getAllUsers());
		
	}
	
	//GET-get single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleuser(@PathVariable("userId") Integer userId){
		return ResponseEntity.ok(userService.getUserById(userId));
		
	}
}

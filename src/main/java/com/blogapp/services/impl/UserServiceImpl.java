package com.blogapp.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapp.exceptions.*;
import com.blogapp.config.AppContstant;
import com.blogapp.entities.Role;
import com.blogapp.entities.User;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.RoleRepository;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = dtoToUser(userDto);
		User savedUser = userRepository.save(user);
		
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = userRepository.save(user);
		UserDto userToDto1 = this.userToDto(updatedUser);
		
		return userToDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user=userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepository.findById(userId)
		.orElseThrow(()->new ResourceNotFoundException("User", "Id",userId));
		
		userRepository.delete(user);
	}
	
	
//dto to user
	public User dtoToUser(UserDto userDto) {
		
		User user = modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	
//user to dto
	public UserDto userToDto(User user) {
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role role = roleRepository.findById(AppContstant.NORMAL_USER).get();
		user.getRoles().add(role);
		User save = userRepository.save(user);
		return modelMapper.map(save, UserDto.class);
	}
}

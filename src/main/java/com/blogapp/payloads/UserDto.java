package com.blogapp.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blogapp.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
	@NotBlank
	@Size(min = 4,message = "Name must contain 4 or more characters")
	private String name;
	
	@Email(message = "Please enter valid email")
	private String email;
	
	@NotBlank
	@Size(min = 4,max=10,message = "Password must be min of 4 chars and maximum of 10 chars")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
}

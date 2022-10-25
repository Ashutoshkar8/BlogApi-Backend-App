package com.blogapp.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blogapp.entities.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {
	
	private int postId;
	
	private String content;
	
	private String title;
	
	private Date addedDate;
	
	private String imageName;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments=new HashSet<>();
}

package com.blogapp.services;

import java.util.List;

import com.blogapp.entities.Post;
import com.blogapp.payloads.PostResponse;
import com.blogapp.payloads.PostDto;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get all posts
	//List<PostDto> getAllPost(Integer pageNumber,Integer pageSize);
	//after making a postresponse class we make the return type as PageReponse
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

	// get single post
	PostDto getPostById(Integer postId);

	// get all post by category
	List<PostDto> getPostByCategory(Integer categoryId);

	// get all post by user
	List<PostDto> getPostByUser(Integer userId);

	// search
	List<PostDto> searchPosts(String keyword);
	
	

}

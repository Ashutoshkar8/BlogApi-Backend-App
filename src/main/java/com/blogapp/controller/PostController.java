package com.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.config.AppContstant;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;

	//create handler
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto
			,@PathVariable ("userId") Integer userId
			,@PathVariable("categoryId") Integer categoryId){
		
		PostDto createPost = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(
			@PathVariable ("userId") Integer userId){
		
		List<PostDto> postByUser = postService.getPostByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(postByUser,HttpStatus.OK);
	}
	
	//get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(
			@PathVariable("categoryId") Integer categoryId){
		
		List<PostDto> postByCategory = postService.getPostByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(postByCategory,HttpStatus.OK);
		
	}
	
	//get all post//pagination
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber",defaultValue = AppContstant.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppContstant.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppContstant.SORT_BY,required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppContstant.SORT_DIR,required = false)String sortDir){
		
		PostResponse allPost = postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
	}
	
	
	//get single post
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable("postId") Integer postId){
		PostDto post = postService.getPostById(postId);
		return new ResponseEntity<>(post,HttpStatus.OK);
	}
	
	//delete post
	@DeleteMapping("posts/{postId}")
	public ApiResponse deletePost(@PathVariable("postId")Integer postId){
		postService.deletePost(postId);
		return new ApiResponse("Post is successfully deleted!!",true);
	}
	
	//update post
	@PutMapping("posts/{postId}")
	public ResponseEntity<PostDto> updatePost( @RequestBody PostDto postDto,
			@PathVariable("postId")Integer postId) {
		PostDto updatePost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//searching
	@GetMapping("posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchByTitlePost(
			@PathVariable("keywords") String keywords){
		
		List<PostDto> searchPosts = postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
	}
}


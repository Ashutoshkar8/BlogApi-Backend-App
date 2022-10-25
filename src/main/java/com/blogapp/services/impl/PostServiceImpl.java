package com.blogapp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.PostResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.repositories.CategoryRepository;
import com.blogapp.repositories.PostRepository;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));

		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = postRepository.save(post);

		return modelMapper.map(newPost, PostDto.class);
	}

	// update post
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		Post updatedPost = postRepository.save(post);
		return modelMapper.map(updatedPost, PostDto.class);
	}

	// delete post
	@Override
	public void deletePost(Integer postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
		postRepository.delete(post);

	}

	// get all posts //pagination
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> page = postRepository.findAll(p);

		List<Post> posts = page.getContent();

		List<PostDto> postDto = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(postDto);
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setLastPage(page.isLast());

		return postResponse;
	}

	// get single post
	@Override
	public PostDto getPostById(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		return modelMapper.map(post, PostDto.class);
	}

	// get all post by category
	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {

		Category cat = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

		List<Post> posts = postRepository.findByCategory(cat);

		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;
	}

	// get all post by user
	@Override
	public List<PostDto> getPostByUser(Integer userId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

		List<Post> posts = postRepository.findByUser(user);

		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;
	}

	//searching
	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = postRepository.findByTitleContaining(keyword);
		List<PostDto> searchedList = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return searchedList;
	}

}

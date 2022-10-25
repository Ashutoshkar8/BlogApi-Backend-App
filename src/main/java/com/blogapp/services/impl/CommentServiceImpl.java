package com.blogapp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CommentDto;
import com.blogapp.repositories.CommentRepository;
import com.blogapp.repositories.PostRepository;
import com.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment= commentRepository.save(comment);
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		commentRepository.delete(comment);
	}

}

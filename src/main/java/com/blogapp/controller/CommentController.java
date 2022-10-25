package com.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CommentDto;
import com.blogapp.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	// handler for create comment
	@PostMapping("post/{postId}/comments")
	private ResponseEntity<CommentDto> createComment(@PathVariable("postId") Integer postId,
			@RequestBody CommentDto comment) {
		CommentDto createComment = commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.OK);

	}

	// delete comment
	@DeleteMapping("comments/{commentId}")
	private ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId) {

		commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("This comment is delete successfully!", true),
				HttpStatus.OK);
	}
}

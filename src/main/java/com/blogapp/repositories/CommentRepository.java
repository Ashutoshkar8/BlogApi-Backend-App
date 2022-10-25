package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	
}

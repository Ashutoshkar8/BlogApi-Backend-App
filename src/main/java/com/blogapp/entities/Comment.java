package com.blogapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private int commentId;
	
	@Column(name = "comment_content")
	private String content;
	
	
	@ManyToOne
	@JoinColumn(name="post_comment_id")
	private Post post;
	
}

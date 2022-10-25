package com.blogapp.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;





@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@Setter
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id") 
	private int postId;
	
	@Column(name = "post_title",length=100,nullable = false)
	private String title;
	
	@Column(name = "post_content",length = 1000)
	private String content;
	
	@Column(name = "post_image")
	private String imageName;
	
	@Column(name = "post_date")
	private Date addedDate;
	
	//mapping
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private Set<Comment> comments=new HashSet<>();
}

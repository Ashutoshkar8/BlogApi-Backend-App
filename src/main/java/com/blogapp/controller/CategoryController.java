package com.blogapp.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.services.CategoryService;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	
	// POST-Create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createdCategory = categoryService.createCategory(categoryDto);

		return new ResponseEntity<CategoryDto>(createdCategory, HttpStatus.CREATED);
	}

	
	// PUT-update category
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("catId") Integer catId) {

		CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}

	
	// DELETE-delete category
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") Integer catId) {

		categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully!", true),
				HttpStatus.OK);

	}

	
	// GET-get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		return ResponseEntity.ok(allCategory);
	}

	
	// GET-get single category
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable("catId") Integer catId) {
		CategoryDto singleCategory = categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDto>(singleCategory, HttpStatus.OK);
	}
}

package com.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entities.Category;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.repositories.CategoryRepository;
import com.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;

	// create
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = modelMapper.map(categoryDto, Category.class);
		Category addCat = categoryRepository.save(cat);

		return modelMapper.map(addCat, CategoryDto.class);
	}

	// update
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		cat.setCategoryTitle(categoryDto.getCategoryTitle());

		Category updatedCat = categoryRepository.save(cat);
		return modelMapper.map(updatedCat, CategoryDto.class);
	}

	// Delete
	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		categoryRepository.delete(cat);

	}

	// get single category
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		return modelMapper.map(cat, CategoryDto.class);
	}

	// get all category
	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepository.findAll();

		List<CategoryDto> cats = categories.stream().map((cat) -> modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return cats;
	}

}

package com.example.eatgo.application;


import com.example.eatgo.domain.Category;
import com.example.eatgo.domain.CategoryRepository;
import com.example.eatgo.domain.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategory(){
        List<Category> categories = categoryRepository.findAll();

        categories.add(Category.builder().name("Japanese Food").build());

        return categories;
    }

    public Category addCategory(String name){
        Category category = Category.builder().name(name).build();

        categoryRepository.save(category);

        return category;
    }
}

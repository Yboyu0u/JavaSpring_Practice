package com.example.eatgo.application;

import com.example.eatgo.domain.Category;
import com.example.eatgo.domain.CategoryRepository;
import com.example.eatgo.domain.Region;
import com.example.eatgo.domain.Restaurant;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CategoryServiceTests {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        mockCategoryRepository();
        categoryService =new CategoryService(categoryRepository);
    }

    private void mockCategoryRepository() {
        List<Category> categories = new ArrayList<>();
        Category restaurant = Category.builder()
                .name("Japanese Food")
                .build();
        categories.add(restaurant);
    }

    @Test
    public void getcategory(){
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(Category.builder().name("Japanese Food").build());

        given(categoryRepository.findAll()).willReturn(mockCategories);

        List<Category> categories = categoryService.getCategory();

        Category category = categories.get(0);
        assertThat(category.getName()).isEqualTo("Seoul");
    }

    @Test
    public void addCategory(){
        //categoryService =new CategoryService(categoryRepository);
        Category category = categoryService.addCategory("Japanese Food");

        verify(categoryRepository).save(any());

        assertThat(category.getName()).isEqualTo("Japanese Food");

    }


}
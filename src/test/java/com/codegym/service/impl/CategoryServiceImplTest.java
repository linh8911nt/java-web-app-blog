package com.codegym.service.impl;

import com.codegym.model.Category;
import com.codegym.repository.CategoryRepository;
import com.codegym.service.CategoryService;
import com.codegym.service.CategoryServiceImplTestConfig;
import org.hibernate.internal.util.collections.JoinedIterable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringJUnitJupiterConfig(CategoryServiceImplTestConfig.class)
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    static private Long id = 1l;
    static private String name;

    static private Category category;
    static private List<Category> categories;
    static private Iterable<Category> categoryIterable;

    static private Category emptyCategory;
    static private Iterable<Category> emptyCategoryIterable;

    static{
        name = "18+";
        category = new Category(name);
        category.setId(id);

        categories = Arrays.asList(category);
        categoryIterable = new PageImpl<>(categories);

        emptyCategory = null;
        emptyCategoryIterable = null;

    }

    @AfterEach
    public void resetAllMockedObject(){
        Mockito.reset(categoryRepository);
    }

    @Test
    public void findAllWithOneCategory(){
        when(categoryRepository.findAll()).thenReturn(categoryIterable);

        assertEquals(categoryIterable, categoryService.findAll());

        verify(categoryRepository).findAll();
    }

    @Test
    public void findAllWithNoCategory(){
        when(categoryRepository.findAll()).thenReturn(emptyCategoryIterable);

        assertNull(categoryService.findAll());

        verify(categoryRepository).findAll();
    }

    @Test
    public void findByIdWithOneCategory(){
        when(categoryRepository.findOne(id)).thenReturn(category);

        assertEquals(category, categoryService.findById(id));

        verify(categoryRepository).findOne(id);
    }

    @Test
    public void findByIdWithNoCategory(){
        when(categoryRepository.findOne(id)).thenReturn(emptyCategory);

        assertNull(categoryService.findById(id));

        verify(categoryRepository).findOne(id);
    }

    @Test
    public void saveCategory(){
        when(categoryRepository.save(category)).thenReturn(category);

        assertEquals(category, categoryService.save(category));

        verify(categoryRepository).save(category);
    }
}

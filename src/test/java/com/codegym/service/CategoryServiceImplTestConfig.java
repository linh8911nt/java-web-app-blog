package com.codegym.service;

import com.codegym.repository.CategoryRepository;
import com.codegym.service.impl.CategoryServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryServiceImplTestConfig {

    @Bean
    public CategoryService categoryService(){
        return new CategoryServiceImpl();
    }

    @Bean
    public CategoryRepository categoryRepository(){
        return Mockito.mock(CategoryRepository.class);
    }
}

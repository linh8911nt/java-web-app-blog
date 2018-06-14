package com.codegym.service;

import com.codegym.repository.BlogRepository;
import com.codegym.service.impl.BlogServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlogServiceImplTestCongfig {

    @Bean
    public BlogService blogService(){
        return new BlogServiceImpl();
    }

    @Bean
    public BlogRepository blogRepository(){
        return Mockito.mock(BlogRepository.class);
    }
}

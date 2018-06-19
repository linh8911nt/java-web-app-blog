package com.codegym.service;

import com.codegym.repository.PostRepository;
import com.codegym.service.impl.PostServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlogServiceImplTestCongfig {

    @Bean
    public PostService blogService(){
        return new PostServiceImpl();
    }

    @Bean
    public PostRepository blogRepository(){
        return Mockito.mock(PostRepository.class);
    }
}

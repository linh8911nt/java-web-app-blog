package com.codegym.controller;

import com.codegym.service.PostService;
import com.codegym.service.impl.PostServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.codegym")
@EnableSpringDataWebSupport
public class BlogControllerImplTestConfig {

    @Bean
    public PostService blogService(){
        return Mockito.mock(PostServiceImpl.class);
    }

//    @Bean
//    public CategoryService categoryService(){
//        return Mockito.mock(CategoryServiceImpl.class);
//    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
                .setName("blog")
                .build();
        return db;
    }
}

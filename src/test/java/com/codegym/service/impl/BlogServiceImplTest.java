package com.codegym.service.impl;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.repository.BlogRepository;
import com.codegym.service.BlogService;
import com.codegym.service.BlogServiceImplTestCongfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitJupiterConfig(BlogServiceImplTestCongfig.class)
public class BlogServiceImplTest {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    static private Long id = 1l;
    static private String title;
    static private String summary;
    static private String content;
    static private LocalDate createDate = LocalDate.now();

    static private Category category;

    static private Blog blog;
    static private List<Blog> blogs;
    static private Page<Blog> blogsPage;

    static private Pageable pageable;

    static private Blog emptyBlog;
    static private List<Blog> emptyBlogs;
    static private Page<Blog> emptyBlogsPage;

    static{
        title = "blog title";
        summary = "blog summary";
        content = "blog content";
        category = new Category("category");
        category.setId(1l);
        blog = new Blog(title, summary, content, createDate, category);
        blog.setId(id);
        blogs = Arrays.asList(blog);
        blogsPage = new PageImpl<>(blogs);

        emptyBlog = new Blog();
        emptyBlogs = new ArrayList<>();
        emptyBlogsPage = new PageImpl<>(emptyBlogs);

        pageable = new PageRequest(0, 2);
    }

    @AfterEach
    public void resetAllMockedObject(){
        Mockito.reset(blogRepository);
    }

    @Test
    public void findAllWithOneBlog(){
        when(blogRepository.findAll(pageable)).thenReturn(blogsPage);

        assertEquals(blogsPage, blogService.findAll(pageable));

        verify(blogRepository).findAll(pageable);
    }

    @Test
    public void findAllWithNoBlog(){
        when(blogRepository.findAll(pageable)).thenReturn(emptyBlogsPage);

        assertEquals(emptyBlogsPage, blogService.findAll(pageable));

        verify(blogRepository).findAll(pageable);
    }

    @Test
    public void findOneByBlogIdWithOneBlog(){
        when(blogRepository.findOne(id)).thenReturn(blog);

        assertEquals(blog, blogService.findById(id));

        verify(blogRepository).findOne(id);
    }

    @Test
    public void findOneByBlogIdWithNoBlog(){
        when(blogRepository.findOne(id)).thenReturn(emptyBlog);

        assertEquals(emptyBlog, blogService.findById(id));

        verify(blogRepository).findOne(id);
    }

    @Test
    public void saveBlog(){
        when(blogRepository.save(blog)).thenReturn(blog);

        assertEquals(blog, blogService.save(blog));

        verify(blogRepository).save(blog);
    }
}

package com.codegym.service.impl;

import com.codegym.model.Post;
import com.codegym.model.Category;
import com.codegym.repository.PostRepository;
import com.codegym.service.PostService;
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
public class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    static private Long id = 1l;
    static private String title;
    static private String summary;
    static private String content;
    static private LocalDate createDate = LocalDate.now();

    static private Category category;

    static private Post post;
    static private List<Post> posts;
    static private Page<Post> blogsPage;

    static private Pageable pageable;

    static private Post emptyPost;
    static private List<Post> emptyPosts;
    static private Page<Post> emptyBlogsPage;

    static{
        title = "post title";
        summary = "post summary";
        content = "post content";
        category = new Category("category");
        category.setId(1l);
        post = new Post(title, summary, content, createDate, category);
        post.setId(id);
        posts = Arrays.asList(post);
        blogsPage = new PageImpl<>(posts);

        emptyPost = new Post();
        emptyPosts = new ArrayList<>();
        emptyBlogsPage = new PageImpl<>(emptyPosts);

        pageable = new PageRequest(0, 2);
    }

    @AfterEach
    public void resetAllMockedObject(){
        Mockito.reset(postRepository);
    }

    @Test
    public void findAllWithOneBlog(){
        when(postRepository.findAll(pageable)).thenReturn(blogsPage);

        assertEquals(blogsPage, postService.findAll(pageable));

        verify(postRepository).findAll(pageable);
    }

    @Test
    public void findAllWithNoBlog(){
        when(postRepository.findAll(pageable)).thenReturn(emptyBlogsPage);

        assertEquals(emptyBlogsPage, postService.findAll(pageable));

        verify(postRepository).findAll(pageable);
    }

    @Test
    public void findOneByBlogIdWithOneBlog(){
        when(postRepository.findOne(id)).thenReturn(post);

        assertEquals(post, postService.findById(id));

        verify(postRepository).findOne(id);
    }

    @Test
    public void findOneByBlogIdWithNoBlog(){
        when(postRepository.findOne(id)).thenReturn(emptyPost);

        assertEquals(emptyPost, postService.findById(id));

        verify(postRepository).findOne(id);
    }

    @Test
    public void saveBlog(){
        when(postRepository.save(post)).thenReturn(post);

        assertEquals(post, postService.save(post));

        verify(postRepository).save(post);
    }
}

package com.codegym.controller.impl;

import com.codegym.controller.BlogController;
import com.codegym.controller.BlogControllerImplTestConfig;
import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringJUnitJupiterConfig(BlogControllerImplTestConfig.class)
@WebAppConfiguration
@ContextConfiguration(classes = {BlogControllerImplTestConfig.class})
public class BlogApiControllerImplTest {

    static private Long id = 1l;
    static private String title;
    static private String summary;
    static private String content;
    static private LocalDate createDate = LocalDate.now();

    static private Category category;
    static private List<Category> categories;
    static private Iterable<Category> categoryIterable;

    static private Blog blog;
    static private List<Blog> blogs;
    static private Page<Blog> blogsPage;

    static private Pageable pageable;

    static private Blog emptyBlog;
    static private List<Blog> emptyBlogs;
    static private Page<Blog> emptyBlogsPage;

    static{
        title = "blogtitle";
        summary = "blog summary";
        content = "blog content";

        category = new Category("category");
        category.setId(1l);
//        categories = Arrays.asList(category);
//        categoryIterable = new PageImpl<>(categories);

        blog = new Blog(title, summary, content, createDate, category);
        blog.setId(id);

        blogs = Arrays.asList(blog);
        blogsPage = new PageImpl<>(blogs);

        emptyBlog = new Blog();
        emptyBlogs = new ArrayList<>();
        emptyBlogsPage = new PageImpl<>(emptyBlogs);

        pageable = new PageRequest(0, 2);
    }

    @Autowired
    private BlogService blogService;

//    @Autowired
//    private CategoryService categoryService;

    @Autowired
    private BlogController blogController;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(blogController)
                .setCustomArgumentResolvers(pageableHandlerMethodArgumentResolver)
                .build();
    }

    @AfterEach
    void resetAllMockedObject(){
        Mockito.reset(blogService);
    }

    @Test
    void listBlogs() throws Exception {
        when(blogService.findAll(pageable)).thenReturn(blogsPage);

        mockMvc.perform(get("/")
                .param("page", pageable.getPageNumber() + "")
                .param("size", pageable.getPageSize() + ""))
                .andExpect(view().name("/index"))
                .andExpect(model().attribute("blogs", blogsPage));

        verify(blogService).findAll(pageable);
    }

    @Test
    void showCreateForm() throws Exception {
        mockMvc.perform(get("/blog/create"))
                .andExpect(view().name("/blog/create-blog"))
                .andExpect(model().attributeExists("blog"));
    }

    @Test
    void saveBlogSuccess() throws Exception {
        mockMvc.perform(post("/blog/create"))
                .andExpect(view().name("/blog/create-blog"))
                .andExpect(model().attributeExists("blog"))
                .andExpect(model().attributeExists("message"));

        verify(blogService).save(any(Blog.class));
    }

    @Test
    void viewBlog() throws Exception {
        when(blogService.findById(id)).thenReturn(blog);

        mockMvc.perform(get("/blog/view/{id}", id))
                .andExpect(view().name("/blog/view-blog"))
                .andExpect(model().attribute("blog", blog));

        verify(blogService).findById(id);
    }

    @Test
    void showEditForm() throws Exception {
        when(blogService.findById(id)).thenReturn(blog);

        mockMvc.perform(get("/blog/edit/{id}", id))
                .andExpect(view().name("/blog/edit-blog"))
                .andExpect(model().attribute("blog", blog));

        verify(blogService).findById(id);
    }

    @Test
    void editBlogSuccess() throws Exception {
        mockMvc.perform(post("/blog/edit"))
                .andExpect(view().name("/blog/edit-blog"))
                .andExpect(model().attributeExists("blog"))
                .andExpect(model().attributeExists("message"));

        verify(blogService).save(any(Blog.class));
    }

    @Test
    void showDeleteForm() throws Exception {
        when(blogService.findById(id)).thenReturn(blog);

        mockMvc.perform(get("/blog/delete/{id}", id))
                .andExpect(view().name("/blog/delete-blog"))
                .andExpect(model().attribute("blog", blog));

        verify(blogService).findById(id);
    }

    @Test
    void deleteBlogSuccess() throws Exception {
        mockMvc.perform(post("/blog/delete").param("id", id + ""))
                .andExpect(view().name("redirect:/"));

        verify(blogService).delete(id);
    }
}

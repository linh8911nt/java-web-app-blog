package com.codegym.controller.impl;

import com.codegym.controller.PostController;
import com.codegym.controller.BlogControllerImplTestConfig;
import com.codegym.model.Post;
import com.codegym.model.Category;
import com.codegym.service.PostService;
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
public class PostApiControllerImplTest {

    static private Long id = 1l;
    static private String title;
    static private String summary;
    static private String content;
    static private LocalDate createDate = LocalDate.now();

    static private Category category;
    static private List<Category> categories;
    static private Iterable<Category> categoryIterable;

    static private Post post;
    static private List<Post> posts;
    static private Page<Post> blogsPage;

    static private Pageable pageable;

    static private Post emptyPost;
    static private List<Post> emptyPosts;
    static private Page<Post> emptyBlogsPage;

    static{
        title = "blogtitle";
        summary = "post summary";
        content = "post content";

        category = new Category("category");
        category.setId(1l);
//        categories = Arrays.asList(category);
//        categoryIterable = new PageImpl<>(categories);

        post = new Post(title, summary, content, createDate, category);
        post.setId(id);

        posts = Arrays.asList(post);
        blogsPage = new PageImpl<>(posts);

        emptyPost = new Post();
        emptyPosts = new ArrayList<>();
        emptyBlogsPage = new PageImpl<>(emptyPosts);

        pageable = new PageRequest(0, 2);
    }

    @Autowired
    private PostService postService;

//    @Autowired
//    private CategoryService categoryService;

    @Autowired
    private PostController postController;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setCustomArgumentResolvers(pageableHandlerMethodArgumentResolver)
                .build();
    }

    @AfterEach
    void resetAllMockedObject(){
        Mockito.reset(postService);
    }

    @Test
    void listBlogs() throws Exception {
        when(postService.findAll(pageable)).thenReturn(blogsPage);

        mockMvc.perform(get("/")
                .param("page", pageable.getPageNumber() + "")
                .param("size", pageable.getPageSize() + ""))
                .andExpect(view().name("/index"))
                .andExpect(model().attribute("posts", blogsPage));

        verify(postService).findAll(pageable);
    }

    @Test
    void showCreateForm() throws Exception {
        mockMvc.perform(get("/post/create"))
                .andExpect(view().name("/post/create-post"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    void saveBlogSuccess() throws Exception {
        mockMvc.perform(post("/post/create"))
                .andExpect(view().name("/post/create-post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("message"));

        verify(postService).save(any(Post.class));
    }

    @Test
    void viewBlog() throws Exception {
        when(postService.findById(id)).thenReturn(post);

        mockMvc.perform(get("/post/view/{id}", id))
                .andExpect(view().name("/post/view-post"))
                .andExpect(model().attribute("post", post));

        verify(postService).findById(id);
    }

    @Test
    void showEditForm() throws Exception {
        when(postService.findById(id)).thenReturn(post);

        mockMvc.perform(get("/post/edit/{id}", id))
                .andExpect(view().name("/post/edit-post"))
                .andExpect(model().attribute("post", post));

        verify(postService).findById(id);
    }

    @Test
    void editBlogSuccess() throws Exception {
        mockMvc.perform(post("/post/edit"))
                .andExpect(view().name("/post/edit-post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("message"));

        verify(postService).save(any(Post.class));
    }

    @Test
    void showDeleteForm() throws Exception {
        when(postService.findById(id)).thenReturn(post);

        mockMvc.perform(get("/post/delete/{id}", id))
                .andExpect(view().name("/post/delete-post"))
                .andExpect(model().attribute("post", post));

        verify(postService).findById(id);
    }

    @Test
    void deleteBlogSuccess() throws Exception {
        mockMvc.perform(post("/post/delete").param("id", id + ""))
                .andExpect(view().name("redirect:/"));

        verify(postService).delete(id);
    }
}

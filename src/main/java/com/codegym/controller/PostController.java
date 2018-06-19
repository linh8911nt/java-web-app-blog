package com.codegym.controller;

import com.codegym.model.Post;
import com.codegym.model.PostForm;
import com.codegym.model.Category;
import com.codegym.service.PostService;
import com.codegym.service.CategoryService;
import com.codegym.utils.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

//    @GetMapping("/")
//    public ModelAndView showIndex(){
//        ModelAndView modelAndView = new ModelAndView("/index");
//        return modelAndView;
//    }

    @GetMapping("/create")
    public ModelAndView showFormCreateBlog(){
        ModelAndView modelAndView = new ModelAndView("/blog/create-blog");
        modelAndView.addObject("postForm", new PostForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView crateBlog(@ModelAttribute("postForm") PostForm postForm){
        try {
            String randomCode = UUID.randomUUID().toString();
            String originFileName = postForm.getImage().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
            postForm.getImage().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));

            Post post = new Post();
            post.setTitle(postForm.getTitle());
            post.setSummary(postForm.getSummary());
            post.setContent(postForm.getContent());
            post.setCreateDate(LocalDate.now());
            post.setCategory(postForm.getCategory());
            post.setImage(randomName);

            postService.save(post);
        } catch (IOException e){
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("/blog/create-blog");
        modelAndView.addObject("postForm", new PostForm());
        modelAndView.addObject("message", "Post saved successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView viewBlog(@PathVariable("id") Long id){
        Post post = postService.findById(id);

        PostForm postForm = new PostForm();
        postForm.setId(post.getId());
        postForm.setTitle(post.getTitle());
        postForm.setSummary(post.getSummary());
        postForm.setContent(post.getContent());
        postForm.setCreateDate(post.getCreateDate());
        postForm.setImageUrl(post.getImage());
        postForm.setCategory(post.getCategory());

        ModelAndView modelAndView = new ModelAndView("/blog/edit-blog");
        modelAndView.addObject("postForm", postForm);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id, @ModelAttribute("postForm") PostForm postForm){
        try {
            Post post = postService.findById(id);

            if (!postForm.getImage().isEmpty()){
                StorageUtils.removeFeature(post.getImage());

                String randomCode = UUID.randomUUID().toString();
                String originBlogName = postForm.getImage().getOriginalFilename();

                String randomName = randomCode + StorageUtils.getFileExtension(originBlogName);

                postForm.getImage().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));

                post.setImage(randomName);
                postForm.setImageUrl(randomName);
            }

            post.setTitle(postForm.getTitle());
            post.setSummary(postForm.getSummary());
            post.setContent(postForm.getContent());
            post.setCategory(postForm.getCategory());

            postService.save(post);
        } catch (IOException e){
            e.printStackTrace();
        }

        ModelAndView modelAndView = new ModelAndView("/blog/edit-blog");
        modelAndView.addObject("postForm", postForm);
        modelAndView.addObject("message", "Update Success!!!");
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView editBlog(@PathVariable("id") Long id){
        Post post = postService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/blog/view-blog");
        modelAndView.addObject("post", post);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id){
        Post post = postService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/blog/delete-blog");
        modelAndView.addObject("post", post);
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id") Long id){
        Post post = postService.findById(id);

        StorageUtils.removeFeature(post.getImage());
        postService.delete(post.getId());

        return "redirect:/";
    }
}

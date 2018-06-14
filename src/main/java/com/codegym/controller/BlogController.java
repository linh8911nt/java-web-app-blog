package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.BlogForm;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import com.codegym.utils.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Controller
//@RequestMapping("/admin")
public class BlogController {

    @Autowired
    BlogService blogService;

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

    @GetMapping("/")
    public ModelAndView showBlog(Pageable pageable){
        Page<Blog> blogs = blogService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/blog/create")
    public ModelAndView showFormCreateBlog(){
        ModelAndView modelAndView = new ModelAndView("/blog/create-blog");
        modelAndView.addObject("blogForm", new BlogForm());
        return modelAndView;
    }

    @PostMapping("/blog/create")
    public ModelAndView crateBlog(@ModelAttribute("blogForm") BlogForm blogForm){
        try {
            String randomCode = UUID.randomUUID().toString();
            String originFileName = blogForm.getImage().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
            blogForm.getImage().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));

            Blog blog = new Blog();
            blog.setTitle(blogForm.getTitle());
            blog.setSummary(blogForm.getSummary());
            blog.setContent(blogForm.getContent());
            blog.setCreateDate(LocalDate.now());
            blog.setCategory(blogForm.getCategory());
            blog.setImage(randomName);

            blogService.save(blog);
        } catch (IOException e){
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("/blog/create-blog");
        modelAndView.addObject("blogForm", new BlogForm());
        modelAndView.addObject("message", "Blog saved successfully");
        return modelAndView;
    }

    @GetMapping("/blog/view/{id}")
    public ModelAndView viewBlog(@PathVariable("id") Long id){
        Blog blog = blogService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/blog/view-blog");
        modelAndView.addObject("blog", blog);
        return modelAndView;
    }

    @GetMapping("/blog/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id){
        Blog blog = blogService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/blog/edit-blog");
        modelAndView.addObject("blog", blog);
        return modelAndView;
    }

    @PostMapping("/blog/edit")
    public ModelAndView editBlog(@ModelAttribute("blog") Blog blog){
        blogService.save(blog);

        ModelAndView modelAndView = new ModelAndView("/blog/edit-blog");
        modelAndView.addObject("blog", blog);
        modelAndView.addObject("message", "Update Success!!!");
        return modelAndView;
    }

    @GetMapping("/blog/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id){
        Blog blog = blogService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/blog/delete-blog");
        modelAndView.addObject("blog", blog);
        return modelAndView;
    }

    @PostMapping("/blog/delete")
    public String deleteBlog(@ModelAttribute("blog") Blog blog){
        blogService.delete(blog.getId());
        return "redirect:/";
    }
}

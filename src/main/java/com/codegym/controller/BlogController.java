package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
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
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("/index", "blogs", blogs);
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/blog/create")
    public ModelAndView showFormCreateBlog(ModelAndView modelAndView){
        modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }

    @PostMapping("/blog/create")
    public ModelAndView crateBlog(@ModelAttribute("blog") Blog blog){
        blogService.save(blog);

        ModelAndView modelAndView = new ModelAndView("/blog/create", "blog", new Blog());
        modelAndView.addObject("message", "Create Success!!!");
        return modelAndView;
    }

    @GetMapping("/blog/view/{id}")
    public ModelAndView viewBlog(@PathVariable("id") Long id){
        Blog blog = blogService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/blog/view", "blog", blog);
        return modelAndView;
    }

    @GetMapping("/blog/edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id){
        Blog blog = blogService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/blog/edit", "blog", blog);
        return modelAndView;
    }

    @PostMapping("/blog/edit")
    public ModelAndView editBlog(@ModelAttribute("blog") Blog blog){
        blogService.save(blog);

        ModelAndView modelAndView = new ModelAndView("/blog/edit", "blog", blog);
        modelAndView.addObject("message", "Update Success!!!");
        return modelAndView;
    }

    @GetMapping("/blog/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id){
        Blog blog = blogService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/blog/delete", "blog", blog);
        return modelAndView;
    }

    @PostMapping("/blog/delete")
    public String deleteBlog(@ModelAttribute("blog") Blog blog){
        blogService.delete(blog.getId());
        return "redirect:/";
    }
}

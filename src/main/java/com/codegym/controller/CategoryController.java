package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/category/list")
    public ModelAndView showCategory(){
        Iterable<Category> categories = categoryService.findAll();

        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/category/create")
    public ModelAndView showFormCreateCategory(){
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/category/create")
    public ModelAndView createCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("message", "Create Success!!!");
        return modelAndView;
    }

    @GetMapping("/category/edit/{id}")
    public ModelAndView showFormEditCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @PostMapping("/category/edit")
    public ModelAndView editCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("message", "Update success!!!");
        return modelAndView;
    }

    @GetMapping("/category/delete/{id}")
    public ModelAndView showFormDeleteCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/category/delete");
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @PostMapping("/category/delete")
    public String deleteCategory(@ModelAttribute("category") Category category){
        categoryService.delete(category.getId());

        return "redirect:/category/list";
    }
}

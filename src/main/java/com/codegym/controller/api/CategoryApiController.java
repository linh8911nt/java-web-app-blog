package com.codegym.controller.api;

import com.codegym.model.Category;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class CategoryApiController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/api/categories", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Category>> listCategories(){
        Iterable<Category> categories = categoryService.findAll();

        return new ResponseEntity<Iterable<Category>>(categories, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/view-category/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> viewCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);

        if (category == null){
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }
}

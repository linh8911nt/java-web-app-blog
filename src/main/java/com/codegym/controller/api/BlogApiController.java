package com.codegym.controller.api;

import com.codegym.model.Blog;
import com.codegym.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlogApiController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/api/blogs", method = RequestMethod.GET)
    public ResponseEntity<Page<Blog>> listAllBlogs(Pageable pageable){

        Page<Blog> blogPage = blogService.findAll(pageable);

        if (blogPage.getTotalElements() == 0){
            return new ResponseEntity<Page<Blog>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Blog>>(blogPage, HttpStatus.OK);
    }
}

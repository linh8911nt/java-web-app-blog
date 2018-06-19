package com.codegym.controller.api;

import com.codegym.model.Post;
import com.codegym.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class PostApiController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/api/posts", method = RequestMethod.GET)
    public ResponseEntity<Page<Post>> listAllBlogs(Pageable pageable){

        Page<Post> blogPage = postService.findAll(pageable);

        if (blogPage.getTotalElements() == 0){
            return new ResponseEntity<Page<Post>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<Post>>(blogPage, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/view/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> getBlog(@PathVariable("id") Long id){
        Post post = postService.findById(id);

        if (post == null){
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
}

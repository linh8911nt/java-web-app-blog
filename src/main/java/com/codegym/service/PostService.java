package com.codegym.service;

import com.codegym.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Page<Post> findAll(Pageable pageable);

    Post findById(Long id);

    Post save(Post post);

    void delete(Long id);
}

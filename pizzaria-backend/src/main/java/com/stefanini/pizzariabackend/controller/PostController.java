package com.stefanini.pizzariabackend.controller;

import com.stefanini.pizzariabackend.domain.Post;
import com.stefanini.pizzariabackend.service.PostService;
import com.stefanini.pizzariabackend.service.impl.PostServiceImpl;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidPageValueException;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostServiceImpl postServiceImpl) {
        this.postService = postServiceImpl;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Post createPost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestBody Post newPost
    ) {
        try {
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(postService.updatePost(id, newPost));
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(postService.deletePostById(id));
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }

    @GetMapping("/pagination/{current-page}/{page-size}")
    public ResponseEntity<?> getPaginatedPosts(
            @PathVariable("current-page") int currentPage,
            @PathVariable("page-size") int pageSize
    ) {
        try {
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(postService.getPaginatedPosts(currentPage, pageSize));
        } catch (InvalidPageValueException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }
}

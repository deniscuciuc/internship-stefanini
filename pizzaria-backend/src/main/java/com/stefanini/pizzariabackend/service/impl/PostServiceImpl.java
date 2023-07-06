package com.stefanini.pizzariabackend.service.impl;

import com.stefanini.pizzariabackend.domain.Post;
import com.stefanini.pizzariabackend.repo.PostRepository;
import com.stefanini.pizzariabackend.service.PostService;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import com.stefanini.pizzariabackend.service.impl.helper.ValuesChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post newPost) {
        verifyIdIfExistAndIfNotThrowException(id);
        Post oldPost = postRepository.findById(id).get();
        updatePostFields(oldPost, newPost);
        return postRepository.save(oldPost);
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Long deletePostById(Long id) {
        verifyIdIfExistAndIfNotThrowException(id);
        postRepository.deleteById(id);
        return id;
    }

    @Override
    public List<Post> getPaginatedPosts(int currentPage, int pageSize) {
        ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(currentPage, pageSize);

        Pageable paging = PageRequest.of(currentPage, pageSize);
        Page<Post> pagedResult = postRepository.findAll(paging);

        return pagedResult.getContent();
    }

    private void verifyIdIfExistAndIfNotThrowException(Long id) throws NotFoundException {
        boolean doesMenuItemExist = postRepository.existsById(id);
        if (!doesMenuItemExist) {
            log.error("Post with such id not found");
            throw new NotFoundException("Post with such id not found");
        }
    }

    private void updatePostFields(Post oldPost, Post newPost) {
        oldPost.setTitle(newPost.getTitle());
        oldPost.setSummary(newPost.getSummary());
        oldPost.setText(newPost.getText());
        oldPost.setImage(newPost.getImage());
    }
}

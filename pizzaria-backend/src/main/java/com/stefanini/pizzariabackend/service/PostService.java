package com.stefanini.pizzariabackend.service;

import com.stefanini.pizzariabackend.domain.Post;

import java.util.List;

/**
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface PostService {

    /**
     * Saves post.
     *
     * @param post to be saved
     * @return saved post
     */
    Post savePost(Post post);

    Post updatePost(Long id, Post newPost);

    /**
     * Finds all posts.
     *
     * @return all posts or empty list
     */
    List<Post> findAllPosts();

    /**
     * Deletes post by its id.
     *
     * @param id of post to be deleted
     * @return id of deleted post
     */
    Long deletePostById(Long id);

    List<Post> getPaginatedPosts(int currentPage, int pageSize);
}

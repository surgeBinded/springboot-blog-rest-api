package com.springbootblog.blog.service;

import com.springbootblog.blog.payload.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long id);

    PostDTO updatePost(PostDTO postDTO, Long id);

    void deletePost(Long id);
}

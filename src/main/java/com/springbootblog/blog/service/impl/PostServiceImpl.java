package com.springbootblog.blog.service.impl;

import com.springbootblog.blog.entity.Post;
import com.springbootblog.blog.payload.PostDTO;
import com.springbootblog.blog.repository.PostRepository;
import com.springbootblog.blog.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(final PostDTO postDTO) {
        // convert DTO to entity
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDTO postResponse = new PostDTO();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());

        return postResponse;
    }
}

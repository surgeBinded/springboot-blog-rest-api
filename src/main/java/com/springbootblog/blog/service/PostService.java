package com.springbootblog.blog.service;

import com.springbootblog.blog.payload.PostDTO;
import com.springbootblog.blog.payload.PostResponse;

public interface PostService {
    PostDTO createPost(final PostDTO postDTO);

    PostResponse getAllPosts(final int pageNo, final int pageSize, String sortBy);

    PostDTO getPostById(final Long id);

    PostDTO updatePost(final PostDTO postDTO, final Long id);

    void deletePost(final Long id);
}

package com.springbootblog.blog.service;

import com.springbootblog.blog.payload.PostDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

}

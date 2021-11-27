package com.springbootblog.blog.payload;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDTO> comments;
}
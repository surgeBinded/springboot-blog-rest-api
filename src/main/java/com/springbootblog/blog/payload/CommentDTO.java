package com.springbootblog.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty(message = "email should not be null or empty")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 1, message = "body should be at least 10 characters")
    private String body;
}

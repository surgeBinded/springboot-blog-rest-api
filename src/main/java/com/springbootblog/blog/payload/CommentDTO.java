package com.springbootblog.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record CommentDTO(
        Long id,
        @NotEmpty String name,
        @NotEmpty(message = "email should not be null or empty") @Email String email,
        @NotEmpty @Size(min = 1, message = "body should be at least 10 characters") String body) {
}

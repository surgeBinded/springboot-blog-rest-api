package com.springbootblog.blog.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public record PostDTO(long id,
                      @NotEmpty @Size(min = 2, message = "Post title should have at least 2 characters") String title,
                      @NotEmpty @Size(min = 10, message = "Post description should have at least 10 characters") String description,
                      @NotEmpty String content,
                      Set<CommentDTO> comments) {}
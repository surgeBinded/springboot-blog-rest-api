package com.springbootblog.blog.payload;

import java.util.List;

public record PostResponse(List<PostDTO> content,
                           int pageNo,
                           int pageSize,
                           long totalElements,
                           int totalPages,
                           boolean last) {
}

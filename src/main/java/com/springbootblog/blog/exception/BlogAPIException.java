package com.springbootblog.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlogAPIException extends RuntimeException{

    private final HttpStatus status;
    private final String message;

    public BlogAPIException(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPIException(final String message, final HttpStatus status, final String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}

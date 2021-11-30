package com.springbootblog.blog.payload;

public record LoginDTO(String usernameOrEmail, String password) {
}
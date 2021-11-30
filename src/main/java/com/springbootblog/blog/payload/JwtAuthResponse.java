package com.springbootblog.blog.payload;

public record JwtAuthResponse(String accessToken,
                              String tokenType
) {
    public JwtAuthResponse(final String accessToken) {
        this(accessToken, "Bearer");
    }
}

package com.springbootblog.blog.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}

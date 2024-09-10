package com.ecommerce.dto.response;

public class JwtResponseDto {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public JwtResponseDto(String token) {
        this.token = token;
    }
}

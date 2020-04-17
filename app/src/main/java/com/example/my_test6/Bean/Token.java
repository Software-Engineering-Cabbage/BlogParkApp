package com.example.my_test6.Bean;

public class Token {
    private String access_token;
    private String expires_in;
    private String token_type;

    public Token(String access_token, String expires_in, String token_type) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.token_type = token_type;
    }

    public Token() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}

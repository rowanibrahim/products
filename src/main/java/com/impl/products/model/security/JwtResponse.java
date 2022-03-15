package com.impl.products.model.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String userType;
    public JwtResponse(String jwttoken, String userType) {
        this.jwttoken = jwttoken;
        this.userType = userType;
    }
    public String getToken() {
        return this.jwttoken;
    }
}

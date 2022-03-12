package com.impl.products.controller.security;

import com.impl.products.model.user.UserPrincipal;
import org.springframework.security.core.Authentication;

public abstract class AbstractController {

    public UserPrincipal getUserDetails(Authentication authentication) {
        return (UserPrincipal) authentication.getPrincipal();
    }

}

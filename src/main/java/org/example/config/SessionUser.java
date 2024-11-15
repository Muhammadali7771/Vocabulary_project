package org.example.config;

import org.example.authuser.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionUser {
    public AuthUser getUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails){
            return userDetails.getAuthUser();
        }
        return null;
    }

    public Long getId(){
        return getUser().getId();
    }
}

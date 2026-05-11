package com.example.OnlineCourse.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityContextUser {

    private SecurityContextUser() {
    }

    public static int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getDetails() == null) {
            throw new IllegalStateException("Authenticated user details not found");
        }
        return Integer.parseInt(authentication.getDetails().toString());
    }

    public static String getCurrentRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities() == null) {
            throw new IllegalStateException("Authenticated user role not found");
        }
        return authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElseThrow(() -> new IllegalStateException("Authenticated user role not found"));
    }
}

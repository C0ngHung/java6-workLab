package org.conghung.lab01.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service("auth")
public class AuthService {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUsername() {
        return getAuthentication().getName();
    }

    public List<String> getRoles() {
        return getAuthentication().getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority().substring(5))
                .toList();
    }

    public boolean isAuthenticated() {
        String username = this.getUsername();
        return (username != null && !username.equals("anonymousUser"));
    }

    public boolean hasAnyRole(String... roleToCheck) {
        var grantedRoles = this.getRoles();
        return Stream.of(roleToCheck).anyMatch(grantedRoles::contains);
    }
}

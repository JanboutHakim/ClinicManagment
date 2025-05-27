package com.example.DocLib.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.security.auth.Subject;

public class UserPrincipleAuthenticationToken extends AbstractAuthenticationToken {
    private final UserPrincipleConfig principal;

    public UserPrincipleAuthenticationToken( UserPrincipleConfig principal) {
        super(principal.getAuthorities());
        this.principal=principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipleConfig getPrincipal() {
        return principal;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }
}

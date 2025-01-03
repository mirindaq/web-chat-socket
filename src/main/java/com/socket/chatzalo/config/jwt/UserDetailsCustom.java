package com.socket.chatzalo.config.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Custom implementation of UserDetails
public class UserDetailsCustom implements UserDetails {

    private String username;
    private String password;
    private String role;

    public UserDetailsCustom(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Return true if account is not expired (you can customize this if needed)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Return true if account is not locked (you can customize this if needed)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Return true if credentials are not expired (you can customize this if needed)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Return true if account is enabled (you can customize this if needed)
        return true;
    }
}

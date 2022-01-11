package com.example.sbks.dto;

import com.example.sbks.model.StatusAuth;
import com.example.sbks.model.UserAuth;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class UserSecurity implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    public UserSecurity(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUserAuth(UserAuth userAuth) {
        return new User(
                userAuth.getEmail(),
                userAuth.getPassword(),
                userAuth.getStatusAuth().equals(StatusAuth.ACTIVE),
                userAuth.getStatusAuth().equals(StatusAuth.ACTIVE),
                userAuth.getStatusAuth().equals(StatusAuth.ACTIVE),
                userAuth.getStatusAuth().equals(StatusAuth.ACTIVE),
                userAuth.getRole().getAuthorities()
        );
    }
}


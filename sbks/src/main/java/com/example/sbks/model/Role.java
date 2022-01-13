package com.example.sbks.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    USER(Set.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.DELETED_MESSAGE_READ,
            Permission.DOWNLOAD_HISTORY_READ, Permission.MESSAGE_SEND)),
    MODERATOR(Set.of(Permission.MESSAGE_READ, Permission.DELETED_MESSAGE_READ, Permission.DOWNLOAD_HISTORY_READ,
            Permission.DELETED_MESSAGE_WRITE)),
    ADMIN(Set.of(Permission.MESSAGE_READ, Permission.DELETED_MESSAGE_READ, Permission.DOWNLOAD_HISTORY_READ,
            Permission.MESSAGE_SEND, Permission.DELETED_MESSAGE_WRITE, Permission.MESSAGE_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(Permission::getPermission)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}

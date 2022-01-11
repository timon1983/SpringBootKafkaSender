package com.example.sbks.model;

public enum Permission {

    MESSAGE_READ("message:read"),
    MESSAGE_WRITE("message:write"),
    DOWNLOAD_HISTORY_READ("download-history:read"),
    DELETED_MESSAGE_READ("deleted-message:read"),
    DELETED_MESSAGE_WRITE("deleted-message:write"),
    MESSAGE_SEND("message-send:read");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}

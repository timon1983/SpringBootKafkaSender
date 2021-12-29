package com.example.sbks.model;

public enum Permission {

    MESSAGE_READ("message:read"),
    MESSAGE_WRITE("message:write"),
    DOWNLOAD_HISTORY_READ("download-history:write"),
    DELETED_MESSAGE_READ("deleted-message-read:read"),
    DELETED_MESSAGE_RESTORE("deleted-message-read:write"),
    DELETED_MESSAGE_DELETE("deleted-message-read:write"),
    CLEAN_DELETED_MESSAGES("deleted-message-read:write"),
    MESSAGE_SEND("message:read");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}

package com.aman.Velora.user.exception.role;

public class RoleAlreadyInactiveException extends RuntimeException {
    public RoleAlreadyInactiveException(String message) {
        super(message);
    }
}

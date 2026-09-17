package com.aman.Velora.user_service.exception.role;

public class RoleAlreadyInactiveException extends RuntimeException {
    public RoleAlreadyInactiveException(String message) {
        super(message);
    }
}

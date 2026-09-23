package com.aman.Velora.auth_service.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid email or password.");
    }
}

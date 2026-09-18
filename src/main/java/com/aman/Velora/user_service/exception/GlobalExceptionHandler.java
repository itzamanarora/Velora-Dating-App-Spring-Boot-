package com.aman.Velora.user_service.exception;

import com.aman.Velora.user_service.exception.role.RoleAlreadyInactiveException;
import com.aman.Velora.user_service.exception.role.RoleNotFoundException;
import com.aman.Velora.user_service.exception.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception exception) {
        log.error("Unexpected internal server error: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message("An unexpected error occurred. Please try again later.")
                                .code("INTERNAL_SERVER_ERROR")
                                .build()
                );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        String message = exception.getMostSpecificCause().getMessage();
        String errorMessage = "Database constraint violation";

        if (message != null) {
            if (message.contains("uk_role_name")) errorMessage = "Role already exists.";
            if (message.contains("uk_user_email")) errorMessage = "Email already exists.";
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT.value())
                .message(errorMessage)
                .code("DATA_INTEGRITY_VIOLATION")
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFound(RoleNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .message(exception.getMessage())
                                .code("NOT_FOUND")
                                .build()
                );
    }

    @ExceptionHandler(RoleAlreadyInactiveException.class)
    public ResponseEntity<ErrorResponse> handleRoleAlreadyInactive(RoleAlreadyInactiveException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.CONFLICT.value())
                                .message(exception.getMessage())
                                .code("ROLE_ALREADY_INACTIVE")
                                .build()
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .timestamp(Instant.now())
                                .status(HttpStatus.CONFLICT.value())
                                .message(exception.getMessage())
                                .code("USER_NOT_FOUND")
                                .build()
                );
    }
}

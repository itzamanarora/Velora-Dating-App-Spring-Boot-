package com.aman.Velora.auth_service.repository;

import com.aman.Velora.auth_service.models.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, UUID> {
}

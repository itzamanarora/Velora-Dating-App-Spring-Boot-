package com.aman.Velora.user_service.repository;

import com.aman.Velora.user_service.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Page<Role> findByIsActiveTrue(Pageable pageable);

    Optional<Role> findByIdAndIsActiveTrue(UUID roleId);

    Optional<Role> findByNameIgnoreCaseAndIsActiveTrue(String name);

    Page<Role> findByNameContainingIgnoreCaseAndIsActiveTrueOrDisplayNameContainingIgnoreCaseAndIsActiveTrue(
            String name, String displayName, Pageable pageable
    );
}

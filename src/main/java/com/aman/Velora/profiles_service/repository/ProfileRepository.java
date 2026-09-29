package com.aman.Velora.profiles_service.repository;

import com.aman.Velora.profiles_service.models.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUserId(UUID userId);

    Page<Profile> findAllByUserId(UUID userId, Pageable pageable);

    Page<Profile> findAll(Pageable pageable);

    Page<Profile> findAllByUserIdNot(UUID userId, Pageable pageable);

//    Optional<Profile> findByUserId(UUID userId);
}

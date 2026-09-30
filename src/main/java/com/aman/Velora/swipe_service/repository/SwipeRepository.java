package com.aman.Velora.swipe_service.repository;

import com.aman.Velora.swipe_service.models.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, UUID> {
    Optional<Swipe> findBySwiperIdAndSwipeeId(UUID swiperId, UUID swipeeId);
}

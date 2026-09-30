package com.aman.Velora.swipe_service.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "swipes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_swipes_swiper_swipee", columnNames = {"swiper_id", "swipee_id"})
        },
        indexes = {
                @Index(name = "idx_swipes_swiper_swipee", columnList = "swiper_id, swipee_id"),
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Swipe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "swiper_id", nullable = false)
    private UUID swiperId;

    @Column(name = "swipee_id", nullable = false)
    private UUID swipeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "swipe_type", nullable = false)
    private SwipeDirection swipeType;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;
}

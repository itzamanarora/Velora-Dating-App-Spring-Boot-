package com.aman.Velora.swipe_service.dto.request;

import com.aman.Velora.swipe_service.models.SwipeDirection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwipeRequestDTO {

    @NotNull(message = "Swipee ID is required")
    private UUID swipeeId;

    @NotNull(message = "Swipe type is required")
    private SwipeDirection swipeType;
}

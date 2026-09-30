package com.aman.Velora.swipe_service.mapper;

import com.aman.Velora.swipe_service.dto.request.SwipeRequestDTO;
import com.aman.Velora.swipe_service.models.Swipe;

public class SwipeDTOMapper {

    public static Swipe maptoSwipe(SwipeRequestDTO swipeRequestDTO) {
        return Swipe.builder()
                .swipeeId(swipeRequestDTO.getSwipeeId())
                .swipeType(swipeRequestDTO.getSwipeType())
                .build();
    }
}

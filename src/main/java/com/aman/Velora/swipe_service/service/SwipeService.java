package com.aman.Velora.swipe_service.service;

import com.aman.Velora.swipe_service.dto.request.SwipeRequestDTO;
import com.aman.Velora.swipe_service.dto.response.SwipeResponseDTO;

import java.util.UUID;

public interface SwipeService {

    SwipeResponseDTO swipe(UUID profileId, SwipeRequestDTO swipeRequestDTO);
}

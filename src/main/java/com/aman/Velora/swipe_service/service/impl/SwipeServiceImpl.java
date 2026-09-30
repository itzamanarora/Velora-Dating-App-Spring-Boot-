package com.aman.Velora.swipe_service.service.impl;

import com.aman.Velora.profiles_service.exception.ProfileNotFoundException;
import com.aman.Velora.profiles_service.models.Profile;
import com.aman.Velora.profiles_service.repository.ProfileRepository;
import com.aman.Velora.swipe_service.dto.request.SwipeRequestDTO;
import com.aman.Velora.swipe_service.dto.response.SwipeResponseDTO;
import com.aman.Velora.swipe_service.mapper.SwipeDTOMapper;
import com.aman.Velora.swipe_service.models.Swipe;
import com.aman.Velora.swipe_service.models.SwipeDirection;
import com.aman.Velora.swipe_service.repository.SwipeRepository;
import com.aman.Velora.swipe_service.service.SwipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class SwipeServiceImpl implements SwipeService {

    private final SwipeRepository swipeRepository;
    private final ProfileRepository profileRepository;

    public SwipeServiceImpl(SwipeRepository swipeRepository, ProfileRepository profileRepository) {
        this.swipeRepository = swipeRepository;
        this.profileRepository = profileRepository;
    }


    @Override
    @Transactional
    public SwipeResponseDTO swipe(UUID profileId, SwipeRequestDTO swipeRequestDTO) {

        log.info(
                "Processing swipe: swiperId={}, swipeeId={}, swipeType={}",
                profileId,
                swipeRequestDTO.getSwipeeId(),
                swipeRequestDTO.getSwipeType()
        );

        Profile swiper = profileRepository.findByUserId(profileId).orElseThrow(() ->
                new ProfileNotFoundException("Profile not found!"));

        if (!profileRepository.existsById(swipeRequestDTO.getSwipeeId())) {
            throw new ProfileNotFoundException("Profile not found!");
        }

        Swipe swipe = SwipeDTOMapper.maptoSwipe(swipeRequestDTO);
        swipe.setSwiperId(swiper.getId());
        swipeRepository.save(swipe);

        boolean isMatch = swipeRequestDTO.getSwipeType() == SwipeDirection.LIKE
                && swipeRepository.findBySwiperIdAndSwipeeId(swipeRequestDTO.getSwipeeId(), swiper.getId())
                .map(s -> s.getSwipeType() == SwipeDirection.LIKE)
                .orElse(false);


        log.info("Swiped Successfully!");

        return SwipeResponseDTO.builder()
                .isMatch(isMatch)
                .build();
    }
}

package com.aman.Velora.user_service.service.user.impl;

import com.aman.Velora.user_service.dto.page.PageResponseDTO;
import com.aman.Velora.user_service.dto.user.UserRequestDTO;
import com.aman.Velora.user_service.dto.user.UserResponseDTO;
import com.aman.Velora.user_service.exception.user.UserNotFoundException;
import com.aman.Velora.user_service.mapper.PageDTOMapper;
import com.aman.Velora.user_service.mapper.UserDTOMapper;
import com.aman.Velora.user_service.models.User;
import com.aman.Velora.user_service.repository.UserRepository;
import com.aman.Velora.user_service.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO getUserById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found."));
        log.info("Getting user by id: {}", user.getId());
        return UserDTOMapper.mapToUserResponse(user);
    }

    @Override
    public PageResponseDTO<UserResponseDTO> getAllUsers(int page, int pageSize, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());

        Page<UserResponseDTO> userPage;

        if (search == null && search.isEmpty())
            userPage = userRepository.findAll(pageable).map(UserDTOMapper::mapToUserResponse);
        else
            userPage = userRepository.findByEmailContainingIgnoreCase(search, pageable).map(UserDTOMapper::mapToUserResponse);

        return PageDTOMapper.mapToPageResponse(userPage);
    }

    @Override
    public UserResponseDTO updateUserBy(UUID userId, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found"));

        return null;
    }
}

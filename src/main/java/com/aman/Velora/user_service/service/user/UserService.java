package com.aman.Velora.user_service.service.user;

import com.aman.Velora.user_service.dto.page.PageResponseDTO;
import com.aman.Velora.user_service.dto.user.UserRequestDTO;
import com.aman.Velora.user_service.dto.user.UserResponseDTO;

import java.util.UUID;

public interface UserService {
    UserResponseDTO getUserById(UUID id);

    PageResponseDTO<UserResponseDTO> getAllUsers(int page, int pageSize, String sortBy, String search);

    UserResponseDTO updateUserBy(UUID userId, UserRequestDTO userRequestDTO);

}

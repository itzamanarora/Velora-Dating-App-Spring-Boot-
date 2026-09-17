package com.aman.Velora.user_service.mapper;

import com.aman.Velora.user_service.dto.user.UserRequestDTO;
import com.aman.Velora.user_service.dto.user.UserResponseDTO;
import com.aman.Velora.user_service.models.User;

public class UserDTOMapper {

    public static User mapToUser(UserRequestDTO userRequestDTO) {
        return User.builder()
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();
    }

    public static UserResponseDTO mapToUserResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(RoleDTOMapper.mapToRoleResponse(user.getRole()))
                .createdAt(user.getCreatedAt())
                .build();
    }
}

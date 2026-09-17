package com.aman.Velora.user.mapper;

import com.aman.Velora.user.dto.user.UserRequestDTO;
import com.aman.Velora.user.dto.user.UserResponseDTO;
import com.aman.Velora.user.models.User;

import java.util.List;

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

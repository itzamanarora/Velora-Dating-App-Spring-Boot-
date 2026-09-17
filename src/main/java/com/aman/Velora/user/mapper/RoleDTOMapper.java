package com.aman.Velora.user.mapper;

import com.aman.Velora.user.dto.role.RoleRequestDTO;
import com.aman.Velora.user.dto.role.RoleResponseDTO;
import com.aman.Velora.user.models.Role;

import java.util.List;

public class RoleDTOMapper {

    public static Role mapToRole(RoleRequestDTO roleRequestDTO) {
        return Role.builder()
                .name(roleRequestDTO.getName())
                .build();
    }

    public static RoleResponseDTO mapToRoleResponse(Role role) {
        return RoleResponseDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .displayName(role.getDisplayName())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }
}

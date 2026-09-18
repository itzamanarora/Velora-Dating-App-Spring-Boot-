package com.aman.Velora.user_service.service.role;

import com.aman.Velora.user_service.dto.page.PageResponseDTO;
import com.aman.Velora.user_service.dto.role.RoleRequestDTO;
import com.aman.Velora.user_service.dto.role.RoleResponseDTO;

import java.util.UUID;

public interface RoleService {
    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);

    PageResponseDTO<RoleResponseDTO> getAllRoles(int page, int pageSize, String sortBy, String search);

    RoleResponseDTO getRoleById(UUID roleId);

    RoleResponseDTO updateRoleById(UUID roleId, RoleRequestDTO roleRequestDTO);

    void deleteRole(UUID id);
}

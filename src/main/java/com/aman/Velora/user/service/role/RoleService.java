package com.aman.Velora.user.service.role;

import com.aman.Velora.user.dto.page.PageResponseDTO;
import com.aman.Velora.user.dto.role.RoleRequestDTO;
import com.aman.Velora.user.dto.role.RoleResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface RoleService {
    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);

    PageResponseDTO<RoleResponseDTO> getAllRoles(int page, int pageSize, String sortBy, String search);

    void deleteRole(UUID id);
}

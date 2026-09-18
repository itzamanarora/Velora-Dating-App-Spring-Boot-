package com.aman.Velora.user_service.service.role.impl;

import com.aman.Velora.user_service.dto.page.PageResponseDTO;
import com.aman.Velora.user_service.dto.role.RoleRequestDTO;
import com.aman.Velora.user_service.dto.role.RoleResponseDTO;
import com.aman.Velora.user_service.exception.role.RoleAlreadyInactiveException;
import com.aman.Velora.user_service.exception.role.RoleNotFoundException;
import com.aman.Velora.user_service.mapper.PageDTOMapper;
import com.aman.Velora.user_service.mapper.RoleDTOMapper;
import com.aman.Velora.user_service.models.Role;
import com.aman.Velora.user_service.repository.RoleRepository;
import com.aman.Velora.user_service.service.role.RoleService;
import com.aman.Velora.user_service.utils.RoleNameUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO) {
        String roleName = RoleNameUtils.normalizeRoleName(roleRequestDTO.getName());
        String displayName = RoleNameUtils.generateDisplayName(roleRequestDTO.getName());

        Role role = RoleDTOMapper.mapToRole(roleRequestDTO);
        role.setName(roleName);
        role.setDisplayName(displayName);

        Role savedRole = roleRepository.save(role);
        log.info("Role created successfully: {}", roleName);
        return RoleDTOMapper.mapToRoleResponse(savedRole);
    }

    @Override
    public PageResponseDTO<RoleResponseDTO> getAllRoles(int page, int pageSize, String sortBy, String search) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());

        Page<RoleResponseDTO> rolePage;

        if (search == null || search.isEmpty())
            rolePage = roleRepository.findByIsActiveTrue(pageable).map(RoleDTOMapper::mapToRoleResponse);
        else {
            rolePage = roleRepository.findByNameContainingIgnoreCaseAndIsActiveTrueOrDisplayNameContainingIgnoreCaseAndIsActiveTrue(
                    search, search, pageable
            ).map(RoleDTOMapper::mapToRoleResponse);
        }
        return PageDTOMapper.mapToPageResponse(rolePage);
    }

    @Override
    public RoleResponseDTO getRoleById(UUID roleId) {
        Role role = roleRepository.findByIdAndIsActiveTrue(roleId).orElseThrow(() ->
                new RoleNotFoundException("Role not found"));
        log.info("Getting role by id: {}", role.getId());
        return RoleDTOMapper.mapToRoleResponse(role);
    }

    @Override
    public RoleResponseDTO updateRoleById(UUID roleId, RoleRequestDTO roleRequestDTO) {
        return null;
    }

    @Override
    public void deleteRole(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() ->
                new RoleNotFoundException("Role not found with this ID"));

        if (!role.isActive())
            throw new RoleAlreadyInactiveException("Role is already inactive.");

        role.setActive(false);
        roleRepository.save(role);

        log.info("Role deactivated successfully: {}", role.getName());
    }
}

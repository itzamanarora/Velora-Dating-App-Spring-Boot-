package com.aman.Velora.user_service.controller;

import com.aman.Velora.user_service.dto.page.PageResponseDTO;
import com.aman.Velora.user_service.dto.role.RoleRequestDTO;
import com.aman.Velora.user_service.dto.role.RoleResponseDTO;
import com.aman.Velora.user_service.service.role.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.createRole(roleRequestDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponseDTO<RoleResponseDTO>> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(roleService.getAllRoles(page, pageSize, sortBy, search));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}

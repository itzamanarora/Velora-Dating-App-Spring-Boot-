package com.aman.Velora.user.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequestDTO {

    @NotBlank(message = "Role name is required")
    @Size(min = 2, max = 75, message = "Role name must be between 2 and 75 characters.")
    private String name;
}

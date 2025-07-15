package com.PrintLab.dto;

import com.PrintLab.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionDto {
    private Long id;
    private String name;
    private Boolean value;
}

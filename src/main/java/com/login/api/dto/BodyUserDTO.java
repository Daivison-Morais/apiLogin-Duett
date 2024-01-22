package com.login.api.dto;

import com.login.api.enums.UserRole;

public record BodyUserDTO(
        String email,
        String name,
        String password,
        String cpf,
        UserRole role
) {}

package com.login.api.dto;

public record ChangePasswordDTO(
    String currentPassword,
    String newPassword,
    String email
) {}

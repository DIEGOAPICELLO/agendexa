package com.agendexa.dto;

import com.agendexa.enums.UserRole;

public record RegisterRequestDTO (String name, String email, String password, UserRole userRole) {
}

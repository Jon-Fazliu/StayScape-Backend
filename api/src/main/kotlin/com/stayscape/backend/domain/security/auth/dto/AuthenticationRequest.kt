package com.stayscape.backend.domain.security.auth.dto

import jakarta.validation.constraints.NotBlank

data class AuthenticationRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String
)

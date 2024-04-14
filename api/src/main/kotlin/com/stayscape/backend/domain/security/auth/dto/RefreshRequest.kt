package com.stayscape.backend.domain.security.auth.dto

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @field:NotBlank
    val refreshToken: String
)
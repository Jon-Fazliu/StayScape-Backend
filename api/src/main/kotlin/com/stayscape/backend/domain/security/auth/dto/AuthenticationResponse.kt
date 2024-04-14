package com.stayscape.backend.domain.security.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.stayscape.backend.domain.user.dto.UserResponseDto

data class AuthenticationResponse(
    val token: String,
    val refreshToken: String,
    val user: UserResponseDto
)
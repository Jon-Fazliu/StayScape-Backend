package com.stayscape.backend.domain.security.auth.dto

import jakarta.validation.constraints.Email

data class RequestResetPassword(
    @field:Email
    val email: String
)
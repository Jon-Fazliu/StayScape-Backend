package com.stayscape.backend.domain.security.auth.dto

import jakarta.validation.constraints.Pattern

data class CompleteRequestPassword(
    @field:Pattern(
        regexp = "^(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*\\p{N})(?=.*[^\\p{Alnum}])[\\p{ASCII}\\p{L}\\p{N}\\p{S}]{8,255}\$",
        message = "Password must be at least 8 characters, contain uppercase and lowercase letters, and have both a number and a special character."
    )
    val password: String
)
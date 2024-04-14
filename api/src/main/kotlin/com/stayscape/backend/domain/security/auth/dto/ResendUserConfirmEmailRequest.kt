package com.stayscape.backend.domain.security.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class ResendUserConfirmEmailRequest(
    val userId: Int
)

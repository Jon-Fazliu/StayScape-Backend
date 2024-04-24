package com.stayscape.backend.domain.security.auth.dto

import com.stayscape.backend.domain.user.address.AddressDto
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

data class AffiliateRegisterRequest(
    @field:NotBlank
    @field:Length(max = 255)
    val name: String,

    @field:NotBlank
    @field:Email
    @field:Length(max = 255)
    val email: String,

    @field:Pattern(
        regexp = "^(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*\\p{N})(?=.*[^\\p{Alnum}])[\\p{ASCII}\\p{L}\\p{N}\\p{S}]{8,255}\$",
        message = "Password must be at least 8 characters, contain uppercase and lowercase letters, and have both a number and a special character."
    )
    val password: String,

    @field:NotBlank
    @field:Length(max = 255)
    val phoneNumber: String,

    @field:Valid
    val address: AddressDto,

    val website: String?

    )

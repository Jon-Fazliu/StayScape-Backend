package com.stayscape.backend.domain.security.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.stayscape.backend.domain.user.address.AddressDto
import com.stayscape.backend.domain.validators.NullOrNotBlank
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length
import java.time.Instant

data class RegisterRequest(
    @field:NotBlank
    @field:Length(max = 255)
    val firstName: String,

    @field:NotBlank
    @field:Length(max = 255)
    val lastName: String,

    @field:NotBlank
    @field:Email
    @field:Length(max = 255)
    val email: String,

    @field:Pattern(
        regexp = "^(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*\\p{N})(?=.*[^\\p{Alnum}])[\\p{ASCII}\\p{L}\\p{N}\\p{S}]{8,255}\$",
        message = "Password must be at least 8 characters, contain uppercase and lowercase letters, and have both a number and a special character."
    )
    val password: String,

    @field:NotNull
    val dateOfBirth: Instant,

    @field:NotBlank
    @field:Length(max = 255)
    val phoneNumber: String,

    @field:Valid
    val address: AddressDto,

)

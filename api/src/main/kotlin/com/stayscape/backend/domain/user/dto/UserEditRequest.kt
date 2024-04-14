package com.stayscape.backend.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.stayscape.backend.domain.user.address.AddressDto
import com.stayscape.backend.domain.validators.NullOrNotBlank
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length
import java.time.Instant

data class UserEditRequest(
    @field:NotBlank
    @field:Length(max = 255)
    val firstName: String,

    @field:NotBlank
    @field:Length(max = 255)
    val lastName: String,

    @field:NotBlank
    @field:Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$",
        message = "Email must be valid"
    )
    @field:Length(max = 255)
    val email: String,

    @field:NotNull
    val dateOfBirth: Instant,

    @field:NotBlank
    @field:Length(max = 255)
    val phoneNumber: String,

    @field:Valid
    val address: AddressDto,
)

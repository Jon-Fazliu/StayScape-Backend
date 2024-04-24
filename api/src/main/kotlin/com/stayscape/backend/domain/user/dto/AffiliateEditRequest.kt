package com.stayscape.backend.domain.user.dto

import com.stayscape.backend.domain.user.address.AddressDto
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length
import java.time.Instant

data class AffiliateEditRequest(
    @field:NotBlank
    @field:Length(max = 255)
    val name: String,

    @field:NotBlank
    @field:Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$",
        message = "Email must be valid"
    )
    @field:Length(max = 255)
    val email: String,

    @field:NotBlank
    @field:Length(max = 255)
    val phoneNumber: String,

    @field:Valid
    val address: AddressDto,
)

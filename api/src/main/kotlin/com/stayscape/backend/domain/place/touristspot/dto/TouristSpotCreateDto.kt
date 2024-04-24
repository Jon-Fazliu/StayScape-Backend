package com.stayscape.backend.domain.place.touristspot.dto

import com.stayscape.backend.domain.place.property.PropertyType
import com.stayscape.backend.domain.user.address.AddressDto
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import java.math.BigDecimal

data class TouristSpotCreateDto(
    @field:NotNull
    @field:Max(90)
    @field:Min(-90)
    val latitude: BigDecimal,

    @field:NotNull
    @field:Max(180)
    @field:Min(-180)
    val longitude: BigDecimal,

    @field:Valid
    val address: AddressDto,

    @field:NotBlank
    @field:Length(max = 255)
    val phoneNumber: String,

    @field:NotBlank
    @field:Length(max = 255)
    val name: String,

    @field:NotBlank
    @field:Length(max = 1000)
    val description: String,

    @field:Length(max = 255)
    val website: String? = null
    )

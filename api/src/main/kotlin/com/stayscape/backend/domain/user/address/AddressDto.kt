package com.stayscape.backend.domain.user.address

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class AddressDto(
    @field:Length(max = 255)
    @field:NotBlank
    val street: String?,
    @field:NotBlank
    @field:Length(max = 255)
    val streetNumber: String?,
    @field:NotBlank
    @field:Length(max = 255)
    val postalCode: String?,
    @field:NotBlank
    @field:Length(max = 255)
    val city: String?,
    @field:NotBlank
    @field:Length(max = 255)
    val country: String?
) {
    @JsonIgnore
    fun isAllNullOrEmpty() : Boolean {
        return street.isNullOrEmpty() && streetNumber.isNullOrEmpty() && postalCode.isNullOrEmpty() && city.isNullOrEmpty() && country.isNullOrEmpty()
    }

    @JsonIgnore
    fun hasEmpty() : Boolean {
        return street.isNullOrEmpty() || streetNumber.isNullOrEmpty() || postalCode.isNullOrEmpty() || city.isNullOrEmpty() || country.isNullOrEmpty()
    }
    
    companion object {
        fun of(address: Address) : AddressDto {
            return AddressDto(
                street = address.street,
                streetNumber = address.streetNumber,
                postalCode = address.postalCode,
                city = address.city,
                country = address.country
            )
        }

        @JsonIgnore
        fun trimmed(address: AddressDto) : AddressDto {
            return AddressDto(
                streetNumber = address.streetNumber?.trim(),
                street = address.street?.trim(),
                postalCode = address.postalCode?.trim(),
                city = address.city?.trim(),
                country = address.country?.trim()
            )
        }
    }
}

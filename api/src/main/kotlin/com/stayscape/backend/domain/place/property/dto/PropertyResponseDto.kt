package com.stayscape.backend.domain.place.property.dto

import com.stayscape.backend.domain.place.property.Property
import com.stayscape.backend.domain.place.property.PropertyType
import com.stayscape.backend.domain.user.address.AddressDto
import java.math.BigDecimal

data class PropertyResponseDto(
    val address: AddressDto,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val name: String,
    val description: String,
    val phoneNumber: String?,
    val website: String?,
    val type: PropertyType
) {
    companion object {
        fun of(property: Property): PropertyResponseDto {
            return PropertyResponseDto(
                address = AddressDto.trimmed(AddressDto.of(property.place!!.address!!)),
                latitude = property.place!!.latitude!!,
                longitude = property.place!!.longitude!!,
                name = property.name!!,
                description = property.description!!,
                phoneNumber = property.phone_number,
                website = property.website,
                type = property.type
            )
        }
    }
}
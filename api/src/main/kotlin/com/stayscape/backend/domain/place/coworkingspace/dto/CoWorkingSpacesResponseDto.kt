package com.stayscape.backend.domain.place.coworkingspace.dto

import com.stayscape.backend.domain.place.coworkingspace.CoWorkingSpace
import com.stayscape.backend.domain.user.address.AddressDto
import java.math.BigDecimal

data class CoWorkingSpacesResponseDto(
    val id: Int,
    val address: AddressDto,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val name: String,
    val description: String,
    val phoneNumber: String?,
    val website: String?
) {
    companion object {
        fun of(coWorkingSpace: CoWorkingSpace): CoWorkingSpacesResponseDto {
            return CoWorkingSpacesResponseDto(
                id = coWorkingSpace.place!!.user!!.id!!,
                address = AddressDto.trimmed(AddressDto.of(coWorkingSpace.place!!.address!!)),
                latitude = coWorkingSpace.place!!.latitude!!,
                longitude = coWorkingSpace.place!!.longitude!!,
                name = coWorkingSpace.name!!,
                description = coWorkingSpace.description!!,
                phoneNumber = coWorkingSpace.phone_number,
                website = coWorkingSpace.website
            )
        }
    }
}

package com.stayscape.backend.domain.place.touristspot.dto

import com.stayscape.backend.domain.place.coworkingspace.CoWorkingSpace
import com.stayscape.backend.domain.place.touristspot.TouristSpot
import com.stayscape.backend.domain.user.address.AddressDto
import java.math.BigDecimal

data class TouristSpotResponseDto(
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
        fun of(touristSpot: TouristSpot): TouristSpotResponseDto {
            return TouristSpotResponseDto(
                id = touristSpot.place!!.user!!.id!!,
                address = AddressDto.trimmed(AddressDto.of(touristSpot.place!!.address!!)),
                latitude = touristSpot.place!!.latitude!!,
                longitude = touristSpot.place!!.longitude!!,
                name = touristSpot.name!!,
                description = touristSpot.description!!,
                phoneNumber = touristSpot.phone_number,
                website = touristSpot.website
            )
        }
    }
}
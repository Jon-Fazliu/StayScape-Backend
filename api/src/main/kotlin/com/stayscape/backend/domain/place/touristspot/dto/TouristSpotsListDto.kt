package com.stayscape.backend.domain.place.touristspot.dto

import jakarta.validation.Valid
import com.stayscape.backend.domain.place.property.Property
import com.stayscape.backend.domain.place.touristspot.TouristSpot

data class TouristSpotsListDto(
    val touristSpots: List<@Valid TouristSpotResponseDto>?
) {
    companion object {
        fun of(list: List<TouristSpot>): TouristSpotsListDto {
            return TouristSpotsListDto(
                touristSpots = list.map { property ->
                    TouristSpotResponseDto.of(property)
                }
            )
        }
    }
}
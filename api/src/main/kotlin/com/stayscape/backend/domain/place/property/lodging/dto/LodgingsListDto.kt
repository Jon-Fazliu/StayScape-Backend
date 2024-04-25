package com.stayscape.backend.domain.place.property.lodging.dto

import com.stayscape.backend.domain.place.property.lodging.Lodging
import jakarta.validation.Valid

data class LodgingsListDto(
    val lodgings: List<@Valid LodgingResponseDto>?
) {
    companion object {
        fun of(list: List<Lodging>): LodgingsListDto {
            return LodgingsListDto(
                lodgings = list.map { lodging ->
                    LodgingResponseDto.of(lodging)
                }
            )
        }
    }
}
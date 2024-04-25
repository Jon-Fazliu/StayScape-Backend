package com.stayscape.backend.domain.place.coworkingspace.dto

import jakarta.validation.Valid
import com.stayscape.backend.domain.place.coworkingspace.CoWorkingSpace

data class CoWorkingSpaceListDto(
    val coWorkingSpaces: List<@Valid CoWorkingSpacesResponseDto>?
) {
    companion object {
        fun of(list: List<CoWorkingSpace>): CoWorkingSpaceListDto {
            return CoWorkingSpaceListDto(
                coWorkingSpaces = list.map { coWorkingSpace ->
                    CoWorkingSpacesResponseDto.of(coWorkingSpace)
                }
            )
        }
    }
}
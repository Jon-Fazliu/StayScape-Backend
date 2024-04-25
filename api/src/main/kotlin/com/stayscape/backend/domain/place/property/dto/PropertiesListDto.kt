package com.stayscape.backend.domain.place.property.dto

import jakarta.validation.Valid
import com.stayscape.backend.domain.place.property.Property

data class PropertiesListDto(
    val properties: List<@Valid PropertyResponseDto>?
) {
    companion object {
        fun of(list: List<Property>): PropertiesListDto {
            return PropertiesListDto(
                properties = list.map { property ->
                    PropertyResponseDto.of(property)
                }
            )
        }
    }
}
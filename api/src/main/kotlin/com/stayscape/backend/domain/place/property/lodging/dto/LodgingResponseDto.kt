package com.stayscape.backend.domain.place.property.lodging.dto

import com.stayscape.backend.domain.place.property.lodging.Lodging

data class LodgingResponseDto(
    val id: Int,
    val singleBeds: Int,
    val doubleBeds: Int,
    val roomCount: Int,
    val description: String?
    ) {
    companion object {
        fun of(lodging: Lodging): LodgingResponseDto {
            return LodgingResponseDto(
                id = lodging.id!!,
                singleBeds = lodging.singleBeds ?: 0,
                doubleBeds = lodging.doubleBeds ?: 0,
                roomCount = lodging.roomCount ?: 1,
                description = lodging.description
            )
        }
    }
}
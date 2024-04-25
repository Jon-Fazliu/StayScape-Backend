package com.stayscape.backend.domain.place.property.lodging.dto

import jakarta.validation.constraints.NotNull

data class LodgingCreateDto(
    @NotNull
    val singleBeds: Int,
    @NotNull
    val doubleBeds: Int,
    @NotNull
    val roomCount: Int,
    val description: String?
    )

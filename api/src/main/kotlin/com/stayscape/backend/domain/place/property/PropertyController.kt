package com.stayscape.backend.domain.place.property

import com.stayscape.backend.domain.place.property.dto.PropertyResponseDto
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.logging.LoggedMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/properties")
@RestController
class PropertyController(
    private val propertyService: PropertyService,
    private val userService: UserService
) {
    @GetMapping("/{id}")
    @LoggedMethod
    fun getTouristSpotWithId(
        @PathVariable("id") id: Int,
    ): ResponseEntity<PropertyResponseDto> {
        userService.updateActivity("getTouristSpotWithId")
        return ResponseEntity.ok(PropertyResponseDto.of(propertyService.getPropertyById(id)))
    }
}
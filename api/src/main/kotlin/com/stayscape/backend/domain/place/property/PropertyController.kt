package com.stayscape.backend.domain.place.property

import com.stayscape.backend.domain.place.property.dto.PropertyCreateDto
import com.stayscape.backend.domain.place.property.dto.PropertyResponseDto
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.logging.LoggedMethod
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/properties")
@RestController
class PropertyController(
    private val propertyService: PropertyService,
    private val userService: UserService
) {
    @GetMapping("/{id}")
    fun getPropertySpotWithId(
        @PathVariable("id") id: Int,
    ): ResponseEntity<PropertyResponseDto> {
        userService.updateActivity("getPropertySpotWithId")
        return ResponseEntity.ok(PropertyResponseDto.of(propertyService.getPropertyById(id)))
    }

    @PostMapping
    @LoggedMethod
    fun createProperty(
        @RequestBody @Valid propertyCreateDto: PropertyCreateDto,
    ): ResponseEntity<PropertyResponseDto> {
        userService.updateActivity("createProperty")
        return ResponseEntity.ok(PropertyResponseDto.of(propertyService.createProperty(propertyCreateDto)))
    }

    @DeleteMapping("/{propertyId}")
    @LoggedMethod
    fun deleteProperty(
        @PathVariable("propertyId") propertyId: Int
    ) : ResponseEntity<Unit> {
        userService.updateActivity("deleteProperty")
        propertyService.deleteProperty(propertyId)
        return ResponseEntity.ok().build()
    }
}
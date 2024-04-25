package com.stayscape.backend.domain.place.property

import com.stayscape.backend.domain.place.property.dto.PropertiesListDto
import com.stayscape.backend.domain.place.property.dto.PropertyCreateDto
import com.stayscape.backend.domain.place.property.dto.PropertyEditDto
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
    fun getPropertyWithId(
        @PathVariable("id") id: Int,
    ): ResponseEntity<PropertyResponseDto> {
        userService.updateActivity("getPropertySpotWithId")
        return ResponseEntity.ok(PropertyResponseDto.of(propertyService.getProperty(id)))
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

    @PutMapping("/{propertyId}")
    @LoggedMethod
    fun editProperty(
        @PathVariable("propertyId") propertyId: Int,
        @RequestBody @Valid propertyEditDto: PropertyEditDto
    ): ResponseEntity<PropertyResponseDto> {
        userService.updateActivity("editProperty")
        return ResponseEntity.ok(PropertyResponseDto.of(propertyService.editProperty(propertyId, propertyEditDto)))
    }

    @GetMapping
    @LoggedMethod
    fun getPropertiesList(): ResponseEntity<PropertiesListDto> {
        userService.updateActivity("getPropertiesList")
        return ResponseEntity.ok(PropertiesListDto.of(propertyService.getPropertiesList()))
    }

}
package com.stayscape.backend.domain.place.property.lodging

import com.stayscape.backend.domain.place.property.lodging.dto.LodgingCreateDto
import com.stayscape.backend.domain.place.property.lodging.dto.LodgingEditDto
import com.stayscape.backend.domain.place.property.lodging.dto.LodgingResponseDto
import com.stayscape.backend.domain.place.property.lodging.dto.LodgingsListDto
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.logging.LoggedMethod
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/properties/{property_id}/lodgings")
@RestController
class LodgingController(
    private val lodgingService: LodgingService,
    private val userService: UserService
) {
    @GetMapping("/{id}")
    @LoggedMethod
    fun getLodgingWithId(
        @PathVariable("id") id: Int,
        @PathVariable("property_id") propertyId: Int
    ): ResponseEntity<LodgingResponseDto> {
        userService.updateActivity("getLodgingWithId")
        return ResponseEntity.ok(LodgingResponseDto.of(lodgingService.getLodgingById(id, propertyId)))
    }

    @PostMapping
    @LoggedMethod
    fun createLodging(
        @PathVariable("property_id") propertyId: Int,
        @RequestBody @Valid lodgingCreateDto: LodgingCreateDto,
    ): ResponseEntity<LodgingResponseDto> {
        userService.updateActivity("createLodging")
        return ResponseEntity.ok(LodgingResponseDto.of(lodgingService.createLodging(propertyId, lodgingCreateDto)))
    }

    @DeleteMapping("/{id}")
    @LoggedMethod
    fun deleteLodging(
        @PathVariable("property_id") propertyId: Int,
        @PathVariable("id") id: Int
    ) : ResponseEntity<Unit> {
        userService.updateActivity("deleteLodging")
        lodgingService.deleteLodging(propertyId, id)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{id}")
    @LoggedMethod
    fun editLodging(
        @PathVariable("property_id") propertyId: Int,
        @PathVariable("id") id: Int,
        @RequestBody @Valid lodgingEditDto: LodgingEditDto
    ): ResponseEntity<LodgingResponseDto> {
        userService.updateActivity("editLodging")
        return ResponseEntity.ok(LodgingResponseDto.of(lodgingService.editLodging(propertyId, id, lodgingEditDto)))
    }

    @GetMapping
    @LoggedMethod
    fun getLodgingList(
        @PathVariable("property_id") propertyId: Int,
    ): ResponseEntity<LodgingsListDto> {
        userService.updateActivity("getLodgingList")
        return ResponseEntity.ok(LodgingsListDto.of(lodgingService.getLodgingsList(propertyId)))
    }
}
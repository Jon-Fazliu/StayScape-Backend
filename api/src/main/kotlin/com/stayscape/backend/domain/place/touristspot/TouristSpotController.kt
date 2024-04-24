package com.stayscape.backend.domain.place.touristspot

import com.stayscape.backend.domain.place.touristspot.dto.TouristSpotCreateDto
import com.stayscape.backend.domain.place.touristspot.dto.TouristSpotResponseDto
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.logging.LoggedMethod
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/tourist-spots")
@RestController
class TouristSpotController(
    private val touristSpotService: TouristSportService,
    private val userService: UserService
) {
    @GetMapping("/{id}")
    @LoggedMethod
    fun getTouristSpotWithId(
        @PathVariable("id") id: Int,
    ): ResponseEntity<TouristSpotResponseDto> {
        userService.updateActivity("getTouristSpotWithId")
        return ResponseEntity.ok(TouristSpotResponseDto.of(touristSpotService.getTouristSpotById(id)))
    }

    @PostMapping
    @LoggedMethod
    fun createTouristSpot(
        @RequestBody @Valid touristSpotCreateDto: TouristSpotCreateDto,
    ): ResponseEntity<TouristSpotResponseDto> {
        userService.updateActivity("createTouristSpot")
        return ResponseEntity.ok(TouristSpotResponseDto.of(touristSpotService.createTouristSpot(touristSpotCreateDto)))
    }

    @DeleteMapping("/{touristSpotId}")
    @LoggedMethod
    fun deleteTouristSpot(
        @PathVariable("touristSpotId") touristSpotId: Int
    ) : ResponseEntity<Unit> {
        userService.updateActivity("deleteTouristSpots")
        touristSpotService.deleteTouristSpot(touristSpotId)
        return ResponseEntity.ok().build()
    }
}
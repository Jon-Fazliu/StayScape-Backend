package com.stayscape.backend.domain.place.touristspot

import com.stayscape.backend.domain.place.coworkingspace.dto.CoWorkingSpaceResponseDto
import com.stayscape.backend.domain.place.touristspot.dto.TouristSpotResponseDto
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.logging.LoggedMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
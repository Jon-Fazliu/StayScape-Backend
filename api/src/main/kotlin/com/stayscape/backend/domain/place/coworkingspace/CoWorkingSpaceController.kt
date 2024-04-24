package com.stayscape.backend.domain.place.coworkingspace

import com.stayscape.backend.domain.place.coworkingspace.dto.CoWorkingSpaceCreateDto
import com.stayscape.backend.domain.place.coworkingspace.dto.CoWorkingSpaceResponseDto
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.logging.LoggedMethod
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/co-working-spaces")
@RestController
class CoWorkingSpaceController(
    private val coWorkingSpaceService: CoWorkingSpaceService,
    private val userService: UserService
) {

    @GetMapping("/{id}")
    @LoggedMethod
    fun getCoWorkingSpaceWithId(
        @PathVariable("id") id: Int,
    ): ResponseEntity<CoWorkingSpaceResponseDto> {
        userService.updateActivity("getCoWorkingSpaceWithId")
        return ResponseEntity.ok(CoWorkingSpaceResponseDto.of(coWorkingSpaceService.getCoWorkingSpaceById(id)))
    }

    @PostMapping
    @LoggedMethod
    fun createCoWorkingSpace(
        @RequestBody @Valid coWorkingSpaceCreateDto: CoWorkingSpaceCreateDto,
    ): ResponseEntity<CoWorkingSpaceResponseDto> {
        userService.updateActivity("createCoWorkingSpace")
        return ResponseEntity.ok(CoWorkingSpaceResponseDto.of(coWorkingSpaceService.createCoWorkingSpot(coWorkingSpaceCreateDto)))
    }

    @DeleteMapping("/{coWorkingSpaceId}")
    @LoggedMethod
    fun deleteCoWorkingSpace(
        @PathVariable("coWorkingSpaceId") coWorkingSpaceId: Int
    ) : ResponseEntity<Unit> {
        userService.updateActivity("deleteCoWorkingSpace")
        coWorkingSpaceService.deleteCoWorkingSpace(coWorkingSpaceId)
        return ResponseEntity.ok().build()
    }
}
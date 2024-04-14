package com.stayscape.backend.domain.place.coworkingspace

import com.stayscape.backend.domain.place.coworkingspace.dto.CoWorkingSpaceResponseDto
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.domain.user.dto.UserResponseDto
import com.stayscape.backend.logging.LoggedMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
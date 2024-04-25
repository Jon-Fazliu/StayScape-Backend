package com.stayscape.backend.domain.place.coworkingspace

import com.stayscape.backend.domain.place.coworkingspace.dto.CoWorkingSpaceCreateDto
import com.stayscape.backend.domain.place.coworkingspace.dto.CoWorkingSpaceEditDto
import com.stayscape.backend.domain.place.coworkingspace.dto.CoWorkingSpaceResponseDto
import com.stayscape.backend.domain.place.coworkingspace.dto.CoWorkingSpaceListDto
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

    @PutMapping("/{coWorkingSpaceId}")
    @LoggedMethod
    fun editCoWorkingSpace(
        @PathVariable("coWorkingSpaceId") coWorkingSpaceId: Int,
        @RequestBody @Valid coWorkingSpaceEditDto: CoWorkingSpaceEditDto
    ): ResponseEntity<CoWorkingSpaceResponseDto> {
        userService.updateActivity("editCoWorkingSpace")
        return ResponseEntity.ok(CoWorkingSpaceResponseDto.of(coWorkingSpaceService.editCoWorkingSpace(coWorkingSpaceId, coWorkingSpaceEditDto)))
    }

    @GetMapping
    @LoggedMethod
    fun getCoWorkingSpaceList(): ResponseEntity<CoWorkingSpaceListDto> {
        userService.updateActivity("getCoWorkingSpaceList")
        return ResponseEntity.ok(CoWorkingSpaceListDto.of(coWorkingSpaceService.getCoworkingSpacesList()))
    }

}
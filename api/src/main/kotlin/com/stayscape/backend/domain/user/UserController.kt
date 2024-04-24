package com.stayscape.backend.domain.user

import com.stayscape.backend.domain.user.dto.AffiliateEditRequest
import com.stayscape.backend.domain.user.dto.UserEditRequest
import com.stayscape.backend.domain.user.dto.UserResponseDto
import com.stayscape.backend.domain.user.dto.UserResponsePageDto
import com.stayscape.backend.domain.user.role.Role
import com.stayscape.backend.logging.LoggedMethod
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/users")
@RestController
class UserController(
    private val userService: UserService
) {

    @GetMapping
    @LoggedMethod
    fun getAllUsersWithRole(
        @RequestParam(defaultValue = "lastName") sortBy: String,
        @RequestParam(defaultValue = "desc") sortOrder: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "USER") role: String,
        @RequestParam(defaultValue = "100") size: Int
    ): ResponseEntity<UserResponsePageDto> {
        userService.updateActivity("getAllUsersWithRole")
        val sortValues = listOf(
            "createdAt",
            "email",
            "lastName",
            "firstName"
        )

        val allowedRoles = listOf(
            "USER",
            "AFFILIATE"
        )

        if(role.uppercase() !in allowedRoles) {
            throw UserException("Invalid role")
        }

        if (sortBy !in sortValues) {
            throw UserException("Invalid sort parameter")
        }
        val direction: Sort.Direction = when {
            "asc".equals(sortOrder, ignoreCase = true) -> Sort.Direction.ASC
            "desc".equals(sortOrder, ignoreCase = true) -> Sort.Direction.DESC
            else -> throw UserException("Invalid sorting order")
        }

        val pageable = when (sortBy) {
            "createdAt", "active" -> PageRequest.of(
                page,
                size,
                Sort.by(
                    Sort.Order(
                        direction,
                        sortBy,
                    )
                )
            )
            else -> PageRequest.of(
                page,
                size,
                Sort.by(
                    Sort.Order(
                        direction,
                        sortBy,
                    ).ignoreCase()
                )
            )
        }

        val roleEnum = when(role.uppercase()) {
            "USER" -> Role.USER
            "AFFILIATE" -> Role.AFFILIATE
            else -> Role.USER
        }

        return ResponseEntity.ok(UserResponsePageDto.of(userService.getAllUsers(pageable, roleEnum)))
    }

    @GetMapping("/self")
    @LoggedMethod
    fun getUser(): ResponseEntity<UserResponseDto> {
        userService.updateActivity("getUser")
        return ResponseEntity.ok(UserResponseDto.of(userService.getCurrentUser()))
    }


    @GetMapping("/{id}")
    @LoggedMethod
    fun getUserWithId(
        @PathVariable("id") id: Int,
    ): ResponseEntity<UserResponseDto> {
        userService.updateActivity("getUserWithId")
        return ResponseEntity.ok(UserResponseDto.of(userService.getUserById(id)))
    }

    @PutMapping("/self")
    @LoggedMethod
    fun editUserSelf(
        @RequestBody @Valid userEditRequest: UserEditRequest
    ): ResponseEntity<UserResponseDto> {
        userService.updateActivity("editUserSelf")
        return ResponseEntity.ok(UserResponseDto.of(userService.updateSelfUser(userEditRequest)))
    }

    @PutMapping("affiliate/self")
    @LoggedMethod
    fun editSelfAffiliate(
        @RequestBody @Valid affiliateEditRequest: AffiliateEditRequest
    ): ResponseEntity<UserResponseDto> {
        userService.updateActivity("editSelfAffiliate")
        return ResponseEntity.ok(UserResponseDto.of(userService.updateSelfAffiliate(affiliateEditRequest)))
    }

}
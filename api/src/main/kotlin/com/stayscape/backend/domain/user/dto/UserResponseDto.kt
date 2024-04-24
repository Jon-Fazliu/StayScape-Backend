package com.stayscape.backend.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.stayscape.backend.domain.user.User
import com.stayscape.backend.domain.user.address.AddressDto
import com.stayscape.backend.domain.user.role.Role
import java.time.Instant

data class UserResponseDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateOfBirth: Instant,
    val phoneNumber: String,
    val address: AddressDto,
    val role: Role,
    val emailConfirmed: Boolean,
    var website: String?
) {
    companion object {
        fun of(user: User): UserResponseDto {
            return UserResponseDto(
                id = user.id!!,
                address = AddressDto.of(user.address!!),
                dateOfBirth = user.dateOfBirth!!,
                email = user.email ?: "",
                firstName = user.firstName ?: "",
                lastName = user.lastName ?: "",
                phoneNumber = user.phoneNumber ?: "",
                role = user.role,
                emailConfirmed = user.confirmed,
                website = user.website
            )
        }
    }
}
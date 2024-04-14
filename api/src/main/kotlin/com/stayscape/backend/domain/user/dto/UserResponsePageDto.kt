package com.stayscape.backend.domain.user.dto

import com.stayscape.backend.domain.user.User
import com.stayscape.backend.domain.util.PaginatedMetaData
import jakarta.validation.Valid
import org.springframework.data.domain.Page

data class UserResponsePageDto(
    val meta: PaginatedMetaData? = null,
    val users: List<@Valid UserResponseDto>?
) {
    companion object {
        fun of(page: Page<User>): UserResponsePageDto {
            return UserResponsePageDto(
                users = page.content.map { user ->
                    UserResponseDto.of(user)
                },
                meta = PaginatedMetaData(
                    first = page.isFirst,
                    last = page.isLast,
                    number = page.number,
                    numberOfElements = page.numberOfElements,
                    size = page.size,
                    totalElements = page.totalElements,
                    totalPages = page.totalPages
                )
            )
        }
    }
}
package com.stayscape.backend.domain.util

import com.stayscape.backend.domain.security.JwtService
import com.stayscape.backend.domain.user.UserException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class SecurityUtils(val httpRequest: HttpServletRequest, val jwtService: JwtService) {

    fun getCurrentUserId(): Optional<Int> {
        val authHeader: String? = httpRequest.getHeader("Authorization")
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            throw UserException("Authentication failed, authHeader wrong")
        }
        val jwtToken = authHeader.substring(7)
        return Optional.ofNullable(jwtService.extractUserId(jwtToken))
    }

    fun userMustBeOfRole(role: String) {
        val currentRole = getCurrentUserRole().orElseThrow { UserException("Failed to get current role") }
        if (role != currentRole) {
            throw UserException("User is not of role $role")
        }
    }

    fun isUsersRole(role: String): Boolean {
        val currentRole = getCurrentUserRole().orElseThrow { UserException("Failed to get current role") }
        return role == currentRole
    }

    fun getCurrentUserRole(): Optional<String> {
        val authHeader: String? = httpRequest.getHeader("Authorization")
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            throw UserException("Authentication failed, authHeader wrong")
        }
        val jwtToken = authHeader.substring(7)
        return Optional.ofNullable(jwtService.extractUserRole(jwtToken))
    }
}
package com.stayscape.backend.domain.security

import com.stayscape.backend.domain.security.auth.AuthenticationException
import com.stayscape.backend.domain.user.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userService: UserService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")
        val email: String?
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        try {
            val jwtToken = authHeader.substring(7)
            email = jwtService.extractUsername(jwtToken)
            if (!email.isNullOrBlank() && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userService.loadUserByUsername(email)
                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    val token = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    token.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = token
                } else {
                    throw AuthenticationException("Authentication failed, invalid token", 40300)
                }
            } else {
                throw AuthenticationException("Authentication failed, email blank", 40300)
            }
        } catch(e: AuthenticationException) {
            throw e
        } catch (e: Exception) {
            logger.error("Authentication failed ${e.javaClass}, ${e.message}" )
            throw AuthenticationException("Authentication failed", 40300)
        }

        filterChain.doFilter(request, response)
    }
}
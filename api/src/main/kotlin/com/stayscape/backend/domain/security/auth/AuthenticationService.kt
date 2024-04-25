package com.stayscape.backend.domain.security.auth

import com.stayscape.backend.domain.security.ExpiryException
import com.stayscape.backend.domain.security.JwtService
import com.stayscape.backend.domain.security.auth.dto.AuthenticationRequest
import com.stayscape.backend.domain.security.auth.dto.AuthenticationResponse
import com.stayscape.backend.domain.security.auth.dto.RefreshResponse
import com.stayscape.backend.domain.user.User
import com.stayscape.backend.domain.user.UserException
import com.stayscape.backend.domain.user.UserJpaRepository
import com.stayscape.backend.domain.user.activity.ActivityJpaRepository
import com.stayscape.backend.domain.user.dto.UserResponseDto
import com.stayscape.backend.domain.util.SecurityUtils
import com.stayscape.backend.util.logger
import io.jsonwebtoken.ExpiredJwtException
import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class AuthenticationService(
    private val userJpaRepository: UserJpaRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val securityUtils: SecurityUtils,
    private val encoder: PasswordEncoder,
    private val mailer: AuthenticationMailer,
    private val activityJpaRepository: ActivityJpaRepository,
    private val utcClock: Clock
) {
    private val log = logger()

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email.lowercase(),
                authenticationRequest.password
            )
        )

        val user = userJpaRepository.findByEmail(authenticationRequest.email.lowercase())
            .orElseThrow { AuthenticationException("Failed authentication", 40303) }

        if (!user.confirmed) {
            mailer.sendConfirmationEmail(user.email!!, jwtService.generateConfirmToken(user.email!!))
            throw AuthenticationException("User not confirmed, confirmation email sent.", 40301)
        }

        return AuthenticationResponse(
            jwtService.generateAccessToken(user),
            createRefreshTokenForUser(user),
            UserResponseDto.of(user)
        )
    }

    @Transactional
    fun createRefreshTokenForUser(user: User): String {
        val refreshToken = jwtService.generateRefreshToken(user.email!!)
        user.refreshToken = refreshToken
        userJpaRepository.save(user)
        return refreshToken
    }

    fun refreshAccessToken(refreshToken: String): RefreshResponse {
        val user = userJpaRepository.findByRefreshToken(refreshToken)
            .orElseThrow { UserException("Access token not present") }
        if (user.refreshToken != null) {

            if (!jwtService.isTokenValid(refreshToken, user)) {
                throw UserException("Refresh token is no longer valid")
            }

            return RefreshResponse(jwtService.generateAccessToken(user))

        } else {
            throw UserException("Invalid refresh token")
        }
    }

    fun requestResetPassword(email: String?): String {
        if (email.isNullOrBlank()) {
            throw UserException("Empty email for reset password request")
        }
        val userOptional = userJpaRepository.findByEmail(email)
        if (!userOptional.isPresent) {
            log.debug("No such email in database: $email")
            return ""
        }

        val token = jwtService.generateResetPasswordToken(email)

        mailer.sendResetPasswordEmail(email, token)

        return token
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    fun saveResetPasswordToken(email: String?, token: String) {
        if (email.isNullOrBlank()) {
            throw UserException("Empty email for reset password request")
        }
        val userOptional = userJpaRepository.findByEmail(email)
        if (!userOptional.isPresent) {
            log.debug("No such email in database: $email")
            return
        }
        userOptional.get().resetPasswordToken = token
        userJpaRepository.save(userOptional.get())
    }

    @Transactional
    fun requestChangePassword() {
        val currentUserId = securityUtils.getCurrentUserId().orElseThrow { UserException("User id not found") }
        val user = userJpaRepository.findById(currentUserId)
            .orElseThrow { UserException("User with id $currentUserId not found") }
        val token = requestResetPassword(user.email)
        saveResetPasswordToken(user.email!!, token)
    }

    @Transactional
    fun completePasswordRequest(password: String, token: String) {
        try {
            val email = jwtService.extractUsername(token) ?: throw UserException("Could not get email from token")
            val user =
                userJpaRepository.findByEmail(email).orElseThrow { UserException("User with email $email not found") }
            if (jwtService.isTokenValid(token, user)) {
                if (user.resetPasswordToken != token) {
                    throw AuthenticationException(
                        "Reset password token does not match with user reset password token",
                        40306
                    )
                }
                user.accountPassword = encoder.encode(password)
                user.resetPasswordToken = null
                userJpaRepository.save(user)
            }
        } catch (e: ExpiredJwtException) {
            val email = e.claims.subject
            log.debug("Expired password change request token for email {}", email)

            throw ExpiryException("Password change request token expired")
        }
    }

    @Transactional
    fun confirm(token: String) {
        try {
            val email = jwtService.extractUsername(token) ?: throw UserException("Could not get email from token")
            val user =
                userJpaRepository.findByEmail(email).orElseThrow { UserException("User with email $email not found") }
            if (jwtService.isTokenValid(token, user)) {
                user.confirmed = true
                userJpaRepository.save(user)
            }
        } catch (e: ExpiredJwtException) {
            val email = e.claims.subject
            mailer.sendConfirmationEmail(email, jwtService.generateConfirmToken(email))
            log.debug("Expired confirmation email token for email {}", email)

            throw ExpiryException("Confirmation token expired")
        }
    }

    fun resendConfirmationEmail(userId: Int) {
        val user = userJpaRepository.findById(userId).orElseThrow { UserException("User with id $userId not found") }
        if(!user.confirmed) {
            mailer.sendConfirmationEmail(user.email!!, jwtService.generateConfirmToken(user.email!!))
        }
    }

}
package com.stayscape.backend.domain.security.auth

import com.stayscape.backend.domain.security.auth.dto.*
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.domain.security.auth.dto.AuthenticationRequest
import com.stayscape.backend.domain.security.auth.dto.AuthenticationResponse
import com.stayscape.backend.domain.security.auth.dto.CompleteRequestPassword
import com.stayscape.backend.domain.security.auth.dto.RefreshRequest
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    @PostMapping(path = ["/register"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun register(
        @RequestBody @Valid request: RegisterRequest
    ): ResponseEntity<Unit> {
        userService.createUser(request)
        return ResponseEntity.ok().build()
    }

    @PostMapping(path = ["/affiliate/register"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerAffiliate(
        @RequestBody @Valid request: AffiliateRegisterRequest
    ): ResponseEntity<Unit> {
        userService.createAffiliate(request)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/register/confirm")
    fun confirmEmail(
        @RequestParam token: String
    ): ResponseEntity<Unit>  {
        authenticationService.confirm(token)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid request: AuthenticationRequest
    ): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authenticationService.authenticate(request))
    }

    @PostMapping("/resend_email_confirmation_link")
    fun resendConfirmationEmail(
        @RequestBody @Valid request: ResendUserConfirmEmailRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(authenticationService.resendConfirmationEmail(request.userId))
    }

    @PostMapping("/refresh")
    fun refreshToken(
        @RequestBody @Valid request: RefreshRequest
    ): ResponseEntity<RefreshResponse> {
        return ResponseEntity.ok(authenticationService.refreshAccessToken(request.refreshToken))
    }

    @PostMapping("/forgot-password-request")
    fun requestResetPassword(
        @RequestBody @Valid request: RequestResetPassword
    ): ResponseEntity<Unit>  {

        val token = authenticationService.requestResetPassword(request.email.lowercase())
        authenticationService.saveResetPasswordToken(request.email.lowercase(), token)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/change-password-request")
    fun requestResetPassword(): ResponseEntity<Unit>  {
        authenticationService.requestChangePassword()
        return ResponseEntity.ok().build()
    }

    @PostMapping("/complete-password-request")
    fun completePasswordRequest(
        @RequestBody @Valid request: CompleteRequestPassword,
        @RequestParam token: String
    ): ResponseEntity<Unit> {
        authenticationService.completePasswordRequest(request.password, token)
        return ResponseEntity.ok().build()
    }
}

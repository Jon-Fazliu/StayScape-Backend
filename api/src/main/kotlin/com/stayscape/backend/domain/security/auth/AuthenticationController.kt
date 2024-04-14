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
}

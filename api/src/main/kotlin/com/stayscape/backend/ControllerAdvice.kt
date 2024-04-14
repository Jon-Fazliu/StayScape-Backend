package com.stayscape.backend

import com.stayscape.backend.domain.security.ExpiryException
import com.stayscape.backend.domain.security.auth.AuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.Clock
import java.time.Instant


@RestControllerAdvice
class ControllerExceptionHandler(
    private val utcClock: Clock,
) {
    @ExceptionHandler(value = [AuthenticationException::class])
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    fun authenticationError(ex: AuthenticationException, request: WebRequest): ErrorMessage {
        return ErrorMessage(
            statusCode = 403,
            Instant.now(utcClock),
            "Exception is: ${ex.javaClass} ${ex.message}",
            "Request threw exception",
            ex.errorCode
        )
    }

    @ExceptionHandler(value = [InternalAuthenticationServiceException::class])
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    fun internalAuthenticationFailure(ex: InternalAuthenticationServiceException, request: WebRequest): ErrorMessage {
        return ErrorMessage(
            statusCode = 403,
            Instant.now(utcClock),
            "Exception is: ${ex.javaClass} ${ex.message}",
            "Request threw exception",
            40303
        )
    }

    @ExceptionHandler(value = [BadCredentialsException::class])
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    fun badCredentials(ex: BadCredentialsException, request: WebRequest): ErrorMessage {
        return ErrorMessage(
            statusCode = 403,
            Instant.now(utcClock),
            "Exception is: ${ex.javaClass} ${ex.message}",
            "Request threw exception",
            40303
        )
    }

    @ExceptionHandler(value = [ExpiryException::class])
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    fun expiredJwt(ex: ExpiryException, request: WebRequest): ErrorMessage {
        return ErrorMessage(
            statusCode = 403,
            Instant.now(utcClock),
            "Exception is: ${ex.javaClass} ${ex.message}",
            "Request threw exception",
            40305
        )
    }

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun badRequest(ex: Exception, request: WebRequest): ErrorMessage {
        return ErrorMessage(
            statusCode = 400,
            Instant.now(utcClock),
            "Exception is: ${ex.javaClass} ${ex.message}",
            "Request threw exception",
            40000
        )
    }
}
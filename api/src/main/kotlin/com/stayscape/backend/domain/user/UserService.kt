package com.stayscape.backend.domain.user

import com.stayscape.backend.domain.security.JwtService
import com.stayscape.backend.domain.security.auth.AuthenticationException
import com.stayscape.backend.domain.security.auth.AuthenticationMailer
import com.stayscape.backend.domain.security.auth.dto.RegisterRequest
import com.stayscape.backend.domain.user.activity.Activity
import com.stayscape.backend.domain.user.activity.ActivityJpaRepository
import com.stayscape.backend.domain.user.address.Address
import com.stayscape.backend.domain.user.address.AddressDto
import com.stayscape.backend.domain.user.dto.UserEditRequest
import com.stayscape.backend.domain.user.role.Role
import com.stayscape.backend.domain.util.SecurityUtils
import com.stayscape.backend.logging.LoggedMethod
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.Instant

@Service
class UserService(
    private val userJpaRepository: UserJpaRepository,
    private val securityUtils: SecurityUtils,
    private val activityJpaRepository: ActivityJpaRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mailer: AuthenticationMailer,
    private val jwtService: JwtService,
    private val utcClock: Clock,
) : UserDetailsService {
    override fun loadUserByUsername(email: String?): UserDetails {
        return getUserByEmail(email)
    }

    fun getUserByEmail(email: String?): User {
        if (email.isNullOrBlank()) {
            throw UserException("Email null or empty")
        }
        return userJpaRepository.findByEmail(email)
            .orElseThrow { AuthenticationException("Email or password was incorrect", 40303) }
    }

    @Transactional
    fun createUser(registerRequest: RegisterRequest) {
        val checkUser = userJpaRepository.findByEmail(registerRequest.email)

        if (checkUser.isPresent) {
            throw AuthenticationException("Email has an account", 40304)
        }

        val user = User(
            firstName = registerRequest.firstName.trim(),
            lastName = registerRequest.lastName.trim(),
            dateOfBirth = registerRequest.dateOfBirth,
            phoneNumber = registerRequest.phoneNumber.trim(),
            address = Address.from(AddressDto.trimmed(registerRequest.address)),
            email = registerRequest.email.lowercase().trim(),
            accountPassword = passwordEncoder.encode(registerRequest.password)
        )


        userJpaRepository.save(user)

        activityJpaRepository.save(
            Activity(
                lastActivity = Instant.now(utcClock),
                user = user
            )
        )

        mailer.sendConfirmationEmail(user.email!!, jwtService.generateConfirmToken(user.email!!))
    }

    @Transactional
    @LoggedMethod
    fun getCurrentUser(): User {
        val currentUserId = securityUtils.getCurrentUserId().orElseThrow { UserException("User id not found") }
        return  userJpaRepository.findById(currentUserId)
            .orElseThrow { UserException("User with id $currentUserId not found") }
    }

    @Transactional
    @LoggedMethod
    fun updateActivity(endpoint: String): Activity {
        val currentUserId = securityUtils.getCurrentUserId().orElseThrow { UserException("User id not found") }
        val user = userJpaRepository.findById(currentUserId)
            .orElseThrow { UserException("User with id $currentUserId not found") }
        val activity = activityJpaRepository.findByUser(user).orElseGet { createActivityFor(user) }

        activity.lastActivity = Instant.now(utcClock)
        activity.lastActivityType = endpoint
        return activityJpaRepository.save(activity)
    }

    @LoggedMethod
    private fun createActivityFor(user: User): Activity {
        val activity = Activity(
            user = user,
            lastActivity = Instant.now(utcClock)
        )
        activityJpaRepository.save(activity)

        return activity
    }

    @LoggedMethod
    @Transactional
    fun getUserById(id: Int): User {
        return userJpaRepository.findById(id).orElseThrow { UserException("User with id $id not found") }
    }


    @Transactional
    @LoggedMethod
    fun updateSelfUser(userEditRequest: UserEditRequest): User {
        val currentUserId = securityUtils.getCurrentUserId().orElseThrow { UserException("User id not found") }
        val user = userJpaRepository.findById(currentUserId)
            .orElseThrow { UserException("User with id $currentUserId not found") }

        if (userEditRequest.email.lowercase() == user.email || !userJpaRepository.findByEmail(userEditRequest.email.lowercase()).isPresent) {
            user.apply {
                firstName = userEditRequest.firstName.trim()
                lastName = userEditRequest.lastName.trim()
                email = userEditRequest.email.lowercase().trim()
                dateOfBirth = userEditRequest.dateOfBirth
                phoneNumber = userEditRequest.phoneNumber.trim()
                address = Address.from(AddressDto.trimmed(userEditRequest.address))
            }

            return  userJpaRepository.save(user)
        }
        throw UserException("Email already present")
    }

    @Transactional
    @LoggedMethod
    fun getAllUsers(pageable: Pageable, role: Role): Page<User> {
        return userJpaRepository.findAllByRole(role, pageable)
    }
}
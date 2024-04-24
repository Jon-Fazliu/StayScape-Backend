package com.stayscape.backend.domain.user

import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.place.Place
import com.stayscape.backend.domain.user.address.Address
import com.stayscape.backend.domain.user.role.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
@Table(name = "users")
class User(
    @Column(name = "first_name", nullable = false, length = 255)
    var firstName: String? = null,

    @Column(name = "last_name", nullable = false, length = 255)
    var lastName: String? = null,

    @Column(name = "date_of_birth", nullable = false)
    var dateOfBirth: Instant? = null,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String? = null,

    @Column(name = "email", nullable = false)
    var email: String? = null,

    @Column(name = "password", nullable = false)
    var accountPassword: String? = null, //name is different because it interferes with UserDetails getPassword

    @Embedded
    var address: Address? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role = Role.USER,

    @Column(name = "refresh_token")
    var refreshToken: String? = null,

    @Column(name = "confirmed")
    var confirmed: Boolean = false,

    @Column(name = "reset_password_token")
    var resetPasswordToken: String? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var places: List<Place> = emptyList(),

    @Column(name = "website")
    var website:  String? = null,
    ) : AbstractJpaEntityVersioned(), UserDetails {

    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return accountPassword ?: throw UserException("Empty password")
    }

    override fun getUsername(): String {
        return email ?: throw UserException("User has no email")
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
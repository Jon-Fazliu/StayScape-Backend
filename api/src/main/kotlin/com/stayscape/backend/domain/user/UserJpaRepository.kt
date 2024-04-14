package com.stayscape.backend.domain.user

import com.stayscape.backend.domain.user.role.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface UserJpaRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): Optional<User>

    @Query(
        value = """
        SELECT t
            FROM User t
            WHERE t.role = :role
        """
    )
    fun findAllByRole(@Param("role") role: Role, pageable: Pageable): Page<User>
    fun findByRefreshToken(refreshToken: String): Optional<User>

}
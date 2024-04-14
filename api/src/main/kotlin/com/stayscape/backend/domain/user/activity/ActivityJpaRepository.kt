package com.stayscape.backend.domain.user.activity

import com.stayscape.backend.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ActivityJpaRepository  : JpaRepository<Activity, Int> {
    fun findByUser(user: User): Optional<Activity>
}
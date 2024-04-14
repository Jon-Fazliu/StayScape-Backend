package com.stayscape.backend.domain.place.favorite

import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.place.Place
import com.stayscape.backend.domain.user.User
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "favorites")
class Favorite(
    @ManyToOne(optional = false)
    @JoinColumn(name = "place_id")
    val place: Place? = null,
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    val user: User? = null
) : AbstractJpaEntityVersioned() {
}
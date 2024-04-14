package com.stayscape.backend.domain.place.review

import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.place.Place
import com.stayscape.backend.domain.user.User
import jakarta.persistence.*

@Entity
@Table(name = "reviews")
class Review(
    @Column(name = "description", nullable = false)
    var description: String? = null,
    @Column(name = "rating", nullable = false)
    var rating: Int? = null,
    @ManyToOne(optional = false)
    @JoinColumn(name = "place_id")
    val place: Place? = null,
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    val user: User? = null
) : AbstractJpaEntityVersioned()  {
}
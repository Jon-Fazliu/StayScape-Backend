package com.stayscape.backend.domain.place.property.booking

import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.place.property.lodging.Lodging
import com.stayscape.backend.domain.user.User
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "bookings")
class Booking(
    @Column(name = "beginning", nullable = false)
    val beginning: Instant? = null,
    @Column(name = "end", nullable = false)
    val end: Instant? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: BookingStatus = BookingStatus.UNCONFIRMED,
    @Column(name = "special_requests")
    val specialRequests: String? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "lodging_id")
    val lodging: Lodging? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    val user: User? = null
) : AbstractJpaEntityVersioned() {

}
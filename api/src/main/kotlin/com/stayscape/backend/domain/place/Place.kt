package com.stayscape.backend.domain.place

import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.user.User
import com.stayscape.backend.domain.user.address.Address
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

@Entity
@Table(name = "places")
class Place(
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @Embedded
    var address: Address? = null,

    @Column(name = "latitude")
    var latitude: BigDecimal? = null,
    @Column(name = "longitude")
    var longitude: BigDecimal? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type: PlaceType = PlaceType.PROPERTY,

    @Column(name = "deleted")
    var deleted: Boolean = false

    ) : AbstractJpaEntityVersioned()
package com.stayscape.backend.domain.place

import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.user.address.Address
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "places")
class Place(
    @Embedded
    var address: Address? = null,

    @Column(name = "latitude")
    var latitude: BigDecimal? = null,
    @Column(name = "longitude")
    var longitude: BigDecimal? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type: PlaceType = PlaceType.PROPERTY
) : AbstractJpaEntityVersioned()
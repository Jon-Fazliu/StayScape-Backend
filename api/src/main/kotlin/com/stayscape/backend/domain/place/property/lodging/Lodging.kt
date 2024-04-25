package com.stayscape.backend.domain.place.property.lodging

import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.place.property.Property
import jakarta.persistence.*

@Entity
@Table(name = "lodgings")
class Lodging(
    var singleBeds: Int? = null,
    var doubleBeds: Int? = null,
    var roomCount: Int? = null,

    var description: String? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id")
    val property: Property? = null,

    var deleted: Boolean = false
) : AbstractJpaEntityVersioned()  {
}
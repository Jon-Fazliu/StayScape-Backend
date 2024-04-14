package com.stayscape.backend.domain.place.property.lodging

import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.place.property.Property
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "lodgings")
class Lodging(
    val singleBeds: Int? = null,
    val doubleBeds: Int? = null,
    val roomCount: Int? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id")
    val property: Property? = null
) : AbstractJpaEntityVersioned()  {
}
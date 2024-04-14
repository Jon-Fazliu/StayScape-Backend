package com.stayscape.backend.domain.place.coworkingspace

import com.fasterxml.jackson.annotation.JsonIgnore
import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.place.Place
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "coworking_spaces")
class CoWorkingSpace(
    @Column(name = "name", nullable = false)
    var name: String? = null,
    @Column(name = "description", nullable = false)
    var description: String? = null,
    @Column(name = "phone_number")
    var phone_number: String? = null,
    @Column(name = "website")
    var website: String? = null,

    @JsonIgnore
    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(
        name = "place_id",
        nullable = false
    )
    var place: Place? = null,
) : AbstractJpaEntityVersioned() {
}
package com.stayscape.backend.domain.place.image

import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.place.Place
import jakarta.persistence.*

@Entity
@Table(name = "images")
class Image(
    @Column(name = "path", nullable = false)
    val path: String? = null,
    @Column(name = "file_type", nullable = false)
    val fileType: String? = null,
    @Column(name = "description", nullable = false)
    val description: String? = null,
    @ManyToOne(optional = false)
    @JoinColumn(name = "place_id")
    val place: Place? = null
) : AbstractJpaEntityVersioned() {
}
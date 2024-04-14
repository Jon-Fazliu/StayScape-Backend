package com.stayscape.backend.domain

import jakarta.persistence.*
import java.time.Instant

@MappedSuperclass
abstract class AbstractJpaEntityVersioned(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id",
        nullable = false
    )
    var id: Int? = null,

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    var createdAt: Instant? = null,

    @Column(name = "updated_at")
    var updatedAt: Instant? = null,

    @Version
    @Column(
        name = "lock_version",
        nullable = false
    )
    var lockVersion: Int? = null,
) : AbstractJpaEntity<Int>() {

    @PrePersist
    open fun prePersist() {
        createdAt = Instant.now()
    }

    @PreUpdate
    open fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun getIdentifier(): Int? {
        return id
    }

    override fun toStringAttributes(): Map<String, Any?> {
        return super.toStringAttributes().plus("createdAt" to createdAt).plus("updatedAt" to updatedAt)
            .plus("lockVersion" to lockVersion)
    }

}
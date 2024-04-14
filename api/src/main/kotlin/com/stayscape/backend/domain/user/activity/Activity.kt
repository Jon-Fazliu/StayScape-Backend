package com.stayscape.backend.domain.user.activity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.stayscape.backend.domain.AbstractJpaEntity
import com.stayscape.backend.domain.user.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.Instant

@Entity
@Table(name = "activities")
class Activity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id",
        nullable = false
    )
    var id: Int? = null,
    @Column(
        name = "last_activity"
    )
    var lastActivity: Instant? = null,

    @Column(name = "last_activity_type")
    var lastActivityType: String? = null,

    @JsonIgnore
    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(
        name = "user_id",
        nullable = false
    )
    var user: User? = null,
) :  AbstractJpaEntity<Int>() {
    override fun getIdentifier(): Int? {
        return id
    }
}
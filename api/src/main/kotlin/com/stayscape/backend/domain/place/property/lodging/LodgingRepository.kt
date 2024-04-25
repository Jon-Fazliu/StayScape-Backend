package com.stayscape.backend.domain.place.property.lodging

import com.stayscape.backend.domain.place.property.Property
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LodgingRepository : JpaRepository<Lodging, Int> {
    @Query("SELECT l FROM Lodging l JOIN l.property p WHERE p.id = :id")
    fun findByPropertyId(id: Int) : List<Lodging>
}
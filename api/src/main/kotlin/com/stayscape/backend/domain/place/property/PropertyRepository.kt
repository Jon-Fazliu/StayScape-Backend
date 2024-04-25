package com.stayscape.backend.domain.place.property

import com.stayscape.backend.domain.place.coworkingspace.CoWorkingSpace
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PropertyRepository : JpaRepository<Property, Int> {

    @Query("SELECT pr FROM Property pr JOIN pr.place p WHERE p.user.id = :id AND NOT p.deleted")
    fun findByPlaceUserIdNotDeleted(id: Int) : List<Property>
}
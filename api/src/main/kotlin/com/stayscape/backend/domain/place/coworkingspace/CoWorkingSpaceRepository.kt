package com.stayscape.backend.domain.place.coworkingspace

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CoWorkingSpaceRepository : JpaRepository<CoWorkingSpace, Int> {

    @Query("SELECT c FROM CoWorkingSpace c JOIN c.place p WHERE p.user.id = :id")
    fun findByPlaceUserId(id: Int) : List<CoWorkingSpace>
}
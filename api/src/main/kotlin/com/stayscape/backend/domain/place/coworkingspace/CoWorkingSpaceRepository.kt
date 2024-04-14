package com.stayscape.backend.domain.place.coworkingspace

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CoWorkingSpaceRepository : JpaRepository<CoWorkingSpace, Int> {

}
package com.stayscape.backend.domain.place.property.lodging

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LodgingRepository : JpaRepository<Lodging, Int> {

}
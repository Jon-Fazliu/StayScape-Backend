package com.stayscape.backend.domain.place.touristspot

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TouristSpotRepository : JpaRepository<TouristSpot, Int> {

}
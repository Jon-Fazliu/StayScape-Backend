package com.stayscape.backend.domain.place.touristspot

import com.stayscape.backend.StayScapeException
import com.stayscape.backend.domain.place.coworkingspace.CoWorkingSpace
import com.stayscape.backend.logging.LoggedMethod
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TouristSportService(
    private val touristSpotRepository: TouristSpotRepository
) {
    @LoggedMethod
    @Transactional
    fun getTouristSpotById(id: Int) : TouristSpot {
        val touristSpot = touristSpotRepository.findById(id).orElseThrow {
            StayScapeException("Tourist spot with id $id not found")
        }

        return touristSpot
    }
}
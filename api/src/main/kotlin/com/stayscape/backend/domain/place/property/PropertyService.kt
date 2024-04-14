package com.stayscape.backend.domain.place.property

import com.stayscape.backend.StayScapeException
import com.stayscape.backend.domain.place.touristspot.TouristSpot
import com.stayscape.backend.logging.LoggedMethod
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PropertyService(
    private val propertyRepository: PropertyRepository
) {
    @LoggedMethod
    @Transactional
    fun getPropertyById(id: Int) : Property {
        val property = propertyRepository.findById(id).orElseThrow {
            StayScapeException("Property with id $id not found")
        }

        return property
    }
}
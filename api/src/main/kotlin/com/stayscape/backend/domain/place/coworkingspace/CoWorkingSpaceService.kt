package com.stayscape.backend.domain.place.coworkingspace

import com.stayscape.backend.StayScapeException
import com.stayscape.backend.logging.LoggedMethod
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CoWorkingSpaceService(
    private val coWorkingSpaceRepository: CoWorkingSpaceRepository
) {
    @LoggedMethod
    @Transactional
    fun getCoWorkingSpaceById(id: Int) : CoWorkingSpace {
        val coWorkingSpace = coWorkingSpaceRepository.findById(id).orElseThrow {
            StayScapeException("Co-working Space with id $id not found")
        }

        return coWorkingSpace
    }
}
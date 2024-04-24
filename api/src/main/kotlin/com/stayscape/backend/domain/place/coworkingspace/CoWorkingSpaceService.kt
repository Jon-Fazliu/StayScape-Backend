package com.stayscape.backend.domain.place.coworkingspace

import com.stayscape.backend.StayScapeException
import com.stayscape.backend.domain.place.Place
import com.stayscape.backend.domain.place.PlaceRepository
import com.stayscape.backend.domain.place.coworkingspace.dto.CoWorkingSpaceCreateDto
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.domain.user.address.Address
import com.stayscape.backend.domain.user.role.Role
import com.stayscape.backend.domain.util.SecurityUtils
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CoWorkingSpaceService(
    private val coWorkingSpaceRepository: CoWorkingSpaceRepository,
    private val placeRepository: PlaceRepository,
    private val securityUtils: SecurityUtils,
    private val userService: UserService
) {
    fun getCoWorkingSpaceById(id: Int) : CoWorkingSpace {
        val coWorkingSpace = coWorkingSpaceRepository.findById(id).orElseThrow {
            StayScapeException("Co-working Space with id $id not found")
        }

        return coWorkingSpace
    }

    @Transactional
    fun createCoWorkingSpot(coWorkingSpaceCreateDto: CoWorkingSpaceCreateDto): CoWorkingSpace {
        securityUtils.userMustBeOfRole(Role.AFFILIATE.toString())

        val user = userService.getCurrentUser()

        val place = Place(
            address =  Address.from(coWorkingSpaceCreateDto.address),
            latitude = coWorkingSpaceCreateDto.latitude,
            longitude = coWorkingSpaceCreateDto.longitude,
            user = user
        )

        placeRepository.save(place)

        val coWorkingSpace = CoWorkingSpace(
            place = place,
            name = coWorkingSpaceCreateDto.name,
            website = coWorkingSpaceCreateDto.website,
            phone_number = coWorkingSpaceCreateDto.phoneNumber,
            description = coWorkingSpaceCreateDto.description,
        )

        return coWorkingSpaceRepository.save(coWorkingSpace)
    }

    @Transactional
    fun deleteCoWorkingSpace(id: Int) {
        securityUtils.userMustBeOfRole(Role.AFFILIATE.toString())

        val coWorkingSpace = coWorkingSpaceRepository.findById(id).orElseThrow {
            StayScapeException(
                "No coworking space with id $id exists"
            )
        }
        val place = coWorkingSpace.place!!
        val user = userService.getCurrentUser()

        if(user.id != place.id) {
            throw StayScapeException(
                "Users can only delete their own coworking spaces"
            )
        }

        place.deleted = true
        placeRepository.save(place)
    }
}
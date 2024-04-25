package com.stayscape.backend.domain.place.touristspot

import com.stayscape.backend.StayScapeException
import com.stayscape.backend.domain.place.Place
import com.stayscape.backend.domain.place.PlaceRepository
import com.stayscape.backend.domain.place.touristspot.dto.TouristSpotCreateDto
import com.stayscape.backend.domain.place.touristspot.dto.TouristSpotEditDto
import com.stayscape.backend.domain.user.address.Address
import com.stayscape.backend.domain.user.role.Role
import com.stayscape.backend.domain.util.SecurityUtils
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TouristSportService(
    private val touristSpotRepository: TouristSpotRepository,
    private val placeRepository: PlaceRepository,
    private val securityUtils: SecurityUtils
) {

    fun getTouristSpotById(id: Int) : TouristSpot {
        val touristSpot = touristSpotRepository.findById(id).orElseThrow {
            StayScapeException("Tourist spot with id $id not found")
        }

        return touristSpot
    }

    @Transactional
    fun createTouristSpot(touristSpotCreateDto: TouristSpotCreateDto): TouristSpot {
        securityUtils.userMustBeOfRole(Role.ADMIN.toString())

        val place = Place(
            address =  Address.from(touristSpotCreateDto.address),
            latitude = touristSpotCreateDto.latitude,
            longitude = touristSpotCreateDto.longitude
        )

        placeRepository.save(place)

        val touristSpot = TouristSpot(
            place = place,
            name = touristSpotCreateDto.name,
            website = touristSpotCreateDto.website,
            phone_number = touristSpotCreateDto.phoneNumber,
            description = touristSpotCreateDto.description
        )

        return touristSpotRepository.save(touristSpot)
    }

    @Transactional
    fun deleteTouristSpot(id: Int) {
        securityUtils.userMustBeOfRole(Role.ADMIN.toString())

        val touristSpot = getTouristSpotById(id)
        val place = touristSpot.place!!

        place.deleted = true
        placeRepository.save(place)
    }

    @Transactional
    fun editTouristSpot(id: Int, touristSpotEditDto: TouristSpotEditDto): TouristSpot {
        securityUtils.userMustBeOfRole(Role.ADMIN.toString())

        val touristSpot = getTouristSpotById(id)

        val place = touristSpot.place!!

        place.apply {
            address = Address.from(touristSpotEditDto.address)
            latitude = touristSpotEditDto.latitude
            longitude = touristSpotEditDto.longitude
        }

        placeRepository.save(place)

        touristSpot.apply {
            name = touristSpotEditDto.name
            website = touristSpotEditDto.website
            phone_number = touristSpotEditDto.phoneNumber
            description = touristSpotEditDto.description
        }

        return touristSpotRepository.save(touristSpot)
    }

    fun getTouristSpotsList(): List<TouristSpot> {
        return touristSpotRepository.findAll()
    }
}
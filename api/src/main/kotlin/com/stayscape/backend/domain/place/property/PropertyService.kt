package com.stayscape.backend.domain.place.property

import com.stayscape.backend.StayScapeException
import com.stayscape.backend.domain.place.Place
import com.stayscape.backend.domain.place.PlaceRepository
import com.stayscape.backend.domain.place.property.dto.PropertyCreateDto
import com.stayscape.backend.domain.place.touristspot.TouristSpot
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.domain.user.address.Address
import com.stayscape.backend.domain.user.role.Role
import com.stayscape.backend.domain.util.SecurityUtils
import com.stayscape.backend.logging.LoggedMethod
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PropertyService(
    private val propertyRepository: PropertyRepository,
    private val placeRepository: PlaceRepository,
    private val securityUtils: SecurityUtils,
    private val userService: UserService
) {
    fun getPropertyById(id: Int) : Property {
        val property = propertyRepository.findById(id).orElseThrow {
            StayScapeException("Property with id $id not found")
        }

        return property
    }

    @Transactional
    fun createProperty(propertyCreateDto: PropertyCreateDto): Property {
        securityUtils.userMustBeOfRole(Role.AFFILIATE.toString())

        val place = Place(
            address =  Address.from(propertyCreateDto.address),
            latitude = propertyCreateDto.latitude,
            longitude = propertyCreateDto.longitude
        )

        placeRepository.save(place)

        val property = Property(
            place = place,
            name = propertyCreateDto.name,
            website = propertyCreateDto.website,
            phone_number = propertyCreateDto.phoneNumber,
            description = propertyCreateDto.description,
            type = propertyCreateDto.type
        )

        return propertyRepository.save(property)
    }

    @Transactional
    fun deleteProperty(id: Int) {
        securityUtils.userMustBeOfRole(Role.AFFILIATE.toString())

        val property = propertyRepository.findById(id).orElseThrow {
            StayScapeException(
                "No property space with id $id exists"
            )
        }
        val place = property.place!!
        val user = userService.getCurrentUser()

        if(user.id != place.id) {
            throw StayScapeException(
                "Users can only delete their own properties"
            )
        }

        place.deleted = true
        placeRepository.save(place)
    }
}
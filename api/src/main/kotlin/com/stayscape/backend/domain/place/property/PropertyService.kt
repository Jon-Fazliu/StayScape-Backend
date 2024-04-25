package com.stayscape.backend.domain.place.property

import com.stayscape.backend.StayScapeException
import com.stayscape.backend.domain.place.Place
import com.stayscape.backend.domain.place.PlaceRepository
import com.stayscape.backend.domain.place.PlaceService
import com.stayscape.backend.domain.place.property.dto.PropertyCreateDto
import com.stayscape.backend.domain.place.property.dto.PropertyEditDto
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.domain.user.address.Address
import com.stayscape.backend.domain.user.role.Role
import com.stayscape.backend.domain.util.SecurityUtils
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PropertyService(
    private val propertyRepository: PropertyRepository,
    private val placeRepository: PlaceRepository,
    private val securityUtils: SecurityUtils,
    private val placeService: PlaceService,
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

        val property = getPropertyById(id)
        val place = property.place!!
        placeService.checkIsOwnedByUser(place)

        place.deleted = true
        placeRepository.save(place)
    }

    @Transactional
    fun editProperty(id: Int, propertyEditDto: PropertyEditDto): Property {
        securityUtils.userMustBeOfRole(Role.AFFILIATE.toString())

        val property = getPropertyById(id)

        val place = property.place!!
        placeService.checkIsOwnedByUser(place)

        place.apply {
            address = Address.from(propertyEditDto.address)
            latitude = propertyEditDto.latitude
            longitude = propertyEditDto.longitude
        }

        placeRepository.save(place)

        property.apply {
            name = propertyEditDto.name
            website = propertyEditDto.website
            phone_number = propertyEditDto.phoneNumber
            description = propertyEditDto.description
        }

        return propertyRepository.save(property)
    }


    fun getPropertiesList(): List<Property> {
        val user = userService.getCurrentUser()

        return when(user.role) {
            Role.AFFILIATE -> propertyRepository.findByPlaceUserId(user.id!!)
            else -> propertyRepository.findAll()
        }
    }
}
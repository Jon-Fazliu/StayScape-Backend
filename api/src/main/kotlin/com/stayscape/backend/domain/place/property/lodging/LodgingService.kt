package com.stayscape.backend.domain.place.property.lodging

import com.stayscape.backend.StayScapeException
import com.stayscape.backend.domain.place.property.PropertyRepository
import com.stayscape.backend.domain.place.property.PropertyService
import com.stayscape.backend.domain.place.property.lodging.dto.LodgingCreateDto
import com.stayscape.backend.domain.place.property.lodging.dto.LodgingEditDto
import com.stayscape.backend.domain.user.role.Role
import com.stayscape.backend.domain.util.SecurityUtils
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LodgingService(
    private val lodgingRepository: LodgingRepository,
    private val securityUtils: SecurityUtils,
    private val propertyService: PropertyService
) {
    fun getLodgingById(id: Int, propertyId: Int): Lodging {
        securityUtils.userMustBeOfRole(Role.AFFILIATE.toString())
        val lodging = lodgingRepository.findById(id).orElseThrow {
            StayScapeException(
                "Lodging with id $id does not exist"
            )
        }
        if(lodging.property?.id != propertyId) {
            throw StayScapeException(
                "Lodging does not belong to property $propertyId"
            )
        }

        return lodging
    }

    @Transactional
    fun createLodging(propertyId: Int, lodgingCreateDto: LodgingCreateDto): Lodging {
        securityUtils.userMustBeOfRole(Role.AFFILIATE.toString())
        val property = propertyService.getPropertyById(propertyId)

        val lodging = Lodging(
            property = property,
            singleBeds = lodgingCreateDto.singleBeds,
            doubleBeds = lodgingCreateDto.doubleBeds,
            roomCount = lodgingCreateDto.roomCount,
            description = lodgingCreateDto.description
        )

        return lodgingRepository.save(lodging)
    }

    @Transactional
    fun deleteLodging(propertyId: Int, id: Int) {
        securityUtils.userMustBeOfRole(Role.AFFILIATE.toString())

        val lodging = getLodgingById(id, propertyId)

        lodging.deleted = true
        lodgingRepository.save(lodging)
    }

    @Transactional
    fun editLodging(propertyId: Int, id: Int, lodgingEditDto: LodgingEditDto): Lodging {
        securityUtils.userMustBeOfRole(Role.AFFILIATE.toString())

        val lodging = getLodgingById(id, propertyId)

        lodging.apply {
            singleBeds = lodgingEditDto.singleBeds
            doubleBeds = lodgingEditDto.doubleBeds
            roomCount = lodgingEditDto.roomCount
            description = lodgingEditDto.description
        }

        return lodgingRepository.save(lodging)
    }

    fun getLodgingsList(propertyId: Int): List<Lodging> {
        securityUtils.userMustBeOfRole(Role.AFFILIATE.toString())
        return lodgingRepository.findByPropertyId(propertyId)
    }
}
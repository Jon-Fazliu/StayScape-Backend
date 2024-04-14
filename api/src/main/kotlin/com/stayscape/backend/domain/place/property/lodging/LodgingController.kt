package com.stayscape.backend.domain.place.property.lodging

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/properties/{property_id}/lodgings")
@RestController
class LodgingController(
    private val lodgingService: LodgingService
) {

}
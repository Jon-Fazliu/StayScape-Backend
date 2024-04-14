package com.stayscape.backend.domain.place.property.booking

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/properties/{property_id}/bookings")
@RestController
class BookingController(
    private val bookingService: BookingService
) {

}
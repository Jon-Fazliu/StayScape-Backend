package com.stayscape.backend.domain.place.review

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/reviews")
@RestController
class ReviewController(
    private val reviewService: ReviewService
) {

}
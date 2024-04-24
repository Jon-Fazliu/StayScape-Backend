package com.stayscape.backend.domain.place

import com.stayscape.backend.StayScapeException
import com.stayscape.backend.domain.user.UserService
import com.stayscape.backend.domain.util.SecurityUtils
import org.springframework.stereotype.Service

@Service
class PlaceService(
    private val userService: UserService
) {
    fun checkIsOwnedByUser(place: Place) {
        val user = userService.getCurrentUser()

        if(user.id != place.id) {
            throw StayScapeException(
                "Not owned by user."
            )
        }
    }
}
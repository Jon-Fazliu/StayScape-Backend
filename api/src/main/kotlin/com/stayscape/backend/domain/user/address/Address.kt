package com.stayscape.backend.domain.user.address

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Address(
    @Column(name = "street", length = 255)
    val street: String? = null,

    @Column(name = "street_number", length = 255)
    val streetNumber: String? = null,

    @Column(name = "postal_code", length = 255)
    val postalCode: String? = null,

    @Column(name = "city", length = 255)
    val city: String? = null,

    @Column(name = "country", length = 255)
    val country: String? = null

) {
    companion object {
        fun from(addressDto: AddressDto): Address {
            return Address(
                street = addressDto.street,
                streetNumber = addressDto.streetNumber,
                postalCode = addressDto.postalCode,
                city = addressDto.city,
                country = addressDto.country
            )
        }
    }
}
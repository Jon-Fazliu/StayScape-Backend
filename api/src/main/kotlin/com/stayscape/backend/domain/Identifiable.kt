package com.stayscape.backend.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable

interface Identifiable<T : Serializable> {

    @JsonIgnore
    fun getIdentifier(): T?

}
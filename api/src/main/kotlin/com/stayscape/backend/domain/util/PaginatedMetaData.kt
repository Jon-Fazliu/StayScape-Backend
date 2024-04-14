package com.stayscape.backend.domain.util

import com.fasterxml.jackson.annotation.JsonProperty

data class PaginatedMetaData (
    var first : Boolean? = null,
    var last : Boolean? = null,
    val number: Int? = null,
    val numberOfElements: Int? = null,
    val size: Int? = null,
    val totalElements: Long? = null,
    val totalPages: Int? = null
)
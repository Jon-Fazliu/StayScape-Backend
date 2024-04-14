package com.stayscape.backend.error

import org.springframework.http.HttpStatus
import java.io.Serializable

interface ErrorCode : Serializable {
    fun getCode(): Int
    fun getTitleKey(): String
    fun getMessageKey(): String
    fun getHttpStatus(): HttpStatus
}
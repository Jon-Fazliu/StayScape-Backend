package com.stayscape.backend

import java.time.Instant

data class ErrorMessage(val statusCode: Int, val timestamp: Instant, val message: String, val description: String, val errorCode: Int)
package com.stayscape.backend.domain.security.auth

class AuthenticationException(message: String, val errorCode: Int) : Exception(message)

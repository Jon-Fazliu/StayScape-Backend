package com.stayscape.backend.error

import org.springframework.http.HttpStatus

enum class ApiErrorCode(
    private val code: Int,
    private val status: HttpStatus,
) : ErrorCode {
    GENERIC_ERROR(
        ErrorValidator.validateCode(100000),
        HttpStatus.INTERNAL_SERVER_ERROR
    );

    companion object {
        private const val TITLE = "TITLE"
        private const val MESSAGE = "MESSAGE"
    }

    override fun getCode(): Int {
        return code
    }

    override fun getTitleKey(): String {
        return toPropertyKey(TITLE)
    }

    override fun getMessageKey(): String {
        return toPropertyKey(MESSAGE)
    }

    override fun getHttpStatus(): HttpStatus {
        return status
    }

    private fun toPropertyKey(key: String): String {
        return "${code}.${name}.${key}"
    }
}
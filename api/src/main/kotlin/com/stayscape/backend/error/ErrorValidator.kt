package com.stayscape.backend.error

object ErrorValidator {
    private const val MIN = 100000
    private const val MAX = 999999
    private val usedCodes: MutableSet<Int> = mutableSetOf()
    private val range = MIN..MAX

    fun validateCode(code: Int): Int {
        if (code !in range) {
            throw IllegalArgumentException("error code $code out of range $range")
        }
        if (code in usedCodes) {
            throw IllegalArgumentException("error code $code already used")
        }
        usedCodes.add(code)
        return code
    }
}

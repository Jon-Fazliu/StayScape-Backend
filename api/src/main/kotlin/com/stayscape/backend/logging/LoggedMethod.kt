package com.stayscape.backend.logging

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggedMethod(
    val mask: Array<String> = []
)

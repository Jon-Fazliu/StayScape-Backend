package com.stayscape.backend.logging

import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Import(LoggingConfig::class)
annotation class EnableLoggingConfig

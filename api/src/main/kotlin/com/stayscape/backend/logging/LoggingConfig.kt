package com.stayscape.backend.logging

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [LoggingConfig::class])
class LoggingConfig

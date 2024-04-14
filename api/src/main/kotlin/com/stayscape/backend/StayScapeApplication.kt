

package com.stayscape.backend

import com.stayscape.backend.logging.EnableLoggingConfig
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableAsync
@EnableTransactionManagement
@EnableScheduling
@EnableLoggingConfig
@SpringBootApplication(
    exclude = [SecurityAutoConfiguration::class, ManagementWebSecurityAutoConfiguration::class]
)
class StayScapeApplication

fun main(args: Array<String>) {
    runApplication<StayScapeApplication>(*args)
}

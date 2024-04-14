package stayscape.backend.domain

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@TestConfiguration
class ClockConfig {

    @Bean
    fun utcClock(): Clock {
        return Clock.fixed(Instant.parse("2024-01-14T10:10:45Z"), ZoneOffset.of("Z"))
    }
}
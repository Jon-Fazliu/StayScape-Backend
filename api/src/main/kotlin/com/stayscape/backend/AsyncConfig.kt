package com.stayscape.backend

import com.stayscape.backend.util.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
class AsyncConfig {
    val log = logger()
    @Bean(name = ["taskExecutor"])
    fun taskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        log.debug("Creating Async Executor")
        executor.corePoolSize = 2
        executor.queueCapacity = 100
        executor.setThreadNamePrefix("AsyncThread-")
        executor.initialize()
        return executor
    }

}

package com.stayscape.backend.mail.imap.config

import jakarta.annotation.PostConstruct
import jakarta.mail.Store
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.stereotype.Component

@Component
class MailConnectionPoolConfig(
    private val properties: MailConnectionPoolConfigProperties
) : GenericObjectPoolConfig<Store?>() {

    @PostConstruct
    fun init() {
        minIdle = properties.minIdle
        maxIdle = properties.maxIdle
        maxTotal = properties.maxTotal
        jmxEnabled = properties.jmxEnabled
    }

}

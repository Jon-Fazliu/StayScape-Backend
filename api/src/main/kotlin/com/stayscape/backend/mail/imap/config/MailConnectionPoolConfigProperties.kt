
package com.stayscape.backend.mail.imap.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "mail.connection.pool")
class MailConnectionPoolConfigProperties(
    var minIdle: Int = 0,
    var maxIdle: Int = 0,
    var maxTotal: Int = 0,
    var jmxEnabled: Boolean = false,
)

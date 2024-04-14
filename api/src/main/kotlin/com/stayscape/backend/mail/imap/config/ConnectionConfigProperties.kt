

package com.stayscape.backend.mail.imap.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "mail.connection")
class ConnectionConfigProperties(
    var protocol: String? = null,
    var host: String? = null,
    var port: Int = 0,
    var ssl: Boolean = false,
    var user: String? = null,
    var pass: String? = null,
    var fetchsize: Int = 0,
    var partialfetch: Boolean = false,
)

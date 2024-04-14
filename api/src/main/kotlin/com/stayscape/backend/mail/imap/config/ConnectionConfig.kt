

package com.stayscape.backend.mail.imap.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class ConnectionConfig {

    @Bean
    @Qualifier("mailConnection")
    fun mailConnectionProperties(properties: ConnectionConfigProperties): Properties {
        val props = Properties()
        props.setProperty(
            "mail.store.protocol",
            properties.protocol
        )
        props.setProperty(
            "mail.imap.host",
            properties.host
        )
        props.setProperty(
            "mail.imap.port",
            properties.port.toString()
        )
        props.setProperty(
            "mail.imap.ssl.enable",
            properties.ssl.toString()
        )
        props.setProperty(
            "mail.imaps.partialfetch",
            properties.partialfetch.toString()
        )
        props.setProperty(
            "mail.imaps.fetchsize",
            properties.fetchsize.toString()
        )
        return props
    }

}

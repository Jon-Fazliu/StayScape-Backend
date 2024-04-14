
package com.stayscape.backend.mail.imap

import com.stayscape.backend.mail.imap.config.MailConnectionPoolConfig
import jakarta.mail.Store
import org.apache.commons.pool2.impl.GenericObjectPool
import org.springframework.stereotype.Component

@Component
class MailConnectionPoolImpl(
    factory: MailConnectionFactory,
    config: MailConnectionPoolConfig
) : GenericObjectPool<Store>(
    factory,
    config
),
    MailConnectionPool

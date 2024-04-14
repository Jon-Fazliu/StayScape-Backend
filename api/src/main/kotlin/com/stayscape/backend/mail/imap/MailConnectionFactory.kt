
package com.stayscape.backend.mail.imap

import com.stayscape.backend.mail.imap.config.ConnectionConfigProperties
import jakarta.mail.Session
import jakarta.mail.Store
import org.apache.commons.pool2.BasePooledObjectFactory
import org.apache.commons.pool2.DestroyMode
import org.apache.commons.pool2.PooledObject
import org.apache.commons.pool2.impl.DefaultPooledObject
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.*

@Component
class MailConnectionFactory(
    @param:Qualifier("mailConnection")
    private val mailConnectionProperties: Properties,
    private val connectionConfigProperties: ConnectionConfigProperties
) : BasePooledObjectFactory<Store>() {

    override fun create(): Store {
        val session = Session.getInstance(mailConnectionProperties)
        val store = session.getStore(connectionConfigProperties.protocol)
        store.connect(
            connectionConfigProperties.host,
            connectionConfigProperties.user,
            connectionConfigProperties.pass
        )
        return store
    }

    override fun wrap(store: Store): PooledObject<Store> {
        return DefaultPooledObject(store)
    }

    @Throws(Exception::class)
    override fun destroyObject(
        p: PooledObject<Store>,
        destroyMode: DestroyMode
    ) {
        p.getObject().close()
        super.destroyObject(
            p,
            destroyMode
        )
    }

    override fun validateObject(p: PooledObject<Store>): Boolean {
        return p.getObject().isConnected
    }
}

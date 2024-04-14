

package com.stayscape.backend.mail.imap

import jakarta.mail.Store

interface MailConnectionPool {
    fun borrowObject(): Store
    fun invalidateObject(store: Store)
    fun returnObject(store: Store)
}
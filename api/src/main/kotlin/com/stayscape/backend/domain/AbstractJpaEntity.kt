package com.stayscape.backend.domain

import jakarta.persistence.MappedSuperclass
import org.springframework.data.util.ProxyUtils
import java.io.Serializable

@MappedSuperclass
abstract class AbstractJpaEntity<T : Serializable> : Identifiable<T> {

    // id only check for jpa entity
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false
        if (other !is AbstractJpaEntity<*>) return false
        val id = getIdentifier()
        return !(null == id || id != other.getIdentifier())
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "${javaClass.simpleName}[${toStringAttributes()}]"
    }

    open fun toStringAttributes(): Map<String, Any?> {
        return mapOf("id" to getIdentifier())
    }

}
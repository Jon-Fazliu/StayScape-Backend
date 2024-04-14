

package com.stayscape.backend.mail.smtp

import com.stayscape.backend.templating.LocalTemplate
import java.util.*

data class TemplateMail(
    val subject: String,
    val localTemplate: LocalTemplate,
    val data: Map<String, Any?> = emptyMap(),
    val locale: Locale = Locale.ENGLISH,

    val from: String? = null,
    val to: List<String>,
    val cc: List<String> = emptyList(),
    val bcc: List<String> = emptyList(),
)

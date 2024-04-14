

package com.stayscape.backend.mail.smtp

import com.stayscape.backend.templating.LocalTemplate
import com.stayscape.backend.templating.TemplatingService
import freemarker.template.Configuration
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import java.io.ObjectInputFilter.Config
import java.nio.charset.StandardCharsets
import java.util.*

@Service
class MailSmtpService(
    private val sender: JavaMailSender,
    @Value("\${app.mail.default-from}")
    private val defaultFrom: String,
    private val templating: TemplatingService,
    private val freemarkerConfig: Configuration
) {
    fun getText(
        localTemplate: LocalTemplate,
        locale: Locale = Locale.ENGLISH,
        data: Map<String, Any?> = emptyMap()
    ): String {
        return templating.getText(
            localTemplate,
            locale,
            data
        )
    }

    @Async
    @Throws(MailException::class)
    fun send(
        mail: TemplateMail
    ) {
        sendMail(
            mail.subject,
            getText(
                mail.localTemplate,
                mail.locale,
                mail.data
            ),
            mail.from ?: defaultFrom,
            mail.to.toTypedArray(),
            mail.cc.toTypedArray(),
            mail.bcc.toTypedArray(),
            true
        )
    }

    @Async
    @Throws(MailException::class)
    fun sendMail(
        subject: String,
        text: String,

        from: String = defaultFrom,
        to: Array<String>,
        cc: Array<String> = emptyArray(),
        bcc: Array<String> = emptyArray(),
        html: Boolean
    ) {
        val mm = sender.createMimeMessage()
        val message = MimeMessageHelper(mm, html, StandardCharsets.UTF_8.displayName())
        message.setFrom(from)
        message.setTo(to)
        message.setCc(cc)
        message.setBcc(bcc)
        message.setSubject(subject)
        message.setText(text, html)
        sender.send(mm)
    }

}
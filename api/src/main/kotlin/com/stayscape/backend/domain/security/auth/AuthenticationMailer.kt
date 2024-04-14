package com.stayscape.backend.domain.security.auth

import com.stayscape.backend.mail.smtp.MailSmtpService
import com.stayscape.backend.mail.smtp.TemplateMail
import com.stayscape.backend.templating.LocalTemplate
import com.stayscape.backend.util.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class AuthenticationMailer(
    private val mailSmtpService: MailSmtpService,
    @Value("\${app.frontend.url}")
    private var frontendUrl : String,
    @Value("\${app.mail.default-from}")
    private val defaultFrom: String,
    @Value("\${spring.mail.host}")
    private val mailHost: String,
    @Value("\${spring.mail.username}")
    private val mailer: String
) {
    private val log = logger()

    @Async
    fun sendConfirmationEmail(email: String, confirmEmailToken: String) {
        try {
            val ts = Instant.now()
            val link = "$frontendUrl/auth/register/confirm?token=$confirmEmailToken"
            mailSmtpService.send(
                TemplateMail(
                    subject = "Stayscape: Confirm your email",
                    localTemplate = LocalTemplate.CONFIRM_EMAIL,
                    data = mapOf(
                        "ts" to ts,
                        "link" to link
                    ),
                    to = listOf(email)
                )
            )
        } catch (e: Exception) {
            log.info("Mailing for email: {} from {} threw exception {} : {}. \n Mail server is: {}, mailer is: {}",
                email,
                defaultFrom,
                e.javaClass,
                e.message,
                mailHost,
                mailer
            )
        }
    }

    @Async
    fun sendResetPasswordEmail(email: String, resetPasswordToken: String) {
        try {
            log.debug("Sending mail to $email")
            val ts = Instant.now()
            val link = "$frontendUrl/auth/complete-password-request?token=$resetPasswordToken"
            mailSmtpService.send(
                TemplateMail(
                    subject = "StayScape: Reset password",
                    localTemplate = LocalTemplate.RESET_PASSWORD,
                    data = mapOf(
                        "ts" to ts,
                        "link" to link
                    ),
                    to = listOf(email)
                )
            )
        } catch (e: Exception) {
            log.info("Mailing threw exception", e)
        }
    }

}
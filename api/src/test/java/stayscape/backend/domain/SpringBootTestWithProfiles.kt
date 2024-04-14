package stayscape.backend.domain

import com.stayscape.backend.AsyncConfig
import com.stayscape.backend.domain.security.PasswordEncoderConfiguration
import com.stayscape.backend.domain.security.WebServerConfiguration
import com.stayscape.backend.mail.imap.config.MailConnectionPoolConfig
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS
)
@Retention(AnnotationRetention.RUNTIME)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(profiles = ["test", "secure"])
@Import(value = [
    WebServerConfiguration::class,
    AsyncConfig::class,
    PasswordEncoderConfiguration::class,
    MailConnectionPoolConfig::class,
    WebServerConfiguration::class
])
@SpringBootTest(
    classes = [ClockConfig::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
annotation class SpringBootTestWithProfiles
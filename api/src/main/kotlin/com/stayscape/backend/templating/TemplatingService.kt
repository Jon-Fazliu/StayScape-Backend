package com.stayscape.backend.templating

import freemarker.template.*
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import java.util.*

@Service
class TemplatingService(
    @Value("\${app.templating.classpath-dir}")
    private val templateDir: String,
    private val config: Configuration = Configuration(Configuration.VERSION_2_3_32),
) {
    private val defaultLocale = Locale.ENGLISH

    @PostConstruct
    protected fun init() {
        config.setClassForTemplateLoading(
            javaClass,
            templateDir
        )
        config.defaultEncoding = StandardCharsets.UTF_8.toString()
        config.locale = Locale.ENGLISH
        config.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
    }

    fun getText(
        cpTemplate: ClasspathTemplate,
        locale: Locale,
        data: Map<String, Any?> = emptyMap()
    ): String {
        val template = config.getTemplate(
            cpTemplate.getFilename().replace(
                "%LANG%",
                locale.language
            )
        )
        return mapText(
            cpTemplate.getInitialBufferLength(),
            template,
            locale,
            data
        )
    }

    private fun mapText(
        bufferSize: Int,
        template: Template,
        locale: Locale,
        data: Map<String, Any?>
    ): String {
        try {
            StringWriter(bufferSize).use { writer ->
                template.process(
                    data,
                    writer
                )
                writer.flush()
                return writer.toString().dropLastWhile { it == '\n' }
            }
        } catch (exc: TemplateNotFoundException) { // fallback to default locale if we can't find requested lang
            if (defaultLocale == locale) { // fail if already tried default locale
                throw IllegalStateException(
                    "template not found ${template.name}",
                    exc
                )
            }
            return mapText(
                bufferSize,
                template,
                defaultLocale,
                data
            )
        } catch (exc: IOException) {
            throw IllegalStateException(
                "could not create text from template",
                exc
            )
        } catch (exc: TemplateException) {
            throw IllegalStateException(
                "could not create text from template",
                exc
            )
        }

    }

}

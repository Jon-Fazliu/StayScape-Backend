package com.stayscape.backend.templating

enum class LocalTemplate(
    private val filename: String,
    private val initialBufferLength: Int = 1024,
) : ClasspathTemplate {
    RESET_PASSWORD("reset_password_%LANG%.ftl"),
    CONFIRM_EMAIL("confirm_email_%LANG%.ftl");

    override fun getFilename(): String {
        return filename
    }

    override fun getInitialBufferLength(): Int {
        return initialBufferLength
    }
}

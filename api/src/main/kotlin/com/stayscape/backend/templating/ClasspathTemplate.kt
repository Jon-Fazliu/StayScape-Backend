package com.stayscape.backend.templating

interface ClasspathTemplate {
    fun getFilename(): String
    fun getInitialBufferLength(): Int
}

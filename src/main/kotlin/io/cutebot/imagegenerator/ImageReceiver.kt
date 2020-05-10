package io.cutebot.imagegenerator

interface ImageReceiver {
    fun receive(filePath: String)
    fun fail(reason: String)
}

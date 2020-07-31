package io.cutebot.memegen.service.model

class Color (
        val red: Int,
        val green: Int,
        val blue: Int,
        val alpha: Int
) {
    fun getAwtColor(): java.awt.Color {
        return java.awt.Color(red, green, blue, alpha)
    }
}

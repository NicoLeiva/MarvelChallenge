package com.example.marvelapp.utils

object ImageUtils {

    fun createUrlImage(path:String, extension:String):String {
        return "${path}.${extension}".replace("http", "https")
    }
}
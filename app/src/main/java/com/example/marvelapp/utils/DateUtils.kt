package com.example.marvelapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val DEFAULT_TIME_ZONE = "America/Argentina/Buenos_Aires"

    private const val PATTERN = "yyyy-MM-dd'T'hh:mm:ss-SSSS"
    private const val PATTERN_PRINT = "dd 'de' MMMM 'de' yyyy"

    fun parseDate(date:String):String {
        val dateFormatted = getSimpleDateFormat().parse(date) ?: ""
        val locale = Locale("es", "ar")
        return getSimpleDateFormat(PATTERN_PRINT, locale)
            .format(dateFormatted)
    }

    private fun getSimpleDateFormat(): SimpleDateFormat {
        return getSimpleDateFormat(PATTERN, Locale.ENGLISH)
    }

    private fun getSimpleDateFormat(pattern: String, locale: Locale): SimpleDateFormat {
        val df = SimpleDateFormat(pattern, locale)
        df.timeZone = TimeZone.getTimeZone(DEFAULT_TIME_ZONE)
        return df
    }
}
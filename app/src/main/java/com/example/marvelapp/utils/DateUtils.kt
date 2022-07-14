package com.example.marvelapp.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.marvelapp.ui.adapter.ExpansibleListViewAdapter
import com.example.marvelapp.utils.DateUtils.PATTERN
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {

    private const val DATE_FORMAT_INPUT = "yyyy-MM-dd HH:mm:ss"
    private const val PATTERN = "dd 'de' MMMM yyyy"

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDate(date: String): String {
        val dateClean = LocalDate.parse(date.removeRange(19,24).replace("T"," "),
            DateTimeFormatter.ofPattern(DATE_FORMAT_INPUT)).toString()
        val dateFormatted = LocalDate.parse(dateClean)
        val sym = DateFormatSymbols.getInstance( Locale("es","ar"))
        sym.months = arrayOf("Enero","Febrero","Marzo","Abril","Mayo","Junio", "Julio",
                            "Agosto","Septiembre","Octubre","Noviembre","Diciembre")
        return  SimpleDateFormat(PATTERN, sym).format(Date(dateFormatted.year-1900,
            dateFormatted.monthValue-1,dateFormatted.dayOfMonth))
    }
}
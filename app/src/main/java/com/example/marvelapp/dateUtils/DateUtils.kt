package com.example.marvelapp.dateUtils

import android.util.Log
import com.example.marvelapp.ui.adapter.ExpansibleListViewAdapter
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {

    private const val DAY_IN_MILLISECONDS = 24 * 60 * 60 * 1000
    private const val ISSUANCE_DAYS_LIMIT_TO_CANCEL = 10 * DAY_IN_MILLISECONDS
    private const val PIN_SET_OFFSET_DAYS_SINCE_ISSUANCEDATE = 7 * DAY_IN_MILLISECONDS
    private const val DATE_FORMAT_INPUT = "yyyy-MM-dd'T'HH:mm:ss"
    const val DATE_FORMAT_UI = "M/dd/YY"

    fun Date.withinCancelLimit(): Boolean = System.currentTimeMillis() - this.time < ISSUANCE_DAYS_LIMIT_TO_CANCEL
    fun Date.canSetPin(): Boolean = System.currentTimeMillis() - this.time > PIN_SET_OFFSET_DAYS_SINCE_ISSUANCEDATE
    fun Date.toStringFormat(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return try {
            dateFormatter.format(this)
        } catch (e: Exception) {
            Log.d("DateUtils","Error while trying format date $this to string. ${e.message}")
            ""
        }
    }

    fun String.parseToDate(): Date? {
        val date: Date? = try {
            SimpleDateFormat(DATE_FORMAT_INPUT, Locale.US).parse(this) as Date
        } catch (e: ParseException) {
            Log.d("DateUtils","Error while trying to parse $this. ${e.message}")
            null
        }
        return date
    }
}
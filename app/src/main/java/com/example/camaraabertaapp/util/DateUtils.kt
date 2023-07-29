package com.example.camaraabertaapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val BRAZILIAN_DATE_TIME_FORMAT = "yyyy-MM-ddTHH:mm"

fun String.toLocalDateTime(): LocalDateTime? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern(BRAZILIAN_DATE_TIME_FORMAT)
        LocalDateTime.parse(this, formatter)
    } else {
        null
    }
}
package com.claudiogalvaodev.camaraaberta.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

fun Date.toString(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(this)
}

fun LocalDate.toDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return this.format(formatter)
}

fun String.toLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    return LocalDateTime.parse(this, formatter)
}

fun String.toLocalDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(this, formatter)
}

fun LocalDateTime.getTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}

fun LocalDate.toReadableFullDate(): String {
    val formatter = DateTimeFormatter.ofPattern("EEE, dd MMMM yyyy")
    val dateFormatted = this.format(formatter)
    val partToReplace = dateFormatted.substringBefore(",")
    return if (this == LocalDate.now()) {
        dateFormatted.replace(partToReplace, "HOJE")
    } else if (this == LocalDate.now().plusDays(1)) {
        dateFormatted.replace(partToReplace, "AMANHÃ")
    } else if (this == LocalDate.now().minusDays(1)) {
        dateFormatted.replace(partToReplace, "ONTEM")
    } else dateFormatted
}

fun LocalDateTime.isNotToday(): Boolean {
    return this.toLocalDate() != LocalDate.now()
}
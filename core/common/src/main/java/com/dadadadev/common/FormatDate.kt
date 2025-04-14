package com.dadadadev.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun formatDate(inputDate: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        val dateTime = LocalDateTime.parse(inputDate, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        dateTime.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        "Неправильный формат"
    } catch (e: Exception) {
        "Ошибка парсинга"
    }
}

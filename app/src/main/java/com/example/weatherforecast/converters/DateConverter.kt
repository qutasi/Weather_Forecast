package com.example.weatherforecast.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {

    @TypeConverter
    fun textToDate(text: String) : LocalDate {
        val parts = text.split("-")
        return LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
    }

    @TypeConverter
    fun dateToText(date: LocalDate) : String {
        return "${date.year}-${date.monthValue}-${date.dayOfMonth}"
    }
}
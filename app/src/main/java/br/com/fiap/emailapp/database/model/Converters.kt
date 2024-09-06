package br.com.fiap.emailapp.database.model

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.Year

class Converters {
    @TypeConverter
    fun fromEmailLabelList(labels: MutableList<EmailLabel>): String {
        return labels.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toEmailLabelList(data: String): MutableList<EmailLabel> {
        return data
            .split(",")
            .mapNotNull {
                try {
                    EmailLabel.valueOf(it)
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
            .toMutableList()
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString)
    }

    @TypeConverter
    fun fromLocalTime(time: LocalTime): String {
        return time.toString()
    }

    @TypeConverter
    fun toLocalTime(timeString: String): LocalTime {
        return LocalTime.parse(timeString)
    }

    @TypeConverter
    fun fromYear(year: Year): Int {
        return year.value
    }

    @TypeConverter
    fun toYear(yearValue: Int): Year {
        return Year.of(yearValue)
    }

    @TypeConverter
    fun fromMonth(month: Month): String {
        return month.value.toString().padStart(2, '0')  // Converte o mês para dois dígitos
    }

    @TypeConverter
    fun toMonth(month: String): Month {
        return Month.of(month.toInt())  // Converte de String para Month
    }
}
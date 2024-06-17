package br.com.fiap.emailapp.database.model

import androidx.room.TypeConverter

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
}
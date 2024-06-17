package br.com.fiap.emailapp.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import br.com.fiap.emailapp.R
import br.com.fiap.emailapp.database.model.EmailLabel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
    fun isFavorite(labels: List<EmailLabel>): ImageVector {
        return if (labels.contains(EmailLabel.FAVORITE)) {
            ImageVector.vectorResource(id = R.drawable.favorite_star)
        } else {
            ImageVector.vectorResource(id = R.drawable.star_outline)
        }
    }

fun isReaded(bol : Boolean) : FontWeight{
    return if (bol) FontWeight.Bold
    else FontWeight.Medium
}

fun toggleFavorite(labels: MutableList<EmailLabel>): MutableList<EmailLabel> {
    val newLabels = labels.toMutableList()
    if (newLabels.contains(EmailLabel.FAVORITE)) {
        newLabels.remove(EmailLabel.FAVORITE)
    } else {
        newLabels.add(EmailLabel.FAVORITE)
    }
    return newLabels
}

fun formatDate(date: String): String{
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    val dateTime = LocalDateTime.parse(date, formatter)
    val current = LocalDateTime.now()

    return when {
        dateTime.dayOfMonth.equals(current.dayOfMonth) -> {
            // Mesmo dia
            dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
        dateTime.year == current.year -> {
            // Mesmo ano
            dateTime.format(DateTimeFormatter.ofPattern("dd-MM"))
        }
        else -> {
            // Anos diferentes
            dateTime.format(DateTimeFormatter.ofPattern("MM-yyyy"))
        }
    }
}

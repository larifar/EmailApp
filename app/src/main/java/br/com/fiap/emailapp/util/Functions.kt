package br.com.fiap.emailapp.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import br.com.fiap.emailapp.R
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.model.EmailLabel
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

fun changeisReadedColor(bol : Boolean) : Color {
    return if (!bol) Color.Black.copy(alpha = 0.65f)
    else Color.Black
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

fun performSearch(query: String, listEmails: List<Email>) : List<Email>{
    if (query.isBlank()) {
        return listEmails
    }
    val lowerCaseQuery = query.lowercase()
    return listEmails.filter { email ->
        email.sender.lowercase().contains(lowerCaseQuery) ||
                email.content.lowercase().contains(lowerCaseQuery) ||
                email.title.lowercase().contains(lowerCaseQuery)
    }
}

fun EmailLabel.toPortuguese(): String {
    return when (this) {
        EmailLabel.PRIMARY -> "Principal"
        EmailLabel.SOCIAL -> "Social"
        EmailLabel.PROMOTIONS -> "Promoções"
        EmailLabel.UPDATES -> "Atualizações"
        EmailLabel.FORUMS -> "Fóruns"
        EmailLabel.FAVORITE -> "Favoritos"
    }
}

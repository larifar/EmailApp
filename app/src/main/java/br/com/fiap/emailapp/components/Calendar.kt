package br.com.fiap.emailapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.emailapp.database.model.Reminder
import com.kizitonwose.calendar.core.CalendarDay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun Day(day: CalendarDay, displayMonth: YearMonth, onClick: ()->Unit, reminderList: List<Reminder>) {
    val backgroundColor =  changeDayColor(day, displayMonth)
    val hasReminder = reminderList.any { it.date == day.date }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColor)
            .clickable{
                onClick()
            }
            .then(if (hasReminder) Modifier.border(color = Color.Yellow, width = 2.dp, shape = CircleShape) else Modifier)
            .then(if (day.date == LocalDate.now())Modifier.border(color = Color.Red, width = 2.dp, shape = CircleShape) else Modifier)
    ) {
        Text(text = day.date.dayOfMonth.toString())
    }
}

@Composable
fun changeDayColor(day: CalendarDay, displayMonth: YearMonth): Color {
    val today = LocalDate.now()
    return when {
        day.date == today -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) // Cor para o dia atual
        day.date.month == today.month -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) // Cor para outros dias do mês atual
        day.date.month == displayMonth.month -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) // Cor para dias do mês exibido
        else -> MaterialTheme.colorScheme.background // Cor de fundo padrão
    }
}

@Composable
fun MonthHeader(yearMonth: YearMonth, daysOfWeek: List<DayOfWeek>) {
    val locale = Locale("pt", "BR")
    val month = yearMonth.month.getDisplayName(TextStyle.FULL, locale)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
    Column {
        Text(
            text = "$month ${yearMonth.year}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale).removeSuffix(".").uppercase(),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
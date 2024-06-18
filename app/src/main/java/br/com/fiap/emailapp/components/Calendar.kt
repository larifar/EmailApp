package br.com.fiap.emailapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun Day(day: CalendarDay) {
    val backgroundColor =  changeDayColor(day)// Cor de fundo para os dias do mês atual


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColor)
    ) {
        Text(text = day.date.dayOfMonth.toString())
    }
}

@Composable
fun changeDayColor(day: CalendarDay): Color {
    val currentMonth = YearMonth.now()
    if (day.date.dayOfMonth == LocalDate.now().dayOfMonth && day.date.month == currentMonth.month) {
        return MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    }else if (day.date.month == currentMonth.month){
        return MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    }
    else {
       return MaterialTheme.colorScheme.background // Cor de fundo padrão
    }
}


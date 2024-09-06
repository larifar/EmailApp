package br.com.fiap.emailapp.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.emailapp.components.Day
import br.com.fiap.emailapp.components.MonthHeader
import br.com.fiap.emailapp.components.ReminderDialog
import br.com.fiap.emailapp.components.ReminderItem
import br.com.fiap.emailapp.components.ReminderViewModel
import br.com.fiap.emailapp.database.model.Reminder
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth

@Composable
fun CalendarScreen(viewModel: ReminderViewModel) {
    var selectedDay by remember { mutableStateOf<LocalDate?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(10) }
    val endMonth = remember { currentMonth.plusMonths(10) }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = DayOfWeek.MONDAY,
    )
    val displayMonth = state.firstVisibleMonth.yearMonth
    val reminders by viewModel.remindersByMonth.collectAsState()

    LaunchedEffect(displayMonth, reminders) {
        viewModel.getAllByMonth(displayMonth.month, Year.of(displayMonth.year))
    }

    Column {
        HorizontalCalendar(
            state = state,
            dayContent = {
                Day(it, displayMonth, onClick = {
                    selectedDay = it.date
                    isDialogOpen = true
                }, reminderList = reminders)
            },
            monthHeader = { month ->
                val daysOfWeek = remember { month.weekDays.first().map { it.date.dayOfWeek } }
                MonthHeader(month.yearMonth, daysOfWeek)
            },
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        LazyColumn {
                items(reminders){
                    ReminderItem(it, onDelete = {
                        viewModel.delete(it)
                    })
                }
            }

        if (isDialogOpen && selectedDay != null) {
            ReminderDialog(
                selectedDay = selectedDay!!,
                onDismiss = { isDialogOpen = false },
                onSaveReminder = { title, time ->
                    viewModel.saveReminder(Reminder(title = title, time = time, date = selectedDay!!))
                    isDialogOpen = false
                }
            )
        }
    }

}
package br.com.fiap.emailapp.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.emailapp.database.model.Reminder
import br.com.fiap.emailapp.database.repository.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Month
import java.time.Year

class ReminderViewModel(private val repository: ReminderRepository) : ViewModel() {
    private val _remindersByMonth = MutableStateFlow<List<Reminder>>(emptyList())
    val remindersByMonth: StateFlow<List<Reminder>> = _remindersByMonth

    fun getAllByMonth(month: Month, year: Year) {
        viewModelScope.launch {
            val currentMonth = month.value.toString().padStart(2, '0')
            val yearString = year.value.toString()
            val reminders = repository.getAllByMonth(currentMonth, yearString)
            _remindersByMonth.emit(reminders)
        }
    }

    fun delete(reminder: Reminder){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.delete(reminder)
            }
            delay(200)
            getAllByMonth(reminder.date.month, Year.of(reminder.date.year))
        }
    }

    fun saveReminder(reminder: Reminder) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insert(reminder)
            }
            delay(200)
            getAllByMonth(reminder.date.month, Year.of(reminder.date.year))
        }
    }
}
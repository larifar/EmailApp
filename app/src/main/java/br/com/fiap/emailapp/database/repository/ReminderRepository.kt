package br.com.fiap.emailapp.database.repository

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.emailapp.database.dao.EmailDatabase
import br.com.fiap.emailapp.database.model.Reminder
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Year

class ReminderRepository(context: Context) : ViewModel(){
    var db = EmailDatabase.getDatabase(context).ReminderDAO()

    suspend fun insert(reminder: Reminder){
        viewModelScope.launch {
            db.insert(reminder)
        }
    }

    suspend fun update(reminder: Reminder){
        viewModelScope.launch {
            db.update(reminder)
        }
    }

    suspend fun delete(reminder: Reminder){
        viewModelScope.launch {
            db.delete(reminder)
        }
    }

    suspend fun getRemindersByDate(date: LocalDate): List<Reminder>{
        return db.getRemindersByDate(date)
    }

    suspend fun getAllReminders(): List<Reminder>{
        return db.getAllReminders()
    }

    suspend fun getAllByMonth(month: String, year: String) : List<Reminder>{
        return db.getAllByMonth(month, year)
    }
}
package br.com.fiap.emailapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.emailapp.database.model.Reminder
import java.time.LocalDate

@Dao
interface ReminderDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: Reminder)

    @Update
    suspend fun update(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)

    @Query("SELECT * FROM tbl_reminder WHERE date = :date")
    suspend fun getRemindersByDate(date: LocalDate): List<Reminder>

    @Query("SELECT * FROM tbl_reminder")
    suspend fun getAllReminders(): List<Reminder>

    @Query("SELECT * FROM tbl_reminder WHERE strftime('%m', date) = :month AND strftime('%Y', date) = :year ORDER BY strftime('%d', date) ASC")
    suspend fun getAllByMonth(month: String, year: String) : List<Reminder>
}
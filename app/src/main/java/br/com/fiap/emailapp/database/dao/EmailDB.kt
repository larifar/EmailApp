package br.com.fiap.emailapp.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.fiap.emailapp.database.model.Converters
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.model.Reminder

@Database(entities = [Email::class, Reminder::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EmailDatabase : RoomDatabase() {
    abstract fun EmailDAO(): EmailDAO
    abstract fun ReminderDAO(): ReminderDAO

    companion object {

        private lateinit var instance: EmailDatabase

        fun getDatabase(context: Context): EmailDatabase {
            if (!::instance.isInitialized) {
                instance = Room
                    .databaseBuilder(
                        context,
                        EmailDatabase::class.java,
                        "email_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}
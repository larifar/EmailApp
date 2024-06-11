package br.com.fiap.emailapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.fiap.emailapp.database.model.Email

@Dao
interface EmailDAO {
    @Insert
    fun salvar(email: Email) : Long

    @Update
    fun update(email: Email)

    @Delete
    fun deletar(email: Email) : Int

    @Query("SELECT * FROM TBL_EMAILS WHERE id = :id")
    fun buscarPeloId(id: Long): Email

    @Query("SELECT * FROM TBL_EMAILS")
    fun listarEmails(): MutableList<Email>
}
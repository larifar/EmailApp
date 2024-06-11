package br.com.fiap.emailapp.database.repository

import android.content.Context
import br.com.fiap.emailapp.database.dao.EmailDatabase
import br.com.fiap.emailapp.database.model.Email

class EmailRepository(context: Context) {

    var db = EmailDatabase.getDatabase(context).EmailDAO()

    fun salvar(email: Email): Long {
        return db.salvar(email)
    }

    fun excluir(email: Email): Int{
        return db.deletar(email)
    }

    fun buscarPeloId(id: Long): Email{
        return db.buscarPeloId(id)
    }

    fun listarEmails(): List<Email>{
        return db.listarEmails()
    }
}
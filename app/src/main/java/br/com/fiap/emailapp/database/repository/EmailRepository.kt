package br.com.fiap.emailapp.database.repository

import android.content.Context
import br.com.fiap.emailapp.database.dao.EmailDatabase
import br.com.fiap.emailapp.database.model.Email

class EmailRepository(context: Context) {

    var db = EmailDatabase.getDatabase(context).EmailDAO()

    fun count() : Int{
        return db.countEmails()
    }

    fun insertAll(emails: List<Email>){
        return db.insertAll(emails)
    }

    fun salvar(email: Email): Long {
        return db.salvar(email)
    }

    fun update(email: Email){
        return db.update(email)
    }

    fun excluir(email: Email): Int{
        return db.deletar(email)
    }

    fun excluirTodos(){
        return db.deleteAll()
    }

    fun buscarPeloId(id: Long): Email{
        return db.buscarPeloId(id)
    }

    fun listarEmails(): List<Email>{
        return db.listarEmails()
    }
}
package br.com.fiap.emailapp.services

import androidx.compose.runtime.Composable
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.model.EmailLabel
import br.com.fiap.emailapp.database.repository.EmailRepository

@Composable
fun SetEmails(repository: EmailRepository) {
    repository.excluirTodos()

    for (i in 0..11) {
        val remetente = "Remetente ${i + 1}"
        val destinatario = "you"
        val titulo = "Titulo Teste ${i + 1}"
        val conteudo = "Conteudo de teste para email ${i + 1}"
        val data = "17-06-2024 08:${i + 10}"
        val novo = i % 2 == 0
        val etiquetasIniciais = mutableListOf(
            EmailLabel.PRIMARY,
            if (i % 3 == 0) EmailLabel.FAVORITE else EmailLabel.UPDATES,
            if (i % 4 == 0) EmailLabel.PROMOTIONS else EmailLabel.FORUMS
        )

        val email = Email(
            id = 0,
            sender = remetente,
            receiver = destinatario,
            title = titulo,
            content = conteudo,
            date = data,
            isNew = novo,
            initialLabel = etiquetasIniciais
        )

        repository.salvar(email)
    }
}
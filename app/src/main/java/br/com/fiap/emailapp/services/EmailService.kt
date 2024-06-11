package br.com.fiap.emailapp.services

import androidx.compose.runtime.Composable
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.model.EmailLabel
import br.com.fiap.emailapp.database.repository.EmailRepository

@Composable
fun setEmails(repository: EmailRepository) {
    repository.salvar(
        Email(
            id = 1,
            sender = "Larissa Faria",
            receiver = "eu",
            title = "Titulo supe importante",
            content = "sim conetudo super importante",
            date = "06h00",
            isNew = true,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FORUMS)
        )
    )
    repository.salvar(
        Email(
            id = 2,
            sender = "Cachorrinhos br",
            receiver = "eu",
            title = "Salve os cachorros",
            content = "Nos ajude a salvar cachorrinhos de rua",
            date = "7h03",
            isNew = true,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FAVORITE)
        )
    )
    repository.salvar(
        Email(
            id = 3,
            sender = "Roberto da Silva",
            receiver = "eu",
            title = "Reunião importante",
            content = "Acesse o link abaixo",
            date = "7h44",
            isNew = true,
            initialLabel = mutableListOf(EmailLabel.PRIMARY)
        )
    )
    repository.salvar(
        Email(
            id = 4,
            sender = "Charlie Jr.",
            receiver = "eu",
            title = "Oportunidade",
            content = "Venha fazer parte da minha comunidade do dircord",
            date = "10h00",
            isNew = true,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FORUMS)
        )
    )
    repository.salvar(
        Email(
            id = 5,
            sender = "Fly Try",
            receiver = "eu",
            title = "An exclusive card game",
            content = "bala hewibnf aihaka aaaaj ",
            date = "21h00",
            isNew = true,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FORUMS)
        )
    )
    repository.salvar(
        Email(
            id = 6,
            sender = "Lol br",
            receiver = "eu",
            title = "jw kaopn ojdwn pp iajabu aaeef deofbfj",
            content = "whjfkwefhe wefjkfefhe kjfbkweeuj rejbf we feh",
            date = "23h00",
            isNew = false,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FORUMS)
        )
    )
    repository.salvar(
        Email(
            id = 7,
            sender = "hau mesuv",
            receiver = "eu",
            title = "Jvw wj rkhh ldjufbe fhdndjhf hshb",
            content = "sufei ewfeb wh ujdkj hirhw hwewoqçj s",
            date = "ontem",
            isNew = false,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FORUMS)
        )
    )
}
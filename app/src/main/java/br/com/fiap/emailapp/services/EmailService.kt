package br.com.fiap.emailapp.services

import androidx.compose.runtime.Composable
import br.com.fiap.emailapp.components.Email
import br.com.fiap.emailapp.components.EmailLabel

@Composable
fun getEmails(): MutableList<Email> {
    val emails : MutableList<Email> = mutableListOf()
    emails.add(
        Email(
        sender = "Larissa Faria",
        title = "Titulo supe importante",
        content = "sim conetudo super importante",
        date = "06h00",
        isNew = true,
        initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FORUMS)
    ))
    emails.add(
        Email(
            sender = "Cachorrinhos br",
            title = "Salve os cachorros",
            content = "Nos ajude a salvar cachorrinhos de rua",
            date = "7h03",
            isNew = true,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FAVORITE)
        ))
    emails.add(
        Email(
            sender = "Roberto da Silva",
            title = "Reunião importante",
            content = "Acesse o link abaixo",
            date = "7h44",
            isNew = true,
            initialLabel = mutableListOf(EmailLabel.PRIMARY)
        ))
    emails.add(
        Email(
            sender = "Charlie Jr.",
            title = "Oportunidade",
            content = "Venha fazer parte da minha comunidade do dircord",
            date = "10h00",
            isNew = true,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FORUMS)
        ))
    emails.add(
        Email(
            sender = "Fly Try",
            title = "An exclusive card game",
            content = "bala hewibnf aihaka aaaaj ",
            date = "21h00",
            isNew = true,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FORUMS)
        ))
    emails.add(
        Email(
            sender = "Lol br",
            title = "jw kaopn ojdwn pp iajabu aaeef deofbfj",
            content = "whjfkwefhe wefjkfefhe kjfbkweeuj rejbf we feh",
            date = "23h00",
            isNew = false,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FORUMS)
        ))
    emails.add(
        Email(
            sender = "hau mesuv",
            title = "Jvw wj rkhh ldjufbe fhdndjhf hshb",
            content = "sufei ewfeb wh ujdkj hirhw hwewoqçj s",
            date = "ontem",
            isNew = false,
            initialLabel = mutableListOf(EmailLabel.PRIMARY, EmailLabel.FORUMS)
        ))
    return emails
}
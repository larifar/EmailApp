package br.com.fiap.emailapp.services

import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.repository.EmailRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SpamControl(val repository: EmailRepository) {

    fun spamAlert(email: Email) : Boolean{
        return limitEmailSent(email, 100) || isContentSpam(email)
    }

    fun limitEmailSent(email: Email, limit : Int) : Boolean{
        if (email.sender.equals("you")){
            val list = repository.listarEmailsEnviados()
            val count = countEmailsList(list)
            return count > limit
        }
        return false
    }

    fun isContentSpam(email: Email) : Boolean{
        if (email.sender.equals("you")){
            return listSpamWords().any() {
                email.content.contains(it, true) || email.title.contains(it, true)
            }
        }
        return false
    }

    private fun countEmailsList(list: List<Email>) : Int {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        val now = LocalDateTime.now()
        val oneHour = now.minusHours(1)
        return list.filter { email ->
            val emailDate = LocalDateTime.parse(email.date, formatter)
            emailDate.isAfter(oneHour) && emailDate.isBefore(now)
        }.size
    }

    private fun listSpamWords() : List<String>{
        return listOf(
            "GRÁTIS", "Atenção", "Oi", "Imediato", "Escondido",
            "Para você", "PARE", "Agora", "Quente", "Incrível",
            "Satisfação", "Faça agora", "Aplique agora", "Só hoje", "Como visto",
            "Como visto na TV", "Evite", "Seja seu próprio chefe", "Trabalhe de casa",
            "Negócio caseiro", "Compre", "Ligue agora", "Bônus em dinheiro",
            "Dinheiro de graça", "Dobre sua renda", "Ganhe $", "Marketing multinível",
            "Ganhe dinheiro", "Faça dinheiro", "Seja pago semanalmente",
            "Dinheiro de verdade", "Como ganhar dinheiro", "Renda extra",
            "Dinheiro rápido", "Pagamento imediato", "Seu pagamento atrasado",
            "Gestão de fundos", "Acesso gratuito", "Presente gratuito",
            "Informação gratuita", "Informação que você solicitou", "Veja isso",
            "Oferta gratuita", "Liberdade financeira", "Remédios", "Comprimidos",
            "Pílulas", "Rivotril", "Diazepam", "Vicodin", "Viagra", "Levitra",
            "Plantas medicinais", "Aumente", "Clique aqui", "Abra agora",
            "Dê uma olhada", "Leia agora", "Não apague", "Isso não é spam", "Receba",
            "Compare", "Fortaleça", "$$$", "Crédito", "Débito", "Saia das dívidas",
            "Acabe com suas dívidas", "Taxas reduzidas", "Refinanciamento",
            "Menores taxas de seguro", "Seguro de vida", "Empréstimos", "Querido amigo",
            "Perca peso", "Graduação online", "Marketing online",
            "Farmácia online", "Oportunidade", "Garantido", "Sites de busca",
            "Adolescente", "Vencedor", "Você é um vencedor", "Sua família",
            "Seu email ganhou", "Por favor me ajude", "Pedido de parceria", "Compre agora",
            "Oportunidade de trabalho!!!", "Tempo limitado", "Destinatário desconhecido",
            "Aplicações Online", "Ganhe bilhões", "Seu dinheiro de volta",
            "Produto milagroso", "Invista agora", "Oferta exclusiva"
        )

    }

}
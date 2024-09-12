package br.com.fiap.emailapp.services

import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.model.EmailLabel

fun addLabels(email: Email): EmailLabel{
    val normalizedTitle = email.title.lowercase()
    val normalizedContent = email.content.lowercase()

    if (listOf("promoção", "desconto", "oferta", "cupom").any { it in normalizedTitle || it in normalizedContent }) {
        return EmailLabel.PROMOTIONS
    }

    if (listOf("facebook", "instagram", "twitter", "amigo", "amizade", "rede social").any { it in normalizedTitle || it in normalizedContent }) {
        return EmailLabel.SOCIAL
    }

    if (listOf("fórum", "discussão", "pergunta", "resposta", "comentário").any { it in normalizedTitle || it in normalizedContent }) {
        return EmailLabel.FORUMS
    }

    return EmailLabel.PRIMARY
}



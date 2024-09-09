package br.com.fiap.emailapp.database.dto

data class EmailDTO(val id: Long,
                    val sender: String,
                    val receiver: String,
                    val content: String,
                    val date: String)

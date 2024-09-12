package br.com.fiap.emailapp.database.model

class UserPreference(
    val id : Long = 0,
    val user_id : Long,
    val theme : String,
    val fontSize: String
) {
}
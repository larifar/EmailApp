package br.com.fiap.emailapp.services

import br.com.fiap.emailapp.database.dto.EmailDTO
import retrofit2.Call
import retrofit2.http.GET

interface EmailApiService {
    @GET("/getEmails")
    fun getData(): Call<List<EmailDTO>>
}

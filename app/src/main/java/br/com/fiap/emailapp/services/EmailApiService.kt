package br.com.fiap.emailapp.services

import br.com.fiap.emailapp.database.dto.EmailDTO
import br.com.fiap.emailapp.database.model.UserPreference
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EmailApiService {
    @GET("/getEmails")
    fun getData(): Call<List<EmailDTO>>

    @GET("/getPreferences/{id}")
    fun getPreference(@Path("id") id: Long) : Call<UserPreference>

    @POST("/savePreference")
    fun savePreference(@Body userPreference: UserPreference) : Call<UserPreference>

}

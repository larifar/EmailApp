package br.com.fiap.emailapp.retrofit

import br.com.fiap.emailapp.services.EmailApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: EmailApiService by lazy {
        instance.create(EmailApiService::class.java)
    }
}

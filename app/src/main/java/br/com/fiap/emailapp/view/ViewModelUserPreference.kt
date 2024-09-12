package br.com.fiap.emailapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.emailapp.database.model.UserPreference
import br.com.fiap.emailapp.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelUserPreference : ViewModel() {
  private val apiService = RetrofitClient.api

    fun savePreferences(userPreference: UserPreference){
        viewModelScope.launch (Dispatchers.IO){
            try {
                val response = apiService.savePreference(userPreference).execute()
                if (response.isSuccessful){
                    println("sucesso ao salvar preferencias")
                } else{
                    println("Erro no save: ${response.code()} - ${response.errorBody()?.string()}")
                }
            } catch (e : Exception){
                println("caiu no catch no save: " + e.message)
                e.printStackTrace()
            }
        }
    }

    fun getPreferences(userId : Long, onSuccess: (UserPreference?)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getPreference(userId).execute()
                if (response.isSuccessful){
                    println("sucesso ao chamar o get")
                    onSuccess(response.body())
                }else{
                    println("caiu no else do get")
                    onSuccess(null)
                }
            } catch (e: Exception){
                onSuccess(null)
                println("caiu no catch do get: " + e.message)
                e.printStackTrace()
            }
        }
    }
}
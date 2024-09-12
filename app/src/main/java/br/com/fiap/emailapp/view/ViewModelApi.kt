import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.emailapp.database.dto.EmailDTO
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.repository.EmailRepository
import br.com.fiap.emailapp.retrofit.RetrofitClient
import br.com.fiap.emailapp.services.addLabels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ViewModelApi : ViewModel() {

    private val _data = MutableStateFlow<List<EmailDTO>>(emptyList())
    val data: StateFlow<List<EmailDTO>> get() = _data

    init {
        fetchData()
    }
    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.getData().execute()
                if (response.isSuccessful) {
                    _data.value = response.body() ?: emptyList()
                } else {
                    _data.value = emptyList()
                }
            } catch (e: Exception) {
                _data.value = emptyList()
            }
        }
    }
    private fun createEmails(dtos: List<EmailDTO>) : List<Email> {
        val lista = mutableListOf<Email>()
        for (dto in dtos){
            val e = Email(
                sender = dto.sender,
                receiver = dto.receiver,
                title = dto.title,
                content = dto.content,
                date = LocalDateTime.parse(dto.date)
            )
            e.initialLabel.add(addLabels(e))
            lista.add(e)
        }
        return lista
    }

    fun populateDatabase(repository: EmailRepository, dto: List<EmailDTO>) {
        if (repository.count() > 0){
            repository.excluirTodos()
        }
        val emails = createEmails(dto)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAll(emails)
        }
    }
}


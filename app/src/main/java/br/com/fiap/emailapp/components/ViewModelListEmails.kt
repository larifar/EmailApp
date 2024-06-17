package br.com.fiap.emailapp.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.repository.EmailRepository
import kotlinx.coroutines.launch

class EmailListViewModel(private val repository: EmailRepository) : ViewModel() {

    private val _emailList = mutableStateOf(listOf<Email>())
    val emailList: State<List<Email>> = _emailList

    private val _enviados = mutableStateOf(mutableListOf<Email>())
    val enviados: State<List<Email>> = _enviados

    private val _arquivados = mutableStateOf(listOf<Email>())
    val arquivados: State<List<Email>> = _arquivados

    fun buscarEmails() {
        viewModelScope.launch {
            val emails = repository.listarEmails()
            _emailList.value = emails
            _arquivados.value = emails.filter { it.isArchived }
            _enviados.value = emails.filter { it.sender == "you" }.toMutableList()
        }
    }
}
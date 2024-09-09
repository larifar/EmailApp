package br.com.fiap.emailapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.repository.EmailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmailListViewModel(private val repository: EmailRepository) : ViewModel() {

    private val _emailList = MutableStateFlow(listOf<Email>())
    val emailList: StateFlow<List<Email>> = _emailList

    private val _enviados = MutableStateFlow(mutableListOf<Email>())
    val enviados: StateFlow<List<Email>> = _enviados

    private val _arquivados = MutableStateFlow(listOf<Email>())
    val arquivados: StateFlow<List<Email>> = _arquivados

    fun buscarEmails() {
        viewModelScope.launch {
            val emails = repository.listarEmails()
            _emailList.value = emails
            _arquivados.value = emails.filter { it.isArchived }
            _enviados.value = emails.filter { it.sender == "you" }.toMutableList()
        }
    }
}
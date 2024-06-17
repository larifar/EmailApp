package br.com.fiap.emailapp.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.fiap.emailapp.components.EmailComp
import br.com.fiap.emailapp.components.FilterComp
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.model.EmailLabel
import br.com.fiap.emailapp.database.repository.EmailRepository

@Composable
fun HomeScreen(navController: NavHostController, emails: List<Email>, repository: EmailRepository) : List<Email> {
    var emailList by remember { mutableStateOf(emails) }
    var filterLabel by remember { mutableStateOf<EmailLabel?>(null) }

    val filteredEmails = emailList.filter { email ->
        filterLabel == null || email.initialLabel.contains(filterLabel)
    }
    Column (
        modifier= Modifier.fillMaxSize()
    ) {
        FilterComp(filterLabel, onFilterChange = { newLabel ->
            filterLabel = newLabel
        })

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredEmails) {email->
                EmailComp(
                    email = email,
                    onClick = {
                        val updatedEmail = email.copy(isNew = false)
                        emailList = emailList.map {
                            if (it.id == updatedEmail.id) updatedEmail else it
                        }
                        repository.update(updatedEmail)
                        navController.navigate("details/${email.id}")

                              },
                    onToggleFavorite = { updatedEmail ->
                        emailList = emailList.map {
                            if (it.id == updatedEmail.id) updatedEmail else it
                        }
                    },
                    repository = repository
                )
            }
        }
    }

    return emailList
}
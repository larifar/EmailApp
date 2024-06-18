package br.com.fiap.emailapp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.fiap.emailapp.components.EmailButton
import br.com.fiap.emailapp.components.EmailComp
import br.com.fiap.emailapp.components.EmailListViewModel
import br.com.fiap.emailapp.components.FilterComp
import br.com.fiap.emailapp.components.SearchBar
import br.com.fiap.emailapp.components.SearchButton
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.model.EmailLabel
import br.com.fiap.emailapp.database.repository.EmailRepository

@Composable
fun HomeScreen(navController: NavHostController, viewModel: EmailListViewModel, repository: EmailRepository): List<Email> {
    viewModel.buscarEmails()
    var emailList by remember { mutableStateOf(viewModel.emailList.value) }
    println(emailList)
    var filterLabel by remember { mutableStateOf<EmailLabel?>(EmailLabel.PRIMARY) }
    val context = LocalContext.current

    var search by remember { mutableStateOf("") }

    var showSearchBar by remember { mutableStateOf(false) }

    val filteredEmails = emailList.filter { email ->
        filterLabel == null || email.initialLabel.contains(filterLabel)
    }.filterNot { email -> email.sender == "you" }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        FilterComp(filterLabel, onFilterChange = { newLabel ->
            filterLabel = newLabel
        })

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredEmails) { email ->
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
                        repository.update(updatedEmail)
                    },
                    repository = repository
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            EmailButton(context)
            Spacer(modifier = Modifier.height(8.dp))
            if (showSearchBar) {
                SearchBar(
                    onClose = { showSearchBar = false },
                    value = search,
                    onValueChange = { search = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
            } else{
                SearchButton(onClick = { showSearchBar = true })
            }
        }
    }

    return emailList
}
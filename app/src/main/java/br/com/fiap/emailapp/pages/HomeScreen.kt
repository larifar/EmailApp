package br.com.fiap.emailapp.pages

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
import br.com.fiap.emailapp.database.model.Email

@Composable
fun HomeScreen(navController: NavHostController, emails: List<Email>) {
    var emailList by remember { mutableStateOf(emails) }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(emailList) {email->
            EmailComp(email = email, onToggleFavorite = { updatedEmail ->
                emailList = emailList.map {
                    if (it.id == updatedEmail.id) updatedEmail else it
                }
            })
        }
    }
}
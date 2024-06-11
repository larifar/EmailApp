package br.com.fiap.emailapp.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.fiap.emailapp.components.Email

@Composable
fun HomeScreen(navController: NavHostController, emails: MutableList<Email>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(emails.size){

        }
    }
}
package br.com.fiap.emailapp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.fiap.emailapp.database.model.Email

@Composable
fun EmailDetail(email: Email, nav : NavController): Email {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = email.sender, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = email.title, fontSize = 18.sp)
        Text(text = email.content, fontSize = 16.sp)
        Text(text = email.date, fontSize = 14.sp)
        Button(onClick = {
            nav.popBackStack()
        }) {

        }
    }
    return email
}
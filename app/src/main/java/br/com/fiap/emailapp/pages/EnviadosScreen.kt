package br.com.fiap.emailapp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.fiap.emailapp.components.EmailComp
import br.com.fiap.emailapp.components.EmailListViewModel
import br.com.fiap.emailapp.database.repository.EmailRepository

@Composable
fun EnviadosScreen(
    viewModel: EmailListViewModel,
    repository: EmailRepository,
    navController: NavHostController
) {
    viewModel.buscarEmails()
    val emailsEnviados = viewModel.enviados.value

    if (emailsEnviados.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nenhum email enviado.",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }

    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn() {
                items(emailsEnviados) { email ->
                    EmailComp(
                        email = email,
                        onToggleFavorite = {},
                        onToggleChecked = {email, b ->  },
                        multipleSelection = false,
                        repository,
                        onClick = { navController.navigate("details/${email.id}") }
                    )
                }
            }
        }

    }
}
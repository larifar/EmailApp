package br.com.fiap.emailapp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.emailapp.components.IconButtonWithDropdownMenu
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.repository.EmailRepository
import br.com.fiap.emailapp.util.isFavorite
import br.com.fiap.emailapp.util.toggleFavorite

@Composable
fun EmailDetail(email: Email, nav: NavController, repo: EmailRepository): Email {
    var label by remember { mutableStateOf(email.initialLabel) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButtonWithDropdownMenu(email, {it.isArchived = true})
            Column{
                Text(
                    text = email.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = email.sender, fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp)
            }


            IconButton(
                onClick = {
                    label = toggleFavorite(label)
                    val updatedEmail = email.copy(initialLabel = label)
                    repo.update(updatedEmail)
                }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = isFavorite(labels = label),
                    contentDescription = "Favorite Icon",
                    tint = Color.Yellow,
                )
            }

        }
        Spacer(modifier = Modifier.padding(10.dp))
        Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = email.content, fontSize = 16.sp)
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = email.date, fontSize = 14.sp)
                Button(onClick = {
                    nav.popBackStack()
                }) {
                    Text(text = "voltar")
                }
            }

        }

    }
    return email
}
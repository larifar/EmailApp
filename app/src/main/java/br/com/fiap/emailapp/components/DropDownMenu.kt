package br.com.fiap.emailapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import br.com.fiap.emailapp.R
import br.com.fiap.emailapp.database.model.Email

@Composable
fun IconButtonWithDropdownMenu(email: Email, onArchive: (Email)-> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.dots_icon),
                contentDescription = "vetor de imagem de 3 pontos"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Arquivar") },
                onClick = {
                    onArchive(email)
                    expanded = false
                }
            )

        }
    }
}
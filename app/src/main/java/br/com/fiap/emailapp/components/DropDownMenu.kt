package br.com.fiap.emailapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import br.com.fiap.emailapp.R
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.model.EmailLabel
import br.com.fiap.emailapp.util.toPortuguese

@Composable
fun IconButtonWithDropdownMenu(email: Email, onArchive: (Email) -> Unit, onUpdate: (Email) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

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
                    val ini =
                        email.initialLabel.filterNot { it == EmailLabel.PRIMARY }.toMutableList()
                    val updatedEmail = email.copy(isArchived = true, initialLabel = ini)
                    onArchive(updatedEmail)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = "Editar Rótulos") },
                onClick = {
                    showDialog = true
                    expanded = false
                }
            )
        }
    }
    if (showDialog) {
        changeLabelsMenu(email = email, onDismiss = { showDialog = false }, onUpdate)
    }
}

@Composable
fun changeLabelsMenu(email: Email, onDismiss: () -> Unit, onUpdate: (Email) -> Unit) {
    val availableLabels = EmailLabel.entries.filter { it != EmailLabel.FAVORITE }
    val selectedLabels by remember {
        mutableStateOf(email.initialLabel)
    }
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = "Editar Rótulos") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                availableLabels.forEach { label ->
                    val isChecked = remember { mutableStateOf(selectedLabels.contains(label)) }
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = {
                                isChecked.value = it
                                if (it) {
                                    selectedLabels.add(label)
                                } else {
                                    selectedLabels.remove(label)
                                }
                            }
                        )
                        Text(text = label.toPortuguese().uppercase())
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val updatedEmail = email.copy(initialLabel = selectedLabels)
                onUpdate(updatedEmail)
                onDismiss()
            }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )

}

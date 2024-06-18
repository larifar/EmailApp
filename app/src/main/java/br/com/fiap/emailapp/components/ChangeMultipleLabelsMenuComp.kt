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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.emailapp.database.model.EmailLabel
import br.com.fiap.emailapp.util.toPortuguese

@Composable
fun MultipleLabelsMenu(onDismiss: () -> Unit, onUpdate: (List<EmailLabel>) -> Unit) {
    val availableLabels = EmailLabel.entries
    val selectedLabels by remember {
        mutableStateOf(mutableListOf<EmailLabel>())
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
                onUpdate(selectedLabels)
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
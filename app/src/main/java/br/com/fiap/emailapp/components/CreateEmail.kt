package br.com.fiap.emailapp.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import br.com.fiap.emailapp.R
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.repository.EmailRepository


@Composable
fun EmailButton(context: Context) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        EmailDialog(onDismiss = { showDialog = false }, context)
    }

    Box (modifier = Modifier.fillMaxSize()){
        IconButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
                .size(60.dp)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.email_icon),
                contentDescription = "carta",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun EmailDialog(onDismiss: () -> Unit, context : Context) {
    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    val emailRepository = EmailRepository(context)

    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        title = { Text(text = "Compose Email") },
        text = {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ){
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("To") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = body,
                    onValueChange = { body = it },
                    label = { Text("Body") },
                    modifier = Modifier.fillMaxWidth().height(150.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newEmail = Email(
                        sender = "you",
                        receiver= email,
                        title = subject,
                        content = body
                    )
                    emailRepository.salvar(newEmail)
                    // Handle email sending logic here
                    onDismiss()
                }
            ) {
                Text("Send")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


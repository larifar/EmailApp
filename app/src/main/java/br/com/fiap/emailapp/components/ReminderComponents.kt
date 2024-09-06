package br.com.fiap.emailapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.emailapp.R
import br.com.fiap.emailapp.database.model.Reminder
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ReminderDialog(selectedDay: LocalDate, onDismiss: ()->Unit, onSaveReminder: (title:String, time: LocalTime)->Unit ){
    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Criar lembrete para ${selectedDay.dayOfMonth}/${selectedDay.monthValue}/${selectedDay.year}")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título do lembrete") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Horário (HH:MM)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    placeholder = { Text("Ex: 12:30") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSaveReminder(title, LocalTime.parse(time)) }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun ReminderItem(reminder: Reminder, onDelete: ()->Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth().clip(RoundedCornerShape(8.dp))
            .background(color = Color.Yellow.copy(alpha = 0.3f)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(modifier = Modifier
            .fillMaxWidth(1/2f)
            .padding(5.dp)
        ) {
            Text(text = "Título: ${reminder.title}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                text = "Data: ${reminder.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}",
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "Horário: ${reminder.time}", fontWeight = FontWeight.SemiBold)
        }
        Image(
            painter = painterResource(id = R.drawable.delete_icon),
            contentDescription = "delete reminder",
            modifier = Modifier.size(30.dp).clickable(onClick = onDelete),
            colorFilter = ColorFilter.tint(Color.Red.copy(0.4f))
        )
    }
    Divider(modifier = Modifier.padding(vertical = 8.dp))

}
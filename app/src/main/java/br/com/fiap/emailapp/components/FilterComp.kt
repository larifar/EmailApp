package br.com.fiap.emailapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.emailapp.database.model.EmailLabel

@Composable
fun FilterComp(currentFilter: EmailLabel?, onFilterChange: (EmailLabel?) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = { onFilterChange(EmailLabel.PRIMARY) }) {
            Text(text = "Principal")
        }
        Button(onClick = { onFilterChange(EmailLabel.SOCIAL) }) {
            Text(text = "Social")
        }
        Button(onClick = { onFilterChange(EmailLabel.PROMOTIONS) }) {
            Text(text = "Promoções")
        }
        Button(onClick = { onFilterChange(null) }) {
            Text(text = "Todos")
        }
    }
}


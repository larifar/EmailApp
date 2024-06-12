package br.com.fiap.emailapp.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.emailapp.database.model.EmailLabel

@Composable
fun FilterComp(currentFilter: EmailLabel?, onFilterChange: (EmailLabel?) -> Unit) {
    val labels = listOf(
        EmailLabel.PRIMARY to "Principal",
        EmailLabel.SOCIAL to "Social",
        EmailLabel.PROMOTIONS to "Promoções",
        EmailLabel.FORUMS to "Fóruns",
        EmailLabel.FAVORITE to "Favoritos"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .height(50.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        labels.forEach { (label, labelName) ->
            Button(
                onClick = { onFilterChange(label) },
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Text(text = labelName)
            }
        }
        Button(onClick = { onFilterChange(null) }) {
            Text(text = "Todos")
        }
    }
}


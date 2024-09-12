package br.com.fiap.emailapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.util.changeIsReadColor
import br.com.fiap.emailapp.util.formatDate
import br.com.fiap.emailapp.util.isFavorite
import br.com.fiap.emailapp.util.isRead

@Composable
fun EmailComp(
    email: Email,
    onToggleFavorite: (Email) -> Unit,
    onToggleChecked: (Email, Boolean) -> Unit,
    multipleSelection: Boolean,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    var isChecked by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            if (multipleSelection){
                Checkbox(checked = isChecked, onCheckedChange = { checked ->
                    isChecked = checked
                    onToggleChecked(email, checked)
                })
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = email.sender,
                            fontWeight = isRead(email.isNew),
                            color= changeIsReadColor(email.isNew, colorScheme),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        IconButton(
                            onClick = {
                                onToggleFavorite(email)
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = isFavorite(email.isFavorite),
                                contentDescription = "Favorite Icon",
                                tint = Color.Yellow,
                            )
                        }
                    }
                    Text(
                        text = formatDate(email.date),
                        color = changeIsReadColor(email.isNew, colorScheme),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = email.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = isRead(email.isNew),
                    color= changeIsReadColor(email.isNew, colorScheme),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = email.content,
                    color= changeIsReadColor(email.isNew, colorScheme),
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }


    }
}

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
import androidx.compose.ui.unit.sp
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.repository.EmailRepository
import br.com.fiap.emailapp.util.changeisReadedColor
import br.com.fiap.emailapp.util.formatDate
import br.com.fiap.emailapp.util.isFavorite
import br.com.fiap.emailapp.util.isReaded
import br.com.fiap.emailapp.util.toggleFavorite

@Composable
fun EmailComp(
    email: Email,
    onToggleFavorite: (Email) -> Unit,
    onToggleChecked: (Email, Boolean) -> Unit,
    multipleSelection: Boolean,
    repository: EmailRepository,
    onClick: () -> Unit
) {
    var label by remember { mutableStateOf(email.initialLabel) }
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
                            text = email.sender,
                            fontWeight = isReaded(email.isNew),
                            color= changeisReadedColor(email.isNew),
                            fontSize = 17.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        IconButton(
                            onClick = {
                                label = toggleFavorite(label)
                                val updatedEmail = email.copy(initialLabel = label)
                                onToggleFavorite(updatedEmail)
                                repository.update(updatedEmail)
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = isFavorite(labels = label),
                                contentDescription = "Favorite Icon",
                                tint = Color.Yellow,
                            )
                        }
                    }
                    Text(text = formatDate(email.date), color= changeisReadedColor(email.isNew), fontSize = 15.sp)
                }
                Text(
                    text = email.title,
                    fontWeight = isReaded(email.isNew),
                    color= changeisReadedColor(email.isNew),
                    fontSize = 17.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = email.content,
                    color= changeisReadedColor(email.isNew),
                    maxLines = 1,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }


    }
}

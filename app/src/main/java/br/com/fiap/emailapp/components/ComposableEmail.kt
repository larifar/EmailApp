package br.com.fiap.emailapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.emailapp.util.isFavorite
import br.com.fiap.emailapp.util.isReaded
import br.com.fiap.emailapp.util.toggleFavorite

@Composable
fun EmailComp(
    sender: String,
    title: String,
    content: String,
    date: String,
    isNew: Boolean,
    initialLabel: MutableList<EmailLabel>
) {
    var label by remember { mutableStateOf(initialLabel) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ){
        Column (modifier = Modifier.fillMaxWidth()){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sender,
                        fontWeight = isReaded(isNew),
                        fontSize = 17.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(
                        onClick = {
                            label = toggleFavorite(label)
                        },
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = isFavorite(labels = label),
                            contentDescription = "outline star",
                            tint = Color.Yellow,
                        )
                    }
                }
                Text(text = date, fontSize = 15.sp)
            }
            Text(text = title, fontWeight = isReaded(isNew), fontSize = (17.5).sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = content, maxLines = 1, fontSize = 16.sp, overflow = TextOverflow.Ellipsis)
            
        }
    }
}
enum class EmailLabel {
    PRIMARY,
    SOCIAL,
    PROMOTIONS,
    UPDATES,
    FORUMS,
    FAVORITE

}

@Preview
@Composable
fun PreviewEmail() {
    EmailComp(
        sender = "Larissa faria",
        title = "Não perca essa oportunidade incrivel",
        content = "Olha aqui essa promoção incrivel sobre coisas que você não tem interesse",
        date = "06h23",
        isNew = true,
        initialLabel = mutableListOf( EmailLabel.PRIMARY)
    )
}
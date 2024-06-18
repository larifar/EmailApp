package br.com.fiap.emailapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import br.com.fiap.emailapp.R

@Composable
fun MultipleSelection(onReturn: ()-> Unit, onArchive: ()-> Unit, onEditLabels: ()-> Unit) {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        IconButton(
            onClick = onReturn,
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary, CircleShape).clip(
                CircleShape
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.return_icon),
                contentDescription = "voltar",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
        IconButton(
            onClick = onArchive,
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary, CircleShape).clip(
                CircleShape
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.archive_icon),
                contentDescription = "arquivar",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
        IconButton(
            onClick = onEditLabels,
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary, CircleShape).clip(
                CircleShape
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.label_edit_icon),
                contentDescription = "editar rótulos",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
}
package br.com.fiap.emailapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import br.com.fiap.emailapp.R

@Composable
fun SearchButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(10.dp)
            .size(60.dp)
            .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
            .clip(CircleShape)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.search_icon),
            contentDescription = "carta",
            modifier = Modifier.size(35.dp),
            tint = Color.White
        )
    }
}

@Composable
fun SearchBar(onClose: () -> Unit, value: String, onValueChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(30.dp))
    ) {
        IconButton(onClick = onClose) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("Search...") },
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent, shape = RoundedCornerShape(30.dp)),
            shape = RoundedCornerShape(10)
        )
        SearchButton {

        }
    }
}


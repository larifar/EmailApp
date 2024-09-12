package br.com.fiap.emailapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState, scope: CoroutineScope, font: TextUnit,onSizeChange: (TextUnit) -> Unit) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route
    Column(
        modifier = Modifier
            .fillMaxSize(0.5f)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text("Email App", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
        DrawerItem(
            "Emails",
            navController,
            "home",
            drawerState,
            scope,
            "home" === currentDestination
        )
        DrawerItem(
            "Calendário",
            navController,
            "calendar",
            drawerState,
            scope,
            "calendar" === currentDestination
        )
        DrawerItem(
            "Enviados",
            navController,
            "enviados",
            drawerState,
            scope,
            "enviados" === currentDestination
        )
        DrawerItem(
            "Arquivados",
            navController,
            "arquivados",
            drawerState,
            scope,
            "arquivados" === currentDestination
        )
        Spacer(Modifier.height(5.dp))
        FontSizePicker(font,onSizeChange)
    }

}

@Composable
fun DrawerItem(
    text: String,
    navController: NavHostController,
    destination: String,
    drawerState: DrawerState,
    scope: CoroutineScope,
    isActive: Boolean
) {
    val backgroundColor = if (isActive) MaterialTheme.colorScheme.primary else Color.Transparent
    Text(
        color = MaterialTheme.colorScheme.onPrimary,
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(backgroundColor)
            .clickable {
                navController.navigate(destination) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                scope.launch {
                    drawerState.close()
                }
            }
    )
}

@Composable
fun FontSizePicker(font: TextUnit,onSizeChange: (TextUnit) -> Unit) {
    var fontSize by remember { mutableStateOf(font) }

    Column {
        Text(text = "Fonte: ${fontSize.value.toInt()}sp", fontSize = fontSize)

        Slider(
            value = fontSize.value,
            onValueChange = { newSize ->
                fontSize = newSize.sp
                onSizeChange(newSize.sp)
            },
            valueRange = 12f..30f, // Define o intervalo de tamanhos de fonte
            steps = 10
        )
    }
}

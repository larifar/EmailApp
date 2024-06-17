package br.com.fiap.emailapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.fiap.emailapp.components.Calendar
import br.com.fiap.emailapp.components.EmailListViewModel
import br.com.fiap.emailapp.database.dao.EmailDatabase
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.repository.EmailRepository
import br.com.fiap.emailapp.pages.EmailDetail
import br.com.fiap.emailapp.pages.EnviadosScreen
import br.com.fiap.emailapp.pages.HomeScreen
import br.com.fiap.emailapp.services.SetEmails
import br.com.fiap.emailapp.ui.theme.EmailAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = EmailDatabase.getDatabase(this)
        setContent {
            EmailAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(database)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(database: EmailDatabase) {
    val context = LocalContext.current
    val repository = EmailRepository(context)
    SetEmails(repository)
    val emailListViewModel = EmailListViewModel(repository)
    emailListViewModel.buscarEmails()

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
                DrawerContent(navController, drawerState, scope)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Email App") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Icon")
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    HomeScreen(navController, emailListViewModel, repository)

                }
                composable("details/{emailId}") {
                    val emailId = it.arguments?.getString("emailId")?.toLongOrNull()
                    val email = emailListViewModel.emailList.value.find{ it.id == emailId }
                    if (email != null) {
                        EmailDetail(email, navController, repository)
                    }

                }
                composable("calendar") {
                    Calendar()
                }
                composable("enviados") {
                    EnviadosScreen(emailListViewModel, repository, navController)
                }
            }
        }
    }
}
@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState, scope: CoroutineScope) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route
    Column(
        modifier = Modifier
            .fillMaxSize(0.5f)
            .background(Color.White)
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
        color = if (isActive) Color.White else Color.Black,
        text = text,
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


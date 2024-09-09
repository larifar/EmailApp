package br.com.fiap.emailapp

import ViewModelApi
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.emailapp.components.DrawerContent
import br.com.fiap.emailapp.database.dao.EmailDatabase
import br.com.fiap.emailapp.database.dto.EmailDTO
import br.com.fiap.emailapp.database.repository.EmailRepository
import br.com.fiap.emailapp.database.repository.ReminderRepository
import br.com.fiap.emailapp.pages.ArquivadosScreen
import br.com.fiap.emailapp.pages.CalendarScreen
import br.com.fiap.emailapp.pages.EmailDetail
import br.com.fiap.emailapp.pages.EnviadosScreen
import br.com.fiap.emailapp.pages.HomeScreen
import br.com.fiap.emailapp.services.populateDatabase
import br.com.fiap.emailapp.ui.theme.EmailAppTheme
import br.com.fiap.emailapp.view.EmailListViewModel
import br.com.fiap.emailapp.view.ReminderViewModel
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
    var isDarkTheme by remember { mutableStateOf(false) }
    EmailAppTheme(isDarkTheme){
        val viewModelApi: ViewModelApi = viewModel()
        var emails by remember { mutableStateOf(emptyList<EmailDTO>()) }

        val context = LocalContext.current
        val repository = EmailRepository(context)
        populateDatabase(repository)
        val reminderRepository = ReminderRepository(context)
        val reminderViewModel = ReminderViewModel(reminderRepository)
        val emailListViewModel = EmailListViewModel(repository)
        emailListViewModel.buscarEmails()

        val navController = rememberNavController()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        DisposableEffect(Unit) {
            val observer = Observer<List<EmailDTO>?> { newEmails ->
                emails = newEmails ?: emptyList()
            }

            viewModelApi.data.observeForever(observer)

            onDispose {
                viewModelApi.data.removeObserver(observer)
            }
        }
        println(emails.toString())

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
                        },
                        actions = {
                            Switch(checked = isDarkTheme, onCheckedChange = {isDarkTheme=it})
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
                        CalendarScreen(reminderViewModel)
                    }
                    composable("enviados") {
                        EnviadosScreen(emailListViewModel, repository, navController)
                    }
                    composable("arquivados"){
                        ArquivadosScreen(emailListViewModel, repository, navController)
                    }
                }
            }
        }
    }
}
package br.com.fiap.emailapp

import ViewModelApi
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.emailapp.components.DrawerContent
import br.com.fiap.emailapp.database.dao.EmailDatabase
import br.com.fiap.emailapp.database.model.UserPreference
import br.com.fiap.emailapp.database.repository.EmailRepository
import br.com.fiap.emailapp.database.repository.ReminderRepository
import br.com.fiap.emailapp.pages.ArquivadosScreen
import br.com.fiap.emailapp.pages.CalendarScreen
import br.com.fiap.emailapp.pages.EmailDetail
import br.com.fiap.emailapp.pages.EnviadosScreen
import br.com.fiap.emailapp.pages.HomeScreen
import br.com.fiap.emailapp.ui.theme.EmailAppTheme
import br.com.fiap.emailapp.view.EmailListViewModel
import br.com.fiap.emailapp.view.ReminderViewModel
import br.com.fiap.emailapp.view.ViewModelUserPreference
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var preferencesViewModel: ViewModelUserPreference
    private var isDarkTheme by mutableStateOf(false)
    private var fontSize by mutableStateOf(16.sp)
    private var isLoading by mutableStateOf(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesViewModel = ViewModelProvider(this)[ViewModelUserPreference::class.java]

        preferencesViewModel.getPreferences( 1L){
            if (it != null){
                isDarkTheme = it.theme == "dark"
                fontSize = it.fontSize.toFloat().sp
            } else{
                preferencesViewModel.savePreferences( UserPreference(
                     user_id = 1L, theme = "light", fontSize = "16"
                ))
            }
            isLoading = false
        }

        val database = EmailDatabase.getDatabase(this)
        setContent {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            } else{
                EmailAppTheme(darkTheme = isDarkTheme, selectedFontSize = fontSize) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MyApp(
                            database = database,
                            toggleTheme = { isDarkTheme = !isDarkTheme },
                            onFontSizeChange = { newSize ->
                                fontSize = newSize
                            },
                            font = fontSize
                        )
                    }
                }
            }
        }
    }
    override fun onStop() {
        super.onStop()

        val themeString = if (isDarkTheme) "dark" else "light"
        val userPreferences = UserPreference(
            user_id = 1L,
            theme = themeString,
            fontSize = fontSize.value.toString()
        )
        preferencesViewModel.savePreferences(userPreferences)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(database: EmailDatabase, toggleTheme: () -> Unit, onFontSizeChange: (TextUnit) -> Unit, font: TextUnit) {
    val viewModelApi: ViewModelApi = viewModel()
    val emails by viewModelApi.data.collectAsState()

    val context = LocalContext.current
    val repository = EmailRepository(context)
    val reminderRepository = ReminderRepository(context)
    val reminderViewModel = ReminderViewModel(reminderRepository)
    val emailListViewModel = EmailListViewModel(repository)
    emailListViewModel.buscarEmails()

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(emails) {
        viewModelApi.populateDatabase(repository, emails)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, drawerState, scope, font, onFontSizeChange)
        }
    ) {
        Scaffold(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary),
            topBar = {
                TopAppBar(
                    title = { Text("Email App")},
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Icon")
                        }
                    },
                    actions = {
                        Switch(checked = isSystemInDarkTheme(), onCheckedChange = { toggleTheme() })
                    }
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.weight(2 / 3f)
                ) {
                    composable("home") {
                        HomeScreen(navController, emailListViewModel, repository)

                    }
                    composable("details/{emailId}") {
                        val emailId = it.arguments?.getString("emailId")?.toLongOrNull()
                        val email = emailListViewModel.emailList.value.find { it.id == emailId }
                        if (email != null) {
                            EmailDetail(email, navController, repository)
                        }

                    }
                    composable("calendar") {
                        CalendarScreen(reminderViewModel)
                    }
                    composable("enviados") {
                        EnviadosScreen(emailListViewModel, navController)
                    }
                    composable("arquivados") {
                        ArquivadosScreen(emailListViewModel, repository, navController)
                    }
                }
            }
        }
    }

}
package br.com.fiap.emailapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.fiap.emailapp.R
import br.com.fiap.emailapp.components.EmailButton
import br.com.fiap.emailapp.components.EmailComp
import br.com.fiap.emailapp.components.EmailListViewModel
import br.com.fiap.emailapp.components.FilterComp
import br.com.fiap.emailapp.components.SearchBar
import br.com.fiap.emailapp.components.SearchButton
import br.com.fiap.emailapp.database.model.Email
import br.com.fiap.emailapp.database.model.EmailLabel
import br.com.fiap.emailapp.database.repository.EmailRepository
import br.com.fiap.emailapp.util.performSearch

@Composable
fun HomeScreen(navController: NavHostController, viewModel: EmailListViewModel, repository: EmailRepository): List<Email> {
    viewModel.buscarEmails()
    var emailList by remember { mutableStateOf(viewModel.emailList.value) }
    var filterLabel by remember { mutableStateOf<EmailLabel?>(EmailLabel.PRIMARY) }
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }
    var multipleSelection by remember { mutableStateOf(false) }

    var filteredEmails by remember { mutableStateOf(emailList) }

    LaunchedEffect(searchText, filterLabel, emailList) {
        filteredEmails = performSearch( listEmails =
            emailList.filter { email ->
                filterLabel == null || email.initialLabel.contains(filterLabel)
            }.filterNot { email -> email.sender == "you" },
            query = searchText
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            IconButton(
                onClick = { multipleSelection = !multipleSelection },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.checklist_icon),
                    contentDescription = "multiple selection",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            FilterComp(filterLabel, onFilterChange = { newLabel ->
                filterLabel = newLabel
            })
        }

        if (multipleSelection){
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                IconButton(
                    onClick = { multipleSelection = !multipleSelection },
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary, CircleShape).clip(
                        CircleShape)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.return_icon),
                        contentDescription = "voltar",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(
                    onClick = {  },
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary, CircleShape).clip(
                        CircleShape)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.archive_icon),
                        contentDescription = "arquivar",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(
                    onClick = {  },
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary, CircleShape).clip(
                        CircleShape)
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


        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredEmails) { email ->
                EmailComp(
                    multipleSelection = multipleSelection,
                    email = email,
                    onClick = {
                        val updatedEmail = email.copy(isNew = false)
                        emailList = emailList.map {
                            if (it.id == updatedEmail.id) updatedEmail else it
                        }
                        repository.update(updatedEmail)
                        navController.navigate("details/${email.id}")
                    },
                    onToggleFavorite = { updatedEmail ->
                        emailList = emailList.map {
                            if (it.id == updatedEmail.id) updatedEmail else it
                        }
                        repository.update(updatedEmail)
                    },
                    onToggleChecked = { updatedEmail, isChecked ->
                        // Lógica para quando a checkbox é alterada
                        // Você pode armazenar ou processar o estado do email conforme necessário
                    },
                    repository = repository
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            EmailButton(context)
            Spacer(modifier = Modifier.height(8.dp))
            if (showSearchBar) {
                SearchBar(
                    onClose = {
                        showSearchBar = false
                        searchText = "" },
                    value = searchText,
                    onValueChange = { searchText = it },
                    onSearch = {filteredEmails = performSearch(
                        listEmails = viewModel.emailList.value,
                        query = searchText
                    )}
                )
                Spacer(modifier = Modifier.height(8.dp))
            } else{
                SearchButton(onClick = { showSearchBar = true })
            }
        }
    }
    return emailList
}
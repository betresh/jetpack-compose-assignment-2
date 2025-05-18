package com.example.todolistapp.presentation.todo_detail
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolistapp.presentation.todo_detail.TodoDetailViewModel

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    viewModel: TodoDetailViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    uiState.error != null -> {
                        Text(uiState.error ?: "", modifier = Modifier.align(Alignment.Center))
                    }
                    uiState.todo != null -> {
                        uiState.todo?.let { todo ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text("Title:", style = MaterialTheme.typography.titleSmall)
                                Text(todo.title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))

                                Text("User ID:", style = MaterialTheme.typography.titleSmall)
                                Text(todo.userId.toString(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 16.dp))

                                Text("Status:", style = MaterialTheme.typography.titleSmall)
                                Text(
                                    if (todo.isCompleted) "Completed" else "Not Completed",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                    }
                }
            }
        }
    )
}

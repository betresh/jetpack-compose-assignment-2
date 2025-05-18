package com.example.todolistapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.todolistapp.presentation.todo_detail.TodoDetailScreen
import com.example.todolistapp.presentation.todo_detail.TodoDetailViewModel

import com.example.todolistapp.presentation.todo_list.TodoListScreen
import com.example.todolistapp.presentation.todo_list.TodoListViewModel


@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    todoListViewModel: TodoListViewModel,
    todoDetailViewModelFactory: (Int) -> TodoDetailViewModel
) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            TodoListScreen(viewModel = todoListViewModel, navController = navController)
        }
        composable("detail/{todoId}") { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId")?.toIntOrNull()
            if (todoId != null) {
                val detailViewModel = todoDetailViewModelFactory(todoId)
                TodoDetailScreen(viewModel = detailViewModel, navController = navController)
            }
        }
    }
}

package com.example.todolistapp

import android.R.style.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.todolistapp.data.repository.TodoRepository
import com.example.todolistapp.navigation.NavGraph
import com.yourpackage.todoapp.data.local.TodoDatabase

import com.example.todolistapp.data.remote.RetrofitInstance


import com.example.todolistapp.di.TodoDetailViewModelFactory

import com.example.todolistapp.presentation.todo_detail.TodoDetailViewModel
import com.example.todolistapp.presentation.todo_list.TodoListViewModel

import com.example.todolistapp.ui.theme.TodolistappTheme
import kotlin.jvm.java


class MainActivity : ComponentActivity() {

    private lateinit var todoListViewModel: TodoListViewModel
    private lateinit var todoRepository: TodoRepository
    private lateinit var db: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup DB and Repository
        db = Room.databaseBuilder(applicationContext, TodoDatabase::class.java, "todo_db").build()
        todoRepository = TodoRepository(RetrofitInstance.api, db.todoDao())

        // Create List ViewModel
        todoListViewModel = TodoListViewModel(todoRepository)

        setContent {
            TodolistappTheme {
                NavGraph(
                    todoListViewModel = todoListViewModel,
                    todoDetailViewModelFactory = { todoId ->
                        TodoDetailViewModelFactory(todoRepository, todoId).create(TodoDetailViewModel::class.java)
                    }
                )
            }
        }
    }
}

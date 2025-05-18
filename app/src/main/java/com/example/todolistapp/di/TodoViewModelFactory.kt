package com.example.todolistapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.data.repository.TodoRepository

import com.example.todolistapp.presentation.todo_detail.TodoDetailViewModel
import kotlin.jvm.java

class TodoDetailViewModelFactory(
    private val repository: TodoRepository,
    private val todoId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoDetailViewModel(repository, todoId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

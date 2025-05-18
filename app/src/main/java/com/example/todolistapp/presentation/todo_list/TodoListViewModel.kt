package com.example.todolistapp.presentation.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.model.Todo
import com.example.todolistapp.data.repository.TodoRepository


import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class TodoListUiState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class TodoListViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListUiState(isLoading = true))
    val uiState: StateFlow<TodoListUiState> = _uiState.asStateFlow()

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            repository.todos.collect { cachedTodos ->
                if (cachedTodos.isNotEmpty()) {
                    _uiState.value = TodoListUiState(todos = cachedTodos, isLoading = false)
                }
            }
        }
        refreshFromNetwork()
    }

    private fun refreshFromNetwork() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                repository.refreshTodos()
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load todos. Showing cached data if available."
                )
            }
        }
    }

    fun retry() {
        refreshFromNetwork()
    }
}

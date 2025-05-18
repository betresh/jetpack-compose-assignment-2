package com.example.todolistapp.presentation.todo_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.repository.TodoRepository
import com.example.todolistapp.data.model.Todo


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TodoDetailUiState(
    val todo: Todo? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class TodoDetailViewModel(
    private val repository: TodoRepository,
    private val todoId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoDetailUiState())
    val uiState: StateFlow<TodoDetailUiState> = _uiState

    init {
        loadTodo()
    }

    private fun loadTodo() {
        viewModelScope.launch {
            _uiState.value = TodoDetailUiState(isLoading = true)
            try {
                val todo = repository.getTodoById(todoId)
                if (todo != null) {
                    _uiState.value = TodoDetailUiState(todo = todo, isLoading = false)
                } else {
                    _uiState.value = TodoDetailUiState(error = "Todo not found", isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = TodoDetailUiState(error = e.message, isLoading = false)
            }
        }
    }
}

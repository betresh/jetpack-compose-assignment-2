package com.example.todolistapp.data.model


data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val isCompleted: Boolean
)

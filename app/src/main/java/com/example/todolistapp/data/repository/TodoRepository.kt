package com.example.todolistapp.data.repository

import com.example.todolistapp.data.local.dao.TodoDao

import com.example.todolistapp.data.local.entity.TodoEntity
import com.example.todolistapp.data.model.Todo

import com.example.todolistapp.data.remote.TodoApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class TodoRepository(
     val apiService: TodoApiService,
     val todoDao: TodoDao
) {

    val todos: Flow<List<Todo>> = todoDao.getTodos().map { entities ->
        entities.map { entity ->
            Todo(entity.userId, entity.id, entity.title, entity.completed)
        }
    }


    suspend fun refreshTodos() {
        val remoteTodos = apiService.getTodos()
        val entities = remoteTodos.map { todo ->
            TodoEntity(todo.userId, todo.id, todo.title, todo.isCompleted)
        }
        todoDao.insertTodos(entities)
    }

    suspend fun getTodoById(id: Int): Todo? {
        val entity = todoDao.getTodoById(id)
        return entity?.let { Todo(it.userId, it.id, it.title, it.completed) }
    }
}

package com.example.todolistapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.todolistapp.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow



@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getTodos(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodos(todo: List<TodoEntity>)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    // ✅ Required for detail screen:
    @Query("SELECT * FROM todos WHERE id = :todoId")
    suspend fun getTodoById(todoId: Int): TodoEntity?
}


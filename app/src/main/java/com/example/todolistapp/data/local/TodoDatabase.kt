package com.yourpackage.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolistapp.data.local.dao.TodoDao
import com.example.todolistapp.data.local.entity.TodoEntity


@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

package com.example.todolistapp.data.remote

import com.example.todolistapp.data.model.Todo
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

import retrofit2.http.GET

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>
}


object RetrofitInstance {
    val api: TodoApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApiService::class.java)
    }
}

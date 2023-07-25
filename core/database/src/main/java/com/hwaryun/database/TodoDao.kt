package com.hwaryun.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hwaryun.database.model.TodoEntity

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos")
    suspend fun getTodos(): List<TodoEntity>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodo(id: Int): TodoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodo(todoEntity: TodoEntity)

    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteTodo(id: Int)
}
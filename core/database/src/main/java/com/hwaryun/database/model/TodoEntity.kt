package com.hwaryun.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val desc: String,
    val dueDate: Long
)

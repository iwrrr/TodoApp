package com.hwaryun.domain.model

data class Todo(
    val id: Int? = null,
    val title: String,
    val desc: String,
    val dueDate: Long
)

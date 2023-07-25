package com.hwaryun.domain.model

data class Note(
    val id: Int? = null,
    val title: String,
    val desc: String,
    val dueDate: Long
)

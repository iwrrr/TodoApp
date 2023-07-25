package com.hwaryun.note_add_edit

import com.hwaryun.domain.model.Todo

data class TodoAddEditState(
    val addOrEdit: Any? = null,
    val todo: Todo? = null,
    val todoId: Int = -1,
    val title: String = "",
    val desc: String = "",
    val dueDate: Long = 0L,
    val isEdit: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)
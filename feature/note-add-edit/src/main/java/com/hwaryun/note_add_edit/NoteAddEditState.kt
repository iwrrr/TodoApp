package com.hwaryun.note_add_edit

data class NoteAddEditState(
    val addOrEdit: Any? = null,
    val noteId: Int = -1,
    val title: String = "",
    val desc: String = "",
    val dueDate: Long = 0L,
    val isEdit: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)
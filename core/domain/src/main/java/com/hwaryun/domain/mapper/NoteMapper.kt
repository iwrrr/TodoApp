package com.hwaryun.domain.mapper

import com.hwaryun.database.model.NoteEntity
import com.hwaryun.domain.model.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        desc = desc,
        dueDate = dueDate
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        desc = desc,
        dueDate = dueDate
    )
}
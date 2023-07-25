package com.hwaryun.domain.mapper

import com.hwaryun.database.model.TodoEntity
import com.hwaryun.domain.model.Todo

fun TodoEntity.toTodo(): Todo {
    return Todo(
        id = id,
        title = title,
        desc = desc,
        dueDate = dueDate
    )
}

fun Todo.toTodoEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        title = title,
        desc = desc,
        dueDate = dueDate
    )
}
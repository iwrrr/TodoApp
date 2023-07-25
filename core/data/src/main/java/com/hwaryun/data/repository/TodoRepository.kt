package com.hwaryun.data.repository

import com.hwaryun.common.DataResult
import com.hwaryun.common.ext.proceed
import com.hwaryun.database.TodoDao
import com.hwaryun.database.model.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface TodoRepository {

    fun getTodos(): Flow<DataResult<List<TodoEntity>>>
    fun getTodo(id: Int): Flow<DataResult<TodoEntity>>
    fun upsertTodo(todoEntity: TodoEntity): Flow<DataResult<Unit>>
    fun deleteTodo(id: Int): Flow<DataResult<Unit>>
}

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override fun getTodos(): Flow<DataResult<List<TodoEntity>>> = flow {
        emit(proceed { todoDao.getTodos() })
    }

    override fun getTodo(id: Int): Flow<DataResult<TodoEntity>> = flow {
        emit(proceed { todoDao.getTodo(id) })
    }

    override fun upsertTodo(todoEntity: TodoEntity): Flow<DataResult<Unit>> = flow {
        emit(proceed { todoDao.upsertTodo(todoEntity) })
    }

    override fun deleteTodo(id: Int): Flow<DataResult<Unit>> = flow {
        emit(proceed { todoDao.deleteTodo(id) })
    }
}
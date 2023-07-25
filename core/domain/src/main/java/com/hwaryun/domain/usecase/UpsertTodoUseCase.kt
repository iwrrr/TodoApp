package com.hwaryun.domain.usecase

import com.hwaryun.common.UiResult
import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.data.repository.TodoRepository
import com.hwaryun.domain.mapper.toTodoEntity
import com.hwaryun.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpsertTodoUseCase @Inject constructor(
    private val repository: TodoRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Todo, UiResult<Unit>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Todo?): Flow<UiResult<Unit>> = flow {
        emit(UiResult.Loading())
        param?.let { todo ->
            if (todo.title.isEmpty() && todo.desc.isEmpty()) {
                emit(UiResult.Failure(Exception("Title or desc cannot be empty!")))
                return@flow
            }

            repository.upsertTodo(todo.toTodoEntity()).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        emit(UiResult.Success(Unit))
                    },
                    doOnError = {
                        emit(UiResult.Failure(result.throwable))
                    }
                )
            }
        }
    }
}
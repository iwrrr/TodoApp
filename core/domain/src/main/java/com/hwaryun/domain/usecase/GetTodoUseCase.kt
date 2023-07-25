package com.hwaryun.domain.usecase

import com.hwaryun.common.UiResult
import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.data.repository.TodoRepository
import com.hwaryun.domain.mapper.toTodo
import com.hwaryun.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(
    private val repository: TodoRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Int, UiResult<Todo>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Int?): Flow<UiResult<Todo>> = flow {
        emit(UiResult.Loading())
        param?.let { noteId ->
            repository.getTodo(noteId).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        result.value?.let { todoEntity ->
                            emit(UiResult.Success(todoEntity.toTodo()))
                        }
                    },
                    doOnError = {
                        emit(UiResult.Failure(result.throwable))
                    }
                )
            }
        }
    }
}
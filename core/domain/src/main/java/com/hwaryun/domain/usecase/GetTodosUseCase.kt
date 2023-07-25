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

class GetTodosUseCase @Inject constructor(
    private val repository: TodoRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Nothing, UiResult<List<Todo>>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Nothing?): Flow<UiResult<List<Todo>>> = flow {
        emit(UiResult.Loading())
        repository.getTodos().collect { result ->
            result.suspendSubscribe(
                doOnSuccess = {
                    result.value?.let { todos ->
                        emit(UiResult.Success(todos.map { it.toTodo() }))
                    }
                },
                doOnError = {
                    emit(UiResult.Failure(result.throwable))
                }
            )
        }
    }
}
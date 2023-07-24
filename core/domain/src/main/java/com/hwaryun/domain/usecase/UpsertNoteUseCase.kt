package com.hwaryun.domain.usecase

import com.hwaryun.common.UiResult
import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<UpsertNoteUseCase.Param, UiResult<Unit>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Param?): Flow<UiResult<Unit>> = flow {
        emit(UiResult.Loading())
        param?.let {
            repository.upsertNote(
                title = param.title,
                desc = param.desc,
                dueDate = param.dueDate,
            ).collect { result ->
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

    data class Param(
        val title: String,
        val desc: String,
        val dueDate: Long,
    )
}
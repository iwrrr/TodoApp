package com.hwaryun.domain.usecase

import com.hwaryun.common.UiResult
import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.data.repository.NoteRepository
import com.hwaryun.domain.mapper.toNote
import com.hwaryun.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Nothing, UiResult<List<Note>>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Nothing?): Flow<UiResult<List<Note>>> = flow {
        emit(UiResult.Loading())
        repository.getNotes().collect { result ->
            result.suspendSubscribe(
                doOnSuccess = {
                    result.value?.let { notes ->
                        emit(UiResult.Success(notes.map { it.toNote() }))
                    }
                },
                doOnError = {
                    emit(UiResult.Failure(result.throwable))
                }
            )
        }
    }
}
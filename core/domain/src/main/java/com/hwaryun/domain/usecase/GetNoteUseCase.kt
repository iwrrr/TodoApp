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

class GetNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Int, UiResult<Note>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Int?): Flow<UiResult<Note>> = flow {
        emit(UiResult.Loading())
        param?.let { noteId ->
            repository.getNote(noteId).collect { result ->
                result.suspendSubscribe(
                    doOnSuccess = {
                        result.value?.let { note ->
                            emit(UiResult.Success(note.toNote()))
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
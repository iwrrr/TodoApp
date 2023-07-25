package com.hwaryun.domain.usecase

import com.hwaryun.common.UiResult
import com.hwaryun.common.di.DispatcherProvider
import com.hwaryun.common.domain.FlowUseCase
import com.hwaryun.common.ext.suspendSubscribe
import com.hwaryun.data.repository.NoteRepository
import com.hwaryun.domain.mapper.toNoteEntity
import com.hwaryun.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Note, UiResult<Unit>>(dispatcherProvider.io) {

    override fun buildFlowUseCase(param: Note?): Flow<UiResult<Unit>> = flow {
        emit(UiResult.Loading())
        param?.let { note ->
            if (note.title.isEmpty() && note.desc.isEmpty()) {
                emit(UiResult.Failure(Exception("Title or desc cannot be empty!")))
                return@flow
            }

            repository.upsertNote(note.toNoteEntity()).collect { result ->
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
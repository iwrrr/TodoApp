package com.hwaryun.note_add_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.orZero
import com.hwaryun.common.ext.subscribe
import com.hwaryun.domain.model.Note
import com.hwaryun.domain.usecase.DeleteNoteUseCase
import com.hwaryun.domain.usecase.GetNoteUseCase
import com.hwaryun.domain.usecase.UpsertNoteUseCase
import com.hwaryun.note_add_edit.navigation.NOTE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAddEditViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val upsertNoteUseCase: UpsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(NoteAddEditState())
    val state = _state.asStateFlow()

    init {
        val noteId = savedStateHandle.get<String>(NOTE_ID)
        noteId?.let { getNote(it.toInt()) }
    }

    private fun getNote(id: Int) {
        viewModelScope.launch {
            getNoteUseCase(id).collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        _state.update {
                            it.copy(
                                note = result.value,
                                noteId = result.value?.id.orZero(),
                                title = result.value?.title.orEmpty(),
                                desc = result.value?.desc.orEmpty(),
                                dueDate = result.value?.dueDate.orZero(),
                                isEdit = !result.value?.title.isNullOrEmpty() || !result.value?.desc.isNullOrEmpty(),
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.throwable?.message ?: "Unknown error"
                            )
                        }
                    }
                )
            }
        }
    }

    fun upsertNote() {
        viewModelScope.launch {
            val note = Note(
                id = state.value.note?.id,
                title = state.value.title,
                desc = state.value.desc,
                dueDate = state.value.dueDate,
            )

            upsertNoteUseCase(note).collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        _state.update {
                            it.copy(
                                addOrEdit = result.value,
                                isLoading = false
                            )
                        }
                    },
                    doOnError = {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.throwable?.message ?: "Unknown error"
                            )
                        }
                    }
                )
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            deleteNoteUseCase(id).collect()
        }

        _state.update {
            it.copy(
                addOrEdit = Unit
            )
        }
    }

    fun updateTitleState(title: String) {
        _state.update { it.copy(title = title) }
    }

    fun updateDescState(desc: String) {
        _state.update { it.copy(desc = desc) }
    }

    fun updateDueDateState(dueDate: Long) {
        _state.update { it.copy(dueDate = dueDate) }
    }

    fun resetErrorState() {
        _state.update { it.copy(error = "") }
    }
}
package com.hwaryun.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.subscribe
import com.hwaryun.domain.model.Note
import com.hwaryun.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NoteListState())
    val state = _state.asStateFlow()

    fun getNotes() {
        viewModelScope.launch {
            getNotesUseCase().collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        result.value?.let { notes ->
                            _state.update {
                                it.copy(
                                    notes = notes,
                                    isLoading = false
                                )
                            }
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

    fun resetErrorState() {
        _state.update {
            it.copy(error = "")
        }
    }
}

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
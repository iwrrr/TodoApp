package com.hwaryun.note_add_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.orZero
import com.hwaryun.common.ext.subscribe
import com.hwaryun.domain.model.Todo
import com.hwaryun.domain.usecase.DeleteTodoUseCase
import com.hwaryun.domain.usecase.GetTodoUseCase
import com.hwaryun.domain.usecase.UpsertTodoUseCase
import com.hwaryun.note_add_edit.navigation.TODO_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoAddEditViewModel @Inject constructor(
    private val getTodoUseCase: GetTodoUseCase,
    private val upsertTodoUseCase: UpsertTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(TodoAddEditState())
    val state = _state.asStateFlow()

    init {
        val todoId = savedStateHandle.get<String>(TODO_ID)
        todoId?.let { getNote(it.toInt()) }
    }

    private fun getNote(id: Int) {
        viewModelScope.launch {
            getTodoUseCase(id).collect { result ->
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
                                todo = result.value,
                                todoId = result.value?.id.orZero(),
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

    fun upsertTodo() {
        viewModelScope.launch {
            val todo = Todo(
                id = state.value.todo?.id,
                title = state.value.title,
                desc = state.value.desc,
                dueDate = state.value.dueDate,
            )

            upsertTodoUseCase(todo).collect { result ->
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

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            deleteTodoUseCase(id).collect()
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
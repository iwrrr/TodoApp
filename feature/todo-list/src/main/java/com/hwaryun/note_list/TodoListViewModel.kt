package com.hwaryun.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwaryun.common.ext.subscribe
import com.hwaryun.domain.model.Todo
import com.hwaryun.domain.usecase.DeleteTodoUseCase
import com.hwaryun.domain.usecase.GetTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TodoListState())
    val state = _state.asStateFlow()

    fun getTodos() {
        viewModelScope.launch {
            getTodosUseCase().collect { result ->
                result.subscribe(
                    doOnLoading = {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    },
                    doOnSuccess = {
                        result.value?.let { todos ->
                            _state.update {
                                it.copy(
                                    todos = todos,
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

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            deleteTodoUseCase(id).collect()
            getTodos()
        }
    }

    fun resetErrorState() {
        _state.update {
            it.copy(error = "")
        }
    }
}

data class TodoListState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
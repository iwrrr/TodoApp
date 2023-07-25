package com.hwaryun.note_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.design_system.ui.theme.TodoAppTheme
import com.hwaryun.note_list.components.TodoGrid

@Composable
internal fun TodoListRoute(
    navigateToAddEditTodo: () -> Unit,
    onTodoClicked: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.getTodos()
    }

    TodoListScreen(
        state = state,
        navigateToAddEditTodo = navigateToAddEditTodo,
        onTodoClicked = onTodoClicked,
        onShowSnackbar = onShowSnackbar,
        deleteTodo = viewModel::deleteTodo,
        resetErrorState = viewModel::resetErrorState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScacffoldPaddingParameter")
@Composable
private fun TodoListScreen(
    state: TodoListState,
    navigateToAddEditTodo: () -> Unit,
    onTodoClicked: (Int) -> Unit,
    deleteTodo: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    resetErrorState: () -> Unit,
) {
    LaunchedEffect(state) {
        if (state.error.isNotEmpty()) {
            onShowSnackbar(state.error, null)
            resetErrorState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { Text(text = stringResource(id = com.hwaryun.design_system.R.string.app_name)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAddEditTodo) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            TodoGrid(
                modifier = Modifier.fillMaxSize(),
                todos = state.todos,
                onTodoClicked = onTodoClicked,
                deleteTodo = deleteTodo
            )

            if (state.todos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Todo is empty")
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DefaultPreview() {
    TodoAppTheme {
        TodoListScreen(
            state = TodoListState(),
            navigateToAddEditTodo = {},
            onTodoClicked = {},
            deleteTodo = {},
            onShowSnackbar = { _, _ -> false },
            resetErrorState = {}
        )
    }
}
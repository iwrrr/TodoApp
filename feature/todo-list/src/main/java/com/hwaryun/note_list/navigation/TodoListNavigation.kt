package com.hwaryun.note_list.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hwaryun.note_list.TodoListRoute

const val todoListRoute = "todos"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.todoListScreen(
    navigateToAddEditTodo: () -> Unit,
    onTodoClicked: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable(
        route = todoListRoute,
    ) {
        TodoListRoute(
            navigateToAddEditTodo = navigateToAddEditTodo,
            onTodoClicked = onTodoClicked,
            onShowSnackbar = onShowSnackbar
        )
    }
}
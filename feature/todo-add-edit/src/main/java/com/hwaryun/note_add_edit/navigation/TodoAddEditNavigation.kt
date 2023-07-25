package com.hwaryun.note_add_edit.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.hwaryun.note_add_edit.TodoAddEditRoute

const val TODO_ID = "todoId"
const val todoAddEditRoute = "todos?id={$TODO_ID}"

fun NavController.navigateToAddEditTodo(id: Int? = null, navOptions: NavOptions? = null) {
    this.navigate("todos?id=$id", navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.todoAddEditScreen(
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable(
        route = todoAddEditRoute,
        arguments = listOf(
            navArgument(TODO_ID) {
                nullable = true
            }
        ),
    ) {
        TodoAddEditRoute(
            popBackStack = popBackStack,
            onShowSnackbar = onShowSnackbar
        )
    }
}
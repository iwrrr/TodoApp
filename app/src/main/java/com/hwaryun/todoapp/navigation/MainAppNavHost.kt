package com.hwaryun.todoapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.hwaryun.note_add_edit.navigation.navigateToAddEditTodo
import com.hwaryun.note_add_edit.navigation.todoAddEditScreen
import com.hwaryun.note_list.navigation.todoListRoute
import com.hwaryun.note_list.navigation.todoListScreen
import com.hwaryun.todoapp.MainAppState

@ExperimentalAnimationApi
@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    mainAppState: MainAppState,
    startDestination: String = todoListRoute,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val navController = mainAppState.navHostController
    AnimatedNavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        todoListScreen(
            navigateToAddEditTodo = navController::navigateToAddEditTodo,
            onTodoClicked = navController::navigateToAddEditTodo,
            onShowSnackbar = onShowSnackbar
        )
        todoAddEditScreen(
            popBackStack = navController::popBackStack,
            onShowSnackbar = onShowSnackbar
        )
    }
}
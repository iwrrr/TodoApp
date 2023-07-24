package com.hwaryun.note_add_edit.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.hwaryun.note_add_edit.NoteAddEditRoute

const val NOTE_ID = "noteId"
const val noteAddEditRoute = "notes?id={$NOTE_ID}"

fun NavController.navigateToAddEditNote(id: Int? = null, navOptions: NavOptions? = null) {
    this.navigate("notes?id=$id", navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteAddEditScreen(
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable(
        route = noteAddEditRoute,
        arguments = listOf(
            navArgument(NOTE_ID) {
                nullable = true
            }
        ),
    ) {
        NoteAddEditRoute(
            popBackStack = popBackStack,
            onShowSnackbar = onShowSnackbar
        )
    }
}
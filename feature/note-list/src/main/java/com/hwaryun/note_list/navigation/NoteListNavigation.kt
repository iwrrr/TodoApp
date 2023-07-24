package com.hwaryun.note_list.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hwaryun.note_list.NoteListRoute

const val noteListRoute = "notes"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteListScreen(
    onNoteClicked: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable(
        route = noteListRoute,
    ) {
        NoteListRoute(
            onNoteClicked = onNoteClicked,
            onShowSnackbar = onShowSnackbar
        )
    }
}
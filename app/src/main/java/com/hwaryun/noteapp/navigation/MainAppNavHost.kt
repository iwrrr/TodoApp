package com.hwaryun.noteapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.hwaryun.note_list.navigation.noteListRoute
import com.hwaryun.note_list.navigation.noteListScreen
import com.hwaryun.noteapp.MainAppState

@ExperimentalAnimationApi
@Composable
fun MainAppNavHost(
    modifier: Modifier = Modifier,
    mainAppState: MainAppState,
    startDestination: String = noteListRoute,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val navController = mainAppState.navHostController
    AnimatedNavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        noteListScreen(onNoteClicked = {})
    }
}
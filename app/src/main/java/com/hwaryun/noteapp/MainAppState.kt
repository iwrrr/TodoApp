package com.hwaryun.noteapp

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.CoroutineScope

@ExperimentalAnimationApi
@Composable
fun rememberMainAppState(
    navHostController: NavHostController = rememberAnimatedNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): MainAppState {
    return remember(navHostController, coroutineScope) {
        MainAppState(navHostController)
    }
}

@Stable
class MainAppState(
    val navHostController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navHostController
            .currentBackStackEntryAsState().value?.destination
}
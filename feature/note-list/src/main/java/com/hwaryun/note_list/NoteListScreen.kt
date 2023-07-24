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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.design_system.ui.theme.NoteAppTheme
import com.hwaryun.note_list.components.NoteGrid

@Composable
internal fun NoteListRoute(
    onNoteClicked: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.getNotes()
    }

    NoteListScreen(
        state = state,
        navigateToAddEditNote = {},
        onNoteClicked = onNoteClicked,
        onShowSnackbar = onShowSnackbar,
        resetErrorState = viewModel::resetErrorState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScacffoldPaddingParameter")
@Composable
private fun NoteListScreen(
    state: NoteListState,
    navigateToAddEditNote: () -> Unit,
    onNoteClicked: (Int) -> Unit,
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
                title = { Text(text = "Note App") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAddEditNote) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NoteGrid(
                modifier = Modifier.fillMaxSize(),
                notes = state.notes,
                onNoteClicked = onNoteClicked
            )

            if (state.notes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Notes is empty")
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
    NoteAppTheme {
        NoteListScreen(
            state = NoteListState(),
            navigateToAddEditNote = {},
            onNoteClicked = {},
            onShowSnackbar = { _, _ -> false },
            resetErrorState = {}
        )
    }
}
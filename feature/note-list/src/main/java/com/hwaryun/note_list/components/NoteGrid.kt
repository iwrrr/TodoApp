package com.hwaryun.note_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.design_system.ui.theme.NoteAppTheme
import com.hwaryun.domain.model.Note
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteGrid(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onNoteClicked: (Int) -> Unit,
    deleteNote: (Int) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp
    ) {
        items(notes, key = { it.id }) { note ->
            val dismissState = rememberDismissState()
            val isDismissed = dismissState.isDismissed(DismissDirection.StartToEnd)

            var itemAppeared by remember { mutableStateOf(false) }

            LaunchedEffect(true) {
                itemAppeared = true
            }

            LaunchedEffect(
                key1 = dismissState.isDismissed(DismissDirection.StartToEnd),
            ) {
                if (isDismissed) {
                    itemAppeared = false
                    delay(500L)
                    deleteNote(note.id)
                }
            }

            AnimatedVisibility(
                visible = itemAppeared,
                enter = slideInHorizontally() + fadeIn(initialAlpha = 0.3f),
                exit = slideOutHorizontally() + fadeOut(targetAlpha = 0.3f)
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd),
                    background = {},
                    dismissContent = {
                        NoteItem(note = note, onNoteClicked = onNoteClicked)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DefaultPreview() {
    NoteAppTheme {
        NoteGrid(
            notes = emptyList(),
            onNoteClicked = {},
            deleteNote = {}
        )
    }
}
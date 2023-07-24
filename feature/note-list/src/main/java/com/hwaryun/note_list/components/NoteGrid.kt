package com.hwaryun.note_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.design_system.ui.theme.NoteAppTheme
import com.hwaryun.domain.model.Note

@Composable
internal fun NoteGrid(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onNoteClicked: (Int) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp
    ) {
        items(notes, key = { it.id }) { note ->
            NoteItem(note = note, onNoteClicked = onNoteClicked)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DefaultPreview() {
    NoteAppTheme {
        NoteGrid(
            notes = emptyList()
        ) {}
    }
}
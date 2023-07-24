package com.hwaryun.note_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.common.utils.convertUnixToDate
import com.hwaryun.design_system.ui.theme.NoteAppTheme
import com.hwaryun.domain.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Int) -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = { onNoteClicked(note.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (note.title.isNotEmpty()) {
                Text(
                    text = note.title,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (note.title.isNotEmpty() && note.desc.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (note.desc.isNotEmpty()) {
                Text(
                    text = note.desc,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Due " + convertUnixToDate(note.dueDate, "dd MMMM yyyy"),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DefaultPreview() {
    NoteAppTheme {
        NoteItem(
            modifier = Modifier.fillMaxWidth(),
            note = Note(1, "", "", 0L),
            onNoteClicked = {}
        )
    }
}
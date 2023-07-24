package com.hwaryun.note_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.design_system.ui.theme.NoteAppTheme
import com.hwaryun.domain.model.Note

@Composable
internal fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Int) -> Unit
) {
    OutlinedCard(
        modifier = modifier.clickable { onNoteClicked(1) },
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.desc,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.dueDate,
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
            note = Note(1, "", "", ""),
            onNoteClicked = {}
        )
    }
}
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
import com.hwaryun.design_system.ui.theme.TodoAppTheme
import com.hwaryun.domain.model.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TodoItem(
    modifier: Modifier = Modifier,
    todo: Todo,
    onNoteClicked: (Int) -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = { todo.id?.let(onNoteClicked) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (todo.title.isNotEmpty()) {
                Text(
                    text = todo.title,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (todo.title.isNotEmpty() && todo.desc.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (todo.desc.isNotEmpty()) {
                Text(
                    text = todo.desc,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Due " + convertUnixToDate(todo.dueDate, "dd MMMM yyyy"),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DefaultPreview() {
    TodoAppTheme {
        TodoItem(
            modifier = Modifier.fillMaxWidth(),
            todo = Todo(1, "", "", 0L),
            onNoteClicked = {}
        )
    }
}
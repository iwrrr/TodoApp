package com.hwaryun.note_list.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hwaryun.design_system.ui.theme.TodoAppTheme
import com.hwaryun.domain.model.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TodoGrid(
    modifier: Modifier = Modifier,
    todos: List<Todo>,
    onTodoClicked: (Int) -> Unit,
    deleteTodo: (Int) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp
    ) {
        items(todos, key = { it.id!! }) { note ->
            val dismissState = rememberDismissState(
                confirmValueChange = {
                    note.id?.let(deleteTodo)
                    true
                },
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.StartToEnd),
                background = {},
                dismissContent = {
                    TodoItem(todo = note, onNoteClicked = onTodoClicked)
                },
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DefaultPreview() {
    TodoAppTheme {
        TodoGrid(
            todos = emptyList(),
            onTodoClicked = {},
            deleteTodo = {}
        )
    }
}
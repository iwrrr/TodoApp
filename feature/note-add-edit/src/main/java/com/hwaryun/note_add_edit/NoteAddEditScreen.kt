package com.hwaryun.note_add_edit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.common.ext.orZero
import com.hwaryun.common.utils.convertUnixToDate
import com.hwaryun.design_system.ui.theme.NoteAppTheme

@Composable
internal fun NoteAddEditRoute(
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: NoteAddEditViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NoteAddEditScreen(
        state = state,
        popBackStack = popBackStack,
        onShowSnackbar = onShowSnackbar,
        upsertNote = viewModel::upsertNote,
        deleteNote = viewModel::deleteNote,
        updateTitleState = viewModel::updateTitleState,
        updateDescState = viewModel::updateDescState,
        updateDueDateState = viewModel::updateDueDateState,
        resetErrorState = viewModel::resetErrorState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScacffoldPaddingParameter")
@Composable
private fun NoteAddEditScreen(
    state: NoteAddEditState,
    popBackStack: () -> Unit,
    upsertNote: () -> Unit,
    deleteNote: (Int) -> Unit,
    updateTitleState: (String) -> Unit,
    updateDescState: (String) -> Unit,
    updateDueDateState: (Long) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    resetErrorState: () -> Unit
) {
    LaunchedEffect(state) {
        if (state.addOrEdit != null) {
            popBackStack()
        }

        if (state.error.isNotEmpty()) {
            onShowSnackbar(state.error, null)
            resetErrorState()
        }
    }

    val datePickerState = rememberDatePickerState()
    var openDialog by remember { mutableStateOf(false) }
    val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = {},
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    if (state.isEdit) {
                        IconButton(onClick = { deleteNote(state.noteId) }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = upsertNote) {
                Icon(imageVector = Icons.Rounded.Save, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = state.title,
                onValueChange = updateTitleState,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.titleLarge,
                placeholder = {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Due Date")
                OutlinedCard(
                    onClick = { openDialog = true }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = convertUnixToDate(state.dueDate, "dd MMMM yyyy"),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
            TextField(
                value = state.desc,
                onValueChange = updateDescState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        text = "Description",
                        modifier = Modifier.offset(y = (-3).dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                minLines = 10
            )
        }

        if (openDialog) {
            DatePickerDialog(
                onDismissRequest = { openDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                            updateDueDateState(datePickerState.selectedDateMillis.orZero())
                        },
                        enabled = confirmEnabled
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { openDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DefaultPreview() {
    NoteAppTheme {
        NoteAddEditScreen(
            state = NoteAddEditState(),
            popBackStack = {},
            upsertNote = {},
            deleteNote = {},
            onShowSnackbar = { _, _ -> false },
            updateTitleState = {},
            updateDescState = {},
            updateDueDateState = {},
            resetErrorState = {}
        )
    }
}
package com.example.viikko1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko1.model.Task
import com.example.viikko1.viewmodel.TaskViewModel
import java.time.LocalDate


@Composable
fun HomeScreen(viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()
    var newTaskTitle by remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text("Task List", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.filterByDone(false) }) {
                    Text("Active tasks")
                }
                Button(onClick = { viewModel.filterByDone(true) }) {
                    Text("Done tasks")
                }
                Button(onClick = { viewModel.sortByDueDate() }) {
                    Text("Sort by date")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("New task") }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    if (newTaskTitle.isNotBlank()) {
                        viewModel.addTask(
                            Task(
                                id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
                                title = newTaskTitle,
                                description = "",
                                priority = 1,
                                dueDate = LocalDate.now().plusDays(1),
                                done = false
                            )
                        )
                        newTaskTitle = ""
                    }
                }) {
                    Text("Add")
                }
            }

            LazyColumn() {
                items(tasks) { task ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { viewModel.selectTask(task) }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(task.title, style = MaterialTheme.typography.headlineSmall)
                                Text(task.description)
                            }
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { viewModel.toggleDone(task.id) }
                            )
                        }
                    }
                }
            }

            if (selectedTask != null) {
                DetailDialog(
                    task = selectedTask!!,
                    onClose = { viewModel.closeDialog() },
                    onUpdate = { viewModel.updateTask(it) },
                    onDelete = { taskToDelete ->
                        viewModel.removeTask(taskToDelete.id)
                        viewModel.closeDialog()
                    })
            }
        }
    }
}

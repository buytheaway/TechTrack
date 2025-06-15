package com.example.techtrack.screens

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuBox(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    label: String
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onItemSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

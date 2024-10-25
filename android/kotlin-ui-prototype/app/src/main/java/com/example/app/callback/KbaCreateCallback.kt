/*
 * Copyright (c) 2024 ForgeRock. All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package com.example.app.callback

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.forgerock.android.auth.callback.ChoiceCallback
import org.forgerock.android.auth.callback.KbaCreateCallback

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KbaCreateCallback(callback: KbaCreateCallback) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember {
        mutableStateOf(callback.predefinedQuestions[0])
    }

    var answer by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            // text field
            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = callback.prompt) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            // menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                callback.predefinedQuestions.forEachIndexed { index, selectedOption ->
                    // menu item
                    DropdownMenuItem(text = {
                        Text(text = selectedOption)
                    }, onClick = {
                        selectedItem = selectedOption
                        expanded = false
                        callback.setSelectedQuestion(callback.predefinedQuestions[index])
                    })
                }
            }
        }
        OutlinedTextField(
            modifier = Modifier,
            value = answer,
            onValueChange = { value ->
                answer = value
                callback.setSelectedAnswer(answer)
            },
            label = { Text(text = "Answer") },
        )

    }

}


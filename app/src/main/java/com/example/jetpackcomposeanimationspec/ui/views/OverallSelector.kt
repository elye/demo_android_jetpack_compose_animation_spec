package com.example.jetpackcomposeanimationspec.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeanimationspec.AnimationSpecEnum

@Composable
fun OverallSelector(selectedAnimationSpec: MutableState<AnimationSpecEnum>) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(36.dp)
    ) {
        Spacer(Modifier.width(16.dp))
        Text("Select AnimationSpec:")
        Spacer(Modifier.width(16.dp))
        DropdownDemo(
            Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 16.dp),
            enumValues<AnimationSpecEnum>().toList(), selectedAnimationSpec
        )
    }
}

@Composable
fun <T> DropdownDemo(modifier: Modifier, items: List<T>, selected: MutableState<T>) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            selected.value.toString(),
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .clickable(
                    onClick = { expanded = true })
                .padding(16.dp, 0.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
        ) {
            items.forEachIndexed { index, select ->
                DropdownMenuItem(
                    onClick = {
                        selected.value = items[index]
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = select.toString(), modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

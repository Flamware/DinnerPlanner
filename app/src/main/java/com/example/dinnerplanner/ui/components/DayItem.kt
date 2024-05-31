package com.example.dinnerplanner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DayItem(day: String, isSelected: Boolean, onDaySelected: (String) -> Unit) {
    Text(
        text = day,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onDaySelected(day) }
            .background(if (isSelected) Color.LightGray else Color.Transparent)
            .padding(8.dp),
        color = if (isSelected) Color.White else Color.Black
    )
}

@Preview
@Composable
fun DayItemPreview() {
    DayItem(day = "Monday", isSelected = true, onDaySelected = {})
}
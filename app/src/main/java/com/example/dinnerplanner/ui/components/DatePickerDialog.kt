package com.example.dinnerplanner.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerDialog(
    selectedDate: MutableState<LocalDate>,
    onDateSelected: (LocalDate) -> Unit
) {
    val daysOfWeek = arrayOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")

    // Calculate the nearest day of the week based on the current date
    fun getNearestDayOfWeek(dayOfWeek: DayOfWeek): LocalDate {
        var date = LocalDate.now()
        while (date.dayOfWeek != dayOfWeek) {
            date = date.plusDays(1)
        }
        return date
    }

    val initialDate = selectedDate.value
    val currentDayOfWeek = initialDate.dayOfWeek
    val nearestDay = getNearestDayOfWeek(currentDayOfWeek)

    val selectedDayIndex = daysOfWeek.indexOf(nearestDay.dayOfWeek.toString())
    val context = LocalContext.current

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, monthOfYear, dayOfMonth ->
            val date = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
            selectedDate.value = date
            onDateSelected(date)
        },
        initialDate.year, initialDate.monthValue - 1, initialDate.dayOfMonth
    )

    // Show DatePickerDialog when a button is clicked
    Button(onClick = { datePickerDialog.show() }) {
        Icon(Icons.Filled.DateRange, contentDescription = "Select Date")
        Spacer(modifier = Modifier.width(8.dp))
        Text("Select Date")
    }
}

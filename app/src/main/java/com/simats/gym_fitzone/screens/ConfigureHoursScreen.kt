package com.simats.gym_fitzone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import androidx.compose.material3.ExperimentalMaterial3Api

@Composable
fun ConfigureHoursScreen(onBackClick: () -> Unit, onCompleteSetup: () -> Unit) {
    var morningOpenTime by remember { mutableStateOf("") }
    var morningCloseTime by remember { mutableStateOf("") }
    var afternoonEnabled by remember { mutableStateOf(false) }
    var afternoonOpenTime by remember { mutableStateOf("") }
    var afternoonCloseTime by remember { mutableStateOf("") }
    var eveningOpenTime by remember { mutableStateOf("") }
    var eveningCloseTime by remember { mutableStateOf("") }

    // Time picker dialog states
    var showMorningOpenPicker by remember { mutableStateOf(false) }
    var showMorningClosePicker by remember { mutableStateOf(false) }
    var showAfternoonOpenPicker by remember { mutableStateOf(false) }
    var showAfternoonClosePicker by remember { mutableStateOf(false) }
    var showEveningOpenPicker by remember { mutableStateOf(false) }
    var showEveningClosePicker by remember { mutableStateOf(false) }

    // Helper function to compare times - returns true if closeTime is after openTime
    val isTimeAfter: (String, String) -> Boolean = { openTime, closeTime ->
        if (openTime.isEmpty() || closeTime.isEmpty()) {
            false
        } else {
            try {
                val openParts = openTime.split(":")
                val closeParts = closeTime.split(":")
                val openHour = openParts[0].toIntOrNull() ?: 0
                val openMin = openParts[1].toIntOrNull() ?: 0
                val closeHour = closeParts[0].toIntOrNull() ?: 0
                val closeMin = closeParts[1].toIntOrNull() ?: 0

                when {
                    closeHour > openHour -> true
                    closeHour == openHour -> closeMin > openMin
                    else -> false
                }
            } catch (e: Exception) {
                false
            }
        }
    }

    // Validation - at least morning session required, times must be valid and closing after opening
    val isFormValid = morningOpenTime.isNotEmpty() && morningCloseTime.isNotEmpty() &&
                     isTimeAfter(morningOpenTime, morningCloseTime) &&
                     eveningOpenTime.isNotEmpty() && eveningCloseTime.isNotEmpty() &&
                     isTimeAfter(eveningOpenTime, eveningCloseTime) &&
                     (!afternoonEnabled || (afternoonOpenTime.isNotEmpty() && afternoonCloseTime.isNotEmpty() &&
                     isTimeAfter(afternoonOpenTime, afternoonCloseTime)))

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {
        // Header with Back Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1BB85B)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Configure Hours",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        // Step Indicator
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Step 1 - Completed
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color(0xFF1BB85B), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))

                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(Color(0xFF1BB85B))
                )
                Spacer(modifier = Modifier.size(8.dp))

                // Step 2 - Active
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color(0xFF1BB85B), shape = CircleShape)
                )
                Spacer(modifier = Modifier.size(8.dp))

                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(Color(0xFFCCCCCC))
                )
                Spacer(modifier = Modifier.size(8.dp))

                // Step 3 - Inactive
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color(0xFFDDDDDD), shape = CircleShape)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Header Icon
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFF1BB85B), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "⏰",
                        fontSize = 48.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Title
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Configure Hours",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Subtitle
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Set your gym operating hours",
                    fontSize = 14.sp,
                    color = Color(0xFF999999)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Morning Session
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🌅",
                            fontSize = 28.sp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Morning Session",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "Required",
                                fontSize = 11.sp,
                                color = Color(0xFF1BB85B),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Time inputs in two columns
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Opening Time Column
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Opening Time",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedButton(
                                onClick = { showMorningOpenPicker = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = if (morningOpenTime.isEmpty()) "Select Time" else morningOpenTime,
                                    fontSize = 14.sp,
                                    color = if (morningOpenTime.isEmpty()) Color(0xFFBBBBBB) else Color.Black
                                )
                            }
                        }

                        // Closing Time Column
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Closing Time",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedButton(
                                onClick = { showMorningClosePicker = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = if (morningCloseTime.isEmpty()) "Select Time" else morningCloseTime,
                                    fontSize = 14.sp,
                                    color = if (morningCloseTime.isEmpty()) Color(0xFFBBBBBB) else Color.Black
                                )
                            }
                        }
                    }

                    // Morning Open Time Picker
                    if (showMorningOpenPicker) {
                        TimePickerDialog(
                            title = "Opening Time",
                            onTimeSelected = { hour, minute ->
                                morningOpenTime = String.format(Locale.US, "%02d:%02d", hour, minute)
                                showMorningOpenPicker = false
                            },
                            onDismiss = { showMorningOpenPicker = false }
                        )
                    }

                    // Morning Close Time Picker
                    if (showMorningClosePicker) {
                        TimePickerDialog(
                            title = "Closing Time",
                            onTimeSelected = { hour, minute ->
                                morningCloseTime = String.format(Locale.US, "%02d:%02d", hour, minute)
                                showMorningClosePicker = false
                            },
                            onDismiss = { showMorningClosePicker = false }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Afternoon Session
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "☀️",
                                fontSize = 28.sp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Afternoon Session",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "(Optional)",
                                    fontSize = 11.sp,
                                    color = Color(0xFF999999),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Switch(
                            checked = afternoonEnabled,
                            onCheckedChange = { afternoonEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF1BB85B),
                                checkedTrackColor = Color(0xFF1BB85B).copy(alpha = 0.5f),
                                uncheckedThumbColor = Color(0xFFCCCCCC),
                                uncheckedTrackColor = Color(0xFFE0E0E0).copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.size(width = 50.dp, height = 24.dp)
                        )
                    }

                    if (afternoonEnabled) {
                        Spacer(modifier = Modifier.height(20.dp))

                        // Time inputs in two columns
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Opening Time Column
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Opening Time",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedButton(
                                    onClick = { showAfternoonOpenPicker = true },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = if (afternoonOpenTime.isEmpty()) "Select Time" else afternoonOpenTime,
                                        fontSize = 14.sp,
                                        color = if (afternoonOpenTime.isEmpty()) Color(0xFFBBBBBB) else Color.Black
                                    )
                                }
                            }

                            // Closing Time Column
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Closing Time",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedButton(
                                    onClick = { showAfternoonClosePicker = true },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = if (afternoonCloseTime.isEmpty()) "Select Time" else afternoonCloseTime,
                                        fontSize = 14.sp,
                                        color = if (afternoonCloseTime.isEmpty()) Color(0xFFBBBBBB) else Color.Black
                                    )
                                }
                            }
                        }

                        // Afternoon Open Time Picker
                        if (showAfternoonOpenPicker) {
                            TimePickerDialog(
                                title = "Opening Time",
                                onTimeSelected = { hour, minute ->
                                    afternoonOpenTime = String.format(Locale.US, "%02d:%02d", hour, minute)
                                    showAfternoonOpenPicker = false
                                },
                                onDismiss = { showAfternoonOpenPicker = false }
                            )
                        }

                        // Afternoon Close Time Picker
                        if (showAfternoonClosePicker) {
                            TimePickerDialog(
                                title = "Closing Time",
                                onTimeSelected = { hour, minute ->
                                    afternoonCloseTime = String.format(Locale.US, "%02d:%02d", hour, minute)
                                    showAfternoonClosePicker = false
                                },
                                onDismiss = { showAfternoonClosePicker = false }
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Evening Session
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🌙",
                            fontSize = 28.sp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Evening Session",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "Required",
                                fontSize = 11.sp,
                                color = Color(0xFF1BB85B),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Time inputs in two columns
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Opening Time Column
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Opening Time",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedButton(
                                onClick = { showEveningOpenPicker = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = if (eveningOpenTime.isEmpty()) "Select Time" else eveningOpenTime,
                                    fontSize = 14.sp,
                                    color = if (eveningOpenTime.isEmpty()) Color(0xFFBBBBBB) else Color.Black
                                )
                            }
                        }

                        // Closing Time Column
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Closing Time",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedButton(
                                onClick = { showEveningClosePicker = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = if (eveningCloseTime.isEmpty()) "Select Time" else eveningCloseTime,
                                    fontSize = 14.sp,
                                    color = if (eveningCloseTime.isEmpty()) Color(0xFFBBBBBB) else Color.Black
                                )
                            }
                        }
                    }

                    // Evening Open Time Picker
                    if (showEveningOpenPicker) {
                        TimePickerDialog(
                            title = "Opening Time",
                            onTimeSelected = { hour, minute ->
                                eveningOpenTime = String.format(Locale.US, "%02d:%02d", hour, minute)
                                showEveningOpenPicker = false
                            },
                            onDismiss = { showEveningOpenPicker = false }
                        )
                    }

                    // Evening Close Time Picker
                    if (showEveningClosePicker) {
                        TimePickerDialog(
                            title = "Closing Time",
                            onTimeSelected = { hour, minute ->
                                eveningCloseTime = String.format(Locale.US, "%02d:%02d", hour, minute)
                                showEveningClosePicker = false
                            },
                            onDismiss = { showEveningClosePicker = false }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Action Buttons
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Back Button
                Button(
                    onClick = { onBackClick() },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF0F0F0),
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Back",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Complete Setup Button
                Button(
                    onClick = { onCompleteSetup() },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1BB85B),
                        disabledContainerColor = Color(0xFFCCCCCC)
                    ),
                    enabled = isFormValid
                ) {
                    Text(
                        text = "Complete Setup",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    title: String,
    onTimeSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedHour by remember { mutableStateOf(9) }
    var selectedMinute by remember { mutableStateOf(0) }

    // Helper function to get display hour (1-24 format for clarity)
    fun getDisplayHour(): Int {
        return if (selectedHour == 0) 24 else selectedHour
    }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 24-Hour Format Info
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F8FF), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "24-Hour Format (00:00 - 23:59)",
                        fontSize = 12.sp,
                        color = Color(0xFF1BB85B),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Time Display (24-hour format)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = String.format(Locale.US, "%02d:%02d", selectedHour, selectedMinute),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1BB85B)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Hour Picker (0-23)
                Column {
                    Text(
                        "Hour (0-23)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF999999)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                selectedHour = if (selectedHour == 0) 23 else selectedHour - 1
                            },
                            modifier = Modifier.size(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("-", fontSize = 20.sp, color = Color.Black)
                        }

                        Text(
                            text = String.format(Locale.US, "%02d", selectedHour),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Button(
                            onClick = {
                                selectedHour = (selectedHour + 1) % 24
                            },
                            modifier = Modifier.size(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("+", fontSize = 20.sp, color = Color.Black)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Minute Picker (0-59)
                Column {
                    Text(
                        "Minute (0-59)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF999999)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                selectedMinute = if (selectedMinute == 0) 59 else selectedMinute - 1
                            },
                            modifier = Modifier.size(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("-", fontSize = 20.sp, color = Color.Black)
                        }

                        Text(
                            text = String.format(Locale.US, "%02d", selectedMinute),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Button(
                            onClick = {
                                selectedMinute = (selectedMinute + 1) % 60
                            },
                            modifier = Modifier.size(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("+", fontSize = 20.sp, color = Color.Black)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Ensure valid 24-hour format (0-23 hours, 0-59 minutes)
                    if (selectedHour in 0..23 && selectedMinute in 0..59) {
                        onTimeSelected(selectedHour, selectedMinute)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1BB85B)
                )
            ) {
                Text("Confirm", color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onDismiss() }
            ) {
                Text("Cancel", color = Color(0xFF1BB85B))
            }
        }
    )
}


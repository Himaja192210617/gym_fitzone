package com.simats.gym_fitzone.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun BookSlotScreen(
    gymName: String = "FitZone Premium Mumbai",
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToBMI: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var selectedDate by remember { mutableStateOf("Select Date") }
    var selectedSlot by remember { mutableStateOf("") }
    var selectedDuration by remember { mutableStateOf("60") }
    val selectedWorkouts = remember { mutableStateListOf<String>() }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth().height(70.dp),
                containerColor = Color.White,
                contentColor = Color(0xFF1BB85B)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomNavItem(icon = Icons.Default.Home, label = "Home", isSelected = false, onClick = onNavigateToHome)
                    BottomNavItem(icon = Icons.Default.DateRange, label = "Book", isSelected = true)
                    BottomNavItem(icon = Icons.Default.FitnessCenter, label = "Workout", isSelected = false, onClick = onNavigateToWorkout)
                    BottomNavItem(icon = Icons.Default.MonitorHeart, label = "BMI", isSelected = false, onClick = onNavigateToBMI)
                    BottomNavItem(icon = Icons.Default.History, label = "History", isSelected = false, onClick = onNavigateToHistory)
                    BottomNavItem(icon = Icons.Default.Person, label = "Profile", isSelected = false, onClick = onNavigateToProfile)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8F9FA))
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1BB85B))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Column {
                        Text(
                            text = "Book Your Slot",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = gymName,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Select Date
                item {
                    BookingSection(title = "Select Date") {
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { datePickerDialog.show() },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color(0xFFEEEEEE))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = selectedDate, fontSize = 14.sp)
                            }
                        }
                    }
                }

                // Select Time Slot
                item {
                    BookingSection(title = "Select Time Slot") {
                        val slots = listOf(
                            SlotData("06:00-07:00", 12, 5),
                            SlotData("07:00-08:00", 25, 10),
                            SlotData("08:00-09:00", 18, 8),
                            SlotData("09:00-10:00", 10, 3),
                            SlotData("10:00-11:00", 5, 2),
                            SlotData("17:00-18:00", 20, 12),
                            SlotData("18:00-19:00", 28, 15),
                            SlotData("19:00-20:00", 22, 10)
                        )
                        
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            slots.forEach { slotData ->
                                AdvancedSlotItem(
                                    data = slotData,
                                    isSelected = selectedSlot == slotData.time,
                                    onClick = { selectedSlot = slotData.time }
                                )
                            }
                        }
                    }
                }

                // Workout Types
                item {
                    BookingSection(title = "Select Workout Types") {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            WorkoutTypeSelection("Cardio", selectedWorkouts)
                            WorkoutTypeSelection("Strength", selectedWorkouts)
                            WorkoutTypeSelection("Functional", selectedWorkouts)
                            WorkoutTypeSelection("Yoga", selectedWorkouts)
                            WorkoutTypeSelection("HIIT", selectedWorkouts)
                        }
                    }
                }

                // Duration
                item {
                    BookingSection(title = "Duration (minutes)") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("30", "60", "90", "120").forEach { duration ->
                                val isSelected = selectedDuration == duration
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedDuration = duration },
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, if (isSelected) Color(0xFF1BB85B) else Color(0xFFEEEEEE)),
                                    color = if (isSelected) Color(0xFFE8F5E9) else Color.White
                                ) {
                                    Text(
                                        text = duration,
                                        modifier = Modifier.padding(vertical = 12.dp),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        fontSize = 14.sp,
                                        color = if (isSelected) Color(0xFF1BB85B) else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                // Confirm Button
                item {
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB85B)),
                        shape = RoundedCornerShape(8.dp),
                        enabled = selectedSlot.isNotEmpty() && selectedDate != "Select Date"
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Confirm Booking", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun BookingSection(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

data class SlotData(val time: String, val bookings: Int, val combinational: Int)

@Composable
fun AdvancedSlotItem(data: SlotData, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, if (isSelected) Color(0xFF1BB85B) else Color(0xFFEEEEEE)),
        color = if (isSelected) Color(0xFFE8F5E9) else Color.White
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = data.time, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Color(0xFF1BB85B) else Color.Black)
                Text(text = "Bookings: ${data.bookings} | Combinational: ${data.combinational}", fontSize = 12.sp, color = Color.Gray)
            }
            if (isSelected) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF1BB85B))
            }
        }
    }
}

@Composable
fun WorkoutTypeSelection(name: String, selectedList: MutableList<String>) {
    val isSelected = selectedList.contains(name)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { 
                if (isSelected) selectedList.remove(name) else selectedList.add(name)
            },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, if (isSelected) Color(0xFF1BB85B) else Color(0xFFEEEEEE)),
        color = if (isSelected) Color(0xFFE8F5E9) else Color.White
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name, fontSize = 14.sp, color = if (isSelected) Color(0xFF1BB85B) else Color.Black)
            Checkbox(
                checked = isSelected,
                onCheckedChange = { 
                    if (it) selectedList.add(name) else selectedList.remove(name)
                },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1BB85B))
            )
        }
    }
}

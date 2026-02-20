package com.simats.gym_fitzone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookingHistoryScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToBook: () -> Unit = {},
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToBMI: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val bookings = listOf(
        BookingRecord("Feb 14, 2025", "07:00 - 08:00", "Completed", "Chest, Triceps"),
        BookingRecord("Feb 12, 2025", "18:00 - 19:30", "Completed", "Cardio, Legs"),
        BookingRecord("Feb 10, 2025", "06:30 - 07:30", "Cancelled", "Functional Training"),
        BookingRecord("Feb 08, 2025", "17:00 - 18:00", "Completed", "Back, Biceps"),
        BookingRecord("Feb 05, 2025", "07:00 - 08:30", "Completed", "HIIT, Core")
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
                    BottomNavItem(icon = Icons.Default.DateRange, label = "Book", isSelected = false, onClick = onNavigateToBook)
                    BottomNavItem(icon = Icons.Default.FitnessCenter, label = "Workout", isSelected = false, onClick = onNavigateToWorkout)
                    BottomNavItem(icon = Icons.Default.MonitorHeart, label = "BMI", isSelected = false, onClick = onNavigateToBMI)
                    BottomNavItem(icon = Icons.Default.History, label = "History", isSelected = true)
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
                    .padding(vertical = 32.dp, horizontal = 24.dp)
            ) {
                Column {
                    Text(
                        text = "Booking History",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Your past and upcoming sessions",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(bookings) { booking ->
                    BookingHistoryCard(booking)
                }
            }
        }
    }
}

data class BookingRecord(
    val date: String,
    val time: String,
    val status: String,
    val focus: String
)

@Composable
fun BookingHistoryCard(booking: BookingRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = booking.date,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = booking.time, fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Focus: ${booking.focus}",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Surface(
                color = if (booking.status == "Completed") Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = booking.status,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (booking.status == "Completed") Color(0xFF2E7D32) else Color(0xFFC62828)
                )
            }
        }
    }
}

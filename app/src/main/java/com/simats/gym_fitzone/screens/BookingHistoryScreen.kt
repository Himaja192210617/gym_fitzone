package com.simats.gym_fitzone.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign
import com.simats.gym_fitzone.viewmodel.GymViewModel
import com.simats.gym_fitzone.viewmodel.HistoryState
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


@Composable
fun BookingHistoryScreen(
    gymViewModel: GymViewModel,
    userId: Int,
    onNavigateToHome: () -> Unit = {},
    onNavigateToBook: () -> Unit = {},
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToBMI: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val historyState by gymViewModel.historyState.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current

    var showCancelDialog by remember { mutableStateOf(false) }
    var bookingIdToCancel by remember { mutableStateOf<Int?>(null) }

    if (showCancelDialog && bookingIdToCancel != null) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Confirm Cancellation") },
            text = { Text("Are you sure to cancel?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val bookingId = bookingIdToCancel
                        showCancelDialog = false
                        if (bookingId != null) {
                            gymViewModel.cancelBooking(userId, bookingId) { success, message ->
                                android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                ) {
                    Text("Yes", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("No", fontWeight = FontWeight.Medium)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }

    LaunchedEffect(userId) {
        if (userId != -1) {
            gymViewModel.getBookingHistory(userId)
        }
    }

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

            when (historyState) {
                is HistoryState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF1BB85B))
                    }
                }
                is HistoryState.Success -> {
                    val bookings = (historyState as HistoryState.Success).history
                    if (bookings.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Default.EventBusy,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("No Bookings", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                                Text("Your haven't made any bookings yet.", fontSize = 14.sp, color = Color.Gray)
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(bookings) { booking ->
                                BookingHistoryCard(
                                    booking = booking,
                                    onCancel = {
                                        bookingIdToCancel = booking.booking_id
                                        showCancelDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
                is HistoryState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text((historyState as HistoryState.Error).message, color = Color.Red)
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun BookingHistoryCard(
    booking: com.simats.gym_fitzone.models.BookingHistory,
    onCancel: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
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
                        Text(text = "${booking.slot} (${booking.duration_minutes} min)", fontSize = 14.sp, color = Color.Gray)
                    }
                }
                
                Surface(
                    color = if (booking.status == "active") Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = booking.status.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (booking.status == "active") Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            
            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
            
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Workout Focus:",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Text(
                text = booking.summary, // Displays "Biceps + Legs" etc.
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )

            // Dynamic Details Section
            androidx.compose.animation.AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFEEEEEE))
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Detailed Breakdown:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Split the details string (Backend sends it as "Cat1 : Ex1, Ex2 | Cat2 : Ex3")
                    val segments = booking.details.split(" | ")
                    segments.forEach { segment ->
                        val parts = segment.split(" : ")
                        if (parts.size == 2) {
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))) {
                                        append(parts[0])
                                    }
                                    append(" : ")
                                    append(parts[1])
                                },
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        } else {
                            Text(
                                text = segment,
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            if (!isExpanded) {
                Text(
                    text = "Tap to view full details",
                    fontSize = 11.sp,
                    color = Color(0xFF1BB85B),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (booking.cancel_allowed) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    border = BorderStroke(1.dp, Color.Red),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Icon(Icons.Default.Cancel, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cancel Booking", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

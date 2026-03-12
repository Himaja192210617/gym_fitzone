package com.simats.gym_fitzone.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    userName: String = "User",
    gymName: String = "Select Gym",
    onLogout: () -> Unit = {},
    onNavigateToBook: () -> Unit = {},
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToBMI: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                    BottomNavItem(icon = Icons.Default.Home, label = "Home", isSelected = true)
                    BottomNavItem(icon = Icons.Default.DateRange, label = "Book", isSelected = false, onClick = onNavigateToBook)
                    BottomNavItem(icon = Icons.Default.FitnessCenter, label = "Workout", isSelected = false, onClick = onNavigateToWorkout)
                    BottomNavItem(icon = Icons.Default.MonitorHeart, label = "BMI", isSelected = false, onClick = onNavigateToBMI)
                    BottomNavItem(icon = Icons.Default.History, label = "History", isSelected = false, onClick = onNavigateToHistory)
                    BottomNavItem(icon = Icons.Default.Person, label = "Profile", isSelected = false, onClick = onNavigateToProfile)
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8F8F8))
        ) {
            // Header
            item {
                Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF1BB85B)).padding(24.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                        Column {
                            Text(text = "Welcome back,", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(text = userName, fontSize = 18.sp, color = Color.White)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = gymName, fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Light)
                        }
                        IconButton(onClick = onLogout) {
                            Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout", tint = Color.White)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Current Crowd Level
            item { CrowdLevelCard() }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Next Available Slot
            item { NextSlotCard(onBookClick = onNavigateToBook) }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Peak Hour Alert
            item { PeakHourAlertCard() }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Feature Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureCard(
                        icon = Icons.Default.MonitorHeart,
                        label = "BMI Calculator",
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToBMI
                    )
                    FeatureCard(
                        icon = Icons.Default.FitnessCenter,
                        label = "Workout Guide",
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToWorkout
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, label: String, isSelected: Boolean, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.size(50.dp).clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFF1BB85B) else Color(0xFFAAAAAA),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) Color(0xFF1BB85B) else Color(0xFFAAAAAA),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun CrowdLevelCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Current Crowd Level", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))
                Surface(color = Color(0xFFFFF9C4), shape = RoundedCornerShape(8.dp)) {
                    Text(text = "Medium", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFBC02D))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Live crowd prediction based on current bookings", fontSize = 12.sp, color = Color.Gray)
            }
            Box(modifier = Modifier.size(50.dp).background(Color(0xFFE8F5E9), CircleShape), contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = Color(0xFF1BB85B))
            }
        }
    }
}

@Composable
fun NextSlotCard(onBookClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Next Available Slot", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "07:00-08:00", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onBookClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB85B)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Book Now", fontWeight = FontWeight.Bold)
                }
            }
            Box(modifier = Modifier.size(50.dp).background(Color(0xFFE3F2FD), CircleShape), contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.AccessTime, contentDescription = null, tint = Color(0xFF1976D2))
            }
        }
    }
}

@Composable
fun PeakHourAlertCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFFFE0B2))
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.Top) {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = Color(0xFFE65100))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Peak Hour Alert", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                Text(text = "Expected peak crowd at 07:00-08:00", fontSize = 13.sp, color = Color(0xFFEF6C00))
                Text(text = "Consider booking earlier or later slots for better experience", fontSize = 12.sp, color = Color(0xFFEF6C00))
            }
        }
    }
}

@Composable
fun FeatureCard(icon: ImageVector, label: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = label, tint = Color(0xFF1BB85B), modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

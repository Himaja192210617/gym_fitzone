package com.simats.gym_fitzone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToBook: () -> Unit = {},
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToBMI: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
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
                    BottomNavItem(icon = Icons.Default.History, label = "History", isSelected = false, onClick = onNavigateToHistory)
                    BottomNavItem(icon = Icons.Default.Person, label = "Profile", isSelected = true)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8F9FA))
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF00893D))
                    .padding(vertical = 32.dp, horizontal = 24.dp)
            ) {
                Column {
                    Text(
                        text = "Profile",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Manage your account",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Profile Details
                ProfileDetailsCard()

                // Gym Membership
                GymMembershipCard()

                // Your Stats
                StatsCard()

                // Actions
                ActionsCard(onLogout)

                // Footer
                Footer()
            }
        }
    }
}

@Composable
fun ProfileDetailsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(color = Color(0xFF1BB85B), shape = CircleShape, modifier = Modifier.size(50.dp)) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text("R", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Rajesh Kumar", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp)) {
                        Text("Member", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 12.sp, color = Color(0xFF2E7D32))
                    }
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            ProfileInfoRow(icon = Icons.Default.Email, text = "rajesh@example.com")
            Spacer(modifier = Modifier.height(12.dp))
            ProfileInfoRow(icon = Icons.Default.Phone, text = "9876543210")
            Spacer(modifier = Modifier.height(12.dp))
            ProfileInfoRow(icon = Icons.Default.Person, text = "28 years • Male")
        }
    }
}

@Composable
fun ProfileInfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontSize = 14.sp, color = Color.DarkGray)
    }
}

@Composable
fun GymMembershipCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Gym Membership", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(color = Color(0xFFE8F5E9), shape = CircleShape, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = Color(0xFF1BB85B), modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("FitZone Premium Mumbai", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("Andheri West, Mumbai", fontSize = 12.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Member ID", color = Color.Gray)
                Text("MEM001", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Gym ID", color = Color.Gray)
                Text("GYM001", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun StatsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Your Stats", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatItem(Modifier.weight(1f), Icons.Default.CalendarToday, "0", "Total Bookings", Color(0xFFE3F2FD), Color(0xFF1976D2))
                StatItem(Modifier.weight(1f), Icons.Default.CheckCircle, "0", "Active Bookings", Color(0xFFE8F5E9), Color(0xFF2E7D32))
            }
        }
    }
}

@Composable
fun StatItem(modifier: Modifier, icon: ImageVector, value: String, label: String, bgColor: Color, textColor: Color) {
    Surface(modifier = modifier, color = bgColor, shape = RoundedCornerShape(8.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 16.dp)) {
            Icon(icon, contentDescription = null, tint = textColor)
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(label, fontSize = 12.sp, color = textColor)
        }
    }
}

@Composable
fun ActionsCard(onLogout: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            ActionItem(icon = Icons.Default.Edit, text = "Edit Profile", onClick = { /* TODO */ })
            Divider()
            ActionItem(icon = Icons.Default.LocationOn, text = "Change Gym", onClick = { /* TODO */ })
            Divider()
            ActionItem(icon = Icons.Default.Logout, text = "Logout", isDestructive = true, onClick = onLogout)
        }
    }
}

@Composable
fun ActionItem(icon: ImageVector, text: String, isDestructive: Boolean = false, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = if(isDestructive) Color.Red else Color.Gray)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, color = if(isDestructive) Color.Red else Color.Black, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        if (!isDestructive) {
            Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun Footer() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("FitZone", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Smart Gym Management System", fontSize = 12.sp, color = Color.Gray)
            Text("Version 1.0.0", fontSize = 12.sp, color = Color.LightGray)
        }
    }
}

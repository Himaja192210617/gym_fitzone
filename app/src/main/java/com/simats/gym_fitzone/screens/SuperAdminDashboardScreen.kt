package com.simats.gym_fitzone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
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
fun SuperAdminDashboardScreen(onLogout: () -> Unit) {
    val primaryGreen = Color(0xFF1BB85B)

    Scaffold(
        topBar = {
            Surface(
                color = primaryGreen,
                contentColor = Color.White,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Super Admin Dashboard",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = onLogout) {
                            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                        }
                    }
                    Text(
                        text = "FitZone Management System",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FA)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Summary Stats Row 1
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AdminStatCard(
                        title = "Total Gyms",
                        value = "2",
                        subtitle = "2 active, 0 pending",
                        icon = Icons.Default.LocationCity,
                        iconColor = Color(0xFF9C27B0),
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = "Total Members",
                        value = "8",
                        subtitle = "Across all gyms",
                        icon = Icons.Default.Groups,
                        iconColor = Color(0xFF2196F3),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Summary Stats Row 2
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AdminStatCard(
                        title = "Total Bookings",
                        value = "0",
                        subtitle = "All time",
                        icon = Icons.Default.CalendarMonth,
                        iconColor = Color(0xFF4CAF50),
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = "AI Performance",
                        value = "94%",
                        subtitle = "Prediction accuracy",
                        icon = Icons.Default.AutoAwesome,
                        iconColor = Color(0xFFFF9800),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // System Status
            item {
                AdminSectionCard(title = "System Status") {
                    SystemStatusItem(label = "API Status", status = "Operational", statusColor = Color(0xFF4CAF50))
                    SystemStatusItem(label = "AI Models", status = "Active", statusColor = Color(0xFF4CAF50))
                    SystemStatusItem(label = "Database", status = "Healthy", statusColor = Color(0xFF4CAF50))
                }
            }

            // Recent Activity
            item {
                AdminSectionCard(title = "Recent Activity") {
                    RecentActivityItem("New gym registered", "2 hours ago", Icons.Default.AddBusiness)
                    RecentActivityItem("AI model updated", "5 hours ago", Icons.Default.Update)
                    RecentActivityItem("System maintenance", "1 day ago", Icons.Default.Settings)
                }
            }

            // Growth Metrics
            item {
                AdminSectionCard(title = "Growth Metrics") {
                    GrowthMetricItem("Gyms Growth", "+25%", Color(0xFF4CAF50))
                    GrowthMetricItem("Members Growth", "+22%", Color(0xFF4CAF50))
                    GrowthMetricItem("Bookings Growth", "+18%", Color(0xFF4CAF50))
                }
            }

            // Registered Gyms
            item {
                AdminSectionCard(title = "Registered Gyms") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Gym Details", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1.5f))
                            Text("Status", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                            Text("Actions", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        }
                        HorizontalDivider(color = Color(0xFFEEEEEE))
                        GymRowItem("FitZone Premium", "Active")
                        GymRowItem("Iron Paradise", "Active")
                    }
                }
            }

            // AI Prediction Performance
            item {
                AdminSectionCard(title = "AI Prediction Performance") {
                    AIPerformanceMetric("Overall Accuracy", "94.2%", Color(0xFFE8F5E9), Color(0xFF4CAF50))
                    AIPerformanceMetric("Peak Hour Detection", "96.8%", Color(0xFFE3F2FD), Color(0xFF2196F3))
                    AIPerformanceMetric("Crowd Level Prediction", "92.5%", Color(0xFFF3E5F5), Color(0xFF9C27B0))
                    AIPerformanceMetric("Booking Trends", "91.3%", Color(0xFFFFF3E0), Color(0xFFFF9800))
                }
            }
        }
    }
}

@Composable
fun AdminStatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(text = title, fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(iconColor.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = subtitle, fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@Composable
fun AdminSectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun SystemStatusItem(label: String, status: String, statusColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.DarkGray)
        Surface(
            color = statusColor.copy(alpha = 0.1f),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(6.dp).background(statusColor, CircleShape))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = status, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = statusColor)
            }
        }
    }
}

@Composable
fun RecentActivityItem(text: String, time: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = text, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color.Black)
            Text(text = time, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Composable
fun GrowthMetricItem(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.DarkGray)
        Surface(
            color = color.copy(alpha = 0.1f),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = value,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
fun GymRowItem(name: String, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name, fontSize = 13.sp, modifier = Modifier.weight(1.5f), color = Color.Black)
        
        Surface(
            color = Color(0xFF4CAF50).copy(alpha = 0.1f),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.weight(1f).wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(
                text = status,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }

        OutlinedButton(
            onClick = { },
            modifier = Modifier.weight(1f).height(32.dp),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(6.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
        ) {
            Text("Suspend", fontSize = 10.sp)
        }
    }
}

@Composable
fun AIPerformanceMetric(label: String, value: String, bgColor: Color, textColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = label, fontSize = 12.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textColor)
        }
    }
}

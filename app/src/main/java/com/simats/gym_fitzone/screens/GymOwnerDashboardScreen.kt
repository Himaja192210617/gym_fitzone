package com.simats.gym_fitzone.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.ModeNight
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun GymOwnerDashboardScreen(
    gymName: String = "dyna women gym",
    gymLocation: String = "Andheri West, Mumbai",
    gymCity: String = "Mumbai",
    onLogout: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("analytics") }
    var memberName by remember { mutableStateOf("") }
    var memberId by remember { mutableStateOf("") }
    var memberList by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
    
    val generatedGymId = remember { "GYM" + UUID.randomUUID().toString().substring(0, 4).uppercase() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        // --- UPPER PART (PERSISTENT) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1BB85B))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Gym Owner Dashboard", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = gymName, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                }
                IconButton(onClick = onLogout) {
                    Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout", tint = Color.White)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Metric Cards
            item {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    MetricCard("Total Members", memberList.size.toString(), Icons.Default.People, Color(0xFF1BB85B), Color(0xFFE8F5E9))
                    MetricCard("Today's Bookings", "0", Icons.Default.CalendarToday, Color(0xFF2E7D32), Color(0xFFE8F5E9))
                    MetricCard("Total Bookings", "0", Icons.Default.TrendingUp, Color(0xFF7B1FA2), Color(0xFFF3E5F5))
                    MetricCard("Time Slots", "11", Icons.Default.AccessTime, Color(0xFFE65100), Color(0xFFFFF3E0))
                }
            }

            // Tab Navigation
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F3F4)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                        NavTabIcon(Icons.Default.TrendingUp, isSelected = selectedTab == "analytics") { selectedTab = "analytics" }
                        NavTabIcon(Icons.Default.People, isSelected = selectedTab == "members") { selectedTab = "members" }
                        NavTabIcon(Icons.Default.Settings, isSelected = selectedTab == "settings") { selectedTab = "settings" }
                        NavTabIcon(Icons.Default.GridView, isSelected = selectedTab == "slots") { selectedTab = "slots" }
                        NavTabIcon(Icons.Default.FileUpload, isSelected = selectedTab == "upload") { selectedTab = "upload" }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // --- LOWER PART ---
            when (selectedTab) {
                "analytics" -> { item { AnalyticsSection() } }
                "members" -> {
                    item {
                        AddMemberForm(memberName, { memberName = it }, memberId, { memberId = it }, {
                            if (memberName.isNotBlank() && memberId.isNotBlank()) {
                                memberList = memberList + (memberName to memberId)
                                memberName = ""; memberId = ""
                            }
                        })
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    item { MemberListSection(memberList) }
                }
                "settings" -> { item { SettingsSection() } }
                "slots" -> { item { TimeSlotsSection(gymName, generatedGymId, gymLocation, gymCity) } }
                "upload" -> { item { DashboardUploadSection() } }
            }
        }
    }
}

@Composable
fun DashboardUploadSection() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Upload Past 2 Weeks Data", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "Keep your analytics updated by uploading recent booking history.", fontSize = 12.sp, color = Color.Gray)
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Format Preview
                Text(text = "📊 Excel File Format", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1BB85B))
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                    border = BorderStroke(1.dp, Color(0xFFEEEEEE))
                ) {
                    Row(modifier = Modifier.padding(8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Column { Text("date", fontWeight = FontWeight.Bold, fontSize = 10.sp); Text("2026-02-01", fontSize = 9.sp) }
                        Column { Text("timeSlot", fontWeight = FontWeight.Bold, fontSize = 10.sp); Text("18:00-19:00", fontSize = 9.sp) }
                        Column { Text("bookingCount", fontWeight = FontWeight.Bold, fontSize = 10.sp); Text("24", fontSize = 9.sp) }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))

                var fileName by remember { mutableStateOf<String?>(null) }
                
                Button(
                    onClick = { fileName = "weekly_data_" + System.currentTimeMillis() + ".xlsx" },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5), contentColor = Color(0xFF1BB85B)),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFDDDDDD))
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = if (fileName == null) "Tap to Upload Excel" else "✓ $fileName", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB85B)),
                    shape = RoundedCornerShape(8.dp),
                    enabled = fileName != null
                ) {
                    Text("Update Analytics")
                }
            }
        }
    }
}

@Composable
fun SettingsSection() {
    var morningOpening by remember { mutableStateOf("06:00") }
    var morningClosing by remember { mutableStateOf("11:00") }
    var eveningOpening by remember { mutableStateOf("16:00") }
    var eveningClosing by remember { mutableStateOf("21:00") }
    var publicHolidays by remember { mutableStateOf(listOf<String>()) }
    var morningOnlyDays by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GymOpeningHours(morningOpening, { morningOpening = it }, morningClosing, { morningClosing = it }, eveningOpening, { eveningOpening = it }, eveningClosing, { eveningClosing = it })
        PublicHolidaysSection(publicHolidays) { publicHolidays = publicHolidays + it }
        MorningOnlyDaysSection(morningOnlyDays) { morningOnlyDays = morningOnlyDays + it }
        CurrentConfigurationSummary(morningOpening, morningClosing, eveningOpening, eveningClosing, publicHolidays.size, morningOnlyDays.size)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymOpeningHours(mOpen: String, onMOpen: (String) -> Unit, mClose: String, onMClose: (String) -> Unit, eOpen: String, onEOpen: (String) -> Unit, eClose: String, onEClose: (String) -> Unit) {
    var showPicker by remember { mutableStateOf<String?>(null) }
    val timePickerState = rememberTimePickerState()
    if (showPicker != null) {
        AlertDialog(onDismissRequest = { showPicker = null }, confirmButton = { TextButton(onClick = { val formattedTime = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute); when(showPicker) { "mOpen" -> onMOpen(formattedTime); "mClose" -> onMClose(formattedTime); "eOpen" -> onEOpen(formattedTime); "eClose" -> onEClose(formattedTime) }; showPicker = null }) { Text("OK", color = Color(0xFF1BB85B)) } }, dismissButton = { TextButton(onClick = { showPicker = null }) { Text("Cancel", color = Color.Gray) } }, text = { TimePicker(state = timePickerState) })
    }
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Gym Opening Hours", fontSize = 16.sp, fontWeight = FontWeight.Bold); Text(text = "Configure gym operating hours for different sessions", fontSize = 12.sp, color = Color.Gray); Spacer(modifier = Modifier.height(16.dp))
            SessionCard("Morning Session", Icons.Outlined.LightMode, Color(0xFFE65100), Color(0xFFFFFDE7), mOpen, mClose, { showPicker = "mOpen" }, { showPicker = "mClose" })
            Spacer(modifier = Modifier.height(12.dp))
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)), shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, Color(0xFFEEEEEE))) { Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Schedule, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp)); Spacer(modifier = Modifier.width(8.dp)); Column { Text("Afternoon Session", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray); Text("(Optional)", fontSize = 12.sp, color = Color.Gray) } }; TextButton(onClick = { }) { Text("Enable", color = Color.Gray, fontSize = 14.sp) } } }
            Spacer(modifier = Modifier.height(12.dp))
            SessionCard("Evening Session", Icons.Outlined.ModeNight, Color(0xFF3F51B5), Color(0xFFE8EAF6), eOpen, eClose, { showPicker = "eOpen" }, { showPicker = "eClose" })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB85B)), shape = RoundedCornerShape(8.dp)) { Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp)); Spacer(modifier = Modifier.width(8.dp)); Text("Save Gym Hours") }
        }
    }
}

@Composable
fun SessionCard(title: String, icon: ImageVector, iconColor: Color, backgroundColor: Color, openingTime: String, closingTime: String, onOpenClick: () -> Unit, onCloseClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = backgroundColor), shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, iconColor.copy(alpha = 0.2f))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) { Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp)); Spacer(modifier = Modifier.width(8.dp)); Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = iconColor) }
            Spacer(modifier = Modifier.height(12.dp)); Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { TimeInputField("Opening Time", openingTime, onOpenClick, Modifier.weight(1f)); TimeInputField("Closing Time", closingTime, onCloseClick, Modifier.weight(1f)) }
            Spacer(modifier = Modifier.height(8.dp)); Text("Current: $openingTime - $closingTime", fontSize = 11.sp, color = iconColor, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun TimeInputField(label: String, value: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.clickable { onClick() }) { Text(label, fontSize = 11.sp, color = Color.Gray); Spacer(modifier = Modifier.height(4.dp)); Box(modifier = Modifier.fillMaxWidth().height(40.dp).background(Color.White, RoundedCornerShape(4.dp)).border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(4.dp)), contentAlignment = Alignment.CenterStart) { Text(text = value, modifier = Modifier.padding(horizontal = 12.dp), fontSize = 13.sp, color = Color.DarkGray) } }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicHolidaysSection(holidays: List<String>, onAddHoliday: (String) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    if (showDatePicker) { DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = { TextButton(onClick = { datePickerState.selectedDateMillis?.let { val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()); onAddHoliday(sdf.format(Date(it))) }; showDatePicker = false }) { Text("Add", color = Color(0xFF1BB85B)) } }, dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel", color = Color.Gray) } }) { DatePicker(state = datePickerState) } }
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Public Holidays", fontSize = 16.sp, fontWeight = FontWeight.Bold); Text(text = "Mark days when gym is closed", fontSize = 12.sp, color = Color.Gray); Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) { Box(modifier = Modifier.weight(1f).height(40.dp).background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp)).clickable { showDatePicker = true }, contentAlignment = Alignment.CenterStart) { Text("Select a date", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 12.dp)) }; Spacer(modifier = Modifier.width(8.dp)); Button(onClick = { showDatePicker = true }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)), shape = RoundedCornerShape(4.dp), modifier = Modifier.height(40.dp)) { Icon(Icons.Default.Block, contentDescription = null, modifier = Modifier.size(16.dp)); Spacer(modifier = Modifier.width(4.dp)); Text("Add", fontSize = 14.sp) } }
            if (holidays.isNotEmpty()) { Spacer(modifier = Modifier.height(12.dp)); holidays.forEach { holiday -> Text("• $holiday", fontSize = 13.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 2.dp)) } } else { Spacer(modifier = Modifier.height(16.dp)); Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { Text("No holidays configured", color = Color.Gray, fontSize = 12.sp) } }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorningOnlyDaysSection(morningDays: List<String>, onAddDay: (String) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    if (showDatePicker) { DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = { TextButton(onClick = { datePickerState.selectedDateMillis?.let { val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()); onAddDay(sdf.format(Date(it))) }; showDatePicker = false }) { Text("Add", color = Color(0xFF1BB85B)) } }, dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel", color = Color.Gray) } }) { DatePicker(state = datePickerState) } }
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Morning-Only Days", fontSize = 16.sp, fontWeight = FontWeight.Bold); Text(text = "Days with only morning session", fontSize = 12.sp, color = Color.Gray); Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) { Box(modifier = Modifier.weight(1f).height(40.dp).background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp)).clickable { showDatePicker = true }, contentAlignment = Alignment.CenterStart) { Text("Select a date", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 12.dp)) }; Spacer(modifier = Modifier.width(8.dp)); Button(onClick = { showDatePicker = true }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)), shape = RoundedCornerShape(4.dp), modifier = Modifier.height(40.dp)) { Icon(Icons.Default.WbSunny, contentDescription = null, modifier = Modifier.size(16.dp)); Spacer(modifier = Modifier.width(4.dp)); Text("Add", fontSize = 14.sp) } }
            if (morningDays.isNotEmpty()) { Spacer(modifier = Modifier.height(12.dp)); morningDays.forEach { day -> Text("• $day", fontSize = 13.sp, color = Color.DarkGray, modifier = Modifier.padding(vertical = 2.dp)) } } else { Spacer(modifier = Modifier.height(16.dp)); Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { Text("No morning-only days configured", color = Color.Gray, fontSize = 12.sp) } }
        }
    }
}

@Composable
fun CurrentConfigurationSummary(mOpen: String, mClose: String, eOpen: String, eClose: String, holidaysCount: Int, morningDaysCount: Int) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)), shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Current Configuration Summary", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20)); Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Column { SummaryItem("Morning:", "$mOpen - $mClose"); Spacer(modifier = Modifier.height(8.dp)); SummaryItem("Evening:", "$eOpen - $eClose") } }
            Spacer(modifier = Modifier.height(12.dp)); HorizontalDivider(color = Color(0xFF1BB85B).copy(alpha = 0.2f)); Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) { Column(modifier = Modifier.weight(1f)) { SummaryItem("Holidays:", "$holidaysCount day(s)") }; Column(modifier = Modifier.weight(1f)) { SummaryItem("Morning-Only:", "$morningDaysCount day(s)") } }
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String) { Column { Text(label, fontSize = 12.sp, color = Color(0xFF1B5E20), fontWeight = FontWeight.Medium); Text(value, fontSize = 12.sp, color = Color(0xFF2E7D32)) } }

@Composable
fun TimeSlotsSection(gymName: String, gymId: String, location: String, city: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) { GymInformationCard(gymName, gymId, location, city); AvailableEquipmentCard() }
}

@Composable
fun GymInformationCard(gymName: String, gymId: String, location: String, city: String) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = "Gym Information", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            GymInfoField("Gym Name", gymName); GymInfoField("Gym ID", gymId); GymInfoField("Location", location); GymInfoField("City", city)
            Column { Text("Status", fontSize = 12.sp, color = Color.Gray); Spacer(modifier = Modifier.height(4.dp)); Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(4.dp)) { Text(text = "active", modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), fontSize = 12.sp, color = Color(0xFF1BB85B), fontWeight = FontWeight.Medium) } }
        }
    }
}

@Composable
fun GymInfoField(label: String, value: String) {
    Column { Text(label, fontSize = 12.sp, color = Color.Gray); Spacer(modifier = Modifier.height(4.dp)); Box(modifier = Modifier.fillMaxWidth().height(40.dp).background(Color(0xFFF5F5F5), RoundedCornerShape(4.dp)), contentAlignment = Alignment.CenterStart) { Text(text = value, modifier = Modifier.padding(horizontal = 12.dp), fontSize = 14.sp, color = Color.DarkGray) } }
}

@Composable
fun AvailableEquipmentCard() {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Available Equipment", fontSize = 16.sp, fontWeight = FontWeight.Bold); Spacer(modifier = Modifier.height(16.dp))
            FlowRow(modifier = Modifier.fillMaxWidth(), mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp) { listOf("Treadmill", "Cycling", "Bench Press", "Lat Pulldown", "Leg Press", "Dumbbells").forEach { equipment -> Surface(border = BorderStroke(1.dp, Color(0xFFEEEEEE)), shape = RoundedCornerShape(8.dp), color = Color.White) { Text(text = equipment, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = Color.DarkGray) } } }
        }
    }
}

@Composable
fun FlowRow(modifier: Modifier = Modifier, mainAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp, crossAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp, content: @Composable () -> Unit) {
    androidx.compose.ui.layout.Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints.copy(minWidth = 0)) }
        val rows = mutableListOf<List<androidx.compose.ui.layout.Placeable>>()
        var currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var currentRowWidth = 0
        placeables.forEach { placeable -> if (currentRowWidth + placeable.width + mainAxisSpacing.roundToPx() > constraints.maxWidth && currentRow.isNotEmpty()) { rows.add(currentRow); currentRow = mutableListOf(); currentRowWidth = 0 }; currentRow.add(placeable); currentRowWidth += placeable.width + mainAxisSpacing.roundToPx() }
        rows.add(currentRow)
        val height = rows.sumOf { row -> row.maxOf { it.height } } + (rows.size - 1) * crossAxisSpacing.roundToPx()
        layout(constraints.maxWidth, height) { var y = 0; rows.forEach { row -> var x = 0; val rowHeight = row.maxOf { it.height }; row.forEach { placeable -> placeable.placeRelative(x, y); x += placeable.width + mainAxisSpacing.roundToPx() }; y += rowHeight + crossAxisSpacing.roundToPx() } }
    }
}

@Composable
fun AnalyticsSection() {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) { PeakHoursAnalysis(); BookingTrends(); PopularWorkouts() }
}

@Composable
fun PeakHoursAnalysis() {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Peak Hours Analysis", fontSize = 16.sp, fontWeight = FontWeight.Bold); Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth().height(150.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom) { val data = listOf(0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f); data.forEach { weight -> Box(modifier = Modifier.width(18.dp).fillMaxHeight(weight).background(Color(0xFF1BB85B), RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp))) } }
            Spacer(modifier = Modifier.height(8.dp)); Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("06:00-07:00", fontSize = 8.sp, color = Color.Gray); Text("08:00-09:00", fontSize = 8.sp, color = Color.Gray); Text("16:00-17:00", fontSize = 8.sp, color = Color.Gray); Text("18:00-19:00", fontSize = 8.sp, color = Color.Gray); Text("20:00-21:00", fontSize = 8.sp, color = Color.Gray) }
        }
    }
}

@Composable
fun BookingTrends() {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = "Booking Trends", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            TrendRow("This Week", "+12%", Color(0xFFE8F5E9), Color(0xFF1BB85B)); TrendRow("This Month", "+8%", Color(0xFFE8F5E9), Color(0xFF1BB85B))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Peak Time", fontSize = 14.sp, color = Color.Black); Text("18:00-19:00", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black) }
        }
    }
}

@Composable
fun TrendRow(label: String, percentage: String, bgColor: Color, textColor: Color) { Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { Text(label, fontSize = 14.sp, color = Color.Black); Surface(color = bgColor, shape = RoundedCornerShape(4.dp)) { Text(text = percentage, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 10.sp, color = textColor, fontWeight = FontWeight.Bold) } } }

@Composable
fun PopularWorkouts() {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(text = "Popular Workouts", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            WorkoutRow("Cardio + Chest", "45%"); WorkoutRow("Legs", "28%"); WorkoutRow("Full Body", "27%")
        }
    }
}

@Composable
fun WorkoutRow(name: String, percentage: String) { Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { Text(name, fontSize = 14.sp, color = Color.Black); Surface(border = BorderStroke(1.dp, Color(0xFFEEEEEE)), shape = RoundedCornerShape(4.dp)) { Text(text = percentage, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 10.sp, color = Color.Gray) } } }

@Composable
fun MetricCard(label: String, value: String, icon: ImageVector, iconColor: Color, backgroundColor: Color) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column { Text(text = label, fontSize = 12.sp, color = Color.Gray); Text(text = value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black) }
            Box(modifier = Modifier.size(50.dp).background(backgroundColor, CircleShape), contentAlignment = Alignment.Center) { Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp)) }
        }
    }
}

@Composable
fun NavTabIcon(icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) { Box(modifier = Modifier.background(if (isSelected) Color.White else Color.Transparent, shape = RoundedCornerShape(8.dp)).clickable { onClick() }.padding(horizontal = 16.dp, vertical = 8.dp), contentAlignment = Alignment.Center) { Icon(imageVector = icon, contentDescription = null, tint = if (isSelected) Color.Black else Color.Gray, modifier = Modifier.size(24.dp)) } }

@Composable
fun AddMemberForm(memberName: String, onNameChange: (String) -> Unit, memberId: String, onIdChange: (String) -> Unit, onAdd: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Add New Member", fontSize = 16.sp, fontWeight = FontWeight.Bold); Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Member Name", fontSize = 12.sp, fontWeight = FontWeight.Medium); OutlinedTextField(value = memberName, onValueChange = onNameChange, modifier = Modifier.fillMaxWidth().height(52.dp), placeholder = { Text("Enter member name", fontSize = 12.sp) }, colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color(0xFFEEEEEE), focusedBorderColor = Color(0xFF1BB85B)), shape = RoundedCornerShape(8.dp), singleLine = true)
            Spacer(modifier = Modifier.height(12.dp)); Text(text = "Member ID", fontSize = 12.sp, fontWeight = FontWeight.Medium); OutlinedTextField(value = memberId, onValueChange = onIdChange, modifier = Modifier.fillMaxWidth().height(52.dp), placeholder = { Text("Enter Member ID", fontSize = 12.sp) }, colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color(0xFFEEEEEE), focusedBorderColor = Color(0xFF1BB85B)), shape = RoundedCornerShape(8.dp), singleLine = true)
            Spacer(modifier = Modifier.height(16.dp)); Button(onClick = onAdd, modifier = Modifier.fillMaxWidth().height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB85B)), shape = RoundedCornerShape(8.dp)) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(18.dp)); Spacer(modifier = Modifier.width(8.dp)); Text("Add Member") } }
        }
    }
}

@Composable
fun MemberListSection(memberList: List<Pair<String, String>>) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Member List (${memberList.size})", fontSize = 16.sp, fontWeight = FontWeight.Bold); Spacer(modifier = Modifier.height(16.dp))
            if (memberList.isEmpty()) { Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) { Text("No members added yet", color = Color.Gray) } } else { memberList.forEach { (name, id) -> Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) { Box(modifier = Modifier.size(40.dp).background(Color(0xFFF5F5F5), CircleShape), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) }; Spacer(modifier = Modifier.width(12.dp)); Column { Text(text = name, fontWeight = FontWeight.Bold, fontSize = 14.sp); Text(text = id, fontSize = 12.sp, color = Color.Gray) } }; HorizontalDivider(color = Color(0xFFF5F5F5)) } }
        }
    }
}

@Composable
fun PlaceholderSection(text: String) { Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp)) { Box(modifier = Modifier.fillMaxWidth().padding(48.dp), contentAlignment = Alignment.Center) { Text(text, color = Color.Gray, fontWeight = FontWeight.Bold) } } }

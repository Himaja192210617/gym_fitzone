package com.simats.gym_fitzone.screens

import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import java.text.SimpleDateFormat
import com.simats.gym_fitzone.models.Slot
import com.simats.gym_fitzone.models.SlotInsightResponse
import com.simats.gym_fitzone.viewmodel.GymViewModel
import com.simats.gym_fitzone.viewmodel.SlotsState
import com.simats.gym_fitzone.viewmodel.SlotInsightState
import com.simats.gym_fitzone.viewmodel.BookingState
import android.widget.Toast

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookSlotScreen(
    gymName: String = "Select Gym",
    gymViewModel: GymViewModel? = null,
    userId: Int = -1,
    gymId: Int = -1,
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToBMI: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var currentPage by remember { mutableIntStateOf(1) }
    
    val initialDate = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }
    val backendDateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val displayDateFormatter = remember { SimpleDateFormat("MMMM d'th', yyyy", Locale.getDefault()) }
    
    var apiDate by remember { mutableStateOf(initialDate) }
    var displayDate by remember { mutableStateOf(displayDateFormatter.format(Date())) }
    
    var selectedSlot by remember { mutableStateOf<Slot?>(null) }
    var selectedDuration by remember { mutableStateOf("60") }
    val selectedSubWorkouts = remember { mutableStateListOf<String>() }

    val slotsState by gymViewModel?.slotsState?.collectAsState(initial = SlotsState.Idle) ?: remember { mutableStateOf(null) }
    val slotInsightState by gymViewModel?.slotInsightState?.collectAsState(initial = SlotInsightState.Idle) ?: remember { mutableStateOf(null) }
    val bookingState by gymViewModel?.bookingState?.collectAsState(initial = BookingState.Idle) ?: remember { mutableStateOf(null) }

    val context = LocalContext.current

    LaunchedEffect(apiDate, gymId) {
        if (gymId != -1) {
            gymViewModel?.getSlots(gymId, apiDate)
            selectedSlot = null
        }
    }

    LaunchedEffect(bookingState) {
        if (bookingState is BookingState.Success) {
            Toast.makeText(context, (bookingState as BookingState.Success).response.message, Toast.LENGTH_SHORT).show()
            gymViewModel?.resetBookingState()
            onConfirm()
        } else if (bookingState is BookingState.Error) {
            Toast.makeText(context, (bookingState as BookingState.Error).message, Toast.LENGTH_SHORT).show()
            gymViewModel?.resetBookingState()
        }
    }
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            apiDate = backendDateFormatter.format(calendar.time)
            displayDate = displayDateFormatter.format(calendar.time)
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
                    IconButton(onClick = {
                        if (currentPage == 2) currentPage = 1 else onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Column {
                        Text(
                            text = if (currentPage == 1) "Select Date & Slot" else "Workout Details",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "$gymName (Step $currentPage of 2)",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            AnimatedContent(targetState = currentPage, label = "PageTransition") { page ->
                if (page == 1) {
                    PageOne(
                        displayDate = displayDate,
                        onShowDatePicker = { datePickerDialog.show() },
                        selectedSlot = selectedSlot,
                        onSlotSelected = { slot ->
                            selectedSlot = slot
                            gymViewModel?.getSlotInsights(gymId, apiDate, slot.slot)
                        },
                        onNext = { currentPage = 2 },
                        slotsState = slotsState,
                        slotInsightState = slotInsightState
                    )
                } else {
                    PageTwo(
                        selectedSubWorkouts = selectedSubWorkouts,
                        selectedDuration = selectedDuration,
                        onDurationSelected = { selectedDuration = it },
                        onConfirm = {
                            if (userId != -1 && gymId != -1 && selectedSlot != null) {
                                // Dynamic category mapping logic
                                val map = mutableMapOf<String, MutableList<String>>()
                                selectedSubWorkouts.forEach { sub ->
                                    val catName = com.simats.gym_fitzone.models.WorkoutData.categories.find { it.subs.contains(sub) }?.name ?: "Other"
                                    if (!map.containsKey(catName)) {
                                        map[catName] = mutableListOf()
                                    }
                                    map[catName]?.add(sub)
                                }

                                gymViewModel?.confirmBooking(
                                    userId = userId,
                                    gymId = gymId,
                                    bookingDate = apiDate,
                                    timeSlot = selectedSlot!!.slot,
                                    workouts = map,
                                    durationMinutes = selectedDuration.toIntOrNull() ?: 60
                                ) { _, _ -> }
                            }
                        },
                        canConfirm = selectedSlot != null
                    )
                }
            }
        }
    }
}

@Composable
fun PageOne(
    displayDate: String,
    onShowDatePicker: () -> Unit,
    selectedSlot: Slot?,
    onSlotSelected: (Slot) -> Unit,
    onNext: () -> Unit,
    slotsState: SlotsState?,
    slotInsightState: SlotInsightState?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BookingCardSection(title = "Select Date") {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onShowDatePicker() },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = displayDate, fontSize = 14.sp)
                    }
                }
            }
        }

        item {
            BookingCardSection(title = "Select Time Slot") {
                if (slotsState is SlotsState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                } else if (slotsState is SlotsState.Success) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        slotsState.slots.forEach { slotInfo ->
                            ColorCodedSlotItem(
                                data = slotInfo,
                                isSelected = selectedSlot?.slot == slotInfo.slot,
                                onClick = { onSlotSelected(slotInfo) }
                            )
                        }
                    }
                } else {
                    Text("Select a date to view available slots.", color = Color.Gray)
                }
            }
        }

        if (selectedSlot != null && slotInsightState is SlotInsightState.Success) {
            item {
                SlotDetailsCard(slotInsightState.response, selectedSlot.slot)
            }
        } else if (slotInsightState is SlotInsightState.Loading) {
            item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
        }

        item {
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB85B)),
                shape = RoundedCornerShape(8.dp),
                enabled = selectedSlot != null
            ) {
                Text("Next: Workout Details", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun PageTwo(
    selectedSubWorkouts: MutableList<String>,
    selectedDuration: String,
    onDurationSelected: (String) -> Unit,
    onConfirm: () -> Unit,
    canConfirm: Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BookingCardSection(title = "Select Workout Types") {
                WorkoutCategoryExpandableList(selectedSubWorkouts)
            }
        }

        item {
            BookingCardSection(title = "Duration (minutes)") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("30", "60", "90", "120").forEach { duration ->
                        val isSelected = selectedDuration == duration
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onDurationSelected(duration) },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, if (isSelected) Color(0xFF1BB85B) else Color(0xFFEEEEEE)),
                            color = if (isSelected) Color(0xFFE8F5E9) else Color.White
                        ) {
                            Text(
                                text = duration,
                                modifier = Modifier.padding(vertical = 12.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = if (isSelected) Color(0xFF1BB85B) else Color.Black
                            )
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB85B)),
                shape = RoundedCornerShape(8.dp),
                enabled = canConfirm && selectedSubWorkouts.isNotEmpty()
            ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Confirm Booking", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ColorCodedSlotItem(data: Slot, isSelected: Boolean, onClick: () -> Unit) {
    val statusColor = when(data.color) {
        "red" -> Color(0xFFF44336)
        "yellow" -> Color(0xFFFFC107)
        "green" -> Color(0xFF4CAF50)
        else -> Color.Gray
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, if (isSelected) Color(0xFF1BB85B) else statusColor.copy(alpha = 0.3f)),
        color = if (isSelected) Color(0xFFE8F5E9) else Color.White
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(statusColor, RoundedCornerShape(6.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = data.slot,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color(0xFF1BB85B) else Color.Black
                )
            }
            if (isSelected) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF1BB85B))
            } else {
                Text(text = data.status, fontSize = 12.sp, color = statusColor, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SlotDetailsCard(insights: SlotInsightResponse, slotTime: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Slot Insights ($slotTime)",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.Black
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF0F0F0))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                InsightItem("Total", "${insights.total_bookings}", Icons.Default.Group)
                InsightItem("Combo", "${insights.combo_bookings.sumOf { it.count }}", Icons.Default.Merge)
                InsightItem("AI Pred.", "${insights.ai_prediction}", Icons.Default.AutoAwesome)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Combinational Breakup
            if (insights.combo_bookings.isNotEmpty()) {
                Text(text = "Combinational Bookings:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF2E7D32))
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    insights.combo_bookings.forEach { cb ->
                        SuggestionChip(
                            onClick = { },
                            label = { Text("${cb.combo} (${cb.count})", fontSize = 10.sp) },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color(0xFFF1F8E9)),
                            border = BorderStroke(1.dp, Color(0xFFC8E6C9))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Normal Breakup
            if (insights.separate_bookings.isNotEmpty()) {
                Text(text = "Normal Bookings:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    insights.separate_bookings.forEach { sb ->
                        SuggestionChip(
                            onClick = { },
                            label = { Text("${sb.workout} (${sb.count})", fontSize = 10.sp) },
                            colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color(0xFFF5F5F5)),
                            border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = insights.trend_message,
                fontSize = 11.sp,
                color = Color.Gray,
                lineHeight = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    content: @Composable FlowRowScope.() -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        content = content
    )
}

@Composable
fun InsightItem(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = Color(0xFF1BB85B), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = label, fontSize = 10.sp, color = Color.Gray)
    }
}

@Composable
fun BookingCardSection(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun WorkoutCategoryExpandableList(selectedSubWorkouts: MutableList<String>) {
    val categories = com.simats.gym_fitzone.models.WorkoutData.categories


    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        categories.forEach { category ->
            var isExpanded by remember { mutableStateOf(false) }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF8F9FA), RoundedCornerShape(8.dp))
                        .clickable { isExpanded = !isExpanded }
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = category.name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Icon(
                        if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Column(modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 8.dp)) {
                        category.subs.forEach { sub ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (selectedSubWorkouts.contains(sub)) selectedSubWorkouts.remove(sub)
                                        else selectedSubWorkouts.add(sub)
                                    }
                                    .padding(vertical = 4.dp)
                            ) {
                                Checkbox(
                                    checked = selectedSubWorkouts.contains(sub),
                                    onCheckedChange = null,
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1BB85B))
                                )
                                Text(text = sub, fontSize = 13.sp, modifier = Modifier.padding(start = 8.dp))
                            }
                        }
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color(0xFFF0F0F0))
            }
        }
    }
}

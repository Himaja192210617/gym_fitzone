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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WorkoutCategoriesScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToBook: () -> Unit = {},
    onNavigateToBMI: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("Cardio") }
    val tabs = listOf("Cardio", "Strength", "Functional", "HIIT")

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
                    BottomNavItem(icon = Icons.Default.FitnessCenter, label = "Workout", isSelected = true)
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
                    .padding(vertical = 32.dp, horizontal = 24.dp)
            ) {
                Column {
                    Text(
                        text = "Workout Categories",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Complete exercise guide",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color(0xFFECEFF1), RoundedCornerShape(24.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabs.forEach { tab ->
                    val isSelected = selectedTab == tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (isSelected) Color.White else Color.Transparent,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedTab = tab }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.Black else Color.Gray
                        )
                    }
                }
            }

            // Content
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    val (headerBg, icon, iconColor, title) = when (selectedTab) {
                        "Cardio" -> arrayOf(Color(0xFFFFF1F1), Icons.Default.FavoriteBorder, Color.Red, "Cardio Exercises")
                        "Strength" -> arrayOf(Color(0xFFE3F2FD), Icons.Default.FitnessCenter, Color(0xFF1976D2), "Strength Training")
                        "Functional" -> arrayOf(Color(0xFFF3E5F5), Icons.Default.RadioButtonChecked, Color(0xFF7B1FA2), "Functional Training")
                        else -> arrayOf(Color(0xFFFFF7F0), Icons.Default.LocalFireDepartment, Color(0xFFE65100), "HIIT Workouts")
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = headerBg as Color),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    icon as androidx.compose.ui.graphics.vector.ImageVector,
                                    contentDescription = null,
                                    tint = iconColor as Color,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = title as String,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                            }
                            if (selectedTab == "HIIT") {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "High-Intensity Interval Training - Short bursts of intense exercise followed by rest",
                                    fontSize = 14.sp,
                                    color = Color.DarkGray,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }

                when (selectedTab) {
                    "Cardio" -> {
                        items(cardioExercises) { exercise ->
                            ExerciseCard(exercise)
                        }
                    }
                    "Strength" -> {
                        strengthExercises.forEach { (category, exercises) ->
                            item {
                                Text(
                                    text = category,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                                )
                            }
                            items(exercises) { exercise ->
                                GenericExerciseCard(
                                    name = exercise.name,
                                    level = exercise.level,
                                    stats = listOf("Sets" to exercise.sets, "Reps" to exercise.reps)
                                )
                            }
                        }
                    }
                    "Functional" -> {
                        items(functionalExercises) { exercise ->
                            GenericExerciseCard(
                                name = exercise.name,
                                level = exercise.level,
                                stats = listOfNotNull(
                                    "Sets" to exercise.sets,
                                    exercise.reps?.let { "Reps" to it },
                                    exercise.duration?.let { "Duration" to it }
                                )
                            )
                        }
                    }
                    "HIIT" -> {
                        items(hiitExercises) { exercise ->
                            GenericExerciseCard(
                                name = exercise.name,
                                level = exercise.level,
                                stats = listOf(
                                    "Work Duration" to exercise.workDuration,
                                    "Rest" to exercise.rest
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Exercise(
    val name: String,
    val level: String,
    val duration: String,
    val calories: String
)

data class StrengthExercise(
    val name: String,
    val level: String,
    val sets: String,
    val reps: String
)

data class FunctionalExercise(
    val name: String,
    val level: String,
    val sets: String,
    val reps: String? = null,
    val duration: String? = null
)

data class HIITExercise(
    val name: String,
    val level: String,
    val workDuration: String,
    val rest: String
)

val cardioExercises = listOf(
    Exercise("Treadmill", "Beginner", "20-30 min", "200-300"),
    Exercise("Cycling", "Beginner", "30-45 min", "250-400"),
    Exercise("Rowing", "Intermediate", "15-25 min", "180-280"),
    Exercise("Stair Climber", "Intermediate", "15-20 min", "150-250"),
    Exercise("Skipping", "Beginner", "10-15 min", "120-200")
)

val strengthExercises = mapOf(
    "Chest" to listOf(
        StrengthExercise("Bench Press", "Intermediate", "3-4", "8-12"),
        StrengthExercise("Pec Deck", "Beginner", "3", "10-15"),
        StrengthExercise("Incline Press", "Intermediate", "3-4", "8-12")
    ),
    "Shoulders" to listOf(
        StrengthExercise("Shoulder Press", "Intermediate", "3-4", "8-12"),
        StrengthExercise("Lateral Raise", "Beginner", "3", "12-15"),
        StrengthExercise("Arnold Press", "Advanced", "3", "10-12")
    ),
    "Triceps" to listOf(
        StrengthExercise("Pushdown", "Beginner", "3", "12-15"),
        StrengthExercise("Skull Crushers", "Intermediate", "3", "10-12")
    ),
    "Back" to listOf(
        StrengthExercise("Lat Pulldown", "Beginner", "3-4", "10-12"),
        StrengthExercise("Deadlift", "Advanced", "3-4", "6-8")
    ),
    "Biceps" to listOf(
        StrengthExercise("Barbell Curl", "Beginner", "3", "10-12"),
        StrengthExercise("Hammer Curl", "Beginner", "3", "12-15")
    ),
    "Legs" to listOf(
        StrengthExercise("Squats", "Intermediate", "4", "8-12"),
        StrengthExercise("Leg Press", "Beginner", "3-4", "10-15")
    )
)

val functionalExercises = listOf(
    FunctionalExercise("Kettlebell Swings", "Intermediate", "3-4", reps = "15-20"),
    FunctionalExercise("Battle Ropes", "Advanced", "3-4", duration = "20-30 sec"),
    FunctionalExercise("TRX Rows", "Intermediate", "3", reps = "12-15"),
    FunctionalExercise("Box Jumps", "Advanced", "3", reps = "10-12")
)

val hiitExercises = listOf(
    HIITExercise("Burpees", "Advanced", "30 sec", "15 sec"),
    HIITExercise("Mountain Climbers", "Intermediate", "30 sec", "15 sec"),
    HIITExercise("Jump Squats", "Intermediate", "30 sec", "15 sec"),
    HIITExercise("High Knees", "Beginner", "30 sec", "15 sec")
)

@Composable
fun ExerciseCard(exercise: Exercise) {
    GenericExerciseCard(
        name = exercise.name,
        level = exercise.level,
        stats = listOf("Duration" to exercise.duration, "Calories Burned" to exercise.calories)
    )
}

@Composable
fun GenericExerciseCard(name: String, level: String, stats: List<Pair<String, String>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                LevelBadge(level)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                stats.forEach { (label, value) ->
                    StatItem(label, value, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun LevelBadge(level: String) {
    val (bgColor, textColor) = when (level) {
        "Beginner" -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
        "Intermediate" -> Color(0xFFFFF9C4) to Color(0xFFF9A825)
        "Advanced" -> Color(0xFFFFEBEE) to Color(0xFFC62828)
        else -> Color(0xFFECEFF1) to Color(0xFF455A64)
    }
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = level,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
fun StatItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(label, fontSize = 14.sp, color = Color.Gray)
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

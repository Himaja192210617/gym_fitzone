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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BMICalculatorScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToBook: () -> Unit = {},
    onNavigateToWorkout: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf<Double?>(null) }

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
                    BottomNavItem(icon = Icons.Default.MonitorHeart, label = "BMI", isSelected = true)
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
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF00893D)) // Darker green as per screenshot
                    .padding(vertical = 32.dp, horizontal = 24.dp)
            ) {
                Column {
                    Text(
                        text = "BMI Calculator",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Track your Body Mass Index",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Calculator Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Color(0xFFE8F5E9),
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = null,
                                tint = Color(0xFF1BB85B),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Calculate Your BMI",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(text = "Height (cm)", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = height,
                        onValueChange = { height = it },
                        placeholder = { Text("Enter height in centimeters") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF1F3F4),
                            focusedContainerColor = Color(0xFFF1F3F4),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Weight (kg)", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = weight,
                        onValueChange = { weight = it },
                        placeholder = { Text("Enter weight in kilograms") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF1F3F4),
                            focusedContainerColor = Color(0xFFF1F3F4),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val h = height.toDoubleOrNull()
                            val w = weight.toDoubleOrNull()
                            if (h != null && w != null && h > 0) {
                                bmiResult = w / ((h / 100.0) * (h / 100.0))
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B14F)), // Green button
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Calculate BMI", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            bmiResult?.let { bmi ->
                Spacer(modifier = Modifier.height(16.dp))
                BMIResultCard(bmi)
                Spacer(modifier = Modifier.height(16.dp))
                PersonalizedRecommendationCard(bmi)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BMI Categories Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "BMI Categories",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    BMICategoryRow("Underweight", "< 18.5", Color(0xFFE3F2FD), Color(0xFF1976D2))
                    Spacer(modifier = Modifier.height(8.dp))
                    BMICategoryRow("Normal Weight", "18.5 - 24.9", Color(0xFFE8F5E9), Color(0xFF2E7D32))
                    Spacer(modifier = Modifier.height(8.dp))
                    BMICategoryRow("Overweight", "25 - 29.9", Color(0xFFFFF9C4), Color(0xFFF9A825))
                    Spacer(modifier = Modifier.height(8.dp))
                    BMICategoryRow("Obese", "≥ 30", Color(0xFFFFEBEE), Color(0xFFC62828))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Disclaimer
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
            ) {
                Text(
                    text = "BMI is a screening tool and does not diagnose health or body fatness. Consult with healthcare professionals for personalized health advice.",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun BMIResultCard(bmi: Double) {
    val category = when {
        bmi < 18.5 -> "Underweight"
        bmi < 25 -> "Normal Weight"
        bmi < 30 -> "Overweight"
        else -> "Obese"
    }

    val categoryColor = when {
        bmi < 18.5 -> Color(0xFF1976D2)
        bmi < 25 -> Color(0xFF2E7D32)
        bmi < 30 -> Color(0xFFB8860B) // Darker golden/yellow for text
        else -> Color(0xFFC62828)
    }

    val categoryBg = when {
        bmi < 18.5 -> Color(0xFFE3F2FD)
        bmi < 25 -> Color(0xFFE8F5E9)
        bmi < 30 -> Color(0xFFFFF9C4)
        else -> Color(0xFFFFEBEE)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE8F5E9))
    ) {
        Column(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Your BMI", fontSize = 16.sp, color = Color.Gray)
            Text(
                text = String.format("%.1f", bmi),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = categoryColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = categoryBg,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, categoryColor.copy(alpha = 0.5f))
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = categoryColor
                )
            }
        }
    }
}

@Composable
fun PersonalizedRecommendationCard(bmi: Double) {
    val recommendation = when {
        bmi < 18.5 -> "Focus on nutrient-dense foods and strength training to build healthy muscle mass."
        bmi < 25 -> "Great job! Maintain your current balanced diet and regular exercise routine."
        bmi < 30 -> "Increase cardio exercises and focus on a balanced diet with portion control."
        else -> "Consult with a nutritionist for a tailored weight management plan and increase daily activity levels."
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8FF)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color(0xFFD0E8FF),
                shape = CircleShape,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Personalized Recommendation",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )
                Text(
                    text = recommendation,
                    fontSize = 14.sp,
                    color = Color(0xFF1976D2),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun BMICategoryRow(category: String, range: String, backgroundColor: Color, textColor: Color) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, textColor.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = 16.sp
            )
            Text(
                text = range,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}

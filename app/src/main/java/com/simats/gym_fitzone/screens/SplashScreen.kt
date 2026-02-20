package com.simats.gym_fitzone.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2000) // Show splash for 2 seconds
        onNavigateToHome()
    }

    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = tween(durationMillis = 1000),
        label = "scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Circle
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .scale(scale)
                    .alpha(alpha)
                    .background(Color(0xFF1BB85B), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                DumbbellIcon()
            }

            Spacer(modifier = Modifier.height(60.dp))

            // App Name
            Text(
                text = "FitZone",
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1BB85B),
                modifier = Modifier.alpha(alpha)
            )

            // Subtitle
            Text(
                text = "Smart Gym Management System",
                fontSize = 16.sp,
                color = Color(0xFF666666),
                modifier = Modifier.alpha(alpha)
            )

            Spacer(modifier = Modifier.height(80.dp))

            // Feature Icons
            Row(
                modifier = Modifier.alpha(alpha),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                FeatureIcon(
                    iconContent = "⚡",
                    label = "AI Powered"
                )
                FeatureIcon(
                    iconContent = "📈",
                    label = "Real-time Tracking"
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Dot Indicators
            Row(
                modifier = Modifier.alpha(alpha),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFF1BB85B), shape = CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun DumbbellIcon() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.size(100.dp)
    ) {
        Text(
            text = "💪",
            fontSize = 64.sp
        )
    }
}

@Composable
fun FeatureIcon(iconContent: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color(0xFF1BB85B).copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = iconContent, fontSize = 28.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF666666),
            fontWeight = FontWeight.Medium
        )
    }
}


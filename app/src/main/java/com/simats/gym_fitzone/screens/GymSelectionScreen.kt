package com.simats.gym_fitzone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Gym(
    val id: String,
    val name: String,
    val location: String,
    val address: String
)

@Composable
fun GymSelectionScreen(onGymSelected: (gym: Gym) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var memberId by remember { mutableStateOf("") }
    var selectedGym by remember { mutableStateOf<Gym?>(null) }

    val gyms = listOf(
        Gym("GYM001", "FitZone Premium Mumbai", "Andheri West, Mumbai", "Andheri West, Mumbai"),
        Gym("GYM002", "PowerFit Bangalore", "Koramangala, Bangalore", "Koramangala, Bangalore"),
        Gym("GYM003", "Elite Fitness Delhi", "Connaught Place, Delhi", "Connaught Place, Delhi"),
        Gym("GYM004", "FitHub Chennai", "T. Nagar, Chennai", "T. Nagar, Chennai")
    )

    val filteredGyms = gyms.filter { gym ->
        gym.name.contains(searchQuery, ignoreCase = true) ||
        gym.location.contains(searchQuery, ignoreCase = true)
    }

    val isFormValid = selectedGym != null && memberId.isNotEmpty()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Title
        item {
            Text(
                text = "Select Your Gym",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Search by City
        item {
            Text(
                text = "Search by City",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFFAAAAAA),
                    modifier = Modifier.padding(start = 8.dp)
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    placeholder = {
                        Text(
                            text = "Enter city name",
                            color = Color(0xFFAAAAAA),
                            fontSize = 14.sp
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                IconButton(onClick = { }) {
                    Text("📍", fontSize = 20.sp)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Available Gyms
        item {
            Text(
                text = "Available Gyms",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(filteredGyms.size) { index ->
            val gym = filteredGyms[index]
            GymCard(
                gym = gym,
                isSelected = selectedGym?.id == gym.id,
                onSelect = { selectedGym = gym }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Member ID Section
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Enter Your Member ID",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = memberId,
                onValueChange = { memberId = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp)),
                placeholder = {
                    Text(
                        text = "e.g., MEM001",
                        color = Color(0xFFAAAAAA),
                        fontSize = 14.sp
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Member ID should be provided by your gym",
                fontSize = 12.sp,
                color = Color(0xFFAAAAAA)
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Verify & Continue Button
        item {
            Button(
                onClick = {
                    selectedGym?.let { onGymSelected(it) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1BB85B),
                    disabledContainerColor = Color(0xFFCCCCCC)
                ),
                enabled = isFormValid
            ) {
                Text(
                    text = "Verify & Continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun GymCard(gym: Gym, isSelected: Boolean, onSelect: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) Color(0xFFE8F5E9) else Color(0xFFF5F5F5),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onSelect() }
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = gym.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color(0xFF1BB85B),
                            modifier = Modifier.height(16.dp)
                        )
                        Text(
                            text = gym.location,
                            fontSize = 12.sp,
                            color = Color(0xFF666666)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Gym ID: ${gym.id}",
                        fontSize = 12.sp,
                        color = Color(0xFFAAAAAA)
                    )
                }

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF1BB85B), shape = RoundedCornerShape(50.dp))
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "✓",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


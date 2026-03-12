package com.simats.gym_fitzone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.gym_fitzone.models.Gym
import com.simats.gym_fitzone.viewmodel.GymViewModel
import com.simats.gym_fitzone.viewmodel.ApiAuthViewModel
import com.simats.gym_fitzone.viewmodel.ApiAuthState
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import com.simats.gym_fitzone.utils.GymPreferencesManager

@Composable
fun GymSelectionScreen(
    gymViewModel: GymViewModel,
    apiAuthViewModel: ApiAuthViewModel,
    userId: Int,
    onGymSelected: (gym: Gym) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var memberId by remember { mutableStateOf("") }
    var selectedGym by remember { mutableStateOf<Gym?>(null) }
    val context = LocalContext.current
    val gyms by gymViewModel.availableGyms.collectAsState()
    val isLoading by gymViewModel.isGymLoading.collectAsState()
    val authState by apiAuthViewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        gymViewModel.fetchAvailableGyms()
    }

    val filteredGyms = gyms.filter { gym ->
        gym.gym_name.contains(searchQuery, ignoreCase = true) ||
        gym.city.contains(searchQuery, ignoreCase = true) ||
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

        if (isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF1BB85B))
                }
            }
        } else if (filteredGyms.isEmpty() && searchQuery.isNotEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = "No gyms found matching '$searchQuery'", color = Color.Gray)
                }
            }
        } else if (gyms.isEmpty() && !isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = "No registered gyms available in the database.", color = Color.Gray)
                }
            }
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
            NewGymCard(
                gym = gym,
                isSelected = selectedGym?.gym_id == gym.gym_id,
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
                    selectedGym?.let { gym ->
                        apiAuthViewModel.verifyMember(userId, gym.gym_id, memberId) { success, response ->
                            if (success && response != null) {
                                // Save gym selection using server-returned data for consistency
                                GymPreferencesManager.saveGymSelection(
                                    context = context,
                                    gymId = gym.gym_id,
                                    gymName = response.gym_name ?: gym.gym_name,
                                    memberId = memberId,
                                    gymLocation = response.gym_location ?: ""
                                )
                                onGymSelected(gym)
                            } else {
                                // Optionally handle error
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1BB85B),
                    disabledContainerColor = Color(0xFFCCCCCC)
                ),
                enabled = isFormValid && authState !is ApiAuthState.Loading
            ) {
                if (authState is ApiAuthState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = "Verify & Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun NewGymCard(gym: Gym, isSelected: Boolean, onSelect: () -> Unit) {
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
                        text = gym.gym_name,
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
                            text = "${gym.location}, ${gym.city}",
                            fontSize = 12.sp,
                            color = Color(0xFF666666)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Gym Code: ${gym.gym_code}",
                        fontSize = 12.sp,
                        color = Color(0xFFAAAAAA)
                    )
                }

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF1BB85B), shape = androidx.compose.foundation.shape.CircleShape)
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

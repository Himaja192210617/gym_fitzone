package com.simats.gym_fitzone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.simats.gym_fitzone.viewmodel.GymViewModel

@Composable
fun GymSetupScreen(
    onNextStep: (String, String, String, Int) -> Unit,
    gymViewModel: GymViewModel,
    adminUserId: Int
) {
    var gymName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    
    val gymSetupState by gymViewModel.gymSetupState.collectAsState()
    
    // Handle gym setup completion
    LaunchedEffect(gymSetupState) {
        when (gymSetupState) {
            is com.simats.gym_fitzone.viewmodel.GymSetupState.Success -> {
                val state = gymSetupState as com.simats.gym_fitzone.viewmodel.GymSetupState.Success
                onNextStep(gymName, city, address, state.response.gym_id)
                gymViewModel.resetGymSetupState()
            }
            else -> {}
        }
    }

    // Validation
    val isFormValid = gymName.isNotEmpty() && address.isNotEmpty() &&
                     city.isNotEmpty() && phoneNumber.isNotEmpty() &&
                     phoneNumber.length == 10 && email.isNotEmpty() &&
                     email.contains("@")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Step Indicator
        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Progress Circles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Step 1 - Active
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color(0xFF1BB85B), shape = CircleShape)
                )
                Spacer(modifier = Modifier.size(8.dp))

                // Divider
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(Color(0xFFCCCCCC))
                )
                Spacer(modifier = Modifier.size(8.dp))

                // Step 2 - Inactive
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color(0xFFDDDDDD), shape = CircleShape)
                )
                Spacer(modifier = Modifier.size(8.dp))

                // Divider
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(Color(0xFFCCCCCC))
                )
                Spacer(modifier = Modifier.size(8.dp))

                // Step 3 - Inactive
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color(0xFFDDDDDD), shape = CircleShape)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Header Icon
        item {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFF1BB85B), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🏢",
                    fontSize = 48.sp
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Title
        item {
            Text(
                text = "Gym Setup",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Subtitle
        item {
            Text(
                text = "Enter your gym details",
                fontSize = 14.sp,
                color = Color(0xFF999999)
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Gym Name Field
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Gym Name *",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = gymName,
                    onValueChange = { gymName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
                    placeholder = {
                        Text(
                            text = "Enter gym name",
                            color = Color(0xFFAAAAAA),
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Gym",
                            tint = Color(0xFFAAAAAA),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF1BB85B),
                        unfocusedIndicatorColor = Color(0xFFDDDDDD)
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Address Field
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Address *",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
                    placeholder = {
                        Text(
                            text = "Enter complete address",
                            color = Color(0xFFAAAAAA),
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Address",
                            tint = Color(0xFFAAAAAA),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF1BB85B),
                        unfocusedIndicatorColor = Color(0xFFDDDDDD)
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // City Field
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "City *",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
                    placeholder = {
                        Text(
                            text = "Enter city",
                            color = Color(0xFFAAAAAA),
                            fontSize = 14.sp
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF1BB85B),
                        unfocusedIndicatorColor = Color(0xFFDDDDDD)
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Phone Number Field
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Phone Number *",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { if (it.length <= 10) phoneNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
                    placeholder = {
                        Text(
                            text = "10-digit number",
                            color = Color(0xFFAAAAAA),
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Phone",
                            tint = Color(0xFFAAAAAA),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF1BB85B),
                        unfocusedIndicatorColor = Color(0xFFDDDDDD)
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Email Field
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Email",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
                    placeholder = {
                        Text(
                            text = "gym@example.com",
                            color = Color(0xFFAAAAAA),
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            tint = Color(0xFFAAAAAA),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF1BB85B),
                        unfocusedIndicatorColor = Color(0xFFDDDDDD)
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Description Field
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Description",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
                    placeholder = {
                        Text(
                            text = "Brief description about your gym",
                            color = Color(0xFFAAAAAA),
                            fontSize = 14.sp
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF1BB85B),
                        unfocusedIndicatorColor = Color(0xFFDDDDDD)
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Configure Hours Button
        item {
            Button(
                onClick = { 
                    if (isFormValid) {
                        gymViewModel.setupGym(
                            gymName = gymName,
                            address = address,
                            city = city,
                            phone = phoneNumber,
                            email = email,
                            description = description,
                            gymAdminId = adminUserId
                        ) { success, response ->
                            // Handle result in LaunchedEffect above
                        }
                    }
                },
                enabled = isFormValid && gymSetupState !is com.simats.gym_fitzone.viewmodel.GymSetupState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1BB85B),
                    disabledContainerColor = Color(0xFFCCCCCC)
                )
            ) {
                if (gymSetupState is com.simats.gym_fitzone.viewmodel.GymSetupState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Configure Hours",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

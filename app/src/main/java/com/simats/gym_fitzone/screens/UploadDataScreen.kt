package com.simats.gym_fitzone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.simats.gym_fitzone.viewmodel.GymViewModel
import com.simats.gym_fitzone.utils.FileUtils
import com.simats.gym_fitzone.viewmodel.UploadDataState

@Composable
fun UploadDataScreen(
    onBackClick: () -> Unit, 
    onCompleteUpload: () -> Unit,
    gymViewModel: GymViewModel,
    adminUserId: Int
) {
    val context = LocalContext.current
    var historicalBookingsUri by remember { mutableStateOf<Uri?>(null) }
    var gymMembersUri by remember { mutableStateOf<Uri?>(null) }
    
    val uploadState by gymViewModel.uploadState.collectAsState()

    val historicalLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        historicalBookingsUri = uri
    }

    val membersLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        gymMembersUri = uri
    }

    val isFormValid = historicalBookingsUri != null && gymMembersUri != null
    val isLoading = uploadState is UploadDataState.Loading

    fun handleUpload() {
        if (historicalBookingsUri == null || gymMembersUri == null) return

        val historicalPart = FileUtils.getMultipartPart(context, historicalBookingsUri!!, "file")
        val membersPart = FileUtils.getMultipartPart(context, gymMembersUri!!, "file")

        if (historicalPart == null || membersPart == null) {
            Toast.makeText(context, "Error processing files", Toast.LENGTH_SHORT).show()
            return
        }

        // Upload historical data first
        gymViewModel.uploadHistoricalData(adminUserId, historicalPart) { success, message ->
            if (success) {
                // Then upload gym members
                gymViewModel.uploadGymMembers(adminUserId, membersPart) { successMembers, messageMembers ->
                    if (successMembers) {
                        Toast.makeText(context, "Data uploaded successfully", Toast.LENGTH_SHORT).show()
                        onCompleteUpload()
                        gymViewModel.resetUploadState()
                    } else {
                        Toast.makeText(context, "Members upload failed: $messageMembers", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Historical data upload failed: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {
        // Header with Back Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF1BB85B)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Upload Data",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        // Step Indicator
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Step 1 - Completed
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color(0xFF1BB85B), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))

                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(Color(0xFF1BB85B))
                )
                Spacer(modifier = Modifier.size(8.dp))

                // Step 2 - Completed
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color(0xFF1BB85B), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))

                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(Color(0xFF1BB85B))
                )
                Spacer(modifier = Modifier.size(8.dp))

                // Step 3 - Active
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color(0xFF1BB85B), shape = CircleShape)
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
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFF1BB85B), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "📊",
                        fontSize = 48.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Title
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Upload Data",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Subtitle
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Upload historical data and gym members",
                    fontSize = 14.sp,
                    color = Color(0xFF999999)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Format Instructions Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, Color(0xFF1BB85B), shape = RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F9F5)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📋 Excel File Format Instructions",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1BB85B)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Historical Bookings Format
                    Column {
                        Text(
                            text = "📅 Historical Bookings Format:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                // Header Row
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(4.dp))
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("date", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                                    Text("slot", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                                    Text("bookingCount", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                // Example Row 1
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("2026-02-01", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                    Text("06:00-07:00", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                    Text("8", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                }

                                // Example Row 2
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("2026-02-01", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                    Text("18:00-19:00", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                    Text("15", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Gym Members Format
                    Column {
                        Text(
                            text = "👥 Gym Members Format:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                // Header Row
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFF0F8F4), shape = RoundedCornerShape(4.dp))
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("memberId", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                                    Text("name", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                                    Text("email", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                // Example Row 1
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("MEM001", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                    Text("John Doe", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                    Text("john@example.com", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                }

                                // Example Row 2
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("MEM002", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                    Text("Jane Smith", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                    Text("jane@example.com", fontSize = 10.sp, modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "* email column is optional",
                        fontSize = 10.sp,
                        color = Color(0xFF999999),
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Historical Bookings Upload
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "📅 Historical Bookings (Past 2 Weeks) *",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Upload Excel file with booking data for AI crowd prediction training",
                        fontSize = 12.sp,
                        color = Color(0xFF999999)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Upload Button
                    Button(
                        onClick = { historicalLauncher.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF5F5F5),
                            contentColor = Color(0xFF1BB85B)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !isLoading
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "⬆️", fontSize = 32.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (historicalBookingsUri == null) "Click to upload Excel file" else "✓ File Selected",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1BB85B)
                            )
                            if (historicalBookingsUri != null) {
                                Text(
                                    text = historicalBookingsUri!!.path?.substringAfterLast("/") ?: "",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Supports .xlsx, .xls, .csv",
                        fontSize = 11.sp,
                        color = Color(0xFF999999),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Gym Members Upload
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "👥 Gym Members List *",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Upload Excel file with all current gym members and their IDs",
                        fontSize = 12.sp,
                        color = Color(0xFF999999)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Upload Button
                    Button(
                        onClick = { membersLauncher.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF5F5F5),
                            contentColor = Color(0xFF1BB85B)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !isLoading
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "⬆️", fontSize = 32.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (gymMembersUri == null) "Click to upload Excel file" else "✓ File Selected",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1BB85B)
                            )
                            if (gymMembersUri != null) {
                                Text(
                                    text = gymMembersUri!!.path?.substringAfterLast("/") ?: "",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Supports .xlsx, .xls, .csv",
                        fontSize = 11.sp,
                        color = Color(0xFF999999),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Action Buttons
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Back Button
                Button(
                    onClick = { onBackClick() },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF0F0F0),
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Back",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Next Button
                Button(
                    onClick = { handleUpload() },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1BB85B),
                        disabledContainerColor = Color(0xFFCCCCCC)
                    ),
                    enabled = isFormValid && !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Next: Set Capacity",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}




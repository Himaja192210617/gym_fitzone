package com.simats.gym_fitzone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.simats.gym_fitzone.database.AppDatabase
import com.simats.gym_fitzone.screens.*
import com.simats.gym_fitzone.ui.theme.Gym_fitzoneTheme
import com.simats.gym_fitzone.utils.GymPreferencesManager
import com.simats.gym_fitzone.viewmodel.*
import com.simats.gym_fitzone.api.ServiceDiscoveryManager
import com.simats.gym_fitzone.api.RetrofitClient
import androidx.compose.runtime.LaunchedEffect
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Gym_fitzoneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val gymName = remember { mutableStateOf(GymPreferencesManager.getSavedGymName(context).ifEmpty { "Select Gym" }) }
    val userName = remember { mutableStateOf(GymPreferencesManager.getSavedUserName(context).ifEmpty { "User" }) }
    val loggedInUserId = remember { mutableIntStateOf(GymPreferencesManager.getSavedUserId(context)) }
    val currentGymId = remember { mutableIntStateOf(GymPreferencesManager.getSavedGymId(context)) }
    val gymLocation = remember { mutableStateOf("") }
    val gymCity = remember { mutableStateOf("") }
    
    val database = remember { AppDatabase.getDatabase(context) }
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(database.userDao())
    )
    val apiAuthViewModel: ApiAuthViewModel = viewModel()
    val gymViewModel: GymViewModel = viewModel()

    val discoveryManager = remember {
        ServiceDiscoveryManager(context) { newUrl ->
            RetrofitClient.updateBaseUrl(newUrl)
        }
    }

    LaunchedEffect(Unit) {
        discoveryManager.startDiscovery()
        
        RetrofitClient.onUrlUpdated = { newUrl ->
            // UI update must be on Main thread
            val message = "Backend Detected: $newUrl"
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        composable("login") {
            LoginScreen(
                apiAuthViewModel = apiAuthViewModel,
                onLoginSuccess = { response ->
                    loggedInUserId.intValue = response.user_id
                    GymPreferencesManager.saveUserId(context, response.user_id)
                    response.name?.let { 
                        userName.value = it 
                        GymPreferencesManager.saveUserName(context, it)
                    }
                    if (response.email != null && response.mobile != null && response.age != null && response.gender != null) {
                        GymPreferencesManager.saveUserProfile(context, response.email, response.mobile, response.age, response.gender)
                    }
                    response.gym_name?.let { gymName.value = it }
                    if (!response.gym_location.isNullOrEmpty()) {
                        GymPreferencesManager.saveGymSelection(
                            context, 
                            response.gym_id ?: -1, 
                            response.gym_name ?: "", 
                            "", 
                            response.gym_location
                        )
                    }
                    
                    when (response.role) {
                        "Super Admin" -> {
                            navController.navigate("super_admin_dashboard") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                        "gym_administrator", "Gym Administrator" -> {
                            if (response.next_page == "setup_gym") {
                                navController.navigate("gym_setup") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                // For administrators, we should also track their gym name
                                response.gym_name?.let { gymName.value = it }
                                navController.navigate("gym_owner_dashboard") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        }
                        "gym_user", "Gym User" -> {
                            // Clear any stale gym selection from previous device use
                            GymPreferencesManager.clearGymSelection(context)
                            
                            // If the server tells us this user already has a gym assigned
                            if (response.gym_name != null && response.gym_id != null && response.gym_id != -1) {
                                // Sync it to local storage for persistence
                                gymName.value = response.gym_name
                                currentGymId.intValue = response.gym_id
                                GymPreferencesManager.saveGymSelection(
                                    context, 
                                    response.gym_id, 
                                    response.gym_name, 
                                    response.member_id ?: "", 
                                    response.gym_location ?: ""
                                )
                                
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                // No gym assigned yet, take them to selection
                                navController.navigate("gym_selection") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        }
                        else -> {
                            navController.navigate("gym_selection") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                },
                onForgotPasswordClick = {
                    navController.navigate("forgot_password")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                apiAuthViewModel = apiAuthViewModel,
                onRegisterSuccess = { response ->
                    loggedInUserId.intValue = response.user_id
                    GymPreferencesManager.saveUserId(context, response.user_id)
                    response.name?.let { 
                        userName.value = it 
                        GymPreferencesManager.saveUserName(context, it)
                    }
                    if (response.email != null && response.mobile != null && response.age != null && response.gender != null) {
                        GymPreferencesManager.saveUserProfile(context, response.email, response.mobile, response.age, response.gender)
                    }
                    // Handle successful registration
                    when (response.role) {
                        "gym_administrator", "Gym Administrator" -> {
                            navController.navigate("gym_setup") {
                                popUpTo("register") { inclusive = true }
                            }
                        }
                        "gym_user", "Gym User" -> {
                            navController.navigate("gym_selection") {
                                popUpTo("register") { inclusive = true }
                            }
                        }
                        else -> {
                            navController.navigate("login") {
                                popUpTo("register") { inclusive = true }
                            }
                        }
                    }
                },
                onBackToLogin = {
                    navController.navigateUp()
                }
            )
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                apiAuthViewModel = apiAuthViewModel,
                onBackToLogin = {
                    navController.navigateUp()
                },
                onSendOTP = { email ->
                    navController.navigate("verify_otp/$email")
                }
            )
        }
        composable(
            route = "verify_otp/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerifyOTPScreen(
                apiAuthViewModel = apiAuthViewModel,
                email = email,
                onResetPassword = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBack = {
                    navController.navigateUp()
                }
            )
        }
        composable("super_admin_dashboard") {
            SuperAdminDashboardScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("super_admin_dashboard") { inclusive = true }
                    }
                }
            )
        }
        composable("gym_selection") {
            GymSelectionScreen(
                gymViewModel = gymViewModel,
                apiAuthViewModel = apiAuthViewModel,
                userId = loggedInUserId.intValue,
                onGymSelected = { gym ->
                    currentGymId.intValue = gym.gym_id
                    gymName.value = gym.gym_name

                    navController.navigate("home") {
                        popUpTo("gym_selection") { inclusive = true }
                    }
                }
            )
        }
        composable("gym_setup") {
            GymSetupScreen(
                onNextStep = { name, location, city, gymId ->
                    gymName.value = name
                    gymLocation.value = location
                    gymCity.value = city
                    currentGymId.intValue = gymId
                    navController.navigate("configure_hours") {
                        popUpTo("gym_setup") { inclusive = false }
                    }
                },
                gymViewModel = gymViewModel,
                adminUserId = loggedInUserId.intValue
            )
        }
        composable("configure_hours") {
            ConfigureHoursScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onCompleteSetup = {
                    navController.navigate("upload_data") {
                        popUpTo("configure_hours") { inclusive = false }
                    }
                },
                gymViewModel = gymViewModel,
                gymId = currentGymId.intValue
            )
        }
        composable("upload_data") {
            UploadDataScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onCompleteUpload = {
                    navController.navigate("set_capacity") {
                        popUpTo("upload_data") { inclusive = false }
                    }
                },
                gymViewModel = gymViewModel,
                adminUserId = loggedInUserId.intValue
            )
        }
        composable("set_capacity") {
            SetCapacityScreen(
                gymViewModel = gymViewModel,
                adminUserId = loggedInUserId.intValue,
                onBackClick = {
                    navController.navigateUp()
                },
                onCompleteSetup = {
                    navController.navigate("gym_owner_dashboard") {
                        popUpTo("set_capacity") { inclusive = true }
                    }
                }
            )
        }
        composable("gym_owner_dashboard") {
            GymOwnerDashboardScreen(
                gymName = gymName.value,
                gymLocation = gymLocation.value,
                gymCity = gymCity.value,
                gymViewModel = gymViewModel,
                adminUserId = loggedInUserId.intValue,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("gym_owner_dashboard") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(
                userName = userName.value,
                gymName = gymName.value,
                onLogout = {
                    GymPreferencesManager.clearGymSelection(context)
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onNavigateToBook = {
                    navController.navigate("book_slot")
                },
                onNavigateToWorkout = {
                    navController.navigate("workout_categories")
                },
                onNavigateToBMI = {
                    navController.navigate("bmi_calculator")
                },
                onNavigateToHistory = {
                    navController.navigate("booking_history")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                }
            )
        }
        composable("book_slot") {
            BookSlotScreen(
                gymName = gymName.value,
                gymViewModel = gymViewModel,
                userId = loggedInUserId.intValue,
                gymId = currentGymId.intValue,
                onBack = { navController.navigateUp() },
                onConfirm = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToWorkout = { navController.navigate("workout_categories") },
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToBMI = { navController.navigate("bmi_calculator") },
                onNavigateToHistory = { navController.navigate("booking_history") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        composable("workout_categories") {
            WorkoutCategoriesScreen(
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToBook = { navController.navigate("book_slot") },
                onNavigateToBMI = { navController.navigate("bmi_calculator") },
                onNavigateToHistory = { navController.navigate("booking_history") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        composable("bmi_calculator") {
            BMICalculatorScreen(
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToBook = { navController.navigate("book_slot") },
                onNavigateToWorkout = { navController.navigate("workout_categories") },
                onNavigateToHistory = { navController.navigate("booking_history") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        composable("booking_history") {
            BookingHistoryScreen(
                gymViewModel = gymViewModel,
                userId = loggedInUserId.intValue,
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToBook = { navController.navigate("book_slot") },
                onNavigateToWorkout = { navController.navigate("workout_categories") },
                onNavigateToBMI = { navController.navigate("bmi_calculator") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        composable("profile") {
            val profileState by apiAuthViewModel.profileState.collectAsState()
            
            LaunchedEffect(Unit) {
                if (loggedInUserId.intValue != -1) {
                    apiAuthViewModel.getUserProfile(loggedInUserId.intValue)
                }
            }

            val profile = (profileState as? ProfileState.Success)?.profile

            ProfileScreen(
                userName = profile?.name ?: userName.value,
                userEmail = profile?.email ?: GymPreferencesManager.getSavedUserEmail(context),
                userMobile = profile?.mobile ?: GymPreferencesManager.getSavedUserMobile(context),
                userAge = (profile?.age ?: GymPreferencesManager.getSavedUserAge(context)).toString(),
                userGender = profile?.gender ?: GymPreferencesManager.getSavedUserGender(context),
                gymName = profile?.gym?.gym_name ?: gymName.value,
                gymLocation = profile?.gym?.location ?: GymPreferencesManager.getSavedGymLocation(context),
                memberId = profile?.gym?.member_id?.takeIf { it.isNotBlank() } ?: GymPreferencesManager.getSavedMemberId(context),
                totalBookings = (profile?.stats?.total_bookings ?: 0).toString(),
                activeBookings = (profile?.stats?.active_bookings ?: 0).toString(),
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToBook = { navController.navigate("book_slot") },
                onNavigateToWorkout = { navController.navigate("workout_categories") },
                onNavigateToBMI = { navController.navigate("bmi_calculator") },
                onNavigateToHistory = { navController.navigate("booking_history") },
                onNavigateToEditProfile = { navController.navigate("edit_profile") },
                onLogout = {
                    GymPreferencesManager.clearGymSelection(context)
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
        composable("edit_profile") {
            EditProfileScreen(
                currentName = userName.value,
                currentEmail = GymPreferencesManager.getSavedUserEmail(context),
                currentPhone = GymPreferencesManager.getSavedUserMobile(context),
                currentAge = GymPreferencesManager.getSavedUserAge(context).toString(),
                currentGender = GymPreferencesManager.getSavedUserGender(context),
                onBack = { navController.navigateUp() },
                onSave = { newName, newEmail, newPhone, newAge, newGender ->
                    userName.value = newName
                    GymPreferencesManager.saveUserName(context, newName)
                    GymPreferencesManager.saveUserProfile(
                        context,
                        newEmail,
                        newPhone,
                        newAge.toIntOrNull() ?: 0,
                        newGender
                    )
                    navController.navigateUp()
                }
            )
        }
    }
}

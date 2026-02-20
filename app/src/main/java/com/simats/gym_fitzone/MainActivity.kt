package com.simats.gym_fitzone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simats.gym_fitzone.screens.*
import com.simats.gym_fitzone.ui.theme.Gym_fitzoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Gym_fitzoneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val gymName = remember { mutableStateOf("FitZone Premium Mumbai") }
    val gymLocation = remember { mutableStateOf("Not Specified") }
    val gymCity = remember { mutableStateOf("Not Specified") }

    NavHost(
        navController = navController,
        startDestination = "splash"
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
                onLoginSuccess = {
                    navController.navigate("gym_selection") {
                        popUpTo("login") { inclusive = true }
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
                onRegisterSuccess = {
                    navController.navigate("gym_selection") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.navigateUp()
                },
                onGymUserSelected = {
                    navController.navigate("gym_selection") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onGymAdminSelected = {
                    navController.navigate("gym_setup") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                onBackToLogin = {
                    navController.navigateUp()
                },
                onSendResetLink = { email ->
                    navController.navigateUp()
                }
            )
        }
        composable("gym_selection") {
            GymSelectionScreen(
                onGymSelected = { gym ->
                    navController.navigate("home") {
                        popUpTo("gym_selection") { inclusive = true }
                    }
                }
            )
        }
        composable("gym_setup") {
            GymSetupScreen(
                onNextStep = { name, location, city ->
                    gymName.value = name
                    gymLocation.value = location
                    gymCity.value = city
                    navController.navigate("configure_hours") {
                        popUpTo("gym_setup") { inclusive = false }
                    }
                }
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
                }
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
                }
            )
        }
        composable("set_capacity") {
            SetCapacityScreen(
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
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("gym_owner_dashboard") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(
                gymName = gymName.value,
                onLogout = {
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
                }
            )
        }
        composable("book_slot") {
            BookSlotScreen(
                gymName = gymName.value,
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
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToBook = { navController.navigate("book_slot") },
                onNavigateToWorkout = { navController.navigate("workout_categories") },
                onNavigateToBMI = { navController.navigate("bmi_calculator") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateToHome = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                onNavigateToBook = { navController.navigate("book_slot") },
                onNavigateToWorkout = { navController.navigate("workout_categories") },
                onNavigateToBMI = { navController.navigate("bmi_calculator") },
                onNavigateToHistory = { navController.navigate("booking_history") },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}

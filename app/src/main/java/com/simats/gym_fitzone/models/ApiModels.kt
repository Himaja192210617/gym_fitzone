package com.simats.gym_fitzone.models

// Updated existing models to match backend responses
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val user_id: Int,
    val role: String,
    val next_page: String,
    val name: String? = null,
    val email: String? = null,
    val mobile: String? = null,
    val age: Int? = null,
    val gender: String? = null,
    val gym_id: Int? = null,
    val gym_name: String? = null,
    val gym_location: String? = null,
    val member_id: String? = null
)

data class RegisterRequest(
    val name: String,
    val age: Int,
    val gender: String,
    val email: String,
    val mobile: String,
    val password: String,
    val role: String = "gym_user"
)

data class RegisterResponse(
    val message: String,
    val user_id: Int,
    val role: String,
    val next_page: String,
    val name: String? = null,
    val email: String? = null,
    val mobile: String? = null,
    val age: Int? = null,
    val gender: String? = null
)

data class Gym(
    val gym_id: Int,
    val gym_name: String,
    val city: String,
    val location: String,
    val gym_code: String = ""
)

data class VerifyMemberRequest(
    val user_id: Int,
    val gym_id: Int,
    val member_id: String
)

data class VerifyMemberResponse(
    val message: String,
    val next_page: String,
    val gym_name: String? = null,
    val gym_location: String? = null
)

data class SlotRequest(
    val gym_id: Int,
    val date: String
)

data class Slot(
    val slot: String,
    val booked: Int,
    val capacity: Int,
    val status: String,
    val color: String
)

data class SlotInsightRequest(
    val gym_id: Int,
    val date: String,
    val slot: String
)

data class SlotInsightResponse(
    val total_bookings: Int,
    val combo_bookings: List<ComboBooking>,
    val separate_bookings: List<SeparateBooking>,
    val ai_prediction: Int,
    val trend_message: String
)

data class ComboBooking(
    val combo: String,
    val count: Int
)

data class SeparateBooking(
    val workout: String,
    val count: Int
)

data class BookingRequest(
    val user_id: Int,
    val gym_id: Int,
    val booking_date: String,
    val time_slot: String,
    val workouts: Map<String, List<String>>,
    val duration_minutes: Int
)

data class BookingResponse(
    val message: String,
    val summary: String = "",
    val details: String = ""
)

data class BookingHistory(
    val booking_id: Int,
    val date: String,
    val slot: String,
    val summary: String,
    val details: String,
    val duration_minutes: Int,
    val status: String,
    val cancel_allowed: Boolean
)

data class ProfileResponse(
    val name: String,
    val role: String,
    val email: String,
    val mobile: String,
    val age: Int,
    val gender: String,
    val gym: GymInfo,
    val stats: UserStats
)

data class GymInfo(
    val gym_id: Int,
    val gym_name: String,
    val location: String,
    val city: String,
    val member_id: String?
)

data class UserStats(
    val total_bookings: Int,
    val active_bookings: Int
)

// Gym Setup Models
data class GymSetupRequest(
    val gym_name: String,
    val address: String,
    val city: String,
    val phone: String,
    val email: String,
    val description: String,
    val gym_admin_id: Int
)

data class GymSetupResponse(
    val message: String,
    val next_step: String,
    val gym_id: Int = 0
)

data class ConfigureHoursRequest(
    val gym_id: Int,
    val sessions: List<GymSession>
)

data class GymSession(
    val session_type: String,
    val open_time: String,
    val close_time: String
)

data class ConfigureHoursResponse(
    val message: String,
    val next_step: String
)

data class SetCapacityRequest(
    val admin_user_id: Int,
    val capacity: Int
)

data class SetCapacityResponse(
    val message: String
)

data class UploadResponse(
    val message: String
)

// Gym Owner Dashboard Models
data class GymDashboardRequest(
    val admin_user_id: Int
)

data class GymDashboardResponse(
    val gym_id: Int? = null,
    val gym_name: String? = null,
    val location: String? = null,
    val city: String? = null,
    val admin_name: String? = null,
    val total_members: Int,
    val todays_bookings: Int,
    val total_bookings: Int,
    val time_slots: Int,
    val peak_time: String,
    val weekly_growth: String,
    val monthly_growth: String,
    val popular_workouts: List<PopularWorkout> = emptyList(),
    val peak_hours: List<PeakHour> = emptyList(),
    val public_holidays: List<String> = emptyList(),
    val morning_only_days: List<String> = emptyList()
)

data class PopularWorkout(
    val workout: String,
    val percentage: String
)

data class PeakHour(
    val time_slot: String,
    val weight: Float
)

data class GetMembersRequest(
    val admin_user_id: Int
)

data class GetMembersResponse(
    val total_members: Int,
    val members: List<GymMember>
)

data class GymMember(
    val member_id: String,
    val name: String,
    val email: String? = null,
    val mobile: String? = null,
    val age: String? = null,
    val gender: String? = null
)

data class AddMemberRequest(
    val admin_user_id: Int,
    val member_id: String,
    val name: String
)

data class AddMemberResponse(
    val message: String
)

data class UpdateGymHoursRequest(
    val admin_user_id: Int,
    val sessions: List<GymSessionUpdate>
)

data class GymSessionUpdate(
    val session_name: String,
    val opening_time: String,
    val closing_time: String
)

data class UpdateGymHoursResponse(
    val message: String
)

data class AddHolidayRequest(
    val admin_user_id: Int,
    val holiday_date: String
)

data class AddHolidayResponse(
    val message: String
)

data class AddMorningOnlyRequest(
    val admin_user_id: Int,
    val special_date: String
)

data class AddMorningOnlyResponse(
    val message: String
)

data class GetGymInfoRequest(
    val admin_user_id: Int
)

data class GetGymInfoResponse(
    val gym_id: Int,
    val gym_name: String,
    val location: String,
    val city: String,
    val status: String
)

// Password Reset Models
data class ForgotPasswordRequest(
    val email: String
)

data class ForgotPasswordResponse(
    val message: String
)

data class ResetPasswordRequest(
    val email: String,
    val otp: String,
    val password: String
)

data class ResetPasswordResponse(
    val message: String
)

// Super Admin Models
data class AdminLoginRequest(
    val email: String,
    val password: String
)

data class AdminLoginResponse(
    val message: String,
    val next_page: String
)

data class AdminTotalGymsResponse(
    val total_gyms: Int
)

data class AdminTotalMembersResponse(
    val total_members: Int
)

data class AdminTotalBookingsResponse(
    val total_bookings: Int
)

data class AdminGym(
    val gym_id: Int,
    val gym_name: String,
    val city: String,
    val members: Int,
    val status: String
)

data class SuspendGymRequest(
    val gym_id: Int
)

data class SuspendGymResponse(
    val message: String
)

data class AdminSystemStatusResponse(
    val api_status: String,
    val ai_models: String,
    val database: String
)

data class AdminGrowthResponse(
    val gym_growth: String,
    val member_growth: String,
    val booking_growth: String
)

data class AdminActivity(
    val activity: String,
    val time: String
)

// Additional User Models
data class UserHomeResponse(
    val user_name: String,
    val gym_name: String,
    val current_crowd_level: String,
    val next_available_slot: String,
    val peak_slot: String,
    val peak_message: String
)

data class CancelBookingRequest(
    val booking_id: Int,
    val user_id: Int
)

data class CancelBookingResponse(
    val message: String
)

data class WorkoutCategoryWrapper(val name: String, val subs: List<String>)

object WorkoutData {
    val categories = listOf(
        WorkoutCategoryWrapper("Cardio", listOf("Treadmill", "Cycling", "Rowing", "Stair Climber", "Skipping")),
        WorkoutCategoryWrapper("Chest", listOf("Bench Press", "Pec Deck", "Incline Press")),
        WorkoutCategoryWrapper("Shoulder", listOf("Shoulder Press", "Lateral Raise", "Arnold Press")),
        WorkoutCategoryWrapper("Triceps", listOf("Pushdown", "Skull Crushers")),
        WorkoutCategoryWrapper("Back", listOf("Lat Pulldown", "Deadlift")),
        WorkoutCategoryWrapper("Biceps", listOf("Barbell Curl", "Hammer Curl")),
        WorkoutCategoryWrapper("Legs", listOf("Squats", "Leg Press")),
        WorkoutCategoryWrapper("Functional Training", listOf("Kettlebell Swings", "Battle Ropes", "TRX", "Box Jumps")),
        WorkoutCategoryWrapper("Yoga", listOf("Yoga Mat")),
        WorkoutCategoryWrapper("HIIT", listOf("HIIT Zone"))
    )
}
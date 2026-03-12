package com.simats.gym_fitzone.api

import com.simats.gym_fitzone.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET("ping")
    suspend fun ping(): Map<String, String>

    @POST("login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): LoginResponse


    @POST("register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): RegisterResponse


    @GET("get-registered-gyms")
    suspend fun getGyms(): List<Gym>

    @POST("verify-member")
    suspend fun verifyMember(
        @Body request: VerifyMemberRequest
    ): VerifyMemberResponse


    @POST("get-slots")
    suspend fun getSlots(
        @Body request: SlotRequest
    ): List<Slot>


    @POST("slot-insights")
    suspend fun slotInsights(
        @Body request: SlotInsightRequest
    ): SlotInsightResponse


    @POST("confirm-booking")
    suspend fun confirmBooking(
        @Body request: BookingRequest
    ): BookingResponse


    @GET("history/{user_id}")
    suspend fun getBookingHistory(
        @Path("user_id") userId: Int
    ): List<BookingHistory>


    @GET("user-profile/{user_id}")
    suspend fun getProfile(
        @Path("user_id") userId: Int
    ): ProfileResponse

    // Gym Registration & Setup
    @GET("get-registered-gyms")
    suspend fun getRegisteredGyms(): List<Gym>

    @POST("setup-gym")
    suspend fun setupGym(
        @Body request: GymSetupRequest
    ): GymSetupResponse

    @POST("configure-hours")
    suspend fun configureHours(
        @Body request: ConfigureHoursRequest
    ): ConfigureHoursResponse

    @POST("set-slot-capacity")
    suspend fun setSlotCapacity(
        @Body request: SetCapacityRequest
    ): SetCapacityResponse

    // File Upload
    @Multipart
    @POST("upload-historical-data")
    suspend fun uploadHistoricalData(
        @Part("admin_user_id") adminUserId: RequestBody,
        @Part file: MultipartBody.Part
    ): UploadResponse

    @Multipart
    @POST("upload-gym-members")
    suspend fun uploadGymMembers(
        @Part("admin_user_id") adminUserId: RequestBody,
        @Part file: MultipartBody.Part
    ): UploadResponse

    // Gym Owner Dashboard
    @POST("gym-dashboard")
    suspend fun getGymDashboard(
        @Body request: GymDashboardRequest
    ): GymDashboardResponse

    @POST("get-members")
    suspend fun getGymMembers(
        @Body request: GetMembersRequest
    ): GetMembersResponse

    @POST("add-member")
    suspend fun addMember(
        @Body request: AddMemberRequest
    ): AddMemberResponse

    @POST("update-gym-hours")
    suspend fun updateGymHours(
        @Body request: UpdateGymHoursRequest
    ): UpdateGymHoursResponse

    @POST("add-holiday")
    suspend fun addHoliday(
        @Body request: AddHolidayRequest
    ): AddHolidayResponse

    @POST("add-morning-only")
    suspend fun addMorningOnly(
        @Body request: AddMorningOnlyRequest
    ): AddMorningOnlyResponse

    @POST("get-gym-info")
    suspend fun getGymInfo(
        @Body request: GetGymInfoRequest
    ): GetGymInfoResponse

    // Password Reset
    @POST("forgot-password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): ForgotPasswordResponse

    @POST("reset-password")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ): ResetPasswordResponse

    // Super Admin Endpoints
    @POST("admin-login")
    suspend fun adminLogin(
        @Body request: AdminLoginRequest
    ): AdminLoginResponse

    @GET("admin-total-gyms")
    suspend fun getAdminTotalGyms(): AdminTotalGymsResponse

    @GET("admin-total-members")
    suspend fun getAdminTotalMembers(): AdminTotalMembersResponse

    @GET("admin-total-bookings")
    suspend fun getAdminTotalBookings(): AdminTotalBookingsResponse

    @GET("admin-gyms")
    suspend fun getAdminGyms(): List<AdminGym>

    @POST("admin-suspend-gym")
    suspend fun suspendGym(
        @Body request: SuspendGymRequest
    ): SuspendGymResponse

    @GET("admin-system-status")
    suspend fun getAdminSystemStatus(): AdminSystemStatusResponse

    @GET("admin-growth")
    suspend fun getAdminGrowth(): AdminGrowthResponse

    @GET("admin-activity")
    suspend fun getAdminActivity(): List<AdminActivity>

    // Additional User Endpoints
    @GET("user-home/{user_id}")
    suspend fun getUserHome(
        @Path("user_id") userId: Int
    ): UserHomeResponse

    @POST("cancel-booking")
    suspend fun cancelBooking(
        @Body request: CancelBookingRequest
    ): CancelBookingResponse
}
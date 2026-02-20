# Quick Build & Run Guide for FitZone App

## Prerequisites
- Android Studio (latest version)
- Android SDK 23+ (API 23+)
- Gradle 8.0+
- Java 11+

## Build Steps

### Option 1: Using Android Studio

1. **Open the Project**
   - Open Android Studio
   - File → Open → Navigate to `gym_fitzone2` folder
   - Wait for Gradle sync to complete

2. **Build the App**
   - Go to Build → Clean Project
   - Then Build → Build Bundle(s) / Build APK(s)
   - Or use the green Play button to Run on device/emulator

3. **Run on Android Device/Emulator**
   - Connect your Android device (or start emulator)
   - Press Shift+F10 (or green Play button)
   - Select your target device

### Option 2: Using Command Line

1. **Navigate to Project Directory**
   ```bash
   cd C:\Users\Himaja Yenikapati\AndroidStudioProjects\gym_fitzone2
   ```

2. **Build Debug APK**
   ```bash
   ./gradlew assembleDebug
   ```
   
   Output will be at: `app/build/outputs/apk/debug/app-debug.apk`

3. **Install on Device**
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

4. **Run the App**
   ```bash
   adb shell am start -n com.simats.gym_fitzone/.MainActivity
   ```

## Navigation Flow Overview

When you launch the app:

1. **Splash Screen** (2 seconds)
   - White background with FitZone logo
   - Auto-transitions to Login screen

2. **Login Screen**
   - Enter Email/Mobile Number
   - Enter Password
   - Click "Login" button (or "Register" to create account)
   - If forgot password, click "Forgot Password?" link

3. **After Successful Login → Gym Selection**
   - Choose your gym from the list
   - Enter your Member ID
   - Click "Verify & Continue"

4. **Home Screen**
   - Main dashboard with available features
   - Bottom navigation for different sections
   - Click profile icon to logout

## Screen Navigation Shortcuts

For testing purposes, you can directly test specific screens by modifying MainActivity.kt:

### To test Splash Screen:
Change in `MainActivity.kt`:
```kotlin
startDestination = "splash"  // (Already default)
```

### To test Login Screen:
Change in `MainActivity.kt`:
```kotlin
startDestination = "login"
```

### To test Register Screen:
Change in `MainActivity.kt`:
```kotlin
startDestination = "register"
```

### To test Gym Selection Screen:
Change in `MainActivity.kt`:
```kotlin
startDestination = "gym_selection"
```

### To test Home Screen:
Change in `MainActivity.kt`:
```kotlin
startDestination = "home"
```

## Testing Checklist

### Splash Screen
- [ ] Shows for exactly 2 seconds
- [ ] White background
- [ ] Auto-navigates to login
- [ ] Logo animation smooth

### Login Screen
- [ ] Email field accepts input
- [ ] Password field hides text
- [ ] Login button disabled when fields empty
- [ ] "Register" link works
- [ ] "Forgot Password?" link works

### Register Screen
- [ ] All fields are present
- [ ] Gender dropdown works
- [ ] Role dropdown shows "Gym User" and "Gym Administrator"
- [ ] Register button only enabled when all valid
- [ ] Mobile number limited to 10 digits
- [ ] Password confirmation validation works
- [ ] Back button returns to login

### Forgot Password Screen
- [ ] Email field accepts input
- [ ] Send button disabled when email empty
- [ ] Back to login button works

### Gym Selection Screen
- [ ] Search filters gyms by city/name
- [ ] Gym selection shows checkmark
- [ ] Member ID field accepts input
- [ ] Verify button only enabled with gym + member ID
- [ ] Navigates to home on success

### Home Screen
- [ ] Shows welcome message
- [ ] Bottom navigation items clickable
- [ ] Logout button works

## Troubleshooting

### Build Fails
1. Clean project: `Build → Clean Project`
2. Rebuild: `Build → Rebuild Project`
3. Check Gradle sync: File → Sync Now

### Gradle Errors
1. Update Android Studio to latest version
2. Update SDK: Settings → Appearance & Behavior → System Settings → Android SDK
3. Clear Gradle cache: Delete `.gradle` folder in user home directory

### Runtime Errors
1. Check Logcat for error messages
2. Ensure all imports are correct in the Kotlin files
3. Verify Android SDK API level is at least 23

### Emulator Issues
1. Use Android Virtual Device (AVD) Manager to create a new emulator
2. Use a modern API level (28+)
3. Allocate sufficient RAM (2GB+)

## File Structure

```
gym_fitzone2/
├── app/
│   └── src/
│       └── main/
│           └── java/
│               └── com/simats/gym_fitzone/
│                   ├── MainActivity.kt
│                   └── screens/
│                       ├── SplashScreen.kt
│                       ├── LoginScreen.kt
│                       ├── RegisterScreen.kt
│                       ├── ForgotPasswordScreen.kt
│                       ├── GymSelectionScreen.kt
│                       └── HomeScreen.kt
├── build.gradle.kts (Project level)
├── app/build.gradle.kts (App level)
└── settings.gradle.kts
```

## Key Features Implemented

✅ Splash Screen with 2-second delay
✅ Login Screen with email/password fields
✅ Register Screen with full user details
✅ Role selection (Gym User/Administrator)
✅ Forgot Password functionality
✅ Gym Selection screen with search
✅ Form validation
✅ Navigation flow
✅ White background for splash
✅ Green primary color (#1BB85B)
✅ Responsive UI design

## Next Steps for Backend Integration

1. **Login API**
   - Endpoint: `POST /api/auth/login`
   - Parameters: email, password
   - Response: user token, user details

2. **Register API**
   - Endpoint: `POST /api/auth/register`
   - Parameters: name, age, gender, email, mobile, password, role
   - Response: confirmation, user ID

3. **Forgot Password API**
   - Endpoint: `POST /api/auth/forgot-password`
   - Parameters: email
   - Response: reset link sent confirmation

4. **Gym & Member Verification API**
   - Endpoint: `POST /api/gym/verify`
   - Parameters: gymId, memberId
   - Response: gym details, member details

5. **Home Screen API**
   - Endpoint: `GET /api/gym/status`
   - Response: crowd level, available slots

## Support & Documentation

For more details on the authentication flow, see: `AUTHENTICATION_FLOW.md`

For implementation details on individual screens:
- SplashScreen.kt (lines 1-183)
- LoginScreen.kt (lines 1-261)
- RegisterScreen.kt (lines 1-613)
- ForgotPasswordScreen.kt (lines 1-192)
- GymSelectionScreen.kt (newly created)
- HomeScreen.kt (lines 1-373)


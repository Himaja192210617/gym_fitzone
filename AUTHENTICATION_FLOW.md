# FitZone Authentication & Navigation Flow

## Overview
This document describes the complete authentication and navigation flow of the FitZone Android application.

## Navigation Structure

```
┌─────────────────────────────────────────────────────────┐
│                 SPLASH SCREEN (2 sec)                   │
│            - White Background                           │
│            - FitZone Logo Animation                     │
│            - Automatically navigates to Login            │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│                    LOGIN SCREEN                          │
│  ┌──────────────────────────────────────────────────┐   │
│  │  Email/Mobile Number Field                       │   │
│  │  Password Field                                  │   │
│  │  "Forgot Password?" Link (bottom right)         │   │
│  │  "Login" Button (only enabled when both fields) │   │
│  │  "Register" Link (bottom)                       │   │
│  └──────────────────────────────────────────────────┘   │
│           ↙                ↓                ↖            │
│    Register          Success              Forgot         │
│      Click           Login                Password        │
│           ↓                ↓                ↓             │
│      REGISTER         GYM SELECTION    FORGOT PASSWORD   │
│      SCREEN           SCREEN          SCREEN            │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│              GYM SELECTION SCREEN                        │
│  ┌──────────────────────────────────────────────────┐   │
│  │  "Select Your Gym" - Title                       │   │
│  │  Search by City input field                      │   │
│  │  Available Gyms (Selectable Cards)               │   │
│  │    - FitZone Premium Mumbai                      │   │
│  │    - PowerFit Bangalore                          │   │
│  │    - Elite Fitness Delhi                         │   │
│  │    - FitHub Chennai                              │   │
│  │  Enter Member ID field                           │   │
│  │  "Verify & Continue" Button                      │   │
│  └──────────────────────────────────────────────────┘   │
│                          ↓                                │
│                    HOME SCREEN                           │
│              (Main Application)                          │
└─────────────────────────────────────────────────────────┘
```

## Screen Details

### 1. SPLASH SCREEN
**File**: `SplashScreen.kt`
- **Background**: White
- **Duration**: 2 seconds
- **Features**:
  - FitZone logo with pulse animation
  - App name and tagline
  - Loading indicators
- **Navigation**: Automatically navigates to Login screen after 2 seconds

### 2. LOGIN SCREEN
**File**: `LoginScreen.kt`
- **Background**: Light gray (#F5F5F5)
- **Fields**:
  - Email/Mobile Number (required)
  - Password (required)
- **Features**:
  - Email/Mobile field validation
  - Password field with eye toggle
  - "Forgot Password?" link (redirects to ForgotPasswordScreen)
  - "Register" link (redirects to RegisterScreen)
  - Login button (enabled only when both fields are filled)
- **Validation**:
  - Both fields must be non-empty to enable Login button
  - Email format validation recommended for implementation

### 3. REGISTER SCREEN
**File**: `RegisterScreen.kt`
- **Background**: Light gray (#F8F8F8)
- **Fields**:
  - Full Name (required)
  - Age (required, numeric)
  - Gender (required, dropdown: Male, Female, Other)
  - Email (required, email format)
  - Mobile Number (required, exactly 10 digits)
  - Password (required)
  - Confirm Password (required)
  - Role (required, dropdown: Gym User, Gym Administrator)
- **Features**:
  - Back button to return to Login
  - All fields are validated
  - Register button enabled only when form is completely valid
  - Password confirmation validation
- **Navigation**: On successful registration → GymSelectionScreen

### 4. FORGOT PASSWORD SCREEN
**File**: `ForgotPasswordScreen.kt`
- **Background**: Light gray (#F5F5F5)
- **Fields**:
  - Email Address (required)
- **Features**:
  - Description text explaining the purpose
  - Email input field
  - "Send Reset Link" button (enabled only when email is filled)
  - "Back to Login" button
- **Functionality**: 
  - Validates email is not empty
  - On "Send Reset Link" click: Backend API call (can be integrated)
  - Shows email validation before sending reset link
- **Navigation**: Returns to Login screen after sending reset link

### 5. GYM SELECTION SCREEN
**File**: `GymSelectionScreen.kt`
- **Background**: White
- **Features**:
  - Search by City field (filters gyms in real-time)
  - Available Gyms list (selectable cards showing):
    - Gym Name
    - Location with icon
    - Gym ID
    - Selection indicator (green checkmark when selected)
  - Enter Member ID field
  - "Verify & Continue" button (enabled when gym selected and member ID filled)
- **Sample Gyms**:
  1. FitZone Premium Mumbai - GYM001
  2. PowerFit Bangalore - GYM002
  3. Elite Fitness Delhi - GYM003
  4. FitHub Chennai - GYM004
- **Validation**:
  - Gym must be selected
  - Member ID must be filled (non-empty)
- **Navigation**: On "Verify & Continue" → HomeScreen

### 6. HOME SCREEN
**File**: `HomeScreen.kt`
- **Main dashboard after successful login**
- **Features**:
  - User welcome message
  - Current crowd level indicator
  - Next available booking slot with "Book Now" button
  - Peak hour alert
  - Bottom navigation (Home, Book, Workout, BMI, History, Profile)
  - Logout functionality
- **Navigation**: Logout button navigates back to LoginScreen

## Color Scheme
- **Primary Green**: #1BB85B (buttons, highlights, links)
- **Background Light**: #F5F5F5 / #F8F8F8
- **Text Primary**: #000000
- **Text Secondary**: #666666 / #999999
- **Disabled**: #CCCCCC
- **White**: #FFFFFF

## Form Validation Rules

### Login
- ✅ Email/Mobile: Non-empty
- ✅ Password: Non-empty

### Register
- ✅ Full Name: Non-empty
- ✅ Age: Non-empty, numeric
- ✅ Gender: Selected from dropdown
- ✅ Email: Non-empty, email format
- ✅ Mobile: Exactly 10 digits
- ✅ Password: Non-empty
- ✅ Confirm Password: Matches password
- ✅ Role: Selected from dropdown (Gym User or Gym Administrator)

### Forgot Password
- ✅ Email: Non-empty, email format

### Gym Selection
- ✅ Gym: Must be selected from list
- ✅ Member ID: Non-empty

## Role Types
Users can register as:
1. **Gym User** - Regular gym member with access to bookings and workout tracking
2. **Gym Administrator** - Gym staff with administrative privileges

## Navigation Summary

| From | To | Action |
|------|----|----|
| Splash | Login | Auto after 2 seconds |
| Login | Register | Click "Register" link |
| Login | Home/GymSelection | Click "Login" button (success) |
| Login | ForgotPassword | Click "Forgot Password?" link |
| Register | Login | Click Back button |
| Register | GymSelection | Click "Register" button (success) |
| ForgotPassword | Login | Click "Back to Login" or "Send Reset Link" |
| GymSelection | Home | Click "Verify & Continue" button |
| Home | Login | Click logout button |

## Implementation Notes

1. **Backend Integration**:
   - Login credentials validation
   - User registration API
   - Password reset email API
   - Gym and member ID verification

2. **State Management**:
   - All screens use Jetpack Compose state management
   - `mutableStateOf` for form field states
   - `remember` for state persistence within composition

3. **UI/UX Features**:
   - Form validation with button enable/disable
   - Smooth animations on splash screen
   - Responsive design with proper spacing
   - Consistent color scheme throughout
   - User-friendly error handling (ready for implementation)

4. **Security Considerations**:
   - Password fields use PasswordVisualTransformation
   - Email format validation
   - Member ID verification with gym selection
   - Session management after login (to be implemented)

## Future Enhancements
- Add email verification
- Implement password strength indicator
- Add phone number OTP verification
- Add biometric authentication
- Implement session timeout
- Add password reset confirmation email


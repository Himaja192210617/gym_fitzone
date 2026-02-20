# Set Capacity Screen - Visual Guide & User Flow

## Screen Layout

```
┌─────────────────────────────────────────────┐
│  ◄  Set Capacity                            │ ← Header with back button
├─────────────────────────────────────────────┤
│                                             │
│  ✓        ✓        ✓        ●              │ ← Step indicator (4 of 4)
│  Step 1  Step 2  Step 3  Step 4 (Current)  │
│                                             │
│                   🏢                        │ ← Building icon
│            Set Capacity                    │
│      Set the capacity for each time slot   │
│                                             │
├─────────────────────────────────────────────┤
│                                             │
│  👥 What is Slot Capacity?                 │ ← Info Card Header
│  ─────────────────────────────────────────  │
│  Slot capacity is the maximum number of    │
│  members who can book a single time slot   │
│  at your gym. This helps manage crowd      │
│  levels and ensure a comfortable workout   │
│  experience.                               │
│                                             │
│  ┌─────────────────────────────────────┐   │
│  │ Example:                            │   │
│  │ • If capacity = 10 and 3 members   │   │
│  │   → 30% booked (Green)              │   │
│  │ • If capacity = 10 and 6 members   │   │
│  │   → 60% booked (Yellow)             │   │
│  │ • If capacity = 10 and 8 members   │   │
│  │   → 80% booked (Red)                │   │
│  └─────────────────────────────────────┘   │
│                                             │
├─────────────────────────────────────────────┤
│                                             │
│  👥 Set Slot Capacity *                    │ ← Required field
│  ─────────────────────────────────────────  │
│  Enter the maximum number of members that  │
│  can book each time slot                   │
│                                             │
│  [ 👥 | e.g., 25            ]              │ ← Input field
│                                             │
│  ┌─────────────────────────────────────┐   │
│  │ Color Coding System:                │   │
│  │ 🟢 Green: Less than 50% booked      │   │
│  │ 🟡 Yellow: 50-75% booked            │   │
│  │ 🔴 Red: More than 75% booked        │   │
│  └─────────────────────────────────────┘   │
│                                             │
├─────────────────────────────────────────────┤
│                                             │
│  [    Back    ] [ Complete Setup ]         │ ← Action buttons
│                                             │
└─────────────────────────────────────────────┘
```

## Complete Navigation Flow

### Gym Administrator Setup Journey

```
1. SPLASH SCREEN
   ↓
2. LOGIN SCREEN
   ↓
3. REGISTER SCREEN (Select: Gym Administrator Role)
   ↓
4. GYM SETUP SCREEN (Step 1/4)
   ├─ Gym Name
   ├─ Address
   ├─ City
   ├─ Phone
   ├─ Email
   └─ Description
   ↓
5. CONFIGURE HOURS SCREEN (Step 2/4)
   ├─ Morning Session: 06:00 - 12:00
   ├─ Afternoon Session (Optional): 14:00 - 17:00
   └─ Evening Session: 17:00 - 23:00
   ↓
6. UPLOAD DATA SCREEN (Step 3/4) ← MANDATORY DATA UPLOAD
   ├─ Historical Bookings File (.xlsx)
   └─ Gym Members File (.xlsx)
   ↓
7. SET CAPACITY SCREEN (Step 4/4) ← NEW SCREEN
   └─ Slot Capacity: 25
   ↓
8. HOME DASHBOARD
   └─ Gym admin can now manage operations
```

## Button States

### Complete Setup Button

**DISABLED State** (Gray)
```
When:
- Input field is empty
- Input is non-numeric
- Input is "0" or negative

Visual:
┌────────────────────────┐
│   Complete Setup       │ ← Grayed out, unclickable
└────────────────────────┘
Color: #CCCCCC (Light Gray)
```

**ENABLED State** (Green)
```
When:
- Input is a positive integer (1+)
- Valid number entered

Visual:
┌────────────────────────┐
│   Complete Setup       │ ← Bright green, clickable
└────────────────────────┘
Color: #1BB85B (App Green)
```

## Input Field Behavior

### Real-Time Validation Example

**State 1: Field Empty**
```
Input: []
Status: INVALID
Button: DISABLED (Gray)
```

**State 2: User Types "2"**
```
Input: [2]
Status: VALID
Button: ENABLED (Green) ✓
```

**State 3: User Types "25"**
```
Input: [25]
Status: VALID
Button: ENABLED (Green) ✓
```

**State 4: User Types Non-Numeric**
```
Input: [25a] ← "a" is rejected
Actual Input: [25] ← Only numeric kept
Status: VALID
Button: ENABLED (Green) ✓
```

## Data Submission Flow

### User Input → Validation → Navigation

```
┌─────────────────────┐
│  User enters "25"   │
└──────────┬──────────┘
           ↓
┌──────────────────────────────┐
│ Validate:                    │
│ • Is not empty? ✓            │
│ • Is numeric? ✓              │
│ • Is positive (>0)? ✓        │
└──────────┬───────────────────┘
           ↓
┌──────────────────────────────┐
│ Complete Setup Button        │
│ Changes to ENABLED (Green)   │
└──────────┬───────────────────┘
           ↓
┌──────────────────────────────┐
│ User clicks Complete Setup   │
└──────────┬───────────────────┘
           ↓
┌──────────────────────────────┐
│ onCompleteSetup() callback   │
│ triggered                    │
└──────────┬───────────────────┘
           ↓
┌──────────────────────────────┐
│ Navigate to Home Screen      │
│ (Pop set_capacity from stack)│
└──────────────────────────────┘
```

## Navigation Path Examples

### Example 1: Successful Setup
```
User: Enters "30" as capacity
      ↓
App: Validates input ✓
     ↓
Button: Becomes enabled (Green)
        ↓
User: Clicks "Complete Setup"
      ↓
App: Saves capacity value
     Navigates to Home Screen
     ↓
User: Lands on Home Dashboard
      Can now manage gym operations
```

### Example 2: Invalid Input (Zero)
```
User: Enters "0"
      ↓
App: Validates - Zero not allowed ✗
     ↓
Button: Remains disabled (Gray)
        ↓
User: Cannot proceed
      Must clear and enter valid value
```

### Example 3: Going Back
```
User: Enters "25"
      Button enabled (Green)
      ↓
User: Clicks "Back" button
      ↓
App: Navigates back to Upload Data Screen
     ↓
User: Can upload more data or proceed forward
```

## Color Coding System Examples

### Green Zone (Less than 50% Booked)
```
Capacity: 20 members per slot
Booked: 8 members
Percentage: 40%
Status: 🟢 Green - Plenty of space available
User Experience: Easy to book without waiting
```

### Yellow Zone (50-75% Booked)
```
Capacity: 20 members per slot
Booked: 14 members
Percentage: 70%
Status: 🟡 Yellow - Getting busy
User Experience: May need to pick different time slot
```

### Red Zone (More than 75% Booked)
```
Capacity: 20 members per slot
Booked: 16 members
Percentage: 80%
Status: 🔴 Red - Nearly full
User Experience: Limited slots available
Gym Should: May consider adding more capacity
```

## Recommended Capacity Values

### By Gym Size

**Small Gym**
```
Space: 500-1000 sq ft
Equipment: Basic setup
Recommended Capacity: 15-20 members/slot
Reasoning: Limited space, personal training focus
```

**Medium Gym**
```
Space: 1000-3000 sq ft
Equipment: Standard setup
Recommended Capacity: 25-35 members/slot
Reasoning: Good space utilization
```

**Large Gym**
```
Space: 3000+ sq ft
Equipment: Full facility
Recommended Capacity: 40-60 members/slot
Reasoning: Multiple zones, larger floor plan
```

**24-Hour Gym**
```
All Sizes: Set higher capacity
Reasoning: Off-peak hours have less crowding
Example: 30-50 members/slot
```

## Error Messages & Feedback

### Valid Input Feedback
```
User enters: "25"
Validation: ✓ Valid positive integer
Button State: Enabled (Green)
Message: (No error message, ready to proceed)
```

### Invalid Input Feedback
```
User enters: "0"
Validation: ✗ Zero is not allowed
Button State: Disabled (Gray)
Message: (Button disabled, user must change value)
```

### Empty Input Feedback
```
User enters: "" (nothing)
Validation: ✗ Field required
Button State: Disabled (Gray)
Message: (Button disabled, user must enter value)
```

## Step Indicator Progression

### At Each Step of Setup

**Step 1: Gym Setup**
```
● ─ ○ ─ ○ ─ ○
(Current) (Next) (Next) (Next)
```

**Step 2: Configure Hours**
```
✓ ─ ● ─ ○ ─ ○
(Done) (Current) (Next) (Next)
```

**Step 3: Upload Data**
```
✓ ─ ✓ ─ ● ─ ○
(Done) (Done) (Current) (Next)
```

**Step 4: Set Capacity** ← You are here
```
✓ ─ ✓ ─ ✓ ─ ●
(Done) (Done) (Done) (Current)
```

## Response Time & Feedback

### Input Field Response
```
User types: "2"
Response Time: Instant (< 50ms)
Feedback: Character appears immediately
Validation: Real-time as typing
```

### Button Transition
```
User types valid input: "25"
Response Time: Instant
Visual Feedback: Button changes to green
User Perception: Immediate action readiness
```

## Accessibility Features

### For Different Device Sizes

**Mobile Phone (320px width)**
```
Layout: Stacked vertically
Input Field: Full width with padding
Buttons: Full width, stacked
Font Size: Readable without zoom
```

**Tablet (768px width)**
```
Layout: Comfortable spacing
Input Field: Centered with margins
Buttons: Side by side
Font Size: Larger for comfort
```

**Desktop (1024px+ width)**
```
Layout: Centered, maximum width constraint
Input Field: 400px width
Buttons: Side by side
Font Size: Standard desktop size
```

## Summary

✅ Complete screen layout documented
✅ Navigation flow clearly shown
✅ Button states and transitions explained
✅ Data validation flow mapped
✅ Color coding system detailed
✅ Recommended capacity values provided
✅ Error handling documented
✅ All device sizes supported


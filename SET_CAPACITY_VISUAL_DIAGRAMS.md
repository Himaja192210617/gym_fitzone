# Set Capacity Implementation - Visual Diagrams

## Complete Setup Flow Diagram

```
┌──────────────────────────────────────────────────────────┐
│                   SPLASH SCREEN                          │
│                  (2 seconds delay)                       │
└─────────────────────┬──────────────────────────────────┘
                      │
                      ↓
┌──────────────────────────────────────────────────────────┐
│               LOGIN / REGISTER SCREEN                    │
│      Choose: Gym User OR Gym Administrator              │
└────┬─────────────────────────────────────┬───────────────┘
     │                                      │
GYM USER                            GYM ADMINISTRATOR
     │                                      │
     ↓                                      ↓
┌────────────────┐           ┌──────────────────────────┐
│  GYM SELECTION │           │   GYM SETUP SCREEN       │
│   Screen       │           │   (Step 1 of 4)          │
│                │           │  [Complete Setup]        │
└────┬───────────┘           └────────────┬─────────────┘
     │                                     │
     ↓                                     ↓
┌────────────────┐           ┌──────────────────────────┐
│   HOME SCREEN  │           │  CONFIGURE HOURS SCREEN  │
│                │           │   (Step 2 of 4)          │
│ (Quick access) │           │  [Complete Setup]        │
└────────────────┘           └────────────┬─────────────┘
                                          │
                                          ↓
                             ┌──────────────────────────┐
                             │  UPLOAD DATA SCREEN      │
                             │   (Step 3 of 4)          │
                             │ [Next: Set Capacity]     │
                             │                          │
                             │ MANDATORY UPLOAD         │
                             │ Cannot be skipped! ✓     │
                             └────────────┬─────────────┘
                                          │
                                          ↓
                             ┌──────────────────────────┐
                             │  SET CAPACITY SCREEN     │
                             │   (Step 4 of 4) ← NEW    │
                             │   [Complete Setup]       │
                             └────────────┬─────────────┘
                                          │
                                          ↓
                             ┌──────────────────────────┐
                             │   HOME DASHBOARD         │
                             │  Setup Complete! ✓ ✓ ✓   │
                             │ Ready to manage gym      │
                             └──────────────────────────┘
```

---

## Set Capacity Screen Layout

```
╔════════════════════════════════════════════════════════╗
║  ◄  Set Capacity                                       ║ ← Header
╠════════════════════════════════════════════════════════╣
║                                                        ║
║  ✓    ✓    ✓    ●                                    ║ ← Step Indicator
║  1    2    3    4 (Current)                          ║
║                                                        ║
║                    🏢                                  ║ ← Icon
║              Set Capacity                            ║
║        Set the capacity for each time slot           ║ ← Title
║                                                        ║
├────────────────────────────────────────────────────────┤
║                                                        ║
║  👥 What is Slot Capacity?     [Light Blue Card]     ║ ← Info Card
║  ──────────────────────────────                      ║
║  Slot capacity is the maximum number of members      ║
║  who can book a single time slot at your gym...      ║
║                                                        ║
║  Example:                                            ║
║  • If capacity = 10 and 3 members book               ║
║    → 30% booked (Green)                              ║
║  • If capacity = 10 and 6 members book               ║
║    → 60% booked (Yellow)                             ║
║  • If capacity = 10 and 8 members book               ║
║    → 80% booked (Red)                                ║
║                                                        ║
├────────────────────────────────────────────────────────┤
║                                                        ║
║  👥 Set Slot Capacity *          [White Card]        ║ ← Input Card
║  ──────────────────────────────                      ║
║  Enter the maximum number of members that            ║
║  can book each time slot                             ║
║                                                        ║
║  [👥 | e.g., 25          ]      ← Input Field        ║
║                                                        ║
║  Color Coding System:                                ║
║  🟢 Green: Less than 50% booked                      ║
║  🟡 Yellow: 50-75% booked                            ║
║  🔴 Red: More than 75% booked                        ║
║                                                        ║
├────────────────────────────────────────────────────────┤
║                                                        ║
║  [  Back  ]      [  Complete Setup  ]               ║ ← Buttons
║  (Gray)          (Green when valid)                  ║
║                                                        ║
╚════════════════════════════════════════════════════════╝
```

---

## Button State Machine

```
                    ┌─────────────────────────┐
                    │  COMPONENT INITIALIZED  │
                    │  Input: Empty ""        │
                    └──────────────┬──────────┘
                                   │
                                   ↓
                      ┌────────────────────────┐
                      │  BUTTON: DISABLED      │
                      │  Color: Gray (#CCCCCC) │
                      │  Clickable: NO         │
                      └────────┬───────────────┘
                               │
               ┌───────────────┴───────────────┐
               │                               │
        USER ENTERS                   USER ENTERS
        INVALID INPUT                 VALID INPUT
        (0, -, abc)                   (1, 10, 25)
               │                               │
               ↓                               ↓
      ┌────────────────┐         ┌────────────────────┐
      │ Stays DISABLED │         │ BUTTON: ENABLED    │
      │ Color: Gray    │         │ Color: Green       │
      │ Clickable: NO  │         │ Clickable: YES ✓   │
      └────────────────┘         └────────┬───────────┘
                                          │
                                   USER CLICKS BUTTON
                                          │
                                          ↓
                                 ┌─────────────────┐
                                 │ NAVIGATE TO HOME│
                                 │ Setup Complete! │
                                 └─────────────────┘
```

---

## Input Validation Flowchart

```
                        ┌──────────────────┐
                        │ USER TYPES INPUT │
                        └────────┬─────────┘
                                 │
                ┌────────────────┴────────────────┐
                │                                 │
          Is Empty?                          Is Non-numeric?
                │                                 │
              YES ✗                             YES ✗
                │                                 │
                ↓                                 ↓
        ┌──────────────┐                  ┌──────────────┐
        │ REJECT INPUT │                  │ IGNORE CHAR  │
        │ Show Nothing │                  │ Discard input│
        └──────┬───────┘                  └──────┬───────┘
               │                                  │
               └──────────────┬───────────────────┘
                              │
                    Both lead to: Input invalid
                              │
                              ↓
                    ┌──────────────────────┐
                    │ CHECK FOR VALID NUM  │
                    └────────┬─────────────┘
                             │
                    ┌────────┴─────────┐
                    │                  │
              Is Zero?            Is Negative?
                │                      │
              YES ✗                   YES ✗
                │                      │
                ↓                      ↓
        ┌──────────────┐      ┌──────────────┐
        │ INVALID ✗    │      │ INVALID ✗    │
        │ Button: Disabled    │ Button: Disabled
        └──────────────┘      └──────────────┘
                │                      │
                └──────────┬───────────┘
                           │
                    Is Positive?
                    (> 0)
                           │
                         YES ✓
                           │
                           ↓
                  ┌──────────────────┐
                  │ VALID INPUT ✓    │
                  │ Button: ENABLED  │
                  │ Color: Green     │
                  └──────────────────┘
```

---

## Navigation State Diagram

```
UPLOAD DATA SCREEN
       │
       │ onCompleteUpload() called
       │ User clicks "Next: Set Capacity" button
       │
       ├─ Check: Both files uploaded? ✓
       │
       ├─ Navigate: navigate("set_capacity")
       │
       ├─ Backstack: popUpTo("upload_data", inclusive = false)
       │
       ↓
SET CAPACITY SCREEN ← CURRENT
       │
       ├─ onBackClick() called
       │ User clicks "Back" button
       │ → navController.navigateUp()
       │ → Returns to Upload Data Screen
       │
       └─ onCompleteSetup() called
         User clicks "Complete Setup" button
         │
         ├─ Check: Capacity is valid? ✓
         │
         ├─ Navigate: navigate("home")
         │
         ├─ Backstack: popUpTo("set_capacity", inclusive = true)
         │
         ↓
      HOME SCREEN
      (Setup complete)
```

---

## Data Flow Diagram

```
USER INPUT
    │
    │ "25"
    │
    ↓
┌──────────────────────────────┐
│ INPUT VALIDATION             │
│ ├─ Is not empty? YES ✓       │
│ ├─ Is numeric? YES ✓         │
│ ├─ Is positive? YES ✓ (25>0) │
└──────────────┬───────────────┘
               │
         isFormValid = TRUE
               │
               ↓
┌──────────────────────────────┐
│ UI STATE UPDATE              │
│ Button color: GRAY → GREEN   │
│ Button state: DISABLED→ ENAB. │
└──────────────┬───────────────┘
               │
        USER CLICKS BUTTON
               │
               ↓
┌──────────────────────────────┐
│ STORE CAPACITY VALUE         │
│ slotCapacity = "25"          │
└──────────────┬───────────────┘
               │
               ↓
┌──────────────────────────────┐
│ TRIGGER NAVIGATION           │
│ onCompleteSetup() callback   │
└──────────────┬───────────────┘
               │
               ↓
       ┌──────────────┐
       │ NAVIGATE HOME│
       │ Setup Done!  │
       └──────────────┘
```

---

## Step Progress Visualization

### At Each Setup Step

**At Step 1 (Gym Setup)**
```
●  ─  ○  ─  ○  ─  ○  ─  ○
Active (current - in progress)
```

**At Step 2 (Configure Hours)**
```
✓  ─  ●  ─  ○  ─  ○  ─  ○
Completed | Active | Upcoming
```

**At Step 3 (Upload Data)**
```
✓  ─  ✓  ─  ●  ─  ○  ─  ○
Completed | Active | Upcoming
```

**At Step 4 (Set Capacity) ← YOU ARE HERE**
```
✓  ─  ✓  ─  ✓  ─  ●  ─  ○
Completed | Active | Upcoming
```

---

## Capacity Calculation Example

```
EXAMPLE 1: ROOM WITH 10 CAPACITY

Bookings       Calculation         Percentage    Status
0 booked       0 / 10 × 100 =     0%           🟢 Green
2 booked       2 / 10 × 100 =     20%          🟢 Green
4 booked       4 / 10 × 100 =     40%          🟢 Green
5 booked       5 / 10 × 100 =     50%          🟡 Yellow (boundary)
6 booked       6 / 10 × 100 =     60%          🟡 Yellow
7 booked       7 / 10 × 100 =     70%          🟡 Yellow
8 booked       8 / 10 × 100 =     80%          🔴 Red (boundary)
9 booked       9 / 10 × 100 =     90%          🔴 Red
10 booked      10 / 10 × 100 =    100%         🔴 Red (Full)


EXAMPLE 2: LARGE ROOM WITH 50 CAPACITY

Bookings       Calculation          Percentage   Status
10 booked      10 / 50 × 100 =     20%          🟢 Green
20 booked      20 / 50 × 100 =     40%          🟢 Green
25 booked      25 / 50 × 100 =     50%          🟡 Yellow (boundary)
30 booked      30 / 50 × 100 =     60%          🟡 Yellow
38 booked      38 / 50 × 100 =     75%          🟡 Yellow (boundary)
40 booked      40 / 50 × 100 =     80%          🔴 Red
50 booked      50 / 50 × 100 =     100%         🔴 Red (Full)
```

---

## Color Coding Visual

```
0%                                                       100%
├─────────────────────────────────────────────────────────┤
│                                                         │
│  🟢 GREEN              🟡 YELLOW            🔴 RED     │
│  <50% booked          50-75% booked        >75% booked │
│  Available capacity   Getting busy        Nearly full   │
│  Users can book       May need alt time   Limited slots │
│                                                         │
└────────────────────────────────────────────────────────┤
0%        20%         40%        50%        75%        100%
```

---

## Complete User Flow

```
┌─────────────────────────────────────────────────────────┐
│ REGISTER AS GYM ADMINISTRATOR                          │
│ Role Selection: "Gym Administrator"                    │
└──────────────┬──────────────────────────────────────────┘
               │
               ↓
┌─────────────────────────────────────────────────────────┐
│ STEP 1: GYM SETUP SCREEN                               │
│ Fill: Name, Address, City, Phone, Email, Description  │
│ Click: [Next Step]                                     │
└──────────────┬──────────────────────────────────────────┘
               │
               ↓
┌─────────────────────────────────────────────────────────┐
│ STEP 2: CONFIGURE HOURS SCREEN                         │
│ Set: Morning, Afternoon (opt), Evening hours          │
│ Click: [Complete Setup]                                │
└──────────────┬──────────────────────────────────────────┘
               │
               ↓
┌─────────────────────────────────────────────────────────┐
│ STEP 3: UPLOAD DATA SCREEN (MANDATORY)                │
│ Upload: Historical Bookings file (.xlsx)              │
│ Upload: Gym Members file (.xlsx)                      │
│ Click: [Next: Set Capacity]                           │
│                                                        │
│ ⚠️  Cannot proceed without uploading both files!     │
└──────────────┬──────────────────────────────────────────┘
               │
               ↓
┌─────────────────────────────────────────────────────────┐
│ STEP 4: SET CAPACITY SCREEN (NEW)                      │
│ Input: Maximum members per time slot (e.g., "25")     │
│ Validate: Positive integer required                    │
│ Click: [Complete Setup] (enabled only when valid)     │
└──────────────┬──────────────────────────────────────────┘
               │
               ↓
┌─────────────────────────────────────────────────────────┐
│ HOME DASHBOARD                                         │
│ ✓ Setup Complete!                                      │
│ Ready to manage gym operations                        │
│ Access: Bookings, Members, Analytics                 │
└─────────────────────────────────────────────────────────┘
```

---

## Summary

✅ Complete flow diagram showing all screens
✅ Set Capacity screen layout with all components
✅ Button state transitions
✅ Input validation logic
✅ Navigation state diagram
✅ Data flow example
✅ Step progress visualization
✅ Capacity calculation examples
✅ Color coding reference
✅ Complete user journey

All diagrams visually explain the implementation!


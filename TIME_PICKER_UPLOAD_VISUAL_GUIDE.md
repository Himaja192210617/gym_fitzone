# 🎯 Time Picker & Upload Data - Visual Guide

## 🕒 NEW Time Picker Dialog - Clearer Design

### Before (Issues):
- Complex TimeInput component
- Unclear AM/PM selection
- Not intuitive for users
- Experimental API with warnings

### After (Improved):
```
┌────────────────────────────────────┐
│     Opening Time                    │
├────────────────────────────────────┤
│                                    │
│       ┌────────┬────────┐         │
│       │  AM ✓  │  PM    │         │ ← Clear Toggle Buttons
│       └────────┴────────┘         │    (Green = Selected)
│                                    │
│            09:00 AM                │ ← Large Display
│         (Bold, Green, 36sp)        │
│                                    │
│       Hour:   [−] 09 [+]          │ ← Easy Increment/Decrement
│                                    │
│       Minute: [−] 00 [+]          │
│                                    │
│    ┌───────────┬──────────┐       │
│    │ Confirm   │  Cancel  │       │
│    └───────────┴──────────┘       │
│                                    │
└────────────────────────────────────┘
```

### Key Improvements:
✅ **AM/PM Buttons** - Clear toggle at top, selected button turns green
✅ **Large Time Display** - 36sp bold green text, easy to read
✅ **Step-by-step Controls** - Hour and Minute separated
✅ **Simple +/- Buttons** - No scrolling, just increment/decrement
✅ **Immediate Feedback** - See time update in real-time
✅ **Locale Safe** - Proper String.format(Locale.US, ...) usage

---

## 📊 Upload Data Screen - Complete Layout

```
┌─────────────────────────────────────────┐
│  ← Configure Hours          Upload Data  │
├─────────────────────────────────────────┤
│                                         │
│  ① ← ② ← ③                            │ ← Step 3 of 3 (Active)
│                                         │
│           📊                            │
│                                         │
│        Upload Data                      │
│   Upload historical data and            │
│     gym members                         │
│                                         │
├─────────────────────────────────────────┤
│ 📋 EXCEL FILE FORMAT INSTRUCTIONS        │ ← Format Guide Card
│                                         │
│ 📅 Historical Bookings Format:         │
│ ┌──────────────────────────────────┐  │
│ │ date    │ timeSlot    │ count   │  │
│ │ 26-02-01│ 06:00-07:00 │    8    │  │
│ │ 26-02-01│ 18:00-19:00 │   15    │  │
│ └──────────────────────────────────┘  │
│                                         │
│ 👥 Gym Members Format:                 │
│ ┌──────────────────────────────────┐  │
│ │ memberid│ name  │ email           │  │
│ │ MEM001  │ John  │ john@example.com│  │
│ │ MEM002  │ Jane  │ jane@example.com│  │
│ └──────────────────────────────────┘  │
│                                         │
├─────────────────────────────────────────┤
│ 📅 HISTORICAL BOOKINGS (PAST 2 WEEKS) * │ ← Required
│                                         │
│  Upload Excel file with booking data   │
│  for AI crowd prediction training      │
│                                         │
│          ┌─────────────┐              │
│          │     ⬆️      │              │ ← Upload Button
│          │ Click to    │              │    (Shows status)
│          │ upload file │              │
│          └─────────────┘              │
│          .xlsx, .xls, .csv            │
│                                         │
├─────────────────────────────────────────┤
│ 👥 GYM MEMBERS LIST *                  │ ← Required
│                                         │
│  Upload Excel file with all current   │
│  gym members and their IDs            │
│                                         │
│          ┌─────────────┐              │
│          │     ⬆️      │              │ ← Upload Button
│          │ Click to    │              │    (Shows status)
│          │ upload file │              │
│          └─────────────┘              │
│          .xlsx, .xls, .csv            │
│                                         │
├─────────────────────────────────────────┤
│  ┌──────────────┬──────────────────┐   │
│  │     Back     │ Next: Set Capacity│  │ ← Buttons
│  │  (Light Gray)│  (Green, Active)  │  │
│  └──────────────┴──────────────────┘   │
│                                         │
└─────────────────────────────────────────┘
```

---

## 🔄 Complete Gym Admin Setup Flow

```
1️⃣ Register Screen
   ├─ Fill name, email, password
   └─ Select Role: "Gym Administrator"
       ↓
2️⃣ Gym Setup Screen
   ├─ Gym Name*
   ├─ Address*
   ├─ City*
   ├─ Phone Number*
   ├─ Email
   └─ Description
       ↓
3️⃣ Configure Hours Screen
   ├─ Morning Session (Required)
   │  ├─ Opening Time: [Select Time] → 06:00 AM
   │  └─ Closing Time: [Select Time] → 11:00 AM
   ├─ Afternoon Session (Optional)
   │  └─ [Enable Toggle] → Not Selected
   └─ Evening Session (Required)
      ├─ Opening Time: [Select Time] → 06:00 PM
      └─ Closing Time: [Select Time] → 10:00 PM
       ↓
4️⃣ Upload Data Screen ← YOU ARE HERE
   ├─ Historical Bookings (Past 2 Weeks)*
   │  └─ [Upload Excel File]
   ├─ Gym Members List*
   │  └─ [Upload Excel File]
   └─ [Next: Set Capacity] → (Step 4)
       ↓
5️⃣ Set Capacity Screen (Next)
   ├─ Maximum Capacity
   └─ [Complete Setup] → Home
```

---

## 📝 Excel File Format Examples

### Historical Bookings File (bookings_data.xlsx):
```
date       | timeSlot     | bookingCount
-----------|--------------|-------------
2026-02-01 | 06:00-07:00 | 8
2026-02-01 | 07:00-08:00 | 12
2026-02-01 | 18:00-19:00 | 15
2026-02-01 | 19:00-20:00 | 10
2026-02-02 | 06:00-07:00 | 9
...
```

### Gym Members File (gym_members.xlsx):
```
memberid | name       | email
---------|------------|------------------
MEM001   | John Doe   | john@example.com
MEM002   | Jane Smith | jane@example.com
MEM003   | Bob Wilson | (optional)
MEM004   | Alice Brown| alice@example.com
...
```

---

## 🎨 Color Scheme

| Element | Color | Hex |
|---------|-------|-----|
| Primary (Buttons, Icons) | Green | #1BB85B |
| Background | Light Gray | #F8F8F8 |
| Cards | White | #FFFFFF |
| Text (Main) | Black | #000000 |
| Text (Secondary) | Gray | #999999 |
| Text (Placeholder) | Light Gray | #BBBBBB |
| Disabled | Light Gray | #CCCCCC |
| Instructions BG | Very Light Green | #F0F9F5 |

---

## ⚡ User Interaction Sequence

### Time Picker Dialog Flow:
```
User: Taps "Select Time" button
       ↓
App: Time picker dialog opens with current time
     (Example: 09:00 AM by default)
       ↓
User: Taps "PM" button
       ↓
App: Display changes to PM, time shows as PM
     (Example: 09:00 PM)
       ↓
User: Taps "+" button next to hour multiple times
       ↓
App: Hour increments (09 → 10 → 11 ...)
       ↓
User: Taps "+" button next to minute once
       ↓
App: Minute increments (00 → 01)
       ↓
User: Sees new time in display (11:01 PM)
       ↓
User: Taps "Confirm" button
       ↓
App: Saves time as "23:01" (24-hour format)
     Shows "11:01 PM" in the button
       ↓
Dialog closes, form updates
```

### File Upload Flow:
```
User: Views format instructions
       ↓
User: Prepares Excel files following format
       ↓
User: Taps "Click to upload Excel file" button
      (for Historical Bookings)
       ↓
App: File picker opens
       ↓
User: Selects bookings_data.xlsx
       ↓
App: File selected, button shows "✓ File Selected"
       ↓
User: Taps "Click to upload Excel file" button
      (for Gym Members)
       ↓
App: File picker opens
       ↓
User: Selects gym_members.xlsx
       ↓
App: File selected, button shows "✓ File Selected"
     "Next: Set Capacity" button becomes enabled (green)
       ↓
User: Taps "Next: Set Capacity" button
       ↓
App: Navigates to Set Capacity screen
     (Step 4 of 4)
```

---

## ✨ Key Features Highlights

### Time Picker Enhancements:
🕐 **Clear AM/PM Selection** - Large buttons at top, visual feedback
🕐 **Large Time Display** - 36sp green bold text, easy to read
🕐 **Simple Controls** - Just +/- buttons, no spinning wheels
🕐 **Real-time Feedback** - See time update as you adjust
🕐 **Proper Formatting** - Converts to 24-hour format internally

### Upload Data Screen:
📁 **Built-in Format Guide** - No need to ask for help
📁 **Example Tables** - Exact column structure shown
📁 **File Status Display** - Shows which files are selected
📁 **Smart Validation** - Button only enabled when both files selected
📁 **Progressive Disclosure** - Step 3 of 3 indicator shows progress

---

## 📱 Mobile Testing Checklist

- [ ] Time picker opens on button click
- [ ] AM/PM buttons toggle correctly
- [ ] Hour +/- buttons work smoothly
- [ ] Minute +/- buttons work smoothly
- [ ] Time display updates in real-time
- [ ] Confirm button saves time correctly
- [ ] Cancel button closes without saving
- [ ] Time shows in correct format in button
- [ ] Upload buttons are tappable
- [ ] Format instructions are clear
- [ ] Next button only enabled with both files
- [ ] Back button returns to previous screen
- [ ] Step indicator shows step 3 of 3

---

**Status**: 🟢 Ready for Mobile Build and Testing


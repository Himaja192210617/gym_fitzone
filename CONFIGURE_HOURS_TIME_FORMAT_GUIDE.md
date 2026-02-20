# Configure Hours - 24-Hour Time Format Guide

## Overview
The Configure Hours screen has been updated to use a proper 24-hour time format (00:00 - 23:59) without AM/PM confusion.

## Time Picker Dialog - How It Works

### Step 1: Select Time
When you click "Select Time" for any session (Opening or Closing):
```
┌─────────────────────────────┐
│     Opening Time            │
│─────────────────────────────│
│ 24-Hour Format (00:00-23:59)│
│                             │
│         09:00               │ ← Large display
│                             │
│ Hour (0-23)                 │
│  [ − ]  09  [ + ]           │
│                             │
│ Minute (0-59)               │
│  [ − ]  00  [ + ]           │
│                             │
│        [ Confirm ] [Cancel] │
└─────────────────────────────┘
```

### Step 2: Adjust Hour (0-23)
- Click **"−"** to decrease hour (wraps from 0 to 23)
- Click **"+"** to increase hour (wraps from 23 to 0)
- Examples:
  - 9:00 AM = 09:00
  - 5:00 PM = 17:00
  - 11:30 PM = 23:30
  - Midnight = 00:00

### Step 3: Adjust Minute (0-59)
- Click **"−"** to decrease minute (wraps from 0 to 59)
- Click **"+"** to increase minute (wraps from 59 to 0)
- Examples:
  - 15 minutes = :15
  - 30 minutes = :30
  - 45 minutes = :45

### Step 4: Confirm
- Click **"Confirm"** to save the time in HH:MM format
- Time is validated to ensure it's within 24-hour format
- Click **"Cancel"** to discard changes

## Validation Rules

### ✅ Valid Times
```
00:00 - Midnight
06:00 - 6 AM
12:00 - Noon
18:00 - 6 PM
23:59 - 11:59 PM
```

### ❌ Invalid Scenarios
```
Opening: 17:00, Closing: 09:00 ✗ (closing before opening)
Opening: 14:30, Closing: 14:30 ✗ (same time)
Opening: 25:00 ✗ (exceeds 24 hours)
Opening: 14:65 ✗ (invalid minutes)
```

## Complete Setup Button

The **"Complete Setup"** button is only enabled when:
1. ✓ Morning opening time is set
2. ✓ Morning closing time is set AND after opening time
3. ✓ Evening opening time is set
4. ✓ Evening closing time is set AND after opening time
5. ✓ (If enabled) Afternoon opening < Afternoon closing

## Session Examples

### Example 1: Standard Gym Hours
```
Morning Session:
  Opening: 06:00 (6 AM)
  Closing: 12:00 (Noon)

Afternoon Session: (Optional)
  Opening: 14:00 (2 PM)
  Closing: 17:00 (5 PM)

Evening Session:
  Opening: 17:00 (5 PM)
  Closing: 23:00 (11 PM)
```

### Example 2: Early Morning to Late Night
```
Morning Session:
  Opening: 05:00 (5 AM)
  Closing: 08:00 (8 AM)

Evening Session:
  Opening: 18:00 (6 PM)
  Closing: 23:59 (11:59 PM)
```

## Time Format Conversion Reference

| Display | 24-Hour | Notes |
|---------|---------|-------|
| Midnight | 00:00 | Start of day |
| 1 AM | 01:00 | Early morning |
| 6 AM | 06:00 | Morning |
| Noon | 12:00 | Midday |
| 1 PM | 13:00 | Afternoon |
| 6 PM | 18:00 | Evening |
| 11 PM | 23:00 | Late night |
| 11:59 PM | 23:59 | End of day |

## Key Features

✅ **No Ambiguity** - 24-hour format eliminates AM/PM confusion
✅ **Easy Navigation** - Simple +/− buttons to adjust time
✅ **Automatic Validation** - Closing time must be after opening time
✅ **Clear Display** - Large, readable time format
✅ **Proper Constraints** - Hours limited to 0-23, minutes to 0-59

## Troubleshooting

**Issue**: Complete Setup button is grayed out
**Solution**: Check that all times are filled and closing times are after opening times

**Issue**: Time display shows incorrect format
**Solution**: The time should always display as HH:MM (e.g., 09:00, 14:30)

**Issue**: Can't select a specific time
**Solution**: Use the +/− buttons to increment/decrement the desired value


# Configure Hours - 24-Hour Format Visual Examples

## Time Picker Dialog - Visual Layout

### Dialog Appearance
```
╔═══════════════════════════════════════════╗
║           Opening Time                     ║
╠═══════════════════════════════════════════╣
║                                           ║
║  ┌─────────────────────────────────────┐  ║
║  │ 24-Hour Format (00:00 - 23:59)      │  ║ ← Info box
║  └─────────────────────────────────────┘  ║
║                                           ║
║                09:00                      ║ ← Time display (48sp)
║                                           ║
║  Hour (0-23)                              ║
║  [ − ]    09    [ + ]                     ║
║                                           ║
║  Minute (0-59)                            ║
║  [ − ]    00    [ + ]                     ║
║                                           ║
║  ┌─────────────────┬─────────────────┐   ║
║  │    Confirm      │     Cancel      │   ║
║  └─────────────────┴─────────────────┘   ║
║                                           ║
╚═══════════════════════════════════════════╝
```

## Time Selection Examples

### Example 1: Morning Session (6 AM to 12 PM)
```
Initial State:
┌─────────────────────────────────────┐
│ Opening Time: [Select Time]         │
│ Closing Time: [Select Time]         │
└─────────────────────────────────────┘

Step 1: Set Opening Time to 06:00
┌─────────────────────────────────────┐
│ Opening Time: [Select Time]         │
│                                     │
│ Time Picker:                        │
│   06:00                             │
│   Hour: [ − ] 06 [ + ]              │
│   Minute: [ − ] 00 [ + ]            │
│   [Confirm] [Cancel]                │
└─────────────────────────────────────┘

After Confirm:
┌─────────────────────────────────────┐
│ Opening Time: 06:00 ✓               │
│ Closing Time: [Select Time]         │
└─────────────────────────────────────┘

Step 2: Set Closing Time to 12:00
After Confirm:
┌─────────────────────────────────────┐
│ Opening Time: 06:00 ✓               │
│ Closing Time: 12:00 ✓               │
│ Status: ✓ Valid (12:00 > 06:00)     │
└─────────────────────────────────────┘
```

### Example 2: Evening Session (5 PM to 11 PM)
```
Setting 17:00 (5 PM):
Time Picker shows:
  17:00
  Hour: 17 (press + from 16)
  Minute: 00

Setting 23:00 (11 PM):
Time Picker shows:
  23:00
  Hour: 23 (press + from 22)
  Minute: 00

Result:
┌─────────────────────────────────────┐
│ Opening Time: 17:00 ✓               │
│ Closing Time: 23:00 ✓               │
│ Status: ✓ Valid (23:00 > 17:00)     │
└─────────────────────────────────────┘
```

### Example 3: Afternoon Session (2 PM to 5 PM)
```
Setting 14:00 (2 PM):
Time Picker shows:
  14:00
  Hour: 14
  Minute: 00

Setting 17:00 (5 PM):
Time Picker shows:
  17:00
  Hour: 17
  Minute: 00

Result:
┌─────────────────────────────────────┐
│ Opening Time: 14:00 ✓               │
│ Closing Time: 17:00 ✓               │
│ Status: ✓ Valid (17:00 > 14:00)     │
└─────────────────────────────────────┘
```

## Complete Session Setup Example

### Full Gym Hours Configuration
```
Morning Session:
  Opening: 06:00 ✓
  Closing: 12:00 ✓
  Status: Valid ✓

Afternoon Session: ☑ Enabled
  Opening: 14:00 ✓
  Closing: 17:00 ✓
  Status: Valid ✓

Evening Session:
  Opening: 17:00 ✓
  Closing: 23:00 ✓
  Status: Valid ✓

Form Validation Status: All Valid ✓
[Complete Setup] ← Button Enabled
```

## Invalid Configurations (Button Disabled)

### Invalid Example 1: Closing Before Opening
```
Morning Session:
  Opening: 17:00
  Closing: 09:00 ← Invalid!
  Status: ✗ Closing before opening

[Complete Setup] ← Button DISABLED
```

### Invalid Example 2: Same Opening and Closing Time
```
Evening Session:
  Opening: 18:00
  Closing: 18:00 ← Invalid!
  Status: ✗ Must be different times

[Complete Setup] ← Button DISABLED
```

### Invalid Example 3: Missing Required Times
```
Morning Session:
  Opening: 06:00 ✓
  Closing: [Not Set] ← Missing!
  Status: ✗ Incomplete

[Complete Setup] ← Button DISABLED
```

## Hour Increment Examples

### Incrementing Hours
```
Starting at 09:00
Click [ + ] once  → 10:00
Click [ + ] again → 11:00
...
Click [ + ] at 23 → 00:00 (wraps around)
Click [ − ] at 00 → 23:00 (wraps around)
```

### Decrementing Hours
```
Starting at 14:00
Click [ − ] once  → 13:00
Click [ − ] again → 12:00
...
Click [ − ] at 00 → 23:00 (wraps around)
Click [ + ] at 23 → 00:00 (wraps around)
```

## Minute Increment Examples

### Incrementing Minutes
```
Starting at :15
Click [ + ] once  → :16
Click [ + ] again → :17
...
Click [ + ] at :59 → :00 (wraps around)
```

### Decrementing Minutes
```
Starting at :30
Click [ − ] once  → :29
Click [ − ] again → :28
...
Click [ − ] at :00 → :59 (wraps around)
```

## Valid Time Ranges by Session

### Morning Session (Typical)
```
Minimum: 05:00 (5 AM)
Maximum: 12:00 (Noon)
Duration: 1-7 hours
```

### Afternoon Session (Optional)
```
Minimum: 12:00 (Noon)
Maximum: 18:00 (6 PM)
Duration: 1-6 hours
```

### Evening Session (Typical)
```
Minimum: 17:00 (5 PM)
Maximum: 23:59 (11:59 PM)
Duration: 1-6 hours
```

## Edge Cases

### Midnight to Early Morning
```
Night Session (if supported):
  Opening: 22:00 (10 PM)
  Closing: 23:59 (11:59 PM)
  Status: ✓ Valid

Next Day Opening:
  Opening: 00:00 (Midnight)
  Closing: 06:00 (6 AM)
  Status: ✓ Valid (separate session)
```

### Very Long Sessions
```
Extended Hours:
  Opening: 06:00 (6 AM)
  Closing: 23:00 (11 PM)
  Duration: 17 hours
  Status: ✓ Valid
```

### Minimal Sessions
```
Short Shift:
  Opening: 18:00 (6 PM)
  Closing: 18:30 (6:30 PM)
  Duration: 30 minutes
  Status: ✓ Valid (18:30 > 18:00)
```

## Common Time Conversions

### AM Times → 24-Hour
```
12:00 AM (Midnight) → 00:00
12:30 AM            → 00:30
1:00 AM             → 01:00
6:00 AM             → 06:00
9:00 AM             → 09:00
11:59 AM            → 11:59
```

### PM Times → 24-Hour
```
12:00 PM (Noon)  → 12:00
12:30 PM         → 12:30
1:00 PM          → 13:00
3:00 PM          → 15:00
5:00 PM          → 17:00
9:00 PM          → 21:00
11:59 PM         → 23:59
```

## Button States

### Enabled State (All Valid)
```
┌──────────────────────────────────────┐
│ [Complete Setup] ✓ Bright Green      │
│ (All times valid and filling)        │
│ (Closing times after opening times)  │
└──────────────────────────────────────┘
```

### Disabled State (Invalid/Incomplete)
```
┌──────────────────────────────────────┐
│ [Complete Setup] ✗ Gray (Disabled)   │
│ (Missing times or invalid config)    │
│ (Closing before opening times)       │
└──────────────────────────────────────┘
```

## Summary

✅ All times in 24-hour format (00:00 - 23:59)
✅ No exceeding 24 hours
✅ Closing time always after opening time
✅ Clear validation feedback
✅ User-friendly time picker
✅ Safe time bounds with wraparound


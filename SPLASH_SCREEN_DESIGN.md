# FitZone Splash Screen - UI Design Guide

## Screen Layout Breakdown

```
┌─────────────────────────────────────┐
│  ╔═══════════════════════════════╗  │
│  ║   FitZone Gradient Background ║  │
│  ║   Green → Teal → Blue         ║  │
│  ║                               ║  │
│  ║         ┌─────────┐           ║  │
│  ║         │ ⭕ 💪  │ (200dp)    ║  │
│  ║         │ White   │           ║  │
│  ║         └─────────┘           ║  │
│  ║                               ║  │
│  ║       ╔═════════════════╗     ║  │
│  ║       ║   FitZone       ║     ║  │
│  ║       ║    (56sp bold)  ║     ║  │
│  ║       ╚═════════════════╝     ║  │
│  ║                               ║  │
│  ║  Smart Gym Management System  ║  │
│  ║        (16sp regular)         ║  │
│  ║                               ║  │
│  ║     ┌──────────┐ ┌──────────┐ ║  │
│  ║     │ ⚡ AI    │ │ 📈 Real │ ║  │
│  ║     │ Powered  │ │ time    │ ║  │
│  ║     │          │ │ Tracking│ ║  │
│  ║     └──────────┘ └──────────┘ ║  │
│  ║                               ║  │
│  ║         ● ● ●                 ║  │
│  ║     (Dot indicators)          ║  │
│  ║                               ║  │
│  ╚═══════════════════════════════╝  │
└─────────────────────────────────────┘
```

## Color Palette

| Element | Color | Hex Code |
|---------|-------|----------|
| Primary Green | Bright Green | #1BB85B |
| Gradient Point 1 | Bright Green | #1BB85B |
| Gradient Point 2 | Lime Green | #00D451 |
| Gradient Point 3 | Teal | #00A896 |
| Gradient Point 4 | Blue | #0066CC |
| Text | White | #FFFFFF |
| Logo Background | White | #FFFFFF |
| Feature Icon Background | White (30% opacity) | #FFFFFF (alpha=0.3) |

## Typography

| Element | Font Size | Font Weight | Color |
|---------|-----------|-------------|-------|
| App Name (FitZone) | 56sp | Bold | White |
| Subtitle | 16sp | Regular | White |
| Feature Labels | 12sp | Medium | White |
| Feature Icons | 28sp | Regular | White |

## Spacing & Sizing

| Element | Size/Spacing |
|---------|--------------|
| Logo Circle Diameter | 200dp |
| Feature Icon Circle | 60dp |
| Dot Indicator Size | 12dp |
| Space between dots | 12dp |
| Space between features | 40dp |
| Space after logo | 60dp |
| Space before features | 80dp |
| Space after features | 60dp |

## Animation Timeline

```
0ms ──────────────────────────────────── 3500ms
│                                        │
├─ Start Trigger                         └─ Navigate to Home
│                                
0ms ──── 800ms ────────────────────────── Fade-in complete
Fade: 0% ─────────────────────────────> 100%
      ├─ Logo opacity increases
      ├─ Title opacity increases
      ├─ Subtitle opacity increases
      ├─ Features opacity increases
      └─ Dots opacity increases

0ms ───── 1000ms ──────────────────────── Scale complete
Scale: 0.8x ──────────────────────────> 1.0x
       └─ Logo grows slightly
```

## Screen States

### Initial State (0ms)
- All elements have 0% opacity
- Logo scale is 0.8x
- No content visible

### Animation State (0-1000ms)
- Fade-in animation runs (0-800ms)
- Scale animation runs (0-1000ms)
- All elements become visible
- Logo grows to full size

### Display State (1000ms-3500ms)
- All elements fully visible at 100% opacity
- Logo at full scale (1.0x)
- User sees complete splash screen

### Exit State (3500ms)
- Navigation triggered to home screen
- Splash route removed from backstack
- Cannot navigate back to splash

## Feature Icons Details

### AI Powered Icon
- Symbol: ⚡ (Lightning bolt emoji)
- Font Size: 28sp
- Background: Semi-transparent white circle (60dp)
- Label: "AI Powered" (12sp)

### Real-time Tracking Icon
- Symbol: 📈 (Chart emoji)
- Font Size: 28sp
- Background: Semi-transparent white circle (60dp)
- Label: "Real-time Tracking" (12sp)

## Gradient Direction

The gradient flows from:
1. **Top-Left**: Bright Green (#1BB85B)
2. **Middle-Top**: Lime Green (#00D451)
3. **Middle-Bottom**: Teal (#00A896)
4. **Bottom-Right**: Blue (#0066CC)

This creates a smooth, visually appealing vertical-to-diagonal transition effect.

## Responsive Behavior

The splash screen is designed to:
- Fill entire screen (fullMaxSize modifier)
- Center all content vertically and horizontally
- Scale content appropriately on different screen sizes
- Maintain proper proportions on various aspect ratios

## Accessibility Considerations

- All text has sufficient contrast (white on gradient background)
- Font sizes are readable (minimum 16sp for body text)
- Tap target sizes meet Material Design guidelines (12dp minimum for dots)
- No critical content relies solely on color differentiation


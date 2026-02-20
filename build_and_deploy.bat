#!/bin/bash
# FitZone App Build & Deploy Script for Android Mobile Device

echo "=========================================="
echo "FitZone Gym Management App"
echo "Build & Deploy Script"
echo "=========================================="
echo ""

# Set project directory
PROJECT_DIR="C:\Users\Himaja Yenikapati\AndroidStudioProjects\gym_fitzone2"

echo "[1/5] Checking Android devices..."
adb devices
echo ""

echo "[2/5] Cleaning previous builds..."
cd "$PROJECT_DIR"
call gradlew.bat clean
echo ""

echo "[3/5] Building the app..."
call gradlew.bat build
echo ""

echo "[4/5] Installing on connected device..."
call gradlew.bat installDebug
echo ""

echo "[5/5] Launching the app..."
adb shell am start -n com.simats.gym_fitzone/.MainActivity
echo ""

echo "=========================================="
echo "✅ Build Complete!"
echo "=========================================="
echo ""
echo "Your FitZone app is now running on your phone!"
echo "You should see the splash screen shortly."
echo ""


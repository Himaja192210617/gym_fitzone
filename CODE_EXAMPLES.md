# FitZone Splash Screen - Code Examples & Customization Guide

## 🎯 Complete Code Reference

### Example 1: Using Custom Vector Drawable Instead of Emoji

**File**: `screens/SplashScreen.kt`

Replace the `DumbbellIcon()` function:

```kotlin
@Composable
fun DumbbellIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_dumbbell),
        contentDescription = "FitZone Logo",
        modifier = Modifier.size(100.dp),
        tint = Color(0xFF1BB85B)
    )
}
```

Then create the vector drawable file:
**File**: `res/drawable/ic_dumbbell.xml`

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="100dp"
    android:height="100dp"
    android:viewportWidth="100"
    android:viewportHeight="100">
    <path
        android:fillColor="#1BB85B"
        android:pathData="M20,45 L35,45 L35,55 L20,55 Z M65,45 L80,45 L80,55 L65,55 Z M40,40 L60,40 L60,60 L40,60 Z" />
</vector>
```

---

### Example 2: Implementing Pager for Multiple Splash Screens

**Add Pager Dependency** to `build.gradle.kts`:

```kotlin
implementation("androidx.compose.foundation:foundation:1.6.0")
```

**Create Enhanced SplashScreen with Pager**:

```kotlin
@Composable
fun SplashScreenWithPager(onNavigateToHome: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(4000)
        if (pagerState.currentPage < 2) {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        } else {
            onNavigateToHome()
        }
    }

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> SplashPage1(startAnimation)
            1 -> SplashPage2(startAnimation)
            2 -> SplashPage3(startAnimation)
        }
    }
}

@Composable
fun SplashPage1(animate: Boolean) {
    // Page 1 content
}

@Composable
fun SplashPage2(animate: Boolean) {
    // Page 2 content
}

@Composable
fun SplashPage3(animate: Boolean) {
    // Page 3 content
}
```

---

### Example 3: Adding Lottie Animation

**Add Lottie Dependency** to `build.gradle.kts`:

```kotlin
implementation("com.airbnb.android:lottie-compose:6.1.0")
```

**Use in SplashScreen**:

```kotlin
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SplashScreenWithLottie(onNavigateToHome: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))
    
    LaunchedEffect(Unit) {
        delay(3500)
        onNavigateToHome()
    }

    Box(modifier = Modifier.fillMaxSize().background(gradientBrush)) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
        )
    }
}
```

---

### Example 4: Dark Mode Support

**Update Theme.kt**:

```kotlin
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1BB85B),
    secondary = Color(0xFF00D451),
    tertiary = Color(0xFF0066CC)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1BB85B),
    secondary = Color(0xFF00D451),
    tertiary = Color(0xFF0066CC)
)

@Composable
fun Gym_fitzoneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

---

### Example 5: Using ViewModel for Splash Logic

**Create SplashViewModel.kt**:

```kotlin
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent

    init {
        viewModelScope.launch {
            delay(3500)
            _navigationEvent.value = NavigationEvent.NavigateToHome
        }
    }

    sealed class NavigationEvent {
        object NavigateToHome : NavigationEvent()
    }
}
```

**Use in SplashScreen**:

```kotlin
@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = viewModel()
) {
    val navigationEvent by viewModel.navigationEvent.collectAsState()

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            SplashViewModel.NavigationEvent.NavigateToHome -> onNavigateToHome()
            null -> {}
        }
    }

    // UI Code
}
```

---

### Example 6: Custom Shimmer Animation

**Create Shimmer Modifier**:

```kotlin
fun Modifier.shimmer(
    duration: Int = 1000,
    delay: Int = 0,
): Modifier {
    return composed {
        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.0f),
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.0f),
        )

        val transition = rememberInfiniteTransition(label = "shimmer")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(duration, delayMillis = delay),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmer_animation"
        )

        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(translateAnimation.value, translateAnimation.value)
        )

        background(brush)
    }
}
```

**Use in SplashScreen**:

```kotlin
Box(
    modifier = Modifier
        .size(200.dp)
        .background(Color.White, shape = CircleShape)
        .shimmer()
)
```

---

## 🎨 Color Customization Guide

### Change the Entire Gradient

**Option 1: Update in SplashScreen.kt**

```kotlin
val gradientBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFF6B6B), // Red
        Color(0xFFFFA500), // Orange
        Color(0xFFFFD93D), // Yellow
        Color(0xFF6BCB77)  // Green
    )
)
```

### Option 2: Create Gradient in Color.kt

```kotlin
// In Color.kt
fun getGradientBrush(): Brush {
    return Brush.linearGradient(
        colors = listOf(
            Color(0xFF1BB85B),
            Color(0xFF00D451),
            Color(0xFF00A896),
            Color(0xFF0066CC)
        )
    )
}
```

Then use in SplashScreen:

```kotlin
val gradientBrush = getGradientBrush()
```

---

## 📱 Animation Customization

### Speed Up Animation

```kotlin
val scale by animateFloatAsState(
    targetValue = if (startAnimation) 1f else 0.8f,
    animationSpec = tween(durationMillis = 500), // Changed from 1000ms
    label = "scale"
)

val alpha by animateFloatAsState(
    targetValue = if (startAnimation) 1f else 0f,
    animationSpec = tween(durationMillis = 400), // Changed from 800ms
    label = "alpha"
)
```

### Add Bounce Animation

```kotlin
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

val scale by animateFloatAsState(
    targetValue = if (startAnimation) 1f else 0.8f,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    ),
    label = "scale"
)
```

### Sequence Multiple Animations

```kotlin
@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    var logoVisible by remember { mutableStateOf(false) }
    var textVisible by remember { mutableStateOf(false) }
    var featuresVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        logoVisible = true
        delay(500)
        textVisible = true
        delay(800)
        featuresVisible = true
        delay(2200)
        onNavigateToHome()
    }

    // Use logoVisible, textVisible, featuresVisible in animations
}
```

---

## 🔧 State Management Options

### Option 1: Simple State (Current Implementation)

```kotlin
var startAnimation by remember { mutableStateOf(false) }
```

**Pros**: Simple, works for basic cases  
**Cons**: Limited for complex logic

### Option 2: ViewModel (Recommended for Large Apps)

```kotlin
class SplashViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState
}

sealed class SplashUiState {
    object Loading : SplashUiState()
    object Ready : SplashUiState()
    object Error : SplashUiState()
}
```

### Option 3: Redux-like Pattern

```kotlin
sealed class SplashAction {
    object StartAnimation : SplashAction()
    object NavigateToHome : SplashAction()
}

data class SplashState(
    val isAnimating: Boolean = false,
    val shouldNavigate: Boolean = false
)

fun splashReducer(state: SplashState, action: SplashAction): SplashState {
    return when (action) {
        SplashAction.StartAnimation -> state.copy(isAnimating = true)
        SplashAction.NavigateToHome -> state.copy(shouldNavigate = true)
    }
}
```

---

## 🧪 Testing Examples

### Unit Test for ViewModel

```kotlin
@ExperimentalCoroutinesApi
class SplashViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        viewModel = SplashViewModel()
    }

    @Test
    fun testNavigationEventFired() = runTest {
        val navigationEvents = mutableListOf<NavigationEvent>()
        
        viewModel.navigationEvent.onEach {
            it?.let { navigationEvents.add(it) }
        }.launchIn(backgroundScope)

        advanceTimeBy(3500)
        
        assert(navigationEvents.isNotEmpty())
    }
}
```

### Compose UI Test

```kotlin
@RunWith(AndroidJUnit4::class)
class SplashScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splashScreenDisplaysCorrectly() {
        composeTestRule.setContent {
            Gym_fitzoneTheme {
                SplashScreen(onNavigateToHome = {})
            }
        }

        composeTestRule.onNodeWithText("FitZone").assertIsDisplayed()
        composeTestRule.onNodeWithText("Smart Gym Management System").assertIsDisplayed()
    }
}
```

---

## 📊 Performance Optimization

### Avoid Recomposition

```kotlin
@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    // BAD: Recreates gradient on every recomposition
    val gradientBrush = Brush.linearGradient(listOf(...))
    
    // GOOD: Create once
    val gradientBrush = remember {
        Brush.linearGradient(listOf(...))
    }
}
```

### Use StableMarker for Custom Objects

```kotlin
@Stable
data class GradientConfig(
    val colors: List<Color>,
    val startOffset: Offset = Offset.Zero,
    val endOffset: Offset = Offset.Infinite, Offset.Infinite)
)
```

### Lazy Composition

```kotlin
@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    var shouldRenderDetails by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(1000)
        shouldRenderDetails = true // Render heavy content after logo
    }

    if (shouldRenderDetails) {
        FeatureIcons() // Only render when needed
    }
}
```

---

## 🚀 Deployment Checklist

- [ ] Gradient colors finalized and approved
- [ ] Animation timings tested on real devices
- [ ] Navigation backstack behavior verified
- [ ] Tested on multiple screen sizes
- [ ] Tested on different API levels (min 24+)
- [ ] Performance profiled in Android Profiler
- [ ] Accessibility tested with TalkBack
- [ ] Color contrast verified (WCAG AA)
- [ ] Memory leaks checked
- [ ] Orientations tested (portrait/landscape)
- [ ] All strings externalized to strings.xml
- [ ] App icon and splash assets finalized

---

## 📚 Further Learning

### Recommended Resources:
1. [Jetpack Compose Documentation](https://developer.android.com/develop/ui/compose)
2. [Material Design 3](https://m3.material.io/)
3. [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-overview.html)
4. [Animation in Compose](https://developer.android.com/develop/ui/compose/animation/overview)

### Advanced Topics:
- State hoisting patterns
- Navigation architecture
- Composition local patterns
- Custom layout creation
- Gesture handling

---

**Last Updated**: February 17, 2026  
**Version**: 1.0


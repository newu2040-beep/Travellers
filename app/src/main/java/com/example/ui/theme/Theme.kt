package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.ui.platform.LocalContext

val LocalCompactMode = staticCompositionLocalOf { false }

val DarkColorScheme = darkColorScheme(
    primary = BurgundyAccent,
    onPrimary = Color.White,
    primaryContainer = BurgundyContainerDark,
    onPrimaryContainer = PastelBlush,
    secondary = PastelGold,
    onSecondary = BurgundyDark,
    secondaryContainer = BurgundyDeepWine,
    onSecondaryContainer = Color.White,
    tertiary = PastelMint,
    onTertiary = Color(0xFF0F3822),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkCardBorder
)

val LavenderDarkColorScheme = darkColorScheme(
    primary = Color(0xFFA78BFA),
    onPrimary = Color(0xFF1E1B4B),
    primaryContainer = Color(0xFF4C1D95),
    onPrimaryContainer = Color(0xFFEDE9FE),
    secondary = Color(0xFFC084FC),
    onSecondary = Color(0xFF3B0764),
    secondaryContainer = Color(0xFF581C87),
    onSecondaryContainer = Color(0xFFF3E8FF),
    tertiary = Color(0xFFF472B6),
    onTertiary = Color(0xFF831843),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkCardBorder
)

val SageDarkColorScheme = darkColorScheme(
    primary = Color(0xFF34D399),
    onPrimary = Color(0xFF064E3B),
    primaryContainer = Color(0xFF065F46),
    onPrimaryContainer = Color(0xFFD1FAE5),
    secondary = Color(0xFF6EE7B7),
    onSecondary = Color(0xFF022C22),
    secondaryContainer = Color(0xFF047857),
    onSecondaryContainer = Color(0xFFA7F3D0),
    tertiary = Color(0xFF38BDF8),
    onTertiary = Color(0xFF0C4A6E),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkCardBorder
)

val CoralDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFB923C),
    onPrimary = Color(0xFF431407),
    primaryContainer = Color(0xFF7C2D12),
    onPrimaryContainer = Color(0xFFFFEDD5),
    secondary = Color(0xFFFDBA74),
    onSecondary = Color(0xFF7C2D12),
    secondaryContainer = Color(0xFF9A3412),
    onSecondaryContainer = Color(0xFFFED7AA),
    tertiary = Color(0xFFFACC15),
    onTertiary = Color(0xFF713F12),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkCardBorder
)

val OceanDarkColorScheme = darkColorScheme(
    primary = Color(0xFF38BDF8),
    onPrimary = Color(0xFF0C4A6E),
    primaryContainer = Color(0xFF075985),
    onPrimaryContainer = Color(0xFFE0F2FE),
    secondary = Color(0xFF7DD3FC),
    onSecondary = Color(0xFF0369A1),
    secondaryContainer = Color(0xFF0284C7),
    onSecondaryContainer = Color(0xFFBAE6FD),
    tertiary = Color(0xFF22D3EE),
    onTertiary = Color(0xFF164E63),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkCardBorder
)

val LightColorScheme = lightColorScheme(
    primary = BurgundyPrimary,
    onPrimary = Color.White,
    primaryContainer = BurgundyContainerLight,
    onPrimaryContainer = BurgundyDark,
    secondary = BurgundyLight,
    onSecondary = Color.White,
    secondaryContainer = PastelPeach,
    onSecondaryContainer = BurgundyDark,
    tertiary = StatusGreen,
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = PastelSage,
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

enum class AppTheme(val label: String, val primaryColor: Color, val containerColor: Color) {
    BURGUNDY("Burgundy & Blush", Color(0xFF6B1D2F), Color(0xFFFDEAEB)),
    LAVENDER("Lavender Breeze", Color(0xFF6A4C93), Color(0xFFF1EDF8)),
    SAGE("Sage Green", Color(0xFF2E6F40), Color(0xFFEAF1EB)),
    CORAL("Sunset Coral", Color(0xFFC04A27), Color(0xFFFEEDE3)),
    OCEAN("Ocean Blue", Color(0xFF1E6091), Color(0xFFE9F1FA)),
    DYNAMIC("Material You Dynamic", Color(0xFF6750A4), Color(0xFFEADDFF))
}

val LavenderLightColorScheme = lightColorScheme(
    primary = Color(0xFF6A4C93),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF1EDF8),
    onPrimaryContainer = Color(0xFF331E4B),
    secondary = Color(0xFF8B5CF6),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE9D5FF),
    onSecondaryContainer = Color(0xFF3B0764),
    tertiary = Color(0xFFEC4899),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = Color(0xFFF3E8FF),
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

val SageLightColorScheme = lightColorScheme(
    primary = Color(0xFF2E6F40),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEAF1EB),
    onPrimaryContainer = Color(0xFF13381E),
    secondary = Color(0xFF10B981),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD1FAE5),
    onSecondaryContainer = Color(0xFF064E3B),
    tertiary = Color(0xFF059669),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = Color(0xFFE0F2FE),
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

val CoralLightColorScheme = lightColorScheme(
    primary = Color(0xFFC04A27),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFEEDE3),
    onPrimaryContainer = Color(0xFF531707),
    secondary = Color(0xFFF97316),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFEDD5),
    onSecondaryContainer = Color(0xFF7C2D12),
    tertiary = Color(0xFFEAB308),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = Color(0xFFFFF7ED),
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

val OceanLightColorScheme = lightColorScheme(
    primary = Color(0xFF1E6091),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE9F1FA),
    onPrimaryContainer = Color(0xFF0B2B43),
    secondary = Color(0xFF0284C7),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2FE),
    onSecondaryContainer = Color(0xFF075985),
    tertiary = Color(0xFF06B6D4),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = Color(0xFFF0F9FF),
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

@Composable
fun TravellersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    appTheme: AppTheme = AppTheme.BURGUNDY,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        appTheme == AppTheme.DYNAMIC && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> when (appTheme) {
            AppTheme.BURGUNDY -> DarkColorScheme
            AppTheme.LAVENDER -> LavenderDarkColorScheme
            AppTheme.SAGE -> SageDarkColorScheme
            AppTheme.CORAL -> CoralDarkColorScheme
            AppTheme.OCEAN -> OceanDarkColorScheme
            AppTheme.DYNAMIC -> DarkColorScheme
        }
        else -> when (appTheme) {
            AppTheme.BURGUNDY -> LightColorScheme
            AppTheme.LAVENDER -> LavenderLightColorScheme
            AppTheme.SAGE -> SageLightColorScheme
            AppTheme.CORAL -> CoralLightColorScheme
            AppTheme.OCEAN -> OceanLightColorScheme
            AppTheme.DYNAMIC -> LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    appTheme: AppTheme = AppTheme.BURGUNDY,
    content: @Composable () -> Unit
) {
    TravellersTheme(darkTheme = darkTheme, appTheme = appTheme, content = content)
}


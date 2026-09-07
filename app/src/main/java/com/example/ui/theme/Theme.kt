package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.ui.platform.LocalContext

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

@Composable
fun TravellersTheme(
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

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    TravellersTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}


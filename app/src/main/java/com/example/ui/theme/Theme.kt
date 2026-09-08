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

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalCompactMode = staticCompositionLocalOf { false }
val LocalSolidMode = staticCompositionLocalOf { false }
val LocalCornerRadius = staticCompositionLocalOf { (-1).dp }

enum class AppFont(val label: String, val fontFamily: FontFamily) {
    SANS("Modern Sans-Serif", FontFamily.SansSerif),
    SERIF("Elegant Serif", FontFamily.Serif),
    MONOSPACE("Clean Monospace", FontFamily.Monospace),
    CURSIVE("Expressive Cursive", FontFamily.Cursive)
}

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
    PLUM("Midnight Plum", Color(0xFF701A75), Color(0xFFFDF4FF)),
    EMERALD("Emerald Forest", Color(0xFF047857), Color(0xFFECFDF5)),
    GOLDEN("Golden Sand", Color(0xFFB45309), Color(0xFFFEF3C7)),
    ROSE("Sweet Rose", Color(0xFFBE185D), Color(0xFFFFF1F2)),
    DYNAMIC("Material You Dynamic", Color(0xFF6750A4), Color(0xFFEADDFF))
}

val PlumLightColorScheme = lightColorScheme(
    primary = Color(0xFF701A75),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFDF4FF),
    onPrimaryContainer = Color(0xFF4A044E),
    secondary = Color(0xFFD946EF),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFAE8FF),
    onSecondaryContainer = Color(0xFF701A75),
    tertiary = Color(0xFF0EA5E9),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = Color(0xFFFDF4FF),
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

val PlumDarkColorScheme = darkColorScheme(
    primary = Color(0xFFE879F9),
    onPrimary = Color(0xFF4A044E),
    primaryContainer = Color(0xFF701A75),
    onPrimaryContainer = Color(0xFFFDF4FF),
    secondary = Color(0xFFF472B6),
    onSecondary = Color(0xFF500724),
    secondaryContainer = Color(0xFF881337),
    onSecondaryContainer = Color(0xFFFFF1F2),
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

val EmeraldLightColorScheme = lightColorScheme(
    primary = Color(0xFF047857),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFECFDF5),
    onPrimaryContainer = Color(0xFF064E3B),
    secondary = Color(0xFF10B981),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD1FAE5),
    onSecondaryContainer = Color(0xFF047857),
    tertiary = Color(0xFFF59E0B),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = Color(0xFFECFDF5),
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

val EmeraldDarkColorScheme = darkColorScheme(
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

val GoldenLightColorScheme = lightColorScheme(
    primary = Color(0xFFB45309),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFEF3C7),
    onPrimaryContainer = Color(0xFF78350F),
    secondary = Color(0xFFF59E0B),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFEF3C7),
    onSecondaryContainer = Color(0xFF92400E),
    tertiary = Color(0xFF10B981),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = Color(0xFFFFFBEB),
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

val GoldenDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFBBF24),
    onPrimary = Color(0xFF78350F),
    primaryContainer = Color(0xFF92400E),
    onPrimaryContainer = Color(0xFFFEF3C7),
    secondary = Color(0xFFF59E0B),
    onSecondary = Color(0xFF451A03),
    secondaryContainer = Color(0xFF78350F),
    onSecondaryContainer = Color(0xFFFEF3C7),
    tertiary = Color(0xFF34D399),
    onTertiary = Color(0xFF064E3B),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkCardBorder
)

val RoseLightColorScheme = lightColorScheme(
    primary = Color(0xFFBE185D),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFF1F2),
    onPrimaryContainer = Color(0xFF4D0520),
    secondary = Color(0xFFF43F5E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE4E6),
    onSecondaryContainer = Color(0xFF9F1239),
    tertiary = Color(0xFF8B5CF6),
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = Color(0xFFFFF1F2),
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

val RoseDarkColorScheme = darkColorScheme(
    primary = Color(0xFFF472B6),
    onPrimary = Color(0xFF500724),
    primaryContainer = Color(0xFF881337),
    onPrimaryContainer = Color(0xFFFFF1F2),
    secondary = Color(0xFFFB7185),
    onSecondary = Color(0xFF4C0519),
    secondaryContainer = Color(0xFF9F1239),
    onSecondaryContainer = Color(0xFFFFE4E6),
    tertiary = Color(0xFFA78BFA),
    onTertiary = Color(0xFF2E1065),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkCardBorder
)

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

fun getTypographyForFont(fontFamily: FontFamily): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 38.sp,
            letterSpacing = (-0.5).sp
        ),
        displayMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 34.sp,
            letterSpacing = (-0.25).sp
        ),
        headlineLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            letterSpacing = 0.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.sp
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.15.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.25.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),
        labelLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),
        labelSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.5.sp
        )
    )
}

@Composable
fun TravellersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    appTheme: AppTheme = AppTheme.BURGUNDY,
    appFont: AppFont = AppFont.SANS,
    isSolidMode: Boolean = false,
    customCornerRadius: Dp = (-1).dp,
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
            AppTheme.PLUM -> PlumDarkColorScheme
            AppTheme.EMERALD -> EmeraldDarkColorScheme
            AppTheme.GOLDEN -> GoldenDarkColorScheme
            AppTheme.ROSE -> RoseDarkColorScheme
            AppTheme.DYNAMIC -> DarkColorScheme
        }
        else -> when (appTheme) {
            AppTheme.BURGUNDY -> LightColorScheme
            AppTheme.LAVENDER -> LavenderLightColorScheme
            AppTheme.SAGE -> SageLightColorScheme
            AppTheme.CORAL -> CoralLightColorScheme
            AppTheme.OCEAN -> OceanLightColorScheme
            AppTheme.PLUM -> PlumLightColorScheme
            AppTheme.EMERALD -> EmeraldLightColorScheme
            AppTheme.GOLDEN -> GoldenLightColorScheme
            AppTheme.ROSE -> RoseLightColorScheme
            AppTheme.DYNAMIC -> LightColorScheme
        }
    }

    val dynamicTypography = getTypographyForFont(appFont.fontFamily)

    CompositionLocalProvider(
        LocalSolidMode provides isSolidMode,
        LocalCornerRadius provides customCornerRadius
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = dynamicTypography,
            content = content
        )
    }
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    appTheme: AppTheme = AppTheme.BURGUNDY,
    content: @Composable () -> Unit
) {
    TravellersTheme(darkTheme = darkTheme, appTheme = appTheme, content = content)
}


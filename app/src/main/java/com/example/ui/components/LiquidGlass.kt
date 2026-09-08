package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * iOS 27 Style Liquid Glass Card Container.
 * Features a translucent frosted glass surface, dynamic light sheen highlight,
 * soft ambient shadow, and clean rounded corners.
 */
@Composable
fun LiquidGlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),
    elevation: Dp = 6.dp,
    borderWidth: Dp = 1.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val isDark = MaterialTheme.colorScheme.background.red < 0.5f
    val isSolid = com.example.ui.theme.LocalSolidMode.current
    val overrideRadius = com.example.ui.theme.LocalCornerRadius.current

    // Resolve shape based on manual corner reduction
    val resolvedShape = if (overrideRadius >= 0.dp) RoundedCornerShape(overrideRadius) else shape

    // Frosted glass background brush with subtle sheen OR Solid mode background
    val glassBgBrush = if (isSolid) {
        Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.surface
            )
        )
    } else {
        Brush.linearGradient(
            colors = if (isDark) {
                listOf(
                    Color(0xFF282836).copy(alpha = 0.75f),
                    Color(0xFF1E1E28).copy(alpha = 0.60f)
                )
            } else {
                listOf(
                    Color.White.copy(alpha = 0.85f),
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.65f)
                )
            }
        )
    }

    // Liquid reflective border highlight brush OR Solid mode border
    val glassBorderBrush = if (isSolid) {
        Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = if (isDark) {
                listOf(
                    Color.White.copy(alpha = 0.35f),
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.30f),
                    Color.White.copy(alpha = 0.10f)
                )
            } else {
                listOf(
                    Color.White.copy(alpha = 0.90f),
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                    Color.White.copy(alpha = 0.40f)
                )
            }
        )
    }

    val baseModifier = modifier
        .shadow(elevation, resolvedShape, clip = false)
        .clip(resolvedShape)
        .background(glassBgBrush)
        .border(borderWidth, glassBorderBrush, resolvedShape)

    val finalModifier = if (onClick != null) {
        baseModifier.clickable { onClick() }
    } else baseModifier

    Box(
        modifier = finalModifier,
        contentAlignment = Alignment.Center,
        content = content
    )
}

/**
 * Translucent Glass Badge / Pill
 */
@Composable
fun LiquidGlassBadge(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f),
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    shape: Shape = RoundedCornerShape(14.dp),
    content: @Composable () -> Unit
) {
    val isSolid = com.example.ui.theme.LocalSolidMode.current
    val overrideRadius = com.example.ui.theme.LocalCornerRadius.current

    val resolvedShape = if (overrideRadius >= 0.dp) RoundedCornerShape(overrideRadius) else shape
    val resolvedBgColor = if (isSolid) MaterialTheme.colorScheme.primaryContainer else backgroundColor

    Box(
        modifier = modifier
            .clip(resolvedShape)
            .background(resolvedBgColor)
            .border(
                0.75.dp,
                if (isSolid) {
                    Brush.linearGradient(listOf(MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)))
                } else {
                    Brush.linearGradient(
                        listOf(
                            Color.White.copy(alpha = 0.6f),
                            Color.White.copy(alpha = 0.1f)
                        )
                    )
                },
                resolvedShape
            )
            .padding(horizontal = 10.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

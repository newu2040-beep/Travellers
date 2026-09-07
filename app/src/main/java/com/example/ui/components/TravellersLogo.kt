package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R

/**
 * Official Travellers circular sailboat brand emblem.
 * Strictly preserves the circular composition, golden circular rim,
 * deep blue background, golden sailboat, sails, and ocean waves.
 */
@Composable
fun TravellersLogo(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    showBorder: Boolean = true
) {
    Box(
        modifier = modifier
            .size(size)
            .testTag("travellers_logo"),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_sailboat_icon),
            contentDescription = "Travellers Official Sailboat Icon",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .then(
                    if (showBorder) {
                        Modifier.border(
                            width = (size.value * 0.025f).coerceAtLeast(1f).dp,
                            color = Color(0xFFD4AF37),
                            shape = CircleShape
                        )
                    } else Modifier
                )
        )
    }
}

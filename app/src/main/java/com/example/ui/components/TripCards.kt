package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.TripEntity
import com.example.data.model.AppCurrency
import com.example.ui.theme.BurgundyPrimary

@Composable
fun NextTripFeaturedCard(
    trip: TripEntity,
    onTripClick: (TripEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val coverRes = if (trip.coverImageRes != 0) trip.coverImageRes else R.drawable.img_kathmandu_hero

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(138.dp)
            .shadow(6.dp, shape = RoundedCornerShape(24.dp), spotColor = Color.Black.copy(alpha = 0.12f))
            .clip(RoundedCornerShape(24.dp))
            .clickable { onTripClick(trip) }
            .testTag("next_trip_featured_card")
    ) {
        // Background Hero Image
        Image(
            painter = painterResource(id = coverRes),
            contentDescription = trip.destination,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay for high-contrast text legibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.75f),
                            Color.Black.copy(alpha = 0.35f),
                            Color.Black.copy(alpha = 0.15f)
                        )
                    )
                )
        )

        // Content
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                // Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.NearMe,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Next Trip",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = trip.destination,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FlightTakeoff,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.85f),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${trip.startDate} – ${trip.endDate} · ${trip.durationDays} days",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        ),
                        maxLines = 1
                    )
                }
            }

            // Circular Forward Action Button
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.28f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "View Trip Details",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun RecentTripItemCard(
    trip: TripEntity,
    spentAmount: Double,
    currency: AppCurrency,
    onTripClick: (TripEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val coverRes = if (trip.coverImageRes != 0) trip.coverImageRes else R.drawable.img_kathmandu_hero
    val progress = if (trip.totalBudget > 0) (spentAmount / trip.totalBudget).toFloat().coerceIn(0f, 1f) else 0f
    val percent = (progress * 100).toInt()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onTripClick(trip) }
            .padding(12.dp)
            .testTag("recent_trip_card_${trip.id}"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Trip thumbnail
        Image(
            painter = painterResource(id = coverRes),
            contentDescription = trip.destination,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = trip.destination,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${trip.startDate} – ${trip.endDate} · ${trip.durationDays} days",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            )
        }

        // Spending & Progress
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = currency.format(spentAmount),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = BurgundyPrimary,
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .width(42.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = BurgundyPrimary,
                    trackColor = BurgundyPrimary.copy(alpha = 0.15f),
                    strokeCap = StrokeCap.Round
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "$percent%",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

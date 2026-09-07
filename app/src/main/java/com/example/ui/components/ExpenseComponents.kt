package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ExpenseEntity
import com.example.data.model.AppCurrency
import com.example.data.model.ExpenseCategory
import com.example.ui.theme.BurgundyDark
import com.example.ui.theme.BurgundyPrimary

fun getCategoryIcon(categoryName: String): ImageVector {
    return when (categoryName.lowercase()) {
        "flights" -> Icons.Default.Flight
        "hotels" -> Icons.Default.Hotel
        "food", "food & drinks" -> Icons.Default.Restaurant
        "transport" -> Icons.Default.DirectionsBus
        "activities" -> Icons.Default.ConfirmationNumber
        "shopping" -> Icons.Default.ShoppingBag
        else -> Icons.Default.Category
    }
}

fun getCategoryColor(categoryName: String): Color {
    val cat = ExpenseCategory.fromString(categoryName)
    return Color(cat.pastelColorHex)
}

@Composable
fun ExpenseRowItem(
    expense: ExpenseEntity,
    currency: AppCurrency,
    onExpenseClick: (ExpenseEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColor = getCategoryColor(expense.category)
    val categoryIcon = getCategoryIcon(expense.category)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onExpenseClick(expense) }
            .padding(horizontal = 14.dp, vertical = 12.dp)
            .testTag("expense_row_${expense.id}"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Category Icon Box
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(categoryColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = categoryIcon,
                contentDescription = expense.category,
                tint = BurgundyDark,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Title & Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = expense.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp
                ),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${expense.dateDisplay} · ${expense.participantsCount} people",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            )
        }

        // Amount
        Text(
            text = currency.format(expense.amount),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )
        )
    }
}

@Composable
fun CategoryCard(
    category: ExpenseCategory,
    totalSpent: Double,
    currency: AppCurrency,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryIcon = getCategoryIcon(category.title)
    val categoryColor = Color(category.pastelColorHex)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(10.dp)
            .testTag("category_card_${category.name.lowercase()}"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(categoryColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = categoryIcon,
                contentDescription = category.title,
                tint = BurgundyDark,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = category.title,
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = currency.format(totalSpent),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = if (totalSpent > 0) BurgundyPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            ),
            maxLines = 1
        )
    }
}

package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.ExpenseCategory
import com.example.ui.MainViewModel
import com.example.ui.components.ExpenseRowItem
import com.example.ui.theme.BurgundyPrimary

@Composable
fun ExpensesScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val allExpenses by viewModel.allExpenses.collectAsStateWithLifecycle()
    val allTrips by viewModel.allTrips.collectAsStateWithLifecycle()
    val currentCurrency by viewModel.selectedCurrency.collectAsStateWithLifecycle()

    var selectedCategoryFilter by remember { mutableStateOf("ALL") }
    var filterText by remember { mutableStateOf("") }

    val filteredExpenses = allExpenses.filter { expense ->
        val matchesCategory = selectedCategoryFilter == "ALL" ||
                expense.category.equals(selectedCategoryFilter, ignoreCase = true)
        val matchesText = filterText.isBlank() ||
                expense.title.contains(filterText, ignoreCase = true) ||
                expense.category.contains(filterText, ignoreCase = true)
        matchesCategory && matchesText
    }

    val totalSpent = filteredExpenses.sumOf { it.amount }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title Bar & Add Button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Expenses Tracker",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 24.sp
                        )
                    )
                    Text(
                        text = "Total Logged: ${currentCurrency.format(totalSpent)}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = BurgundyPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                IconButton(
                    onClick = { viewModel.showAddExpenseDialog.value = true },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(BurgundyPrimary)
                        .testTag("expenses_screen_add_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Expense",
                        tint = Color.White
                    )
                }
            }
        }

        // Search text field
        item {
            OutlinedTextField(
                value = filterText,
                onValueChange = { filterText = it },
                placeholder = { Text("Filter expenses...", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BurgundyPrimary,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                singleLine = true
            )
        }

        // Category filter chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("ALL" to "All", "Food" to "Food", "Hotels" to "Hotels", "Flights" to "Flights", "Activities" to "Activities").forEach { (code, label) ->
                    FilterChip(
                        selected = selectedCategoryFilter == code,
                        onClick = { selectedCategoryFilter = code },
                        label = { Text(label, fontSize = 11.sp) },
                        shape = RoundedCornerShape(16.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BurgundyPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Expenses List
        items(
            count = filteredExpenses.size,
            key = { index -> filteredExpenses[index].id }
        ) { index ->
            val expense = filteredExpenses[index]
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    ExpenseRowItem(
                        expense = expense,
                        currency = currentCurrency,
                        onExpenseClick = { }
                    )
                }
                IconButton(
                    onClick = { viewModel.deleteExpense(expense.id) },
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Expense",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

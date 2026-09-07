package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.ExpenseEntity
import com.example.ui.MainViewModel
import com.example.ui.components.CategoryDonutChart
import com.example.ui.components.ExpenseRowItem
import com.example.ui.components.LiquidGlassCard
import com.example.ui.components.SpendingBarChart

@Composable
fun ExpensesScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val allExpenses by viewModel.allExpenses.collectAsStateWithLifecycle()
    val currentCurrency by viewModel.selectedCurrency.collectAsStateWithLifecycle()
    val isCompact = com.example.ui.theme.LocalCompactMode.current
    val primaryColor = MaterialTheme.colorScheme.primary

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var selectedCategoryFilter by remember { mutableStateOf("ALL") }
    var filterText by remember { mutableStateOf("") }
    var pasteInput by remember { mutableStateOf("") }
    var showPasteBox by remember { mutableStateOf(false) }

    val filteredExpenses = allExpenses.filter { expense ->
        val matchesCategory = selectedCategoryFilter == "ALL" ||
                expense.category.equals(selectedCategoryFilter, ignoreCase = true)
        val matchesText = filterText.isBlank() ||
                expense.title.contains(filterText, ignoreCase = true) ||
                expense.category.contains(filterText, ignoreCase = true)
        matchesCategory && matchesText
    }

    val totalSpent = filteredExpenses.sumOf { it.amount }
    val itemSpacing = if (isCompact) 12.dp else 16.dp

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        contentPadding = PaddingValues(
            start = if (isCompact) 14.dp else 20.dp,
            end = if (isCompact) 14.dp else 20.dp,
            top = 12.dp,
            bottom = 100.dp
        ),
        verticalArrangement = Arrangement.spacedBy(itemSpacing)
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
                            fontSize = if (isCompact) 20.sp else 24.sp
                        )
                    )
                    Text(
                        text = "Total Logged: ${currentCurrency.format(totalSpent)}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = primaryColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = if (isCompact) 13.sp else 14.sp
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Copy All Action
                    IconButton(
                        onClick = {
                            val historyText = filteredExpenses.joinToString("\n") { exp ->
                                "• ${exp.title}: ${currentCurrency.symbol}${exp.amount} (${exp.category} - ${exp.dateDisplay})"
                            }
                            clipboardManager.setText(AnnotatedString("Trip Expenses History:\n$historyText\nTotal: ${currentCurrency.format(totalSpent)}"))
                            Toast.makeText(context, "Expenses history copied to clipboard! 📋", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Expenses History",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = { viewModel.showAddExpenseDialog.value = true },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(primaryColor)
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
        }

        // Graph Charts Section (Liquid Glass)
        item {
            LiquidGlassCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Category Analytics",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = if (isCompact) 14.sp else 16.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CategoryDonutChart(
                        expenses = filteredExpenses,
                        currencySymbol = currentCurrency.symbol
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SpendingBarChart(
                        expenses = filteredExpenses,
                        currencySymbol = currentCurrency.symbol
                    )
                }
            }
        }

        // Quick Paste & Parse Box Toggle Card
        item {
            LiquidGlassCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showPasteBox = !showPasteBox },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ContentPaste,
                                contentDescription = null,
                                tint = primaryColor,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Quick Paste & Auto-Add Expense",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 13.sp
                                )
                            )
                        }
                        Text(
                            text = if (showPasteBox) "▲ Hide" else "▼ Paste",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = primaryColor,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    if (showPasteBox) {
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = pasteInput,
                            onValueChange = { pasteInput = it },
                            placeholder = { Text("Paste text e.g. 'Airport Taxi $35'", fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                val clipData = clipboardManager.getText()?.text ?: pasteInput
                                val textToParse = pasteInput.ifBlank { clipData }
                                if (textToParse.isNotBlank()) {
                                    // Parse title & amount
                                    val amountMatch = Regex("""\$?(\d+(?:\.\d+)?)""").find(textToParse)
                                    val parsedAmount = amountMatch?.groupValues?.get(1)?.toDoubleOrNull() ?: 20.0
                                    val parsedTitle = textToParse.replace(Regex("""\$?\d+(?:\.\d+)?"""), "").trim().ifBlank { "Pasted Expense" }

                                    val currentTripId = viewModel.selectedTripId.value ?: 1L
                                    viewModel.addExpense(
                                        ExpenseEntity(
                                            tripId = currentTripId,
                                            title = parsedTitle,
                                            amount = parsedAmount,
                                            currencyCode = currentCurrency.code,
                                            category = "Food",
                                            dateDisplay = "Today",
                                            payerName = "You"
                                        )
                                    )
                                    Toast.makeText(context, "Added '$parsedTitle' (${currentCurrency.symbol}$parsedAmount)", Toast.LENGTH_SHORT).show()
                                    pasteInput = ""
                                    showPasteBox = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth().height(40.dp)
                        ) {
                            Text("Parse & Add Expense", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Search text field
        item {
            OutlinedTextField(
                value = filterText,
                onValueChange = { filterText = it },
                placeholder = { Text("Filter expenses history...", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
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
                            selectedContainerColor = primaryColor,
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
                        onExpenseClick = {
                            clipboardManager.setText(AnnotatedString("${expense.title}: ${currentCurrency.symbol}${expense.amount} (${expense.category})"))
                            Toast.makeText(context, "Copied expense details! 📋", Toast.LENGTH_SHORT).show()
                        }
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

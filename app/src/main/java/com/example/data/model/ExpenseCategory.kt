package com.example.data.model

enum class ExpenseCategory(
    val title: String,
    val iconName: String,
    val pastelColorHex: Long
) {
    FLIGHTS("Flights", "flight", 0xFFFDE8E9),
    HOTELS("Hotels", "hotel", 0xFFFEEDE3),
    FOOD("Food", "restaurant", 0xFFE5F5EC),
    TRANSPORT("Transport", "directions_bus", 0xFFE9F1FA),
    ACTIVITIES("Activities", "photo_camera", 0xFFF1EDF8),
    SHOPPING("Shopping", "shopping_bag", 0xFFFEF8E7),
    OTHER("Other", "category", 0xFFCCD9CE);

    companion object {
        fun fromString(value: String): ExpenseCategory {
            return entries.firstOrNull {
                it.name.equals(value, ignoreCase = true) || it.title.equals(value, ignoreCase = true)
            } ?: OTHER
        }
    }
}

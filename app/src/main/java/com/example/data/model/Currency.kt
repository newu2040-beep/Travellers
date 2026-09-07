package com.example.data.model

enum class AppCurrency(val code: String, val symbol: String, val label: String) {
    USD("USD", "$", "US Dollar ($)"),
    NPR("NPR", "₨", "Nepalese Rupee (₨)"),
    INR("INR", "₹", "Indian Rupee (₹)"),
    EUR("EUR", "€", "Euro (€)"),
    GBP("GBP", "£", "British Pound (£)"),
    AUD("AUD", "A$", "Australian Dollar (A$)"),
    CAD("CAD", "C$", "Canadian Dollar (C$)"),
    JPY("JPY", "¥", "Japanese Yen (¥)"),
    SAR("SAR", "﷼", "Saudi Riyal (﷼)"),
    AED("AED", "د.إ", "UAE Dirham (د.إ)");

    fun format(amount: Double): String {
        return if (amount % 1.0 == 0.0) {
            "$symbol${amount.toLong()}"
        } else {
            String.format(java.util.Locale.US, "%s%.2f", symbol, amount)
        }
    }

    companion object {
        fun fromCode(code: String): AppCurrency {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: USD
        }
    }
}

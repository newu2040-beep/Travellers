package com.example.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.data.local.ExpenseEntity
import com.example.data.local.TripEntity
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter

object ExportHelper {
    fun exportData(context: Context, trips: List<TripEntity>, expenses: List<ExpenseEntity>, format: String) {
        val fileName = "travellers_export.${format.lowercase()}"
        val file = File(context.cacheDir, fileName)
        
        when (format.uppercase()) {
            "JSON" -> {
                val root = JSONObject()
                val tripsArray = JSONArray()
                trips.forEach { trip ->
                    val tripObj = JSONObject()
                    tripObj.put("destination", trip.destination)
                    tripObj.put("country", trip.country)
                    tripObj.put("startDate", trip.startDate)
                    tripObj.put("endDate", trip.endDate)
                    tripObj.put("budget", trip.totalBudget)
                    
                    val tripExpenses = JSONArray()
                    expenses.filter { it.tripId == trip.id }.forEach { exp ->
                        val expObj = JSONObject()
                        expObj.put("title", exp.title)
                        expObj.put("amount", exp.amount)
                        expObj.put("category", exp.category)
                        expObj.put("date", exp.dateDisplay)
                        tripExpenses.put(expObj)
                    }
                    tripObj.put("expenses", tripExpenses)
                    tripsArray.put(tripObj)
                }
                root.put("trips", tripsArray)
                file.writeText(root.toString(4))
            }
            "CSV" -> {
                val writer = FileWriter(file)
                writer.append("Trip,Country,Expense Title,Category,Amount,Date\n")
                trips.forEach { trip ->
                    val tripExpenses = expenses.filter { it.tripId == trip.id }
                    if (tripExpenses.isEmpty()) {
                        writer.append("${trip.destination},${trip.country},,,, \n")
                    } else {
                        tripExpenses.forEach { exp ->
                            writer.append("${trip.destination},${trip.country},${exp.title},${exp.category},${exp.amount},${exp.dateDisplay}\n")
                        }
                    }
                }
                writer.flush()
                writer.close()
            }
            "TXT" -> {
                val sb = java.lang.StringBuilder()
                sb.append("TRAVELLERS DATA EXPORT\n\n")
                trips.forEach { trip ->
                    sb.append("Trip to ${trip.destination}, ${trip.country}\n")
                    sb.append("Budget: ${trip.totalBudget}\n")
                    val tripExpenses = expenses.filter { it.tripId == trip.id }
                    if (tripExpenses.isNotEmpty()) {
                        sb.append("Expenses:\n")
                        tripExpenses.forEach { exp ->
                            sb.append("  - ${exp.title} (${exp.category}): ${exp.amount}\n")
                        }
                    }
                    sb.append("\n")
                }
                file.writeText(sb.toString())
            }
        }
        
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = if (format == "JSON") "application/json" else "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Export Data"))
    }
}

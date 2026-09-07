package com.example.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import android.graphics.Canvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.data.local.ExpenseEntity
import com.example.data.local.TripEntity
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ExportHelper {
    fun exportData(context: Context, trips: List<TripEntity>, expenses: List<ExpenseEntity>, format: String, currencySymbol: String = "$") {
        val fileName = "travellers_export.${format.lowercase()}"
        val file = File(context.cacheDir, fileName)
        
        when (format.uppercase()) {
            "PDF" -> {
                exportPdfInvoice(context, trips, expenses, currencySymbol)
                return
            }
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
                    sb.append("Budget: $currencySymbol${trip.totalBudget}\n")
                    val tripExpenses = expenses.filter { it.tripId == trip.id }
                    if (tripExpenses.isNotEmpty()) {
                        sb.append("Expenses:\n")
                        tripExpenses.forEach { exp ->
                            sb.append("  - ${exp.title} (${exp.category}): $currencySymbol${exp.amount}\n")
                        }
                    }
                    sb.append("\n")
                }
                file.writeText(sb.toString())
            }
        }
        
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = when (format.uppercase()) {
                "JSON" -> "application/json"
                "CSV" -> "text/csv"
                else -> "text/plain"
            }
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Export Data"))
    }

    private fun exportPdfInvoice(context: Context, trips: List<TripEntity>, expenses: List<ExpenseEntity>, currencySymbol: String) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 Size
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paint = Paint()
        val titlePaint = Paint()
        val headerBgPaint = Paint()
        val linePaint = Paint()

        // Header Background Bar (Burgundy)
        headerBgPaint.color = AndroidColor.parseColor("#6B1D2F")
        canvas.drawRect(0f, 0f, 595f, 90f, headerBgPaint)

        // Title Text
        titlePaint.color = AndroidColor.WHITE
        titlePaint.textSize = 22f
        titlePaint.isFakeBoldText = true
        canvas.drawText("TRAVELLERS EXPENSE INVOICE", 30f, 45f, titlePaint)

        titlePaint.textSize = 12f
        titlePaint.isFakeBoldText = false
        val currentDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
        canvas.drawText("Statement Date: $currentDate", 30f, 68f, titlePaint)

        // Body Text Paint
        paint.color = AndroidColor.parseColor("#1C1917")
        paint.textSize = 12f

        linePaint.color = AndroidColor.parseColor("#EFEBE4")
        linePaint.strokeWidth = 1f

        var yPosition = 120f

        // Overview Summary
        val totalSpentAll = expenses.sumOf { it.amount }
        val totalBudgetAmount = trips.sumOf { it.totalBudget }

        paint.isFakeBoldText = true
        paint.textSize = 14f
        canvas.drawText("Executive Financial Summary", 30f, yPosition, paint)
        yPosition += 20f

        paint.isFakeBoldText = false
        paint.textSize = 11f
        canvas.drawText("Total Active Trips: ${trips.size}", 30f, yPosition, paint)
        canvas.drawText("Total Budget: $currencySymbol${String.format(Locale.US, "%.2f", totalBudgetAmount)}", 200f, yPosition, paint)
        canvas.drawText("Total Spent: $currencySymbol${String.format(Locale.US, "%.2f", totalSpentAll)}", 400f, yPosition, paint)
        yPosition += 25f

        canvas.drawLine(30f, yPosition, 565f, yPosition, linePaint)
        yPosition += 25f

        // Trips Breakdown
        trips.forEach { trip ->
            paint.isFakeBoldText = true
            paint.textSize = 13f
            paint.color = AndroidColor.parseColor("#6B1D2F")
            canvas.drawText("Trip: ${trip.destination}, ${trip.country}", 30f, yPosition, paint)
            yPosition += 18f

            paint.isFakeBoldText = false
            paint.textSize = 10f
            paint.color = AndroidColor.parseColor("#78716C")
            canvas.drawText("Dates: ${trip.startDate} - ${trip.endDate}  |  Budget: $currencySymbol${trip.totalBudget}", 30f, yPosition, paint)
            yPosition += 22f

            // Table Header
            paint.color = AndroidColor.parseColor("#1C1917")
            paint.isFakeBoldText = true
            canvas.drawText("Expense Description", 30f, yPosition, paint)
            canvas.drawText("Category", 250f, yPosition, paint)
            canvas.drawText("Date", 380f, yPosition, paint)
            canvas.drawText("Amount", 490f, yPosition, paint)
            yPosition += 8f

            canvas.drawLine(30f, yPosition, 565f, yPosition, linePaint)
            yPosition += 16f

            val tripExpenses = expenses.filter { it.tripId == trip.id }
            if (tripExpenses.isEmpty()) {
                paint.isFakeBoldText = false
                paint.color = AndroidColor.parseColor("#78716C")
                canvas.drawText("No recorded expenses for this trip.", 30f, yPosition, paint)
                yPosition += 20f
            } else {
                tripExpenses.forEach { exp ->
                    if (yPosition > 780f) return@forEach // Safety boundary

                    paint.isFakeBoldText = false
                    paint.color = AndroidColor.parseColor("#1C1917")
                    canvas.drawText(exp.title.take(28), 30f, yPosition, paint)
                    canvas.drawText(exp.category, 250f, yPosition, paint)
                    canvas.drawText(exp.dateDisplay, 380f, yPosition, paint)
                    paint.isFakeBoldText = true
                    canvas.drawText("$currencySymbol${String.format(Locale.US, "%.2f", exp.amount)}", 490f, yPosition, paint)
                    yPosition += 18f
                }
            }
            yPosition += 15f
        }

        // Footer Credit
        val footerPaint = Paint()
        footerPaint.color = AndroidColor.parseColor("#78716C")
        footerPaint.textSize = 10f
        footerPaint.isFakeBoldText = true
        canvas.drawText("Made with ❤️ by Rahul Shah  •  Travellers Travel & Budget Management", 30f, 820f, footerPaint)

        pdfDocument.finishPage(page)

        val file = File(context.cacheDir, "travellers_invoice_statement.pdf")
        val outputStream = FileOutputStream(file)
        pdfDocument.writeTo(outputStream)
        pdfDocument.close()
        outputStream.close()

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Export PDF Invoice Statement"))
    }
}

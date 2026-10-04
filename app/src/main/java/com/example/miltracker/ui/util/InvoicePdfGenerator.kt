package com.example.miltracker.ui.util

import android.graphics.pdf.PdfDocument
import android.graphics.Paint
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

object InvoicePdfGenerator {
    fun export(month: String, entries: List<Pair<String, Pair<Float, Float>>>) = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
        "invoice_$month.pdf"
    ).also { file ->
        PdfDocument().apply {
            var y = 50f
            val paint = Paint().apply { textSize = 14f; isAntiAlias = true }
            val lineH = 20f
            val page = startPage(PdfDocument.PageInfo.Builder(595, 842, 1).create())
            page.canvas.drawText("Milk & Dahi Invoice — $month", 50f, y, paint)
            y += lineH
            page.canvas.drawText("Date | Milk (L) | Dahi (L)", 50f, y, paint)
            y += lineH
            var totalMilk = 0f
            var totalDahi = 0f
            entries.forEach { (date, p) ->
                val (m, d) = p
                totalMilk += m
                totalDahi += d
                page.canvas.drawText("$date | ${indianFormat(m)} | ${indianFormat(d)}", 50f, y, paint)
                y += lineH
                if (y > 780f) {
                    finishPage(page)
                    y = 50f
                    startPage(PdfDocument.PageInfo.Builder(595, 842, 1).create()).also { p ->
                        p.canvas.drawText("... continued", 50f, 30f, paint)
                    }
                }
            }
            page.canvas.drawText("Total — Milk: ${indianFormat(totalMilk)} L | Dahi: ${indianFormat(totalDahi)} L", 50f, y + 10f, paint)
            finishPage(page)
            writeTo(FileOutputStream(file))
            close()
        }
    }

    private fun indianFormat(n: Float): String {
        val v = n.toInt()
        if (v == 0) return "0"
        val s = v.toString()
        val len = s.length
        if (len <= 3) return s
        val first = len % 2
        val firstPortion = if (first == 1) s.substring(0, 1) else s.substring(0, 2)
        val rest = if (first == 1) s.substring(1) else s.substring(2)
        val sb = StringBuilder()
        for (i in rest.indices) {
            if (i > 0 && i % 2 == 0) sb.append(",")
            sb.append(rest[i])
        }
        return "$firstPortion,$sb"
    }
}

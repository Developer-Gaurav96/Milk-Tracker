package com.example.miltracker.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.miltracker.data.local.MiltrackerDatabase
import com.example.miltracker.data.local.entities.DailyEntry
import com.example.miltracker.settings.SettingsRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AutomationWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val dao = MiltrackerDatabase.getInstance(applicationContext).dailyEntryDao()
        val settings = SettingsRepository.getInstance(applicationContext).settings.first()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDate.now()

        // 1) Backfill: 90-day sweep — create a default (non-manual) entry for any
        //    missing day in [today-90, today). Today itself is handled by the trigger.
        val milk = (settings["milkPackets"] as? Int)?.toFloat() ?: 1f
        val dahi = (settings["dahiPackets"] as? Int)?.toFloat() ?: 1f
        var day = today.minusDays(90)
        while (day.isBefore(today)) {
            val dateStr = day.format(formatter)
            if (dao.getEntryByDate(dateStr) == null) {
                dao.insert(DailyEntry(date = dateStr, milk = milk, dahi = dahi, manual = false, absent = false))
            }
            day = day.plusDays(1)
        }

        // 2) Trigger check: once the clock reaches autoTime (default 19:30), auto-log
        //    today's entry if it doesn't already exist.
        val autoTime = (settings["autoTime"] as? String) ?: "19:30"
        val parts = autoTime.split(":")
        val triggerTime = LocalTime.of(parts[0].toInt(), parts[1].toInt())
        val now = LocalTime.now()
        if (!now.isBefore(triggerTime)) {
            val todayStr = today.format(formatter)
            if (dao.getEntryByDate(todayStr) == null) {
                dao.insert(DailyEntry(date = todayStr, milk = milk, dahi = dahi, manual = false, absent = false))
            }
        }

        return Result.success()
    }
}

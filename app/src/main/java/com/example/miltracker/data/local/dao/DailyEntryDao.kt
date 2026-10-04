package com.example.miltracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.example.miltracker.data.local.entities.DailyEntry

@Dao
interface DailyEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: DailyEntry)

    @Query("UPDATE daily_entries SET milk = :milk, dahi = :dahi, manual = :manual, absent = :absent, timestamp = :timestamp WHERE id = :id")
    suspend fun update(id: Long, milk: Float, dahi: Float, manual: Boolean, absent: Boolean, timestamp: Long)

    @Query("SELECT * FROM daily_entries WHERE date = :date LIMIT 1")
    suspend fun getEntryByDate(date: String): DailyEntry?

    @Query("SELECT * FROM daily_entries WHERE date LIKE :month || '%' ORDER BY date")
    suspend fun getEntriesByMonth(month: String): List<DailyEntry>

    @Query("SELECT * FROM daily_entries ORDER BY date DESC")
    suspend fun getAllEntries(): List<DailyEntry>

    @Query("DELETE FROM daily_entries WHERE date = :date")
    suspend fun deleteEntryByDate(date: String)

    @Delete
    suspend fun deleteEntry(entry: DailyEntry)

    @Query("SELECT SUM(milk) as totalMilk, SUM(dahi) as totalDahi FROM daily_entries WHERE date LIKE :month || '%'")
    suspend fun getMonthlyTotals(month: String): MonthlyTotal
}

class MonthlyTotal(val totalMilk: Float?, val totalDahi: Float?)

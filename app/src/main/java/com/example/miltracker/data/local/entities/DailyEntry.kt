package com.example.miltracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_entries")
data class DailyEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val milk: Float,
    val dahi: Float,
    val timestamp: Long = System.currentTimeMillis(),
    val manual: Boolean = true,
    val absent: Boolean = false
)

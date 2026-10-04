package com.example.miltracker.data.repository

import com.example.miltracker.data.local.dao.DailyEntryDao
import com.example.miltracker.data.local.entities.DailyEntry
import kotlinx.coroutines.flow.Flow

class DailyEntryRepository(private val dao: DailyEntryDao) {
    fun getTodayEntryFlow(date: String): Flow<DailyEntry?> = dao.getEntryByDate(date).let { kotlinx.coroutines.flow.flowOf(it) }
    suspend fun saveEntry(entry: DailyEntry) = dao.insert(entry)
    suspend fun deleteByDate(date: String) = dao.deleteEntryByDate(date)
}

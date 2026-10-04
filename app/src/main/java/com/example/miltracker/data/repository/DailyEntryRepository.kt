package com.example.miltracker.data.repository

import com.example.miltracker.data.local.dao.DailyEntryDao
import com.example.miltracker.data.local.entities.DailyEntry
import kotlinx.coroutines.flow.Flow

class DailyEntryRepository(private val dao: DailyEntryDao) {
    suspend fun getTodayEntry(date: String): DailyEntry? = dao.getEntryByDate(date)
    fun getTodayEntryFlow(date: String): Flow<DailyEntry?> = kotlinx.coroutines.flow.flow { emit(dao.getEntryByDate(date)) }
    suspend fun saveEntry(entry: DailyEntry) = dao.insert(entry)
    suspend fun deleteByDate(date: String) = dao.deleteEntryByDate(date)
}

package com.example.miltracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.miltracker.data.local.dao.DailyEntryDao
import com.example.miltracker.data.local.entities.DailyEntry

@Database(entities = [DailyEntry::class], version = 1, exportSchema = false)
abstract class MiltrackerDatabase : RoomDatabase() {
    abstract fun dailyEntryDao(): DailyEntryDao
    companion object {
        @Volatile private var INSTANCE: MiltrackerDatabase? = null
        fun getInstance(context: Context): MiltrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, MiltrackerDatabase::class.java, "miltracker_database").fallbackToDestructiveMigration().build()
                INSTANCE = instance; instance
            }
        }
    }
}

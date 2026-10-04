package com.example.miltracker.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val context: Context) {
    companion object {
        @Volatile private var INSTANCE: SettingsRepository? = null
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) { INSTANCE ?: SettingsRepository(ctx.applicationContext).also { INSTANCE = it } }
    }
    private val Context.store: DataStore<Preferences> by preferencesDataStore(name = "settings")
    val settings: Flow<Map<String, Any>> = context.store.data.map { p ->
        mapOf(
            "automation" to (p[booleanPreferencesKey("automation")] ?: false),
            "autoTime" to (p[stringPreferencesKey("auto_time")] ?: "19:30"),
            "milkPackets" to (p[intPreferencesKey("milk_packets")] ?: 1),
            "dahiPackets" to (p[intPreferencesKey("dahi_packets")] ?: 1),
            "milkPrice" to (p[floatPreferencesKey("milk_price")] ?: 60f),
            "dahiPrice" to (p[floatPreferencesKey("dahi_price")] ?: 40f),
            "vendorName" to (p[stringPreferencesKey("vendor_name")] ?: ""),
            "vendorPhone" to (p[stringPreferencesKey("vendor_phone")] ?: ""),
            "theme" to (p[stringPreferencesKey("theme")] ?: "light")
        )
    }
}

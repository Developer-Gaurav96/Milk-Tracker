package com.example.miltracker

import android.app.Application
import com.example.miltracker.data.local.MiltrackerDatabase

class MilktrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread { MiltrackerDatabase.getInstance(this) }.start()
    }
}

package com.example.miltracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.miltracker.ui.navigation.AppNavHost
import com.example.miltracker.ui.theme.AppColorScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = AppColorScheme(isDark = false)) {
                Surface(modifier = Modifier.fillMaxSize()) { AppNavHost() }
            }
        }
    }
}

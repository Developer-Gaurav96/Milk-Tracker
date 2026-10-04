package com.example.miltracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.miltracker.ui.screens.calendar.CalendarScreen
import com.example.miltracker.ui.screens.invoices.InvoicesScreen
import com.example.miltracker.ui.screens.settings.SettingsScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "calendar", modifier = modifier) {
        composable("calendar") { CalendarScreen(context = context) }
        composable("invoices") { InvoicesScreen(context = context) }
        composable("settings") { SettingsScreen(context = context) }
    }
}

package com.example.miltracker.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.miltracker.settings.SettingsRepository
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(context: android.content.Context) {
    val settingsRepo = remember { SettingsRepository.getInstance(context) }
    val settings by settingsRepo.settings.collectAsState(initial = emptyMap())
    val scope = rememberCoroutineScope()

    // For a real edit flow we'd wire DataStore writes; here we display live settings + glass UI.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Glass settings card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.Transparent,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF0284C7).copy(alpha = 0.10f), Color(0xFF7C3AED).copy(alpha = 0.10f))
                        )
                    )
            ) {
                SettingRow(label = "Auto-logging", value = (settings["automation"] as? Boolean)?.toString() ?: "Off")
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                SettingRow(label = "Trigger", value = (settings["autoTime"] as? String) ?: "19:30")
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                SettingRow(label = "Milk packets", value = (settings["milkPackets"] as? Int)?.toString() ?: "1")
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                SettingRow(label = "Dahi packets", value = (settings["dahiPackets"] as? Int)?.toString() ?: "1")
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                SettingRow(label = "Milk price", value = "₹${settings["milkPrice"] as? Float ?: 60f}")
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                SettingRow(label = "Dahi price", value = "₹${settings["dahiPrice"] as? Float ?: 40f}")
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                SettingRow(label = "Theme", value = (settings["theme"] as? String)?.replaceFirstChar { it.uppercase() } ?: "Light")
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                SettingRow(label = "Vendor", value = (settings["vendorName"] as? String)?.ifEmpty { "—" } ?: "—")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Settings updates written via DataStore in full app */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Save settings")
        }
    }
}

@Composable
private fun SettingRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text(text = value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
    }
}

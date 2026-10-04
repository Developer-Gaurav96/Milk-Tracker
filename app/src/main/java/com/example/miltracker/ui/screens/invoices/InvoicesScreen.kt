package com.example.miltracker.ui.screens.invoices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.miltracker.settings.SettingsRepository

@Composable
fun InvoicesScreen(context: android.content.Context) {
    val settingsRepo = remember { SettingsRepository.getInstance(context) }
    val settings by settingsRepo.settings.collectAsState(initial = emptyMap())

    val vendor = settings["vendorName"] as? String ?: "—"
    val price = settings["milkPrice"] as? Float ?: 60f
    val packets = settings["milkPackets"] as? Int ?: 1

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Invoices",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Glass card with vendor + payable
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.linearGradient(colors = listOf(Color(0xFF0284C7).copy(alpha = 0.12f), Color(0xFF7C3AED).copy(alpha = 0.12f)))),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Vendor: $vendor",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Payable: ₹${if (vendor == "—") 0 else (price * packets).toInt()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Packets / day: $packets", style = MaterialTheme.typography.labelMedium)
                    Text("Rate: ₹$price / pkt", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

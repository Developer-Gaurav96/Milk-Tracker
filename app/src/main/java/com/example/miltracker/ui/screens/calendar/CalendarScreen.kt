package com.example.miltracker.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.miltracker.data.local.MiltrackerDatabase
import com.example.miltracker.data.local.entities.DailyEntry
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarScreen(context: android.content.Context) {
    val db = remember { MiltrackerDatabase.getInstance(context) }
    val dao = db.dailyEntryDao()
    val scope = rememberCoroutineScope()
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var entries by remember { mutableStateOf<List<DailyEntry>>(emptyList()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(currentMonth) {
        try {
            val m = "${currentMonth.year}-${currentMonth.monthValue.toString().padStart(2, '0')}"
            entries = dao.getEntriesByMonth(m)
        } catch (e: Exception) { entries = emptyList() }
    }

    val entryByDate = entries.associateBy { it.date }
        try {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Surface(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large, color = Color(0xFF0284C7).copy(alpha = 0.12f), tonalElevation = 4.dp) {
            Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) { Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Prev") }
                Text("${currentMonth.month.name} ${currentMonth.year}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) { Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next") }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(7), modifier = Modifier.fillMaxWidth()) {
            items(31) { dayIndex ->
                val day = dayIndex + 1
                val dateStr = "${currentMonth.year}-${currentMonth.monthValue.toString().padStart(2,'0')}-${day.toString().padStart(2,'0')}"
                val entry = entryByDate[dateStr]
                val isSelected = selectedDate == LocalDate.of(currentMonth.year, currentMonth.monthValue, day)
                Box(modifier = Modifier.aspectRatio(1f).padding(4.dp).clip(MaterialTheme.shapes.medium).background(
                    brush = when {
                        isSelected -> Brush.linearGradient(colors = listOf(Color(0xFF0284C7), Color(0xFF7C3AED)))
                        entry != null -> Brush.linearGradient(colors = listOf(Color(0xFF0284C7).copy(alpha = 0.35f), Color(0xFF7C3AED).copy(alpha = 0.25f)))
                        else -> Brush.linearGradient(colors = listOf(Color(0xFFF8FAFC), Color(0xFFFFFFFF)))
                    }, shape = MaterialTheme.shapes.medium
                ).clickable { selectedDate = LocalDate.of(currentMonth.year, currentMonth.monthValue, day) }, contentAlignment = Alignment.Center) {
                    Text(text = day.toString(), style = MaterialTheme.typography.bodyMedium, color = if (entry != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                    if (entry != null) Text("${entry.milk}L", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
        }
    }
    } catch (e: Exception) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Load error. Restart app.", color = MaterialTheme.colorScheme.error)
        }
    }
}

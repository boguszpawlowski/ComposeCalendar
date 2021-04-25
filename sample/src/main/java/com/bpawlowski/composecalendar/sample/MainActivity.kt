package com.bpawlowski.composecalendar.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.bpawlowski.composecalendar.Calendar
import com.bpawlowski.composecalendar.config.CalendarConfig

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MainScreen()
    }
  }
}

@Composable
fun MainScreen() {
  MaterialTheme {
    Calendar(
      config = CalendarConfig(
        showAdjacentMonths = true
      ),
    )
  }
}

package com.bpawlowski.composecalendar.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.bpawlowski.composecalendar.Calendar
import com.bpawlowski.composecalendar.config.CalendarConfig
import com.bpawlowski.composecalendar.selection.SelectionMode
import com.bpawlowski.composecalendar.selection.SelectionMode.Multiple
import com.bpawlowski.composecalendar.selection.SelectionMode.Period
import com.bpawlowski.composecalendar.selection.SelectionMode.Single
import com.bpawlowski.composecalendar.selection.SelectionValue
import com.bpawlowski.composecalendar.selection.rememberSelectionState

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
  val (selectionMode, onSelectionModeChanged) = remember { mutableStateOf(SelectionMode.Multiple) }

  MaterialTheme {
    Column {
      Button(onClick = { onSelectionModeChanged(Period) }) {
        Text(text = "Period")
      }

      Button(onClick = { onSelectionModeChanged(Multiple) }) {
        Text(text = "Multiple")
      }

      Button(onClick = { onSelectionModeChanged(Single) }) {
        Text(text = "Single")
      }


      Calendar(
        selectionMode = selectionMode,
        config = CalendarConfig(
          showAdjacentMonths = true
        ),
      )
    }
  }
}

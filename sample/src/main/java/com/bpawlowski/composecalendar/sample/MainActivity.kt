package com.bpawlowski.composecalendar.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bpawlowski.composecalendar.Calendar
import com.bpawlowski.composecalendar.config.CalendarConfig
import com.bpawlowski.composecalendar.rememberCalendarState
import com.bpawlowski.composecalendar.selection.SelectionMode

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
    val calendarState = rememberCalendarState()
    Column(
      modifier = Modifier
        .padding(8.dp)
        .wrapContentHeight()
    ) {
      Calendar(
        calendarState = calendarState,
        config = CalendarConfig(
          showAdjacentMonths = true
        ),
        modifier = Modifier.padding(vertical = 8.dp)
      )

      Text(
        text = "Calendar Selection Mode",
        style = MaterialTheme.typography.h5,
      )
      SelectionMode.values().forEach { selectionMode ->
        Row(modifier = Modifier.fillMaxWidth()) {
          RadioButton(
            selected = calendarState.selectionState.selectionMode == selectionMode,
            onClick = { calendarState.selectionState.selectionMode = selectionMode }
          )
          Text(text = selectionMode.name)
          Spacer(modifier = Modifier.height(4.dp))
        }
      }
    }
  }
}

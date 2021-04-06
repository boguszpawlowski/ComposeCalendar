package com.bpawlowski.composecalendar

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // setContentView(R.layout.activity_main) FIXME uncomment if not using Compose
    setContent {
      MainScreen()
    }
  }
}

@Composable
fun MainScreen() {
  MaterialTheme {
    Text(text = "Hello")
  }
}

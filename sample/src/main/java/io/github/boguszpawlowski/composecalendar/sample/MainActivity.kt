package io.github.boguszpawlowski.composecalendar.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
  MaterialTheme(
    colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
  ) {
    Surface(
      modifier = Modifier.fillMaxSize(),
    ) {
      val navController = rememberNavController()

      NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainMenu(navController = navController) }
        composable("static") { StaticCalendarSample() }
        composable("selection") { SelectableCalendarSample() }
        composable("components") { CustomComponentsSample() }
        composable("custom_selection") { CustomSelectionSample() }
        composable("viewmodel") { ViewModelSample() }
        composable("kotlinx_datetime") { KotlinXDateTimeSample() }
      }
    }
  }
}

@Composable
fun MainMenu(navController: NavController) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Button(onClick = { navController.navigate("static") }) {
      Text(text = "Static Calendar")
    }
    Spacer(modifier = Modifier.height(16.dp))

    Button(onClick = { navController.navigate("selection") }) {
      Text(text = "Selectable Calendar")
    }
    Spacer(modifier = Modifier.height(16.dp))

    Button(onClick = { navController.navigate("components") }) {
      Text(text = "Custom Components")
    }
    Spacer(modifier = Modifier.height(16.dp))

    Button(onClick = { navController.navigate("custom_selection") }) {
      Text(text = "Custom Selection")
    }
    Spacer(modifier = Modifier.height(16.dp))

    Button(onClick = { navController.navigate("viewmodel") }) {
      Text(text = "ViewModel")
    }
    Spacer(modifier = Modifier.height(16.dp))

    Button(onClick = { navController.navigate("kotlinx_datetime") }) {
      Text(text = "Kotlinx DateTime")
    }
  }
}

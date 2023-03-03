import org.ajoberstar.grgit.Grgit

plugins {
  id(Android.ApplicationPluginId)
  kotlin(Kotlin.AndroidPluginId)
  id("common-android-plugin")
}

val commitsCount = Grgit.open(mapOf("dir" to rootDir)).log().size

android {
  defaultConfig {
    versionCode = commitsCount
    versionName = "0.0.1"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  packagingOptions {
    exclude("META-INF/AL2.0")
    exclude("META-INF/LGPL2.1")
  }
}

dependencies {
  implementation(project(":library"))
  implementation(project(":kotlinx-datetime"))

  implementation(Material.Core)

  implementation(AndroidX.AppCompat)
  implementation(AndroidX.ComposeActivity)

  implementation(Compose.Runtime)
  implementation(Compose.Navigation)
  implementation(Compose.Ui)
  implementation(Compose.UiTooling)
  implementation(Compose.Foundation)
  implementation(Compose.FoundationLayout)
  implementation(Compose.Material)
  implementation(Timber.Core)

  debugImplementation(Debug.LeakCanary)
  debugImplementation(Hyperion.Core)
  debugImplementation(Hyperion.Crash)
  debugImplementation(Hyperion.GeigerCounter)
  debugImplementation(Hyperion.Measurement)
  debugImplementation(ComposeTest.Manifest)

  androidTestImplementation(ComposeTest.Core)
}

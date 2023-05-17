import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id(Android.LibraryPluginId)
  kotlin(Kotlin.AndroidPluginId)
  id("common-android-plugin")
  id(MavenPublish.PluginId)
}

android {
  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict" + "-Xcontext-receivers"
    }
  }
}

dependencies {
  implementation(Compose.Ui)
  implementation(Compose.UiTooling)
  implementation(Compose.AccompanistPager)
  implementation(Compose.Foundation)
  implementation(Compose.FoundationLayout)
  implementation(Compose.Material)
  implementation(Timber.Core)

  testImplementation(Kotest.Assertions)
  testImplementation(Kotest.RunnerJunit5)
}

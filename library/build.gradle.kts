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
      freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict"
    }
  }
}

dependencies {
  implementation(Kotlin.StdLib)
  implementation(Compose.Runtime)
  implementation(Compose.Ui)
  implementation(Compose.UiTooling)
  implementation(Compose.Foundation)
  implementation(Compose.FoundationLayout)
  implementation(Compose.Material)
  implementation(Timber.Core)

  testImplementation(Kotest.Assertions)
  testImplementation(Kotest.RunnerJunit5)
}

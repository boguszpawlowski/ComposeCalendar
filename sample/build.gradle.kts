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
    versionName = App.VersionName
  }
}

dependencies {
  implementation(project(autoModules.library))

  implementation(Kotlin.StdLib)

  implementation(Material.Core)

  implementation(AndroidX.AppCompat)
  implementation(AndroidX.ComposeActivity)

  implementation(Compose.Runtime)
  implementation(Compose.Ui)
  implementation(Compose.Foundation)
  implementation(Compose.FoundationLayout)
  implementation(Compose.Material)

  debugImplementation(Debug.LeakCanary)
  debugImplementation(Debug.FoQA)
}

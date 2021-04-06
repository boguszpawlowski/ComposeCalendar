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
  implementation(Kotlin.StdLib)

  implementation(Material.Core)

  implementation(AndroidX.AppCompat)
  implementation(AndroidX.ConstraintLayout)
  implementation(AndroidX.ComposeActivity)

  implementation(Compose.Runtime) // FIXME remove if not using compose
  implementation(Compose.Ui) // FIXME remove if not using compose
  implementation(Compose.Foundation) // FIXME remove if not using compose
  implementation(Compose.FoundationLayout) // FIXME remove if not using compose
  implementation(Compose.Material) // FIXME remove if not using compose

  debugImplementation(Debug.LeakCanary)
  debugImplementation(Debug.FoQA)
}

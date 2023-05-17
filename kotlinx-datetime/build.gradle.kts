plugins {
  kotlin(Kotlin.JvmPluginId)
  id(MavenPublish.PluginId)
}

kotlin {
  explicitApi()
}

dependencies {
  api(Kotlin.DateTime)

  testImplementation(Kotest.Assertions)
  testImplementation(Kotest.RunnerJunit5)
}

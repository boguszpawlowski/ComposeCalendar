import com.vanniktech.maven.publish.SonatypeHost

plugins {
  kotlin(Kotlin.JvmPluginId)
  id(MavenPublish.PluginId)
}

kotlin {
  explicitApi()
}

dependencies {
  api(Kotlin.DateTime)
  implementation(Kotlin.StdLib)

  testImplementation(Kotest.Assertions)
  testImplementation(Kotest.RunnerJunit5)
  testImplementation(Kotlin.Reflect)
}

plugins.withId("com.vanniktech.maven.publish") {
  mavenPublish {
    sonatypeHost = SonatypeHost.S01
    releaseSigningEnabled = true
  }
}

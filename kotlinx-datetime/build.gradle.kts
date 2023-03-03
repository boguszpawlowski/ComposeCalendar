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

  testImplementation(Kotest.Assertions)
  testImplementation(Kotest.RunnerJunit5)
}

plugins.withId("com.vanniktech.maven.publish") {
  mavenPublish {
    sonatypeHost = SonatypeHost.S01
    releaseSigningEnabled = true
  }
}

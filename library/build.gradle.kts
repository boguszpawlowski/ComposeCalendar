import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id(Android.LibraryPluginId)
  kotlin(Kotlin.AndroidPluginId)
  id("common-android-plugin")
  `maven-publish`
}

android {
  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict"
    }
  }
}

afterEvaluate {
  publishing {
    publications {
      create<MavenPublication>("release") {
        pom {
          name.set("ComposeCalendar")
          description.set("Composable for handling calendar logic")
          licenses {
            license {
              name.set("The Apache License, Version 2.0")
              url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
          }
          developers {
            developer {
              id.set("boguszpawlowski")
              name.set("Bogusz Pawłowski")
              email.set("bogusz.pawlowski.dev@gmail.com")
            }
          }
        }
        groupId = "com.boguszpawlowski.ComposeCalendar"
        artifactId = "ComposeCalendar"
        version = "0.1"
      }
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

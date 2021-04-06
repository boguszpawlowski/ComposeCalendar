import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id(DetektLib.PluginId) version DetektLib.Version
  id(GradleVersions.PluginId) version GradleVersions.Version
  id(GrGit.PluginId) version GrGit.Version
}

buildscript {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    jcenter()
  }
  dependencies {
    classpath(Android.GradlePlugin)
    classpath(Kotlin.GradlePlugin)
    classpath(DetektLib.Plugin)
    classpath(GradleVersions.Plugin)
    classpath(Firebase.CrashlyticsPlugin)
  }
}

allprojects {
  repositories {
    mavenCentral()
    google()
    maven("https://jitpack.io")
    jcenter()
  }

  tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = "1.8"
      languageVersion = "1.5"
      apiVersion = "1.5"
      freeCompilerArgs = freeCompilerArgs + listOf(
        "-progressive",
        "-Xopt-in=kotlin.RequiresOptIn",
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xskip-prerelease-check",
        "-Xuse-experimental=kotlin.contracts.ExperimentalContracts",
        "-Xjvm-enable-preview"
      )
    }
  }

  tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
      events("passed", "skipped", "failed")
    }
  }
}

dependencies {
  detekt(DetektLib.Formatting)
  detekt(DetektLib.Cli)
}

tasks.withType<Detekt> {
  parallel = true
  config.setFrom(rootProject.file("detekt-config.yml"))
  setSource(files(projectDir))
  exclude(subprojects.map { "${it.buildDir.relativeTo(rootDir).path}/" })
  exclude("**/.gradle/**")
  reports {
    xml {
      enabled = true
      destination = file("build/reports/detekt/detekt-results.xml")
    }
    html.enabled = false
    txt.enabled = false
  }
}

tasks.register("check") {
  group = "Verification"
  description = "Allows to attach Detekt to the root project."
}

tasks.withType<DependencyUpdatesTask> {
  rejectVersionIf {
    isNonStable(candidate.version) && !isNonStable(currentVersion)
  }
}

fun isNonStable(version: String): Boolean {
  val regex = "^[0-9,.v-]+(-r)?$".toRegex()
  return !regex.matches(version)
}

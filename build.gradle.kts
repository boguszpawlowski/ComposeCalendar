import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id(DetektLib.PluginId) version DetektLib.Version
  id(GradleVersions.PluginId) version GradleVersions.Version
  id(GrGit.PluginId) version GrGit.Version
  id(Shipkit.AutoVersion.PluginId) version Shipkit.AutoVersion.Version
  id(Shipkit.Changelog.PluginId) version Shipkit.Changelog.Version
  id(Shipkit.GithubRelease.PluginId) version Shipkit.GithubRelease.Version
  id(Kotlin.CompatibilityPlugin) version Kotlin.CompatibilityPluginVersion
}

buildscript {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
  dependencies {
    classpath(Android.GradlePlugin)
    classpath(Kotlin.GradlePlugin)
    classpath(Kotlin.DokkaGradlePlugin)
    classpath(DetektLib.Plugin)
    classpath(GradleVersions.Plugin)
    classpath(MavenPublish.GradlePlugin)
    classpath(Firebase.GoogleServicesPlugin)
  }
}

allprojects {
  repositories {
    mavenCentral()
    google()
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = "11"
      freeCompilerArgs = freeCompilerArgs + listOf(
        "-progressive",
        "-Xopt-in=kotlin.RequiresOptIn",
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xuse-experimental=kotlin.contracts.ExperimentalContracts",
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

apiValidation {
  ignoredProjects.add("sample")
}

tasks {
  withType<Detekt> {
    parallel = true
    config.setFrom(rootProject.file("detekt-config.yml"))
    setSource(files(projectDir))
    exclude(subprojects.map { "${it.buildDir.relativeTo(rootDir).path}/" })
    exclude("**/.gradle/**")
    reports {
      xml {
        required.set(true)
        outputLocation.set(file("build/reports/detekt/detekt-results.xml"))
      }
      html.required.set(false)
      txt.required.set(false)
    }
  }

  register("check") {
    group = "Verification"
    description = "Allows to attach Detekt to the root project."
  }

  withType<DependencyUpdatesTask> {
    rejectVersionIf {
      isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
  }

  withType(org.shipkit.changelog.GenerateChangelogTask::class) {
    previousRevision = project.ext["shipkit-auto-version.previous-tag"] as String?
    githubToken = System.getenv("GITHUB_TOKEN")
    repository = "boguszpawlowski/composecalendar"
  }

  withType(org.shipkit.github.release.GithubReleaseTask::class) {
    dependsOn(named("generateChangelog"))
    repository = "boguszpawlowski/composecalendar"
    changelog = named("generateChangelog").get().outputs.files.singleFile
    githubToken = System.getenv("GITHUB_TOKEN")
    newTagRevision = System.getenv("GITHUB_SHA")
  }
}

fun isNonStable(version: String): Boolean {
  val regex = "^[0-9,.v-]+(-r)?$".toRegex()
  return !regex.matches(version)
}

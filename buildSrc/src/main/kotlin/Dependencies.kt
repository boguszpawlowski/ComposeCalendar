@file:Suppress("ObjectPropertyNaming", "ClassNaming", "UnderscoresInNumericLiterals")
object MavenPublish {
  const val PluginId = "com.vanniktech.maven.publish"
  const val GradlePlugin = "com.vanniktech:gradle-maven-publish-plugin:0.25.2"
}

object AndroidSdk {
  const val Min = 21
  const val Compile = 33
  const val Target = Compile
}

object Kotlin {
  const val Version = "1.8.10"
  const val CompatibilityPluginVersion = "0.11.0"

  const val GradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$Version"
  const val DokkaGradlePlugin = "org.jetbrains.dokka:dokka-gradle-plugin:1.7.20"

  const val SafeArgsPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:2.2.0"

  const val AndroidPluginId = "android"
  const val KaptPluginId = "kapt"
  const val SafeArgsPluginId = "androidx.navigation.safeargs.kotlin"
  const val JvmPluginId = "jvm"

  const val CompatibilityPlugin = "org.jetbrains.kotlinx.binary-compatibility-validator"
  const val CompatibilityPluginId = "binary-compatibility-validator"

  const val DesugarJdkLibs = "com.android.tools:desugar_jdk_libs:1.1.5"
  const val DateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"
}

object Android {
  const val GradlePlugin = "com.android.tools.build:gradle:7.4.2"

  const val ApplicationPluginId = "com.android.application"
  const val LibraryPluginId = "com.android.library"
}

object GradleVersions {
  const val Version = "0.42.0"

  const val PluginId = "com.github.ben-manes.versions"
  const val Plugin = "com.github.ben-manes:gradle-versions-plugin:$Version"
}

object GrGit {
  const val Version = "5.0.0"

  const val PluginId = "org.ajoberstar.grgit"
}

object Shipkit {
  object AutoVersion {
    const val Version = "1.1.19"

    const val PluginId = "org.shipkit.shipkit-auto-version"
  }

  object Changelog {
    const val Version = "1.1.15"

    const val PluginId = "org.shipkit.shipkit-changelog"
  }

  object GithubRelease {
    const val Version = "1.1.15"

    const val PluginId = "org.shipkit.shipkit-github-release"
  }
}

object AndroidX {
  const val Version = "1.0.0"
  const val LifecycleVersion = "2.2.0"

  const val AppCompat = "androidx.appcompat:appcompat:1.6.1"
  const val ComposeActivity = "androidx.activity:activity-compose:1.6.1"
}

object Material {
  const val Core = "com.google.android.material:material:1.5.0"
}

object DetektLib {
  const val Version = "1.21.0"

  const val PluginId = "io.gitlab.arturbosch.detekt"
  const val Plugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$Version"

  const val Formatting = "io.gitlab.arturbosch.detekt:detekt-formatting:$Version"
  const val Cli = "io.gitlab.arturbosch.detekt:detekt-cli:$Version"
}

object Timber {
  const val Version = "5.0.1"
  const val Core = "com.jakewharton.timber:timber:$Version"
}

object Compose {
  const val Version = "1.3.1"
  const val CompilerVersion = "1.4.3"
  const val AccompanistVersion = "0.25.1"

  const val Runtime = "androidx.compose.runtime:runtime:$Version"
  const val Compiler = "androidx.compose.compiler:compiler:$CompilerVersion"
  const val Foundation = "androidx.compose.foundation:foundation:$Version"
  const val FoundationLayout = "androidx.compose.foundation:foundation-layout:$Version"
  const val Material = "androidx.compose.material:material:$Version"
  const val Ui = "androidx.compose.ui:ui:$Version"
  const val UiTooling = "androidx.compose.ui:ui-tooling:$Version"
  const val MaterialIconsExtended = "androidx.compose.material:material-icons-extended:$Version"
  const val AccompanistPager = "com.google.accompanist:accompanist-pager:$AccompanistVersion"
  const val Navigation = "androidx.navigation:navigation-compose:2.5.0-alpha03"
  const val Testing = "androidx.compose.ui:ui-test:$Version"
  const val JunitTesting = "androidx.compose.ui:ui-test-junit4:$Version"
}

object Debug {
  const val LeakCanary = "com.squareup.leakcanary:leakcanary-android:2.7"
}

object Hyperion {
  private const val Version = "0.9.34"
  const val Core = "com.willowtreeapps.hyperion:hyperion-core:$Version"
  const val Crash = "com.willowtreeapps.hyperion:hyperion-crash:$Version"
  const val GeigerCounter = "com.willowtreeapps.hyperion:hyperion-geiger-counter:$Version"
  const val Measurement = "com.willowtreeapps.hyperion:hyperion-measurement:$Version"
}

object Kotest {
  const val Version = "5.4.2"

  const val RunnerJunit5 = "io.kotest:kotest-runner-junit5-jvm:$Version"

  const val Assertions = "io.kotest:kotest-assertions-core-jvm:$Version"
}

object ComposeTest {
  const val Core = "androidx.compose.ui:ui-test-junit4:${Compose.Version}"
  const val Manifest = "androidx.compose.ui:ui-test-manifest:${Compose.Version}"
}

object Firebase {
  const val Bom = "com.google.firebase:firebase-bom:30.0.0"
  const val GoogleServicesPlugin = "com.google.gms:google-services:4.3.10"
}

@file:Suppress("ObjectPropertyNaming", "ClassNaming", "UnderscoresInNumericLiterals")
object App {
  private const val versionMajor = 0
  private const val versionMinor = 0
  private const val versionPatch = 1
  private val versionClassifier: String? = null
  private const val isSnapshot = true

  private fun generateVersionName(): String {
    val versionName = "$versionMajor.$versionMinor.$versionPatch"
    val classifier = if (versionClassifier == null && isSnapshot) {
      "-SNAPSHOT"
    } else versionClassifier ?: ""

    return "$versionName$classifier"
  }

  val VersionName = generateVersionName()
}

object AndroidSdk {
  const val Min = 24
  const val Compile = 30
  const val Target = Compile
  const val BuildTools = "30.0.2"
}

object Kotlin {
  const val Version = "1.4.31"

  const val GradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$Version"
  const val StdLib = "org.jetbrains.kotlin:kotlin-stdlib:$Version"
  const val SafeArgsPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:2.2.0"

  const val AndroidPluginId = "android"
  const val KaptPluginId = "kapt"
  const val SafeArgsPluginId = "androidx.navigation.safeargs.kotlin"
  const val JvmPluginId = "jvm"

  const val DesugarJdkLibs = "com.android.tools:desugar_jdk_libs:1.0.9"
}

object Android {
  const val GradlePlugin = "com.android.tools.build:gradle:7.0.0-alpha12"

  const val ApplicationPluginId = "com.android.application"
  const val LibraryPluginId = "com.android.library"
}

object GradleVersions {
  const val Version = "0.38.0"

  const val PluginId = "com.github.ben-manes.versions"
  const val Plugin = "com.github.ben-manes:gradle-versions-plugin:$Version"
}

object GrGit {
  const val Version = "4.1.0"

  const val PluginId = "org.ajoberstar.grgit"
}

object Coroutines {
  const val Version = "1.4.3"

  const val Core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$Version"
}

object SqlDelight {
  const val Version = "1.4.4"

  const val PluginId = "com.squareup.sqldelight"
  const val Plugin = "com.squareup.sqldelight:gradle-plugin:$Version"

  const val AndroidDriver = "com.squareup.sqldelight:android-driver:$Version"
  const val JdbcDriver = "org.xerial:sqlite-jdbc:3.34.0"
  const val Driver = "com.squareup.sqldelight:sqlite-driver:$Version"

  const val CoroutineExtensions = "com.squareup.sqldelight:coroutines-extensions:$Version"
}

object Retrofit {
  const val Version = "2.9.0"

  const val Core = "com.squareup.retrofit2:retrofit:$Version"
  const val ConverterKotlinxSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
}

object KotlinXSerialization {
  const val Core = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.1.0"
  const val Json = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0"
}

object AndroidX {
  const val Version = "1.0.0"
  const val LifecycleVersion = "2.2.0"

  const val AppCompat = "androidx.appcompat:appcompat:1.3.0-rc01"
  const val Activity = "androidx.activity:activity-ktx:1.1.0"
  const val ConstraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
  const val ComposeActivity = "androidx.activity:activity-compose:1.3.0-alpha05"
  const val Lifecycle = "androidx.lifecycle:lifecycle-extensions:$LifecycleVersion"
  const val LifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$LifecycleVersion"
  const val LifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LifecycleVersion"
  const val Startup = "androidx.startup:startup-runtime:1.0.0"
  const val ComposeLifecycle = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha03"
}

object Material {
  const val Core = "com.google.android.material:material:1.3.0"
}

object DetektLib {
  const val Version = "1.16.0"

  const val PluginId = "io.gitlab.arturbosch.detekt"
  const val Plugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$Version"

  const val Formatting = "io.gitlab.arturbosch.detekt:detekt-formatting:$Version"
  const val Cli = "io.gitlab.arturbosch.detekt:detekt-cli:$Version"
}

object Koin {
  const val Version = "2.2.2"

  const val Core = "org.koin:koin-core:$Version"
  const val Android = "org.koin:koin-android:$Version"
  const val ViewModel = "org.koin:koin-androidx-viewmodel:$Version"
  const val Compose = "org.koin:koin-androidx-compose:$Version"
}

object Timber {
  const val Version = "4.7.1"
  const val Core = "com.jakewharton.timber:timber:$Version"
}

object Compose {
  const val Version = "1.0.0-beta03"
  const val AccompanistVersion = "0.6.2"

  const val Runtime = "androidx.compose.runtime:runtime:$Version"
  const val Compiler = "androidx.compose.compiler:compiler:$Version"
  const val Foundation = "androidx.compose.foundation:foundation:$Version"
  const val FoundationLayout = "androidx.compose.foundation:foundation-layout:$Version"
  const val Material = "androidx.compose.material:material:$Version"
  const val Ui = "androidx.compose.ui:ui:$Version"
  const val UiTooling = "androidx.compose.ui:ui-tooling:$Version"
  const val MaterialIconsExtended = "androidx.compose.material:material-icons-extended:$Version"
  const val AccompanistCoil = "dev.chrisbanes.accompanist:accompanist-coil:$AccompanistVersion"
  const val Navigation = "androidx.navigation:navigation-compose:1.0.0-alpha09"
  const val Testing = "androidx.compose.ui:ui-test:$Version"
  const val JunitTesting = "androidx.compose.ui:ui-test-junit4:$Version"
}

object Firebase {
  const val CrashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:2.5.2"
  const val GoogleServicesPlugin = "com.google.gms:google-services:4.3.5"
  const val AppDistributionPlugin = "com.google.firebase:firebase-appdistribution-gradle:1.3.1"

  const val CrashlyticsPluginId = "com.google.firebase.crashlytics"
  const val GoogleServicesPluginId = "com.google.gms.google-services"
  const val AppDistributionPluginId = "com.google.firebase.appdistribution"

  const val Bom = "com.google.firebase:firebase-bom:26.8.0"

  const val Analytics = "com.google.firebase:firebase-analytics-ktx"
  const val Crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
}

object Debug {
  const val LeakCanary = "com.squareup.leakcanary:leakcanary-android:2.7"
  const val FoQA = "pl.droidsonroids.foqa:foqa:0.1.24"
}

object Kotest {
  const val Version = "4.4.3"

  const val RunnerJunit5 = "io.kotest:kotest-runner-junit5-jvm:$Version"

  const val Assertions = "io.kotest:kotest-assertions-core-jvm:$Version"
}

object CoroutineTest {
  const val Turbine = "app.cash.turbine:turbine:0.4.1"
}

object AndroidXTest {
  const val Runner = "androidx.test:runner:1.3.0"
  const val Rules = "androidx.test:rules:1.3.0"
}

object ComposeTest {
  const val Core = "androidx.compose.ui:ui-test-junit4:${Compose.Version}"
}

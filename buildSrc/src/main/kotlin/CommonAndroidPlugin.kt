import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.Plugin
import org.gradle.api.Project

class CommonAndroidPlugin : Plugin<Project> {

  @Suppress("LongMethod")
  override fun apply(target: Project) {
    val androidExtension = target.extensions.getByName("android")

    (androidExtension as? BaseExtension)?.apply {
      compileSdkVersion(AndroidSdk.Compile)
      buildToolsVersion(AndroidSdk.BuildTools)
      defaultConfig {
        minSdkVersion(AndroidSdk.Min)
        targetSdkVersion(AndroidSdk.Target)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      }

      compileOptions {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
      }

      buildFeatures.compose = true // FIXME remove if not using compose

      composeOptions { // FIXME remove if not using compose
        kotlinCompilerExtensionVersion = Compose.Version
      }

      target.dependencies.add("coreLibraryDesugaring", Kotlin.DesugarJdkLibs)
    }
  }
}

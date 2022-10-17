plugins {
  `kotlin-dsl`
}

repositories {
  mavenCentral()
  google()
}

gradlePlugin {
  plugins {
    register("common-android-plugin") {
      id = "common-android-plugin"
      implementationClass = "CommonAndroidPlugin"
    }
  }
}

dependencies {
  implementation("com.android.tools.build:gradle:7.2.1")
  implementation(kotlin("gradle-plugin", "1.7.20"))
}

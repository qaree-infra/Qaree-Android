buildscript {
    repositories {
        // Check that you have the following line (if not, add it):
        google()  // Google's Maven repository
        mavenCentral()

    }
}
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}
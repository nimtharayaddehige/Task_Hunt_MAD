plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.test"
    compileSdk = 31

    defaultConfig {
        applicationId = "com.example.test"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildToolsVersion = "33.0.1"
}

dependencies {

    // Basic Android Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // CameraX Library
    implementation(libs.camera.effects)
<<<<<<< Updated upstream
    implementation(libs.play.services.maps)

=======
    implementation(libs.camera.effects)

    // Google Play Services dependencies
    implementation("com.google.android.gms:play-services-location:21.0.1") // Latest stable
    implementation("com.google.android.gms:play-services-maps:18.1.0") // Latest stable

    // Room components for managing SQLite databases with ORM features
    implementation("androidx.room:room-runtime:2.5.2")

    // Optional Room components
    implementation("androidx.room:room-ktx:2.5.2") // Room Kotlin extensions for Coroutines support

    // SQLite directly (optional)
    implementation("androidx.sqlite:sqlite:2.3.1")
    implementation("androidx.sqlite:sqlite-ktx:2.3.1") // Kotlin extensions for SQLite
>>>>>>> Stashed changes

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
<<<<<<< Updated upstream

    // Google Play Services dependencies (e.g., maps, location, etc.)
    implementation("com.google.android.gms:play-services-location:18.0.0")
    implementation("com.google.android.gms:play-services-maps:18.0.0")
    implementation("com.google.android.gms:play-services-places:18.0.0")


    // Room components for managing SQLite databases with ORM features
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2") // For annotation processing

    // Optional Room components
    implementation("androidx.room:room-ktx:2.5.2") // Room Kotlin extensions for Coroutines support

    // SQLite directly (optional)
    implementation("androidx.sqlite:sqlite:2.3.1")
    implementation("androidx.sqlite:sqlite-ktx:2.3.1") // Kotlin extensions for SQLite

    // Testing dependencies for Room
=======
>>>>>>> Stashed changes
    testImplementation("androidx.room:room-testing:2.5.2")
}

plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    namespace = "com.acemoney.matm"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding=true
    }
//    configurations {
//        all {
//            exclude(group = "com.squareup.okhttp3", module = "okhttp")
//            exclude(group = "com.squareup.okio", module = "okio")
//        }
//    }


}
configurations.all {
    resolutionStrategy {
        force("com.squareup.okhttp3:okhttp:4.10.0")
    }
}
publishing {
    publications {
        create<MavenPublication>("release") {
            // Artifact details:
            groupId = "com.acemoney"
            artifactId = "matm"
            version = "1.0.3"
//
//            // Tell Gradle to publish the Android library
//            from(components["release"])
        }
    }
}
//afterEvaluate {
//    publishing.publications.release.from(components.findByName("android"))
//}

dependencies {
    implementation(fileTree("libs") {
        include("*.jar")
    })
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(files("libs/ksoap2-android-assembly-3.6.4-jar-with-dependencies.jar"))
    implementation(files("libs/morefun_mpos_sdk_v2.1.20210628.jar"))
    implementation (project(":fingpaymicroatm-release"))
//    api(fileTree(mapOf("include" to listOf("fingpaymicroatm-release.aar"), "dir" to "libs")))
//    implementation(project(":finpay"))
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation ("com.squareup.okio:okio:3.0.0")
    implementation ("com.google.code.gson:gson:2.10")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("org.bouncycastle:bcprov-jdk15on:1.68")
}
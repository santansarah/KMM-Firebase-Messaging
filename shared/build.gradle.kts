plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.8.0"
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
    id("dev.icerock.moko.kswift")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "MultiPlatformLibrary"

            // this fixes firestore, but breaks MOKO
            // isStatic = true
            export("dev.icerock.moko:resources:0.23.0")
            export("dev.icerock.moko:mvvm-core:0.16.1")
            export("dev.icerock.moko:mvvm-flow:0.16.1")
            export("dev.icerock.moko:graphics:0.9.0")
        }
        pod("FirebaseAnalyticsSwift")
        pod("FirebaseInAppMessagingSwift", "> 10.12-beta")
        // ld: framework not found FirebaseFirestore
        // To fix this issue, we can use the `linkOnly` parameter in the gradle pod dsl.
        // From the KMM docs: this parameter instructs the CocoaPods plugin to use Pod dependencies
        // with dynamic frameworks without generating cinterop bindings.
        pod("FirebaseFirestore", linkOnly = true)
        pod("FirebaseUI/Auth")
        pod("FirebaseUI/Google")
    }

    sourceSets {

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.koin.compose)
                implementation(libs.koin.android)
            }
        }

        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

                implementation(libs.kermit)
                implementation(libs.bundles.ktor)
                api(libs.shareResources)
                implementation(libs.koin.core)
                implementation(libs.androidx.datastore.preferences.core)

                api("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
                api("dev.icerock.moko:mvvm-flow:0.16.1") // api mvvm-core, CFlow for native and binding extensions
                api("dev.icerock.moko:mvvm-flow-resources:0.16.1") // api mvvm-core, moko-resources, extensions for Flow with moko-resources
                api("dev.icerock.moko:kswift-runtime:0.6.1") // if you want use annotations

                // Update: To use this, you need to set `isStatic = true` in your
                // cocopods framework. BUT, then MOKO breaks, because MOKO MVVM
                // needs to use a dynamic framework.
                implementation("dev.gitlive:firebase-firestore:1.8.0")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.ios)
            }
        }
    }
}

android {
    namespace = "com.santansarah.kmmfirebasemessaging"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.santansarah.kmmfirebasemessaging"
    multiplatformResourcesClassName = "SharedRes"
}

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
}












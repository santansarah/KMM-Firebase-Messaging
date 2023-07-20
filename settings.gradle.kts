pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        //jcenter()
        //maven { url = uri("https://dl.bintray.com/aakira/maven") }
    }
}

rootProject.name = "KMM_Firebase_Messaging"
include(":androidApp")
include(":shared")
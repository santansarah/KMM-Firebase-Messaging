package com.santansarah.kmmfirebasemessaging

class AndroidPlatform : Platform {
    override val name: String = "android"
}

actual fun getPlatform(): Platform = AndroidPlatform()
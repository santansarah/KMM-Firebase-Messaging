package com.santansarah.kmmfirebasemessaging

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package com.santansarah.kmmfirebasemessaging.domain

enum class LinkScreen {
    SIGNIN,
    UNKNOWN
}

object AppDeepLinks {
    private val APP_DEEP_LINKS = mapOf(
        LinkScreen.SIGNIN to "kmm://signin"
    )

    fun getByLink(fullDeepLink: String): LinkScreen {
        val currentPair = APP_DEEP_LINKS
            .filter { it.value == fullDeepLink }.iterator().next()

        return if (currentPair.value.isNotBlank()) currentPair.key else LinkScreen.UNKNOWN
    }
}

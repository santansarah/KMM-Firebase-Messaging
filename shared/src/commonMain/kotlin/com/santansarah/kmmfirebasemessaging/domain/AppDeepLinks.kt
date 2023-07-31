package com.santansarah.kmmfirebasemessaging.domain

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

enum class AppDeepLink {
    SIGNIN,
    UNKNOWN;

    @OptIn(ExperimentalObjCName::class)
    @ObjCName(swiftName = "utils")
    companion object {
        private val APP_DEEP_LINKS = mapOf(
            SIGNIN to "kmm://signin"
        )

        fun typeFromPath(fullDeepLink: String)= APP_DEEP_LINKS.entries
            .find { it.value == fullDeepLink }?.key ?: UNKNOWN

        fun pathFromType(linkType: AppDeepLink) = APP_DEEP_LINKS[linkType] ?: ""
    }
}


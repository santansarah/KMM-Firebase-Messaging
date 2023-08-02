package com.santansarah.kmmfirebasemessaging.utils

import io.ktor.util.generateNonce


object SignUpHelper {
    const val SIGN_UP_REQUEST_CODE = "1001"
    fun getNonce() = generateNonce()
}
package com.santansarah.kmmfirebasemessaging.android

import android.app.Application
import com.santansarah.kmmfirebasemessaging.di.initKoin

class SanTanShop: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}


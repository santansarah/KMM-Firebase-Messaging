package com.santansarah.kmmfirebasemessaging.android

import android.app.Application
import com.santansarah.kmmfirebasemessaging.di.initKoin
import org.koin.android.ext.koin.androidContext

class SanTanShop: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SanTanShop)
        }
    }
}


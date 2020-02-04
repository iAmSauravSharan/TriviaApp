package com.sauravsharan.appscrip

import android.app.Application
import android.os.StrictMode
import timber.log.Timber

//import timber.log.Timber

class AppScrip: Application() {

    override fun onCreate() {

        if(BuildConfig.DEBUG) {
            enableStrictMode()
        }

        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )

    }

}
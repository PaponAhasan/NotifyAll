package com.example.notifyall

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NotifyAll : Application() {
    companion object{

    }
    override fun onCreate() {
        super.onCreate()
    }
}
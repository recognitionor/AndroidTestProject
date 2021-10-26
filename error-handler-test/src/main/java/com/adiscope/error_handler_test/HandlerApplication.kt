package com.adiscope.error_handler_test

import androidx.multidex.MultiDexApplication

class HandlerApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Thread.getDefaultUncaughtExceptionHandler() ?: return
        Thread.setDefaultUncaughtExceptionHandler(
            ExceptionHandler(this)
        )
    }
}
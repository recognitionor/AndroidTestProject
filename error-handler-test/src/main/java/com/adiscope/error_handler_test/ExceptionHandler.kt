package com.adiscope.error_handler_test

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlin.system.exitProcess

class ExceptionHandler(application: Application) : Thread.UncaughtExceptionHandler {

    var activity: Activity? = null

    init {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                activity = p0
                Log.d("jhlee", "onActivityCreated : " + p0.componentName)
            }

            override fun onActivityStarted(p0: Activity) {
                Log.d("jhlee", "onActivityStarted : " + p0.componentName)
            }

            override fun onActivityResumed(p0: Activity) {
                Log.d("jhlee", "onActivityResumed : " + p0.componentName)
            }

            override fun onActivityPaused(p0: Activity) {
                Log.d("jhlee", "onActivityPaused : " + p0.componentName)

            }

            override fun onActivityStopped(p0: Activity) {
                Log.d("jhlee", "onActivityStopped : " + p0.componentName)
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                Log.d("jhlee", "onActivitySaveInstanceState : " + p0.componentName)
            }

            override fun onActivityDestroyed(p0: Activity) {
                Log.d("jhlee", "onActivityDestroyed : " + p0.componentName)
            }

        })
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        Log.d("jhlee", "uncaughtException")
        activity?.let {
            activity?.startActivity(Intent(it, StartActivity::class.java))
        }

        exitProcess(-1)

    }
}

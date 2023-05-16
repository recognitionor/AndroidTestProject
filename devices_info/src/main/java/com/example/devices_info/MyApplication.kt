package com.example.devices_info

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.d("jhlee", "onActivityCreated : ${activity.componentName}" )
            }

            override fun onActivityStarted(activity: Activity) {
                Log.d("jhlee", "onActivityCreated : ${activity.componentName}" )
            }

            override fun onActivityResumed(activity: Activity) {
                Log.d("jhlee", "onActivityResumed : ${activity.componentName}" )
            }

            override fun onActivityPaused(activity: Activity) {
                Log.d("jhlee", "onActivityPaused : ${activity.componentName}" )
            }

            override fun onActivityStopped(activity: Activity) {
                Log.d("jhlee", "onActivityStopped : ${activity.componentName}" )
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.d("jhlee", "onActivitySaveInstanceState : ${activity.componentName}" )
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.d("jhlee", "onActivityDestroyed : ${activity.componentName}" )
            }

        })
    }
}
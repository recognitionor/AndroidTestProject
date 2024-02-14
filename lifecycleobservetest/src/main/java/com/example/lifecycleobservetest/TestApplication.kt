package com.example.lifecycleobservetest

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class TestApplication : Application(), LifecycleOwner {

    private var mLifecycleRegistry: LifecycleRegistry? = null
    override fun onCreate() {
        super.onCreate()
        Log.d("jhlee", "onCreate")
        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry!!.addObserver(MyObserve())

        lifecycle.addObserver(MyObserve())
    }

    override val lifecycle: Lifecycle = object : Lifecycle() {
        override val currentState: State
            get() {
                Log.d("jhlee", "getState")
                return State.STARTED
            }


        override fun addObserver(observer: LifecycleObserver) {
            Log.d("jhlee", "addObserver")
        }

        override fun removeObserver(observer: LifecycleObserver) {
            Log.d("jhlee", "removeObserver")
        }

    }

}
package com.example.lifecycleobservetest

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry


class MainActivity : Activity() {

    private var mLifecycleRegistry: LifecycleRegistry? = null
    private val lifecycle: Lifecycle = object : Lifecycle() {
        override fun addObserver(observer: LifecycleObserver) {
            // Observer 등록 로직
        }

        override fun removeObserver(observer: LifecycleObserver) {
            // Observer 제거 로직
        }

        override val currentState: State
            get() = State.STARTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 라이프사이클 객체를 컴포넌트에 연결
        lifecycle.addObserver(MyObserve())
    }


    private class MyObserver : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            when (event) {
                Lifecycle.Event.ON_CREATE -> {}
                Lifecycle.Event.ON_START -> {}
                else -> {}
            }
        }
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // 라이프사이클 객체 생성
//        mLifecycleRegistry = LifecycleRegistry(this)
//        mLifecycleRegistry!!.addObserver(MyObserve())
//
//        // 라이프사이클 객체를 컴포넌트에 연결
//
//        // 라이프사이클 객체를 컴포넌트에 연결
//        lifecycle.addObserver(MyObserve())
//
//        val manager = TestManager()
//        manager.init(this@MainActivity)
//
//
//    }
}
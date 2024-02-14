package com.example.lifecycleobservetest

import androidx.lifecycle.LifecycleRegistry

class TestManager {

    private var mLifecycleRegistry: LifecycleRegistry? = null

    fun init(ctx: MainActivity) {
        // 라이프사이클 객체 생성
//        mLifecycleRegistry = LifecycleRegistry(ctx)
//        mLifecycleRegistry!!.addObserver(MyObserve())

        // 라이프사이클 객체를 컴포넌트에 연결

        // 라이프사이클 객체를 컴포넌트에 연결
    }
}
package com.example.devices_info

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 패키지 매니저 가져오기
        val packageManager = packageManager

        // 설치된 앱의 리스트 가져오기
        val installedPackages = packageManager.getInstalledPackages(0)

        for (packageInfo in installedPackages) {
            // 앱의 패키지 이름 출력
            val packageName = packageInfo.packageName
            Log.d("jhlee", "packageName : $packageName")
            println("Package Name: $packageName")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    Log.d("jhlee", "onActivityCreated1 : ${activity.componentName}" )
                }

                override fun onActivityStarted(activity: Activity) {
                    Log.d("jhlee", "onActivityCreated1 : ${activity.componentName}" )
                }

                override fun onActivityResumed(activity: Activity) {
                    Log.d("jhlee", "onActivityResumed1 : ${activity.componentName}" )
                }

                override fun onActivityPaused(activity: Activity) {
                    Log.d("jhlee", "onActivityPaused1 : ${activity.componentName}" )
                }

                override fun onActivityStopped(activity: Activity) {
                    Log.d("jhlee", "onActivityStopped1 : ${activity.componentName}" )
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                    Log.d("jhlee", "onActivitySaveInstanceState1 : ${activity.componentName}" )
                }

                override fun onActivityDestroyed(activity: Activity) {
                    Log.d("jhlee", "onActivityDestroyed1 : ${activity.componentName}" )
                }
            })
        }
        startActivity(Intent(this, TestActivity::class.java))
    }
}
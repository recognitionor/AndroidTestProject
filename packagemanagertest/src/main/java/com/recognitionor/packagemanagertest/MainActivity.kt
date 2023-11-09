package com.recognitionor.packagemanagertest

import android.app.Activity
import android.content.pm.PackageManager
import android.content.pm.PackageManager.ApplicationInfoFlags
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi


class MainActivity : Activity() {


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.txt)
        val packageManager = packageManager

        // 모든 설치된 앱 목록 가져오기
        // 패키지 매니저의 getInstalledApplications 메서드를 사용
        // GET_META_DATA 플래그를 사용하여 앱 이름 및 아이콘 등의 추가 정보를 얻을 수 있음

        // 모든 설치된 앱 목록 가져오기
        // 패키지 매니저의 getInstalledApplications 메서드를 사용
        // GET_META_DATA 플래그를 사용하여 앱 이름 및 아이콘 등의 추가 정보를 얻을 수 있음
//        val flags = PackageManager.MATCH_UNINSTALLED_PACKAGES // 또는 다른 플래그
        val flags: ApplicationInfoFlags = ApplicationInfoFlags.of(1.toLong())

        val sb = StringBuilder()
        for (app in packageManager.getInstalledApplications(flags)) {
            // 앱 이름과 패키지 이름을 출력
            val appName = packageManager.getApplicationLabel(app) as String
            val packageName = app.packageName
            sb.append("appName : $appName \npackageName : $packageName \n")
        }

        tv.text = sb.toString()
    }
}
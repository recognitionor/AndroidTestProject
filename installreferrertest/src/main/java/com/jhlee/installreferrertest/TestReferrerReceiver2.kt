package com.jhlee.installreferrertest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class TestReferrerReceiver2 : BroadcastReceiver() {

    /*
    * adb shell am broadcast -a com.android.vending.INSTALL_REFERRER -n com.jhlee.installreferrertest/com.jhlee.installreferrertest.TestReferrerReceiver2 --es "referrer" "test"
    * adb shell am broadcast -a com.android.vending.INSTALL_REFERRER -n {본인의 패키지 명}/{본인의 브로드케스트 리시버 명 전체 경로 기입} --es "referrer" "test"
    * 해당 로직을 테스트 하려면 cmd 창에서 위 명령어를 입력 해주면 된다.
    *
    * */
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("jhlee", "onReceive2 : ${intent?.getStringExtra("referrer")}")
        Thread {
            var count = 0
            while (count < 100) {
                count++;
                Thread.sleep(1000)
                Log.d("jhlee", "onReceive2 ${Thread.currentThread().name}")
            }
        }.start()
    }
}
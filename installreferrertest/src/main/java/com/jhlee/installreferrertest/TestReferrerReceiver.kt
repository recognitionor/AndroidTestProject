package com.jhlee.installreferrertest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class TestReferrerReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("jhlee", "onReceive")
        Log.d("jhlee", "onReceive : ${intent?.getStringExtra("referrer")}")
    }
}
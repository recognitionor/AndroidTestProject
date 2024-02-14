package com.nhnace.contentprovidertest

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn).setOnClickListener {
            val contentUri = Uri.parse("content://com.nhnace.contentprovidertest.ContentProviderTest")
            val cursor =
                contentResolver.query(contentUri, null, null, null, null)
            Log.d("jhlee", "${cursor == null}")

            Intent().apply {
                this.component = ComponentName(
                    "com.nhnace.contentprovidertest",
                    "com.nhnace.contentprovidertest.TestActivity"
                )

                startActivity(this)
            }
        }




    }
}
package com.jhlee.deeplinktest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<WebView>(R.id.webview).loadUrl("file:///android_asset/test.html")
        findViewById<Button>(R.id.btn).setOnClickListener {
            try {

                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("adlib://nhnace.com/test")).apply {
                    setPackage("com.jhlee.deeplinktest")
                })
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "test", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
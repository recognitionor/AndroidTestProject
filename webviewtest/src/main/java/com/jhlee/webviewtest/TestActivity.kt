package com.jhlee.webviewtest

import android.os.Bundle
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("jhlee", "TestActivity_onCreate")

        setContentView(R.layout.activity_main2) // layout.xml 파일 로드

        val webView: WebView = findViewById(R.id.webview)

        webView.settings.javaScriptEnabled = true


        webView.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?, request: WebResourceRequest?
            ): WebResourceResponse? {
                val url = request?.url?.toString()


                Log.d("jhlee", url ?: "")
                return super.shouldInterceptRequest(view, request)
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Log.d("jhlee", "onConsoleMessage : " + consoleMessage.message().toString())
                return super.onConsoleMessage(consoleMessage)
            }
        }

//        val baseUrl = "file:///android_asset/"
        val baseUrl = ""
        val inputStream = assets.open("test2.html")
        val htmlContent = inputStream.bufferedReader().use { it.readText() }
        webView.loadDataWithBaseURL(
            baseUrl, htmlContent, "text/html", "UTF-8", null
        )
    }
}
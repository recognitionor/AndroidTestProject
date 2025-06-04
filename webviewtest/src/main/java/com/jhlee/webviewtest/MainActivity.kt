package com.jhlee.webviewtest

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors

class MainActivity : Activity(), CompoundButton.OnCheckedChangeListener {

    private var webView: WebView? = null
    private lateinit var frame: FrameLayout
    private lateinit var cbCreateWindow: CheckBox
    private lateinit var cbShouldOverrideUrlLoading: CheckBox
    private lateinit var cbJavaScriptEnabled: CheckBox
    private lateinit var cbJavaScriptCanOpenWindowsAutomatically: CheckBox
    private lateinit var cbSupportMultipleWindows: CheckBox

    private lateinit var log: TextView
    private lateinit var btnBack: Button
    private lateinit var btnLoad: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var rbExternal: RadioButton
    private lateinit var rbAnother: RadioButton
    private lateinit var rbInWebView: RadioButton
    private var selectRadio: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("jhlee", "MainActivity ~~~~~~~ : $this");
        setContentView(R.layout.activity_main)
        frame = findViewById(R.id.frame)
        btnBack = findViewById(R.id.btn_back)
        btnLoad = findViewById(R.id.btn_load)
        log = findViewById(R.id.log)
        radioGroup = findViewById(R.id.radio_group)
        cbCreateWindow = findViewById(R.id.check_box_WebChromeClient_onCreateWindow)
        cbShouldOverrideUrlLoading =
            findViewById(R.id.check_box_WebViewClient_shouldOverrideUrlLoading)
        cbJavaScriptEnabled = findViewById(R.id.check_box_JavaScriptEnabled)
        cbJavaScriptCanOpenWindowsAutomatically =
            findViewById(R.id.check_box_setJavaScriptCanOpenWindowsAutomatically)
        cbSupportMultipleWindows = findViewById(R.id.check_box_setSupportMultipleWindows)
        rbExternal = findViewById(R.id.external)
        rbAnother = findViewById(R.id.another)
        rbInWebView = findViewById(R.id.in_webview)


        cbShouldOverrideUrlLoading.setOnCheckedChangeListener(this)
        cbCreateWindow.setOnCheckedChangeListener(this)
        cbJavaScriptEnabled.setOnCheckedChangeListener(this)
        cbSupportMultipleWindows.setOnCheckedChangeListener(this)
        cbJavaScriptCanOpenWindowsAutomatically.setOnCheckedChangeListener(this)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            selectRadio = when (checkedId) {
                rbAnother.id -> {
                    1
                }

                rbInWebView.id -> {
                    2
                }

                else -> {
                    0
                }
            }
            Log.d("jhlee", "check : $selectRadio")
        }


        btnLoad.setOnClickListener {
            load()
        }
        btnBack.setOnClickListener {
            webView?.goBack()
        }
    }

    private fun load() {
        log.text = ""
        webView = WebView(this)
        frame.removeAllViews()
        frame.addView(webView)
        val setting = webView?.settings
        setting?.javaScriptEnabled = cbJavaScriptEnabled.isChecked == true
        setting?.javaScriptCanOpenWindowsAutomatically =
            cbJavaScriptCanOpenWindowsAutomatically.isChecked == true
        setting?.setSupportMultipleWindows(cbSupportMultipleWindows.isChecked)
        val html =
            BufferedReader(InputStreamReader(this.assets.open("test2.html"))).lines().collect(
                Collectors.joining("\n")
            )

        if (cbShouldOverrideUrlLoading.isChecked) {
            webView?.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?, request: WebResourceRequest?
                ): Boolean {
                    log.text = "call : shouldOverrideUrlLoading"
                    Log.d("jhlee", "shouldOverrideUrlLoading request.url : ${request?.url}")
                    Log.d(
                        "jhlee",
                        "shouldOverrideUrlLoading request?.isRedirect : ${request?.isRedirect}"
                    )
                    Log.d(
                        "jhlee",
                        "shouldOverrideUrlLoading request?.hasGesture : ${request?.hasGesture()}"
                    )

                    val url = request?.url?.toString()
                    if (request?.isForMainFrame == true && !request.isRedirect && request.hasGesture()) {
                        // 여기서만 원하는 처리를 해줌
                        Log.d("jhlee", "shouldOverrideUrlLoading!! : $selectRadio")
                        when (selectRadio) {
                            1 -> {
                                Log.d("jhlee", "shouldOverrideUrlLoading : 111")
                                val intent = Intent(this@MainActivity, TestActivity::class.java)
                                val bundle = Bundle()
                                bundle.putString("url", url)
                                intent.putExtras(bundle) // <- 이렇게!
                                startActivity(intent)
                                return true
                            }

                            2 -> {
                                Log.d("jhlee", "shouldOverrideUrlLoading : 222")
                                return false
                            }

                            else -> {
                                Log.d("jhlee", "shouldOverrideUrlLoading : 333")
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                startActivity(intent)
                                return true
                            }
                        }
                    }
                    return false
                }
            }
        }

        if (cbCreateWindow.isChecked) {
            webView?.webChromeClient = object : WebChromeClient() {

                override fun onCreateWindow(
                    view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?
                ): Boolean {
                    log.text = "call : onCreateWindow"
                    Log.d("jhlee", "onCreateWindow")
                    val activity = this@MainActivity
                    val popupWebView = WebView(activity)

                    // URL 캐치용 WebViewClient 등록!
                    popupWebView.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?, request: WebResourceRequest?
                        ): Boolean {
                            Log.d("jhlee", "onCreateWindow_shouldOverrideUrlLoading")
                            val url = request?.url?.toString()
                            when (selectRadio) {
                                1 -> {
                                    val intent = Intent(this@MainActivity, TestActivity::class.java)
                                    intent.putExtra("url", url)
                                    startActivity(intent)
                                    return true // 외부에서 열기
                                }
                                2 -> {
                                    // **팝업 웹뷰 안에서 열기**
                                    return false
                                }
                                else -> {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    startActivity(intent)
                                    return false // 외부 브라우저에서 열기
                                }
                            }
                        }
                    }
                    frame.removeAllViews()
                    frame.addView(popupWebView)
                    // 팝업용 WebView를 "운반"해줌 (WebViewTransport)
                    (resultMsg?.obj as? WebView.WebViewTransport)?.webView = popupWebView
                    resultMsg?.sendToTarget()
                    return true
                }
            }
        }
        webView?.loadDataWithBaseURL("", html, "text/html", "utf-8", null)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        load()
    }
}
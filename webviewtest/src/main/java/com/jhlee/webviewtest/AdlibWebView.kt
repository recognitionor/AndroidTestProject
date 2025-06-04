package com.jhlee.webviewtest

import android.content.Context
import android.media.AudioManager
import android.media.MediaRouter
import android.os.Build
import android.os.Message
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import java.lang.reflect.Field
import java.util.Locale



/**
 * see http://stackoverflow.com/questions/3130654/memory-leak-in-webview and http://code.google.com/p/android/issues/detail?id=9375
 * Note that the bug does NOT appear to be fixed in android 2.2 as romain claims
 *
 *
 * Also, you must call [.destroy] from your activity's onDestroy method.
 */
class AdlibWebView(context: Context) :
    WebView(context.applicationContext) {

    var isFirstPageFinished: Boolean = false
        private set

    init {
        scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY

        // 스크롤바를 안보이게 설정
        this.isVerticalScrollBarEnabled = false
        this.isHorizontalScrollBarEnabled = false

        // 화면내에 컨텐츠 크기가 줄어들기 위해 필요
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        // WebChromClient 의 onCreateWindow() 호출 받기위해 필요
        settings.setSupportMultipleWindows(true)
        settings.defaultTextEncodingName = "utf-8"
        settings.allowUniversalAccessFromFileURLs = true

        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                return super.onConsoleMessage(consoleMessage)
            }
        }
    }

    override fun loadDataWithBaseURL(
        baseUrl: String?,
        data: String,
        mimeType: String?,
        encoding: String?,
        historyUrl: String?
    ) {
        // MRAID_ENV 삽입
//        String modifiedHtml = data;
//        String mraidEnvScript = "<script>window.MRAID_ENV = " + MRAIDUtilManager.getInstance().createMraidEnv(this.getContext()) + ";</script>";
//        String replaceMraidEnvStr = "<!--(ADLIB_SDK_MRAID_ENV)-->";
//        String replaceMraidJsStr = "<!--(ADLIB_SDK_MRAID_JS)-->";
//
//        try {
//            if (modifiedHtml.contains(replaceMraidEnvStr)) {
//                modifiedHtml = modifiedHtml.replace(replaceMraidEnvStr, mraidEnvScript);
//            }
//        } catch (Exception e) {
//            // ignored
//        }
//
//        // mraid.js 삽입
//        try {
//            String mraidjs = MRAIDUtilManager.getInstance().getMraidJs(getContext());
//            if (modifiedHtml.contains(replaceMraidJsStr)) {
//                modifiedHtml = modifiedHtml.replace(replaceMraidJsStr, mraidjs);
//            }
//        } catch (Exception e) {
//            // ignored
//        }
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl)
    }

    override fun destroy() {
        super.destroy()
        try {
            if (sConfigCallback != null) sConfigCallback!![null] =
                null
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun enableMraid(placementType: String) {
        isFirstPageFinished = true
        // Placement type 지정
        val setPlacementTypeScript =
            "javascript:window.mraid.util.setPlacementType('$placementType')"
        evaluateJavascript(setPlacementTypeScript, null)
        // Mraid 초기화 setScreenSize

        // Mraid 초기화 setMaxSize
        mraidSetDefaultPosition()

        // Mraid 초기화 setCurrentAppOrientation

        // Mraid 초기화 setCurrentAppOrientation
        val stateChangeEventScript = "javascript:window.mraid.util.stateChangeEvent('default')"
        evaluateJavascript(stateChangeEventScript, null)
        val readyEventScript = "javascript:window.mraid.util.readyEvent()"
        evaluateJavascript(readyEventScript, null)


        val volumePercentage = volumePercent
        val newAudioVolumeVal =
            String.format(Locale.ROOT, "{\"volumePercentage\":%.1f}", volumePercentage)
        evaluateJavascript(
            String.format(
                "javascript:window.mraid.util.audioVolumeChangeEvent(%s)",
                newAudioVolumeVal
            ), null
        )

        val mediaRouter = context.getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter
    }

    private val volumePercent: Double
        get() {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            return (100.0 * currentVolume) / maxVolume
        }


    fun mraidSetDefaultPosition() {
        try {
            val defaultPosition = defaultPosition
            val getDefaultPositionScript =
                "javascript:window.mraid.util.setDefaultPosition(" + defaultPosition[0] + ", " + defaultPosition[1] + ", " + defaultPosition[2] + ", " + defaultPosition[3] + ")"
            evaluateJavascript(getDefaultPositionScript, null)
        } catch (ignored: Exception) {
        }
    }

    private val defaultPosition: IntArray
        get() {
            val defaultPosition = IntArray(4)
            // 1. 화면에서의 x, y 좌표를 가져옴
            val location = IntArray(2) // 좌표를 저장할 임시 배열
            this.getLocationOnScreen(location)
            defaultPosition[0] = location[0] // x 좌표
            defaultPosition[1] = location[1] // y 좌표
            // 2. View의 너비와 높이를 가져옴

            return defaultPosition
        }

    fun onPageFinished(url: String?) {
        val javascript = "javascript:window.mraid.util.pageFinished()"
        // 최초 로드 시점에만 호출
        if (!isFirstPageFinished) {
            this.evaluateJavascript(javascript, null)
        }
    }

    companion object {
        private var sConfigCallback: Field? = null

        init {
            try {
                sConfigCallback =
                    Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback")
            } catch (e: Exception) {
                // ignored
            }
        }
    }
}
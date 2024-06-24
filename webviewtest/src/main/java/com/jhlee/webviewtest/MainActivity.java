package com.jhlee.webviewtest;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.*;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;

    private MRAIDImplementation mraidImpl;

    private final Boolean isCall = false;

    private String createMraidEnv() {
        JSONObject obj = new JSONObject();
        try {

            obj.put("version", "1.0.3");
            obj.put("sdk", "NHNACE_ADLIB");
            obj.put("sdkVersion", "1.0.3");
            obj.put("appId", getPackageName());
            obj.put("adid", "1111");
            obj.put("limitedAdTracking", true);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return obj.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("jhlee", "onCreate");
        findViewById(R.id.test_btn).setOnClickListener(v -> {
            Log.d("jhlee", "test");
            String test = "javascript:window.mraid.getVendor()";
            mWebView.evaluateJavascript(test, null);
        });
        mWebView = findViewById(R.id.webview);
        mraidImpl = new MRAIDImplementation(mWebView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // JavaScript 활성화
        webSettings.setLoadsImagesAutomatically(true); // 이미지 자동 로드 활성화
        webSettings.setDomStorageEnabled(true); // DOM 스토리지 활성화
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        // MRAID 스크립트 로드
//        Log.d("jhlee", "getAdContent : " + getAdContent());
        mWebView.loadDataWithBaseURL("file:///android_asset/", getAdContent(), "text/html", "UTF-8", null);
//        mWebView.loadUrl("file:///android_asset/test2.html");

        // 광고 컨텐츠 로드
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("jhlee", consoleMessage.message() + " -- From line "
                        + consoleMessage.lineNumber() + " of "
                        + consoleMessage.sourceId());
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("jhlee", consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("jhlee", "shouldOverrideUrlLoading : " + request.getUrl());
                String url = request.getUrl().toString();
                Log.d("jhlee", "url : " + url);
                if (url.startsWith("javascript:")) {
                    return false;
                }
                if (MRAIDUtils.isMRAIDUrl(url)) {
                    Log.d("jhlee", request.getUrl().getHost() + "~!");
                    switch (request.getUrl().getHost()) {
                        case "initMraidEnv": {
//                            mWebView.evaluateJavascript(String.format("javascript:window.mraid.initMraidEnv('%s')", createMraidEnv()), null);
//                            mWebView.evaluateJavascript(createMraidEnv(), null);
                            Log.d("jhlee", request.getUrl().getHost());
                        }

                        case "test": {
                            Log.d("jhlee", "test!!!!");
                        }

                        case "enable": {
                            Log.d("jhlee", "enable!!!!");
                        }
                        case "open": {

                        }
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("jhlee", "onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 여기에 있어야 하는데 일단 로그
                Log.d("jhlee", "onPageFinished");
                String str = "ready";
//                String javascript = "javascript:window.mraid.util.pageFinished()";
//                mWebView.evaluateJavascript(String.format("javascript:window.mraid.util.stateChangeEvent('%s')", str), null);
//                mWebView.evaluateJavascript(javascript, null);
//                mWebView.evaluateJavascript("javascript:window.mraid.util.readyEvent()", null);
//                Log.d("jhlee", "onPageFinished : " + isCall + " ," + url);

                // JavaScript 함수 호출 타이밍 조정
//                String test = "javascript:(function() { " + "if (typeof mraid !== 'undefined') { " + "window.mraid.open('https://www.nhnace.com/bannerinfo'); " + "} else { " + "console.log('MRAID not ready'); " + "} " + "})()";
//                view.evaluateJavascript(test, null);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d("jhlee", "onLoadResource : " + url);
            }
        });

    }

    // 광고 컨텐츠를 반환하는 메서드
    private String getAdContent() {
        try {
            String test = new BufferedReader(new InputStreamReader(this.getAssets().open("test2.html"))).lines().collect(Collectors.joining("\n"));
            return test;
//            Log.d("jhlee", "test : " + test);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

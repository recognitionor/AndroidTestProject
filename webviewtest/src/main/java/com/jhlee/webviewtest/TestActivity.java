package com.jhlee.webviewtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

public class TestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("jhlee", "TestActivity");
        setContentView(R.layout.activity_main);
        WebView webview = findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("jhlee", "shouldOverrideUrlLoading");
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
                Log.d("jhlee", "onPageFinished");
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d("jhlee", "onLoadResource");
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.d("jhlee", "shouldInterceptRequest");
                return super.shouldInterceptRequest(view, request);
            }
        });
        String htmlData = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>\n" +
                "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n" +
                "<meta http-equiv='Cache-Control' content='no-cache, no-store, must-revalidate'>\n" +
                "<meta http-equiv='Pragma' content='no-cache'>\n" +
                "<meta http-equiv='Expires' content='-1'>\n" +
                "<style> *{margin:0;padding:0;}html, body{width:100%;height:100%;padding:0;margin:0;border:0;overflow:hidden;}a{text-decoration:none;outline:0;-webkit-tap-highlight-color:rgba(0,0,0,0);-webkit-tap-highlight-color:transparent;}img{border:0;}</style>\n" +
                "<script>function customScaleThisScreen() { var contentWidth = document.body.scrollWidth, windowWidth = window.innerWidth,newScale = windowWidth / contentWidth; document.body.style.zoom = newScale; } window.onload=customScaleThisScreen;</script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<a href='https://acetrader.co.kr/'>\n" +
                "<img src='https://alpha-gwx.adlibr.com/resources/img/sample/banner_640x100.png' style='z-index:1;max-width:100%;' />\n" +
                "</a>\n" +
                "<a href='https://www.google.com'>Go to Google</a>\n" +  // Added link to test shouldOverrideUrlLoading
                "</body>\n" +
                "</html>";
//        webview.loadUrl("https://naver.com");
        webview.loadDataWithBaseURL("", htmlData, "text/html", "UTF-8", null);

    }
}

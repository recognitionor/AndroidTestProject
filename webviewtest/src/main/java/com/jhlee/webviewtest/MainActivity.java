package com.jhlee.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webview = findViewById(R.id.webview);
        webview.loadDataWithBaseURL("", "<!DOCTYPE html>\n" +
                "                                                                                                    <html>\n" +
                "                                                                                                    <head>\n" +
                "                                                                                                    <meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>\n" +
                "                                                                                                    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n" +
                "                                                                                                    <meta http-equiv='Cache-Control' content='no-cache, no-store, must-revalidate'>\n" +
                "                                                                                                    <meta http-equiv='Pragma' content='no-cache'>\n" +
                "                                                                                                    <meta http-equiv='Expires' content='-1'>\n" +
                "                                                                                                    <style> *{margin:0;padding:0;}html, body{width:100%;height:100%;padding:0;margin:0;border:0;overflow:hidden;}a{text-decoration:none;outline:0;-webkit-tap-highlight-color:rgba(0,0,0,0);-webkit-tap-highlight-color:transparent;}img{border:0;}</style>\n" +
                "                                                                                                    <script>function customScaleThisScreen() { var contentWidth = document.body.scrollWidth, windowWidth = window.innerWidth,newScale = windowWidth / contentWidth; document.body.style.zoom = newScale; } window.onload=customScaleThisScreen;</script>\n" +
                "                                                                                                    </head>\n" +
                "                                                                                                    <body>\n" +
                "                                                                                                    <a href='https://acetrader.co.kr/'>\n" +
                "                                                                                                    <img src='https://gwx.adlibr.com/resources/img/sample/banner_640x100.png' style='z-index:1;max-width:100%;' />\n" +
                "                                                                                                    </a>\n" +
                "                                                                                                    </body>\n" +
                "                                                                                                    </html>", "text/html", "utf-8", null);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("jhlee", "shouldOverrideUrlLoading1 : " + request.getUrl());
                return true;
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("jhlee", "shouldOverrideUrlLoading2 " + url);
                return true;
            }
        });
    }
}
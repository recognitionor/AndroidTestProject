package com.jhlee.webviewtest;

import android.webkit.*;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webview = findViewById(R.id.webview);
        webview.setWebChromeClient(new WebChromeClient(){

        });
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("jhlee", "onPageFinished : " + view.getSettings().getUserAgentString());
            }
        });
//        String ws = WebSettings.getDefaultUserAgent(this);
//        Log.d("jhlee", ws);
//        webview.loadDataWithBaseURL("", Url.url, "text/html", "utf-8", null);
        webview.loadUrl("https://google.com");


    }
}
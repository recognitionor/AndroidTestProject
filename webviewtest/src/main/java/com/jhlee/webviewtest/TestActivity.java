package com.jhlee.webviewtest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestActivity extends Activity {
    private WebView webView;
    private OnBackInvokedCallback backInvokedCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("jhlee", "onCreate ");

        FrameLayout frameLayout = new FrameLayout(this);
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
//
        webView.setWebViewClient(new WebViewClient());



        String url = getIntent().getStringExtra("url");
        Log.d("jhlee", "TestActivity.onCreate : " + url);
//
        setContentView(frameLayout);
        frameLayout.addView(webView);
        webView.loadUrl(url);

    }
    @Override
    public void onBackPressed() {
        Log.d("jhlee", "onBackPressed ");
        finish();
    }
}

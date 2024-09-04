package com.jhlee.webviewtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MainActivity2 extends Activity {
    static public JSONObject createMraidEnv(Context ctx) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("version", BuildConfig.VERSION_NAME);
            obj.put("sdk", "NHNACE_ADLIB");
            obj.put("sdkVersion", "1.0.3");
            obj.put("appId", ctx.getPackageName());
//            obj.put("adid", "1111");
//            obj.put("limitedAdTracking", true);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btn = findViewById(R.id.load);
        WebView wv = findViewById(R.id.webview);

        wv.setWebViewClient(new WebViewClient() {

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.d("jhlee", "shouldInterceptRequest");
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("jhlee", "shouldOverrideUrlLoading : " + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("jhlee", "onPageStarted");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("jhlee", "onPageFinished");
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                Log.d("jhlee", "onLoadResource : " + url);
                super.onLoadResource(view, url);
            }
        });
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("jhlee", "log : " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        wv.getSettings().setJavaScriptEnabled(true);
        String html;
        try {
            html = new BufferedReader(new InputStreamReader(this.getAssets().open("test2.html"))).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String mraidEnvScript = "<script>window.MRAID_ENV = " + createMraidEnv(this) + ";</script>";
        String modifiedHtml;
        if (html.toUpperCase().startsWith("<!DOCTYPE")) {
            // DOCTYPE 선언이 있는 경우, 그 뒤에 스크립트 삽입
            int doctypeEndIndex = html.indexOf(">") + 1;
            modifiedHtml = html.substring(0, doctypeEndIndex) + "\n" + mraidEnvScript + html.substring(doctypeEndIndex);
        } else {
            // DOCTYPE 선언이 없는 경우, <html> 태그 앞에 스크립트 삽입
            modifiedHtml = mraidEnvScript + html;
        }

//        String test = "<!DOCTYPE html>\n" + "<script>\n" + "\n" + "    console.log(\"MRAID_ENV ~~~~~~~~~: \");\n" + "    console.log(\"MRAID_ENV 1 : \" + (window.MRAID_ENV === undefined));\n" + "\n" + "</script>\n" + "<head>\n" + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" + "    <meta http-equiv=\"Cache-Control\" content=\"no-cache, no-store, must-revalidate\">\n" + "    <meta http-equiv=\"Pragma\" content=\"no-cache\">\n" + "    <meta http-equiv=\"Expires\" content=\"-1\">\n" + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + "</head>\n" + "<body>\n" + "test~!\n" + "</body>\n" + "\n" + "</html>";

        btn.setOnClickListener(v -> {
            Log.d("jhlee", "modifiedHtml : " + modifiedHtml);
            wv.loadDataWithBaseURL("", modifiedHtml, "text/html", "UTF-8", null);
        });

    }
}

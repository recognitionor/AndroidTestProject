package com.jhlee.webviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private NonLeakingWebView mWebView;

    private FrameLayout content;

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

    private void getInterWebView() {
        mWebView = new NonLeakingWebView(this);

        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER;
        mWebView.setLayoutParams(p);
        content.addView(mWebView);

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        String test = "<!DOCTYPE html>\n" +
                "\n" +
                "<head>\n" +
                "    <style>\n" +
                "        *,\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            border: 0\n" +
                "        }\n" +
                "\n" +
                "        html,\n" +
                "        body {\n" +
                "            height: 100%%\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            text-align: center;\n" +
                "            overflow-x: hidden;\n" +
                "            overflow-y: hidden;\n" +
                "            background-color: #FFF5F1\n" +
                "        }\n" +
                "\n" +
                "        iframe {\n" +
                "            position: absolute;\n" +
                "            left: 0;\n" +
                "            right: 0;\n" +
                "            top: 0;\n" +
                "            bottom: 0;\n" +
                "            margin: auto\n" +
                "        }\n" +
                "    </style>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <meta http-equiv=\"Cache-Control\" content=\"no-cache, no-store, must-revalidate\">\n" +
                "    <meta http-equiv=\"Pragma\" content=\"no-cache\">\n" +
                "    <meta http-equiv=\"Expires\" content=\"-1\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "test~!\n" +
                "<script>\n" +
                "\n" +
                "    console.log(\"MRAID_ENV ~~~~~~~~~: \");\n" +
                "    console.log(\"MRAID_ENV 1 : \" + (window.MRAID_ENV === undefined));\n" +
                "    console.log(\"MRAID_ENV 2 : \" + MRAID_ENV.sdk);\n" +
                "\n" +
                "</script>\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("jhlee", "consoleMessage : " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // MRAID_ENV 초기화
                Log.d("jhlee", "onPageStarted : " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("jhlee", "onPageFinished : " + url);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.d("jhlee", "shouldInterceptRequest : " + request.getUrl());
                return super.shouldInterceptRequest(view, request);
            }
        });
        String mraidEnvScript = "javascript:window.MRAID_ENV = " + createMraidEnv(this) + ";";
        Log.d("jhlee", "MRAID_ENV is : " + mraidEnvScript);
        mWebView.evaluateJavascript(mraidEnvScript, value -> {
            Log.d("jhlee", "#MRAID_ENV result : " + value);
            mWebView.loadDataWithBaseURL("", test, "text/html", "UTF-8", null);
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content = findViewById(R.id.webview);
        getInterWebView();
    }
}

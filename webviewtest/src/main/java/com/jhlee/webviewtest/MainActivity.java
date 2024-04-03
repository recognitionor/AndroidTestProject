package com.jhlee.webviewtest;

import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;

    private Boolean isCall = false;

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

        mWebView = findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // JavaScript 활성화

        // MRAID 스크립트 로드
        mWebView.loadUrl("file:///android_asset/mraid.js");
        mWebView.loadDataWithBaseURL("file:///android_asset/", getAdContent(), "text/html", "UTF-8", null);
        // 광고 컨텐츠 로드

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("jhlee", "consoleMessage : " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });

        mWebView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void postMessage(String message) {
                Log.d("jhlee", "postMessage");
                // 여기서 message는 MRAID에서 오는 메시지입니다.
                // 예를 들어, MRAID 이벤트 처리를 여기서 할 수 있습니다.
            }
        }, "Android");

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("jhlee", "shouldOverrideUrlLoading : " + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("jhlee", "onPageFinished : " + isCall + " ," + url);
//                createMraidEnv();
//                String test = "javascript:window.mraid.open(\"test~!\")";
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
        // MRAID 광고 마크업 및 스크립트를 포함한 HTML 문자열 반환
        return "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "\t<head>\n" + "\t\t<meta charset=\"utf-8\" />\n" + "\t\t<meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\" />\n" + "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" + "\n" + "\t\t<title></title>\n" + "\t\t<link rel=\"stylesheet\" href=\"css/main.css\" />\n" + "\t\t<link rel=\"icon\" href=\"images/favicon.png\" />\n" + "\n" + "\t\t<script src=\"mraid.js\"></script>\n" + "\t</head>\n" + "\n" + "\t<body>\n" + "\t\t<div id=\"imageContainer\">\n" + "\t\t\t<img src=\"https://nhnace.com/share/img/logo_main_ci.png\" width=\"300\" height=\"250\"/>\n" + "\t\t</div>\t\n" + "\t \n" + "    <script src=\"mraid.js\"></script>\n" + // MRAID 스크립트 로드
                "\t\t\n" + "\t\t<script>\n" + "\t\talert(mraid.getVersion());\n" + "\t\t\n" + "\t\tfunction addEvent(evnt, elem, func) {\n" + "\t\t\tif (elem.addEventListener) { // W3C DOM\n" + "\t\t\t\telem.addEventListener(evnt, func, false);\n" + "\t\t\t} else if (elem.attachEvent) { // IE DOM\n" + "\t\t\t\telem.attachEvent(\"on\" + evnt, func);\n" + "\t\t\t} else { // No much to do\n" + "\t\t\t\telem[evnt] = func;\n" + "\t\t\t}\n" + "\t\t}\n" + "\t\t\n" + "\t\tfunction displayAd()\n" + "\t\t{\n" + "\t\t\t//mraid.addEventListener(\"stateChange\", stateChangeHandler);\n" + "\t\t\t//mraid.addEventListener(\"sizeChange\", sizeChangeHandler);\n" + "\t\t\t//mraid.addEventListener(\"viewableChange\", viewableChangeHandler);\n" + "\t\t\t//mraid.addEventListener(\"error\", errorHandler);\n" + "\t\t\t\n" + "\t\t\tvar adContainer = document.querySelector('#imageContainer');\n" + "\t\t\taddEvent(\"click\", adContainer , function (e) {\n" + "\t\t\t\te.preventDefault();\n" + "\t\t\t\tmraid.open('https://www.nhnace.com/bannerinfo');\n" + "\t\t\t\t\n" + "\t\t\t\treturn false;\n" + "\t\t\t});\n" + "\t\t}\t\n" + "\t\t\n" + "\t\tfunction readyAdPage() {\n" + "\t\t\t mraid.removeEventListener(\"ready\", readyAdPage);\n" + "\n" + "\t\t\tdisplayAd();\n" + "\t\t}\n" + "\t\t\n" + "\t\tfunction detectAdPageStatus() {\n" + "\t\t\tvar success = false;\n" + "\n" + "\t\t\tif (document.readyState === 'complete') {\n" + "\t\t\t\n" + "\t\t\t\tif (typeof (mraid) === \"undefined\") {\n" + "\t\t\t\t\tsetTimeout(detectAdPageStatus, 250);\n" + "\t\t\t\t} else {\n" + "\t\t\t\t\tif (mraid.getState() === 'loading') {\n" + "\t\t\t\t\t\tmraid.addEventListener('ready', readyAdPage);\n" + "\t\t\t\t\t\t\n" + "\t\t\t\t\t\tmraid.addEventListener(error,function(message,action){\n" + "\t\t\t\t\t\t\talert('Error [' + action + ']' + message);\n" + "\t\t\t\t\t\t})\n" + "\t\t\t\t\t} else if (mraid.getState() === 'default') {\n" + "\t\t\t\t\t\tdisplayAd();\n" + "\t\t\t\t\t}\n" + "\t\t\t\t\t\n" + "\t\t\t\t\tsuccess = true;\n" + "\t\t\t\t}\n" + "\t\t\t}\n" + "\t\t\t\n" + "\t\t\treturn success;\t\n" + "\t\t}\n" + "\t\t\n" + "\t\tdetectAdPageStatus();\n" + "\t\t</script>\n" + "\t</body>\n" + "</html>";

//        return "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "    <title>MRAID Ad Example</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "    <h1>MRAID Ad Example</h1>\n" +
//                "    <div id=\"result\"></div>\n" + // 결과를 표시할 곳
//                "    <script src=\"mraid.js\"></script>\n" + // MRAID 스크립트 로드
//                "    <script>\n" +
//                "        console.log(111);\n" + // testPrint 함수 호출
////                "        var result = mraid.testPrint();\n" + // testPrint 함수 호출
////                "        document.getElementById('result').innerHTML = result;\n" + // 결과를 표시할 곳에 결과 삽입
//                "    </script>\n" +
//                "</body>\n" +
//                "</html>\n";
    }


}
